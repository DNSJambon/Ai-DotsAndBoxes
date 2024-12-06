package dqn;


import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.index.NDIndex;
import ai.djl.translate.TranslateException;
import dqn.dataset.Experience;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class DQNTrainer {
    private List<Experience> replayBuffer;


    public DQNTrainer() {
        this.replayBuffer = new ArrayList<Experience>();
    }

    public List<Experience> train(int episodes, Model model) {
        try (NDManager manager = NDManager.newBaseManager()) {
            for (int episode = 0; episode < episodes; episode++) {
                GameState game = new GameState(3);
                int[] state = game.getState().clone();

                while (!game.isGameOver()) {
                    int action = selectAction(state);
                    if (state[action] != 0) {
                        replayBuffer.add(new Experience(state, action, -10, state));
                        break;
                    }

                    int reward = game.applyAction(action);

                    Experience exp = new Experience(state, action, reward, game.getState().clone());
                    replayBuffer.add(exp);


                    state = game.getState().clone();
                }
            }
        }
        return replayBuffer;
    }

    private int selectAction(int[] state) {
        // Epsilon-greedy action selection
        if (true) { // Exploration
            int action = new Random().nextInt(state.length);
            while (state[action] != 0) {
                action = new Random().nextInt(state.length);
            }
            return action;
        } else { // Exploitation
            // Compute Q-values from the policy model
            return 0;
        }
    }




}
