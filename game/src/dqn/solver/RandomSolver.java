package dqn.solver;

public class RandomSolver implements Solver {

    public RandomSolver() {
    }

    public int solve(float[] state) {
        int action = (int) (Math.random() * state.length);
        while (state[action] != 0) {
            action = (int) (Math.random() * state.length);
        }
        return action;
    }

}
