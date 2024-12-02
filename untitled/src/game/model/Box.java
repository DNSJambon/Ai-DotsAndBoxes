package game.model;

public class Box {
    private final Line left;
    private final Line top;
    private final Line right;
    private final Line bottom;

    private final Player owner;

    protected Box(Line top, Line right, Line bottom, Line left, Player owner){
        if (left == null || top == null || right == null || bottom == null){
            throw new IllegalArgumentException("Invalid box, one or more lines are null");
        }
        if (left.getDot1() != top.getDot1() || top.getDot2() != right.getDot1() || right.getDot2() != bottom.getDot2() || bottom.getDot1() != left.getDot2()){
            throw new IllegalArgumentException("Invalid box, not a 1x1 square");
        }
        if (owner == null){
            throw new IllegalArgumentException("null owner");
        }


        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.owner = owner;
        owner.addScore();
    }


    public Line getLeft(){
        return left;
    }

    public Line getTop(){
        return top;
    }

    public Line getRight(){
        return right;
    }

    public Line getBottom(){
        return bottom;
    }

    public Player getOwner(){
        return owner;
    }
}
