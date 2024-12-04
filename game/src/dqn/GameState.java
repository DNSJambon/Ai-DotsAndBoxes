package dqn;

import game.model.*;

import java.util.ArrayList;

public class GameState {
    public int N;
    public Grid grid;
    public Player currentPlayer;

    public GameState(int N){
        this.N = N;
        this.grid = new Grid(N, false);
        this.currentPlayer = grid.getPlayer1();
    }

    public void turn(Line line){
        boolean box = grid.addLine(line.getDot1(), line.getDot2(), currentPlayer);
        if (!box){
            currentPlayer = (currentPlayer == grid.getPlayer1()) ? grid.getPlayer2() : grid.getPlayer1();
        }
    }



    /*
    The state is a string of 0 and 1 representing the lines of the grid, where 0 means no line and 1 means a line.
    the order of the lines in the string in a 3x3 grid for reference:
    *-1-*-3-*
    |   |   |
    2   4   5
    |   |   |
    *-6-*-8-*
    |   |   |
    7   9   10
    |   |   |
    *-11*-12-*
     */
    public int[] getState(){
        ArrayList<Integer> state = new ArrayList<>();
        int n = grid.getSize();
        for (int j = 0; j < n; j++){
            for (int i = 0; i < n; i++){
                Dot d = grid.getDot(i, j);

                if (i==n-1 && j==n-1){
                    //last row and last column, no line to count
                    continue;
                }

                if (i==n-1){
                    //last column, so we count only the line to the bottom
                    if (grid.getLine(d, grid.getDot(i, j+1)) != null)
                        state.add(1);
                    else
                        state.add(0);
                    continue;
                }

                if (j==n-1){
                    //last row, so we count only the line to the right
                    if (grid.getLine(d, grid.getDot(i+1, j)) != null)
                        state.add(1);
                    else
                        state.add(0);
                    continue;
                }

                //line to his right
                if (grid.getLine(d, grid.getDot(i+1, j)) != null)
                    state.add(1);
                else
                    state.add(0);
                //line to his bottom
                if (grid.getLine(d, grid.getDot(i, j+1)) != null)
                    state.add(1);
                else
                    state.add(0);

            }
        }
        return state.stream().mapToInt(i -> i).toArray();
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


    public void applyAction(int action) {

    }
}
