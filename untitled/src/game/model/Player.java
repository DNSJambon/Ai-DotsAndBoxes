package game.model;


public class Player {
    private int score;
    boolean isBot;

    protected Player(boolean isBot){
        this.score = 0;
        this.isBot = isBot;
    }

    public int getScore(){
        return score;
    }

    public boolean isBot(){
        return isBot;
    }

    public void swapBot(){
        isBot = !isBot;
    }

    protected void addScore(){
        this.score += 1;
    }

    protected void reset(){
        score = 0;
    }



}
