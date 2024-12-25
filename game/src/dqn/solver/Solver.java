package dqn.solver;

public interface Solver {


    // return the index of the action to play given the current state of the game
    int solve(float[] state) throws Exception;

}
