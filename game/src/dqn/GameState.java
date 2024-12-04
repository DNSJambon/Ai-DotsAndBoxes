package dqn;

import game.model.*;

import java.util.ArrayList;

public class GameState {
    public int N;
    public Grid grid;
    public Player currentPlayer;
    public int[] state;

    public GameState(int N){
        this.N = N;
        this.grid = new Grid(N, false);
        this.currentPlayer = grid.getPlayer1();
        this.state = new int[2 * N * (N-1)];
    }


    public void turn(Dot d1, Dot d2){
        boolean box = grid.addLine(d1, d2, currentPlayer);
        if (!box){
            currentPlayer = (currentPlayer == grid.getPlayer1()) ? grid.getPlayer2() : grid.getPlayer1();
        }
    }


    // we have to convert the action to the corresponding line
    public void applyAction(int action) {
        int nbHorizontalLines = (N-1) * N;
        Dot d1;
        Dot d2;
        if (action < nbHorizontalLines){
            int j = action / (N-1);
            int i = action % (N-1);
            d1 = grid.getDot(i, j);
            d2 = grid.getDot(i+1, j);
            turn(d1, d2);
            state[action] = 1;
        }
        else {
            int j = (action - nbHorizontalLines) / N;
            int i = (action - nbHorizontalLines) % N;
            d1 = grid.getDot(i, j);
            d2 = grid.getDot(i, j+1);
            turn(d1, d2);
            state[action] = 1;
        }
        //System.out.println("Applying action: " + action + " = " + d1 + "-" + d2);

    }



    /*
    The state is a string of 0 and 1 representing the lines of the grid, where 0 means no line and 1 means a line.
    the order of the lines in the string in a 3x3 grid for reference:
    *-1-*-2-*
    |   |   |
    7   9   11
    |   |   |
    *-3-*-4-*
    |   |   |
    8   10  12
    |   |   |
    *-5-*-6-*
     */
    public int[] getState(){
        return state;
    }

    public int getReward(Player player){
        if (player == grid.getPlayer1()){
            return grid.getPlayer1().getScore() - grid.getPlayer2().getScore();
        }
        else {
            return grid.getPlayer2().getScore() - grid.getPlayer1().getScore();
        }
    }

    public boolean isGameOver(){
        return grid.isGameOver();
    }



}
