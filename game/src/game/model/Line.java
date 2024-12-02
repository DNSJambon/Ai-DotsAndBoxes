package game.model;

public class Line {
    private final Dot d1;
    private final Dot d2;

    private final Player owner;

    // this constructor is used to create a line that is not owned by any player
    public Line (Dot d1, Dot d2){
        this(d1, d2, null);
    }

    protected Line (Dot d1, Dot d2, Player owner){
        // invert the dots if d1 is to the right of d2 or below d2
        if (d1.getX() > d2.getX() || d1.getY() > d2.getY()){
            Dot temp = d1;
            d1 = d2;
            d2 = temp;
        }

        if ((d2.getX() != d1.getX() || d2.getY() != d1.getY() + 1) && (d2.getX() != d1.getX() + 1 || d2.getY() != d1.getY()))
            // the line do not connect two adjacent dots
            throw new IllegalArgumentException("Invalid line");

        else {
            this.d1 = d1;
            this.d2 = d2;
            this.owner = owner;
        }
    }

    public Dot getDot1(){
        return d1;
    }

    public Dot getDot2(){
        return d2;
    }

    public Player getOwner(){
        return owner;
    }

    @Override
    public String toString(){
        return d1 + " -> " + d2;
    }
}
