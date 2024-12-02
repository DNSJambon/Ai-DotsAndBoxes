package dqn;

import game.model.*;

public class GameState {
    public Grid grid;
    private Player currentPlayer;

    public GameState(int N){
        this.grid = new Grid(N, false);
        this.currentPlayer = grid.getPlayer1();
    }

    public void turn(Line line){
        boolean box = grid.addLine(line.getDot1(), line.getDot2(), currentPlayer);
        if (!box){
            currentPlayer = (currentPlayer == grid.getPlayer1()) ? grid.getPlayer2() : grid.getPlayer1();
        }
    }



    public boolean isGameOver(){
        return grid.isGameOver();
    }


}
