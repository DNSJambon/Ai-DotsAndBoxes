package dqn;

import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.Trainer;
import ai.djl.training.TrainingConfig;
import ai.djl.training.listener.TrainingListener;
import ai.djl.training.loss.Loss;
import ai.djl.training.optimizer.Optimizer;
import ai.djl.translate.TranslateException;

import java.util.Random;
import java.util.List;

public class DQNTrainer {
    private ReplayBuffer replayBuffer;
    private Model policyModel;
    private Model targetModel;
    private Trainer trainer;

    private int batchSize = 32;
    private double gamma = 0.99;

    public DQNTrainer(Model policyModel, Model targetModel, int bufferSize) {
        this.policyModel = policyModel;
        this.targetModel = targetModel;
        this.replayBuffer = new ReplayBuffer(bufferSize);

        TrainingConfig config = new DefaultTrainingConfig(Loss.l2Loss())
                .optOptimizer(Optimizer.adam().build()) // Adam optimizer
                .addTrainingListeners(TrainingListener.Defaults.logging());

        trainer = policyModel.newTrainer(config);

    }

    public void train(int episodes) {
        for (int episode = 0; episode < episodes; episode++) {
            GameState game = new GameState(3);
            int[] state = game.getState();

            while (!game.isGameOver()) {
                int action = selectAction(state);
                if (state[action] != 0) {
                    int reward = -100;
                    replayBuffer.addExperience(new Experience(state, action, reward, state, true));
                    break;
                }

                game.applyAction(action);
                int[] nextState = game.getState();
                int reward = game.getReward(game.currentPlayer);
                boolean done = game.isGameOver();

                replayBuffer.addExperience(new Experience(state, action, reward, nextState, done));

                state = nextState;


                if (replayBuffer.size() >= replayBuffer.getCapacity()) {
                    trainFromReplayBuffer();
                }

                if (done) break;
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
            return action;
        } else { // Exploitation
            // Compute Q-values from the policy model
            return 0;
        }
    }

    private void trainFromReplayBuffer(){
        List<Experience> batch = replayBuffer.sampleBatch(batchSize);


    }

    private void updateTargetModel() {
        // Copy weights from policy model to target model

    }
}
