package dqn.dataset;

import java.util.Arrays;

public record Experience(float[] state, int action, float reward, float[] nextState, game.model.Player currentPlayer) {

    public String toString() {
        return "State: " + Arrays.toString(state) + " Action: " + action + " Reward: " + reward;
    }

}