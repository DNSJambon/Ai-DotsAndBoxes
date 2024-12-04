package dqn;

public class Experience {
    private final int[] state;
    private final int action;
    private final int reward;
    private final int[] nextState;
    private final boolean isTerminal;

    public Experience(int[] state, int action, int reward, int[] nextState, boolean isTerminal) {
        this.state = state;
        this.action = action;
        this.reward = reward;
        this.nextState = nextState;
        this.isTerminal = isTerminal;
    }

    public int[] getState() {
        return state;
    }

    public int getAction() {
        return action;
    }

    public int getReward() {
        return reward;
    }

    public int[] getNextState() {
        return nextState;
    }

    public boolean isDone() {
        return isTerminal;
    }
}