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
}