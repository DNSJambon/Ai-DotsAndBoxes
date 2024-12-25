package game.controller;
import ai.djl.translate.TranslateException;
import dqn.solver.*;
import game.model.*;
import game.GameView;

import java.awt.*;

public class Controller {
    private final Grid grid;
    private final GameView view;
    private Player currentPlayer;
    private Solver solver;

    public Controller(Grid grid, GameView view){
        this.grid = grid;
        this.view = view;
        view.setController(this);
        this.currentPlayer = grid.getPlayer1();
        try {
            this.solver = new AiSolver(grid.getSize());
        } catch (Exception e) {
            System.out.println("No model found for this grid size.\n setting the bot to play randomly.");
            this.solver = new RandomSolver();
        }
    }

    public void playerClicked(int x, int y){
        // find the closest dot
        Dot d1 = getClosestDot(x, y);

        // find the second-closest dot
        Dot d2 = getSecondClosestDot(d1, x, y);

        // try to add the corresponding line
        boolean box = true;
        try{
            box = grid.addLine(d1, d2, currentPlayer);
            view.repaint();
            view.paintImmediately(view.getBounds());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


        // change the current player
        if (!box){
            currentPlayer = (currentPlayer == grid.getPlayer1()) ? grid.getPlayer2() : grid.getPlayer1();
        }

        // if now the current player is a bot, let it play
        if (currentPlayer.isBot()){
            botTurn();
        }
    }

    public void botTurn(){
        try {
            System.out.println("Bot is thinking...");
            Thread.sleep(700);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // find a line for the bot
        Line l;
        try {
            l = LineFromAction(solver.solve(grid.getState())); // use the solver

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Dot d1 = l.getDot1();
        Dot d2 = l.getDot2();

        // try to add the corresponding line
        boolean box = true;
        try{
            box = grid.addLine(d1, d2, currentPlayer);
            view.repaint();
            view.paintImmediately(view.getBounds());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }



        System.out.println("Bot added the line: " + l);


        if (!grid.isGameOver()) {
            // change the current player
            if (!box) {
                currentPlayer = (currentPlayer == grid.getPlayer1()) ? grid.getPlayer2() : grid.getPlayer1();
            } else {
                botTurn();
            }
        }


    }

    public Dot getClosestDot(int x, int y){
        int cellWidth = view.getCellWidth();
        int cellHeight = view.getCellHeight();
        int dotSize = view.getDotSize();

        int col = (x + cellWidth/2 - dotSize/2) / cellWidth;
        int row = (y + cellHeight/2 - dotSize/2) / cellHeight;

        return grid.getDot(col, row);
    }

    public Dot getSecondClosestDot(Dot d, int x, int y){
        int col = d.getX();
        int row = d.getY();
        int cellWidth = view.getCellWidth();
        int cellHeight = view.getCellHeight();
        int dotSize = view.getDotSize();

        Point top = new Point(col * cellWidth + dotSize/2, (row-1) * cellHeight + dotSize/2);
        Point right = new Point((col+1) * cellWidth + dotSize/2, row * cellHeight + dotSize/2);
        Point bottom = new Point(col * cellWidth + dotSize/2, (row+1) * cellHeight + dotSize/2);
        Point left = new Point((col-1) * cellWidth + dotSize/2, row * cellHeight + dotSize/2);

        int deltaTop = (row > 0) ? (int) Math.sqrt(Math.pow(x-top.getX(), 2) + Math.pow(y-top.getY(), 2)) : Integer.MAX_VALUE;
        int deltaRight = (col < grid.getSize()-1) ? (int) Math.sqrt(Math.pow(x-right.getX(), 2) + Math.pow(y-right.getY(), 2)) : Integer.MAX_VALUE;
        int deltaBottom = (row < grid.getSize()-1) ? (int) Math.sqrt(Math.pow(x-bottom.getX(), 2) + Math.pow(y-bottom.getY(), 2)) : Integer.MAX_VALUE;
        int deltaLeft = (col > 0) ? (int) Math.sqrt(Math.pow(x-left.getX(), 2) + Math.pow(y-left.getY(), 2)) : Integer.MAX_VALUE;

        int closest = Math.min(deltaTop, Math.min(deltaRight, Math.min(deltaBottom, deltaLeft)));
        int col2, row2;

        if (closest == deltaTop){
            col2 = col;
            row2 = row-1;
        }
        else if (closest == deltaRight){
            col2 = col + 1;
            row2 = row;
        }
        else if (closest == deltaBottom){
            col2 = col;
            row2 = row + 1;
        }
        else {
            col2 = col - 1;
            row2 = row;
        }

        return grid.getDot(col2, row2);
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public Line LineFromAction(int action) {
        int nbHorizontalLines = (grid.getSize()-1) * grid.getSize();
        Dot d1;
        Dot d2;
        if (action < nbHorizontalLines){
            int j = action / (grid.getSize()-1);
            int i = action % (grid.getSize()-1);
            d1 = grid.getDot(i, j);
            d2 = grid.getDot(i+1, j);
            return new Line(d1, d2);
        }
        else {
            int i = (action - nbHorizontalLines) / (grid.getSize()-1);
            int j = (action - nbHorizontalLines) % (grid.getSize()-1);
            d1 = grid.getDot(i, j);
            d2 = grid.getDot(i, j+1);
            return new Line(d1, d2);
        }
    }

    public void restartGame() {
        grid.resetGrid();
        currentPlayer = grid.getPlayer1();
    }
}
