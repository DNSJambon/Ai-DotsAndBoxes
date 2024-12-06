package dqn.dataset;

import java.util.Arrays;

public class Experience {
    private final int[] state;
    private final int action;
    private final int reward;
    private final int[] nextState;


    public Experience(int[] state, int action, int reward, int[] nextState) {
        this.state = state;
        this.action = action;
        this.reward = reward;
        this.nextState = nextState;

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

    public String toString() {
        return "State: " + Arrays.toString(state) + " Action: " + action + " Reward: " + reward;
    }

}