package game.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Grid{
    private final int size;
    private final Dot[][] dots;
    private final ArrayList<Line> lines;
    private final ArrayList<Box> boxes;

    private final Player player1;
    private final Player player2;


    // represents all possible lines in the grid,
    // in a defined order (horizontal lines first, then vertical lines),
    // null if the line do not exist, for now
    private final Line[] state;



    public Grid (int N, boolean bot){
        if (N < 2 || N > 10){
            throw new IllegalArgumentException("Grid size has to be between 2 and 10.");
        }
        size = N;
        dots = new Dot[N][N];
        lines = new ArrayList<>();
        boxes = new ArrayList<>();
        state = new Line[2 * N * (N-1)];

        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                dots[i][j] = new Dot(i, j);
            }
        }

        if (bot){
            Random random = new Random();
            boolean b = random.nextBoolean();
            player1 = new Player(b);
            player2 = new Player(!b);
        }
        else {
            player1 = new Player(false);
            player2 = new Player(false);
        }

    }

    public int getSize(){
        return size;
    }

    public Dot getDot(int x, int y){
        if (x < 0 || x >= size || y < 0 || y >= size){
            return null;
        }
        return dots[x][y];
    }


    // return a line if it exists, else null
    public Line getLine(Dot d1, Dot d2){
        if (d1 == null || d2 == null){
            return null;
        }

        if (d1.getX() > d2.getX() || d1.getY() > d2.getY()){
            Dot temp = d1;
            d1 = d2;
            d2 = temp;
        }

        // we use the state array to get the line
        // so this often used method is O(1)
        return state[DotsToIndex(d1, d2)];
    }


    public Iterator<Line> getLinesIterator() {
        return lines.iterator(); // Retourne un itérateur des lignes
    }

    // return a box if it exists, else null
    public Box getBox(Line top, Line right, Line bottom, Line left){
        for (Box b : boxes){
            if (b.getTop() == top && b.getRight() == right && b.getBottom() == bottom && b.getLeft() == left){
                return b;
            }
        }
        return null;
    }
    public Iterator<Box> getBoxesIterator() {
        return boxes.iterator(); // Retourne un itérateur des boîtes
    }


    public Player getPlayer1(){
        return player1;
    }
    public Player getPlayer2(){
        return player2;
    }



    // if a given line forms 1 or 2 boxes, add the box to the grid and return true
    private boolean checkNewBox(Line l){
        boolean newBox = false;

        Dot d1 = l.getDot1();
        Dot d2 = l.getDot2();
        //horizontal line, we need to check the top and bottom boxes
        if (d1.getY() == d2.getY()){
            //top box
            Dot tl = getDot(d1.getX(), d1.getY() - 1);
            Dot tr = getDot(d1.getX() + 1, d1.getY() - 1);
            Line left = getLine(tl, d1);
            Line right = getLine(tr, d2);
            Line top = getLine(tl, tr);

            if (left != null && right != null && top != null) {
                Box b = new Box(top, right, l, left, l.getOwner());
                boxes.add(b);
                newBox = true;
            }

            //bottom box
            Dot bl = getDot(d1.getX(), d1.getY() + 1);
            Dot br = getDot(d1.getX() + 1, d1.getY() + 1);
            left = getLine(d1, bl);
            right = getLine(d2, br);
            Line bottom = getLine(bl, br);

            if (left != null && right != null && bottom != null) {
                Box b = new Box(l, right, bottom, left, l.getOwner());
                boxes.add(b);
                newBox = true;
            }

        }

        //vertical line, we need to check the left and right boxes
        if (d1.getX() == d2.getX()){
            //left box
            Dot tl = getDot(d1.getX() - 1, d1.getY());
            Dot bl = getDot(d1.getX() - 1, d1.getY() + 1);
            Line top = getLine(tl, d1);
            Line bottom = getLine(bl, d2);
            Line left = getLine(tl, bl);

            if (top != null && bottom != null && left != null) {
                Box b = new Box(top, l, bottom, left, l.getOwner());
                boxes.add(b);
                newBox = true;
            }

            //right box
            Dot tr = getDot(d1.getX()+1, d1.getY() );
            Dot br = getDot(d1.getX() + 1, d1.getY() + 1);
            top = getLine(d1, tr);
            bottom = getLine(d2, br);
            Line right = getLine(tr, br);

            if (top != null && bottom != null && right != null) {
                Box b = new Box(top, right, bottom, l, l.getOwner());
                boxes.add(b);
                newBox = true;
            }

        }

        return newBox;
    }

    // Add the line to the grid if possible and
    // return true if this action completes a new box while adding the box(es) to the grid
    // return false otherwise
    public boolean addLine(Dot d1, Dot d2, Player p){
        if (getLine(d1, d2) != null){
            throw new IllegalArgumentException("Line already exists");
        }

        Line l = new Line(d1, d2, p);
        lines.add(l);
        state[DotsToIndex(l.getDot1(), l.getDot2())] = l;
        return checkNewBox(l);

    }

    public int DotsToIndex(Dot d1, Dot d2){
        int index;
        if (d1.getX() == d2.getX()){
            index = d1.getY() + d1.getX() * (size-1) + size * (size-1);
        }
        else if (d1.getY() == d2.getY()){
            index = d1.getX() + d1.getY() * (size-1);
        }
        else {
            throw new IllegalArgumentException("Invalid line");
        }
        return index;
    }


    public boolean isGameOver(){
        return boxes.size() == (size-1) * (size-1);
    }

    public void resetGrid(){
        lines.clear();
        boxes.clear();
        player1.reset();
        player2.reset();
        if (player1.isBot() || player2.isBot()){
            player1.swapBot();
            player2.swapBot();
        }



    }


}
