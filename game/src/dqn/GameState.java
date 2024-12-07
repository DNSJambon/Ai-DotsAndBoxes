package dqn;

import game.model.*;


public class GameState {
    public int N;
    public Grid grid;
    public Player currentPlayer;
    public float[] state;

    public GameState(int N){
        this.N = N;
        this.grid = new Grid(N, false);
        this.currentPlayer = grid.getPlayer1();
        this.state = new float[2 * N * (N-1)];
    }


    public float turn(Dot d1, Dot d2){
        boolean box = grid.addLine(d1, d2, currentPlayer);
        if (!box){
            currentPlayer = (currentPlayer == grid.getPlayer1()) ? grid.getPlayer2() : grid.getPlayer1();
        }
        return (float) ((box) ? 1.0 : 0.0);
    }


    // we have to convert the action to the corresponding line
    public float applyAction(int action) {
        int nbHorizontalLines = (N-1) * N;
        Dot d1;
        Dot d2;
        if (action < nbHorizontalLines){
            int j = action / (N-1);
            int i = action % (N-1);
            d1 = grid.getDot(i, j);
            d2 = grid.getDot(i+1, j);
            state[action] = 1;
            return turn(d1, d2);
        }
        else {
            int i = (action - nbHorizontalLines) / (N-1);
            int j = (action - nbHorizontalLines) % (N-1);
            d1 = grid.getDot(i, j);
            d2 = grid.getDot(i, j+1);
            state[action] = 1;
            return turn(d1, d2);
        }
        //System.out.println("Applying action: " + action + " = " + d1 + "-" + d2);

    }



    /*
    The state is a string of 0 and 1 representing the lines of the grid, where 0 means no line and 1 means a line.
    the order of the lines in the string in a 3x3 grid for reference:
    *-0-*-1-*
    |   |   |
    6   8   10
    |   |   |
    *-2-*-3-*
    |   |   |
    7   9   11
    |   |   |
    *-4-*-5-*
     */
    public float[] getState(){
        return state;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }


    public boolean isGameOver(){
        return grid.isGameOver();
    }



}
