package dqn;


import dqn.dataset.Experience;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class DQNTrainer {
    private final List<Experience> replayBuffer;


    public DQNTrainer() {
        this.replayBuffer = new ArrayList<>();
    }

    public List<Experience> train(int episodes) {

            List<Experience> tempBuffer = new ArrayList<>();

            for (int episode = 0; episode < episodes; episode++) {
                GameState game = new GameState(3);
                float[] state = game.getState().clone();

                while (!game.isGameOver()) {
                    int action = selectAction(state);
                    if (state[action] != 0) {
                        replayBuffer.add(new Experience(state, action, 0, state, game.getCurrentPlayer()));
                        break;
                    }

                    float reward = game.applyAction(action);

                    Experience exp = new Experience(state, action, reward, game.getState().clone(), game.getCurrentPlayer());
                    //tempBuffer.add(exp);
                    replayBuffer.add(exp);

                    state = game.getState().clone();
                }



            }

        return replayBuffer;
    }

    private int selectAction(float[] state) {
        // Epsilon-greedy action selection
        if (true) { // Exploration
            int action = new Random().nextInt(state.length);
//            while (state[action] != 0.0) {
//                action = new Random().nextInt(state.length);
//            }
            return action;
        } else { // Exploitation
            // Compute Q-values from the policy model
            return 0;
        }
    }

    public List<Experience> computeReward(List<Experience> buffer) {
        List<Experience> updatedBuffer = new ArrayList<>();
        for (int i = 0; i < buffer.size()-1; i++) {
            Experience exp = buffer.get(i);
            Experience nextExp = buffer.get(i+1);
            if (exp.currentPlayer() != nextExp.currentPlayer() && nextExp.reward() > 0) {
                updatedBuffer.add(new Experience(exp.state(), exp.action(), exp.reward() - 1, exp.nextState(), exp.currentPlayer()));
            }

        }
        return updatedBuffer;
    }




}
