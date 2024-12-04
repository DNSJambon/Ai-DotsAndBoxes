package dqn;

import ai.djl.Model;
import java.util.Random;
import java.util.List;

public class DQNTrainer {
    private ReplayBuffer replayBuffer;
    private Model policyModel;
    private Model targetModel;
    private int batchSize = 32;
    private double gamma = 0.99;

    public DQNTrainer(Model policyModel, Model targetModel, int bufferSize) {
        this.policyModel = policyModel;
        this.targetModel = targetModel;
        this.replayBuffer = new ReplayBuffer(bufferSize);
    }

    public void train(int episodes) {
        for (int episode = 0; episode < episodes; episode++) {
            GameState game = new GameState(3);
            int[] state = game.getState();

            while (!game.isGameOver()) {
                int action = selectAction(state);
                game.applyAction(action);
                int[] nextState = game.getState();
                int reward = game.getReward(game.currentPlayer);
                boolean done = game.isGameOver();

                replayBuffer.addExperience(new Experience(state, action, reward, nextState, done));

                state = nextState;
                if (done) break;

                trainFromReplayBuffer();
            }

            updateTargetModel(); // Sync target model with policy model
        }
    }

    private int selectAction(int[] state) {
        // Epsilon-greedy action selection
        if (Math.random() < 0.1) { // Exploration
            int action = new Random().nextInt(state.length);
            while (state[action] != 0) {
                action = new Random().nextInt(state.length);
            }
        } else { // Exploitation
            // Predict Q-values and choose the best action

        }
        return 0;
    }

    private void trainFromReplayBuffer() {
        List<Experience> batch = replayBuffer.sampleBatch(batchSize);
        // Compute targets and train the policy model

    }

    private void updateTargetModel() {
        // Copy weights from policy model to target model

    }
}
