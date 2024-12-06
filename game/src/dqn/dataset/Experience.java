package dqn.dataset;

import java.util.Arrays;

public record Experience(float[] state, int action, int reward, float[] nextState) {

    public String toString() {
        return "State: " + Arrays.toString(state) + " Action: " + action + " Reward: " + reward;
    }

}