package dqn;

import ai.djl.*;
import ai.djl.ndarray.types.Shape;
import ai.djl.nn.*;
import ai.djl.nn.core.*;
import ai.djl.training.*;
import ai.djl.training.evaluator.Accuracy;
import ai.djl.training.listener.TrainingListener;
import ai.djl.training.loss.Loss;

import ai.djl.translate.TranslateException;
import dqn.dataset.Experience;
import dqn.dataset.GameDataset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class DQNModel {
    public static Model buildModel(int inputSize, int outputSize) {
        SequentialBlock block = new SequentialBlock()
                .add(Blocks.batchFlattenBlock(inputSize)) // Flatten the input
                .add(Linear.builder().setUnits(128).build()) // Hidden layer
                .add(Activation::relu)
                .add(Linear.builder().setUnits(64).build())
                .add(Activation::relu)
                .add(Linear.builder().setUnits(outputSize).build()); // Output layer

        Model model = Model.newInstance("DQN");
        model.setBlock(block);
        return model;
    }

    public static void main(String[] args) throws TranslateException, IOException {
        int N = 3;
        int inputSize = 2 * N * (N-1);
        int outputSize = 2 * N * (N-1);
        Model model = buildModel(inputSize, outputSize);


        DefaultTrainingConfig config = new DefaultTrainingConfig(Loss.softmaxCrossEntropyLoss())
                //softmaxCrossEntropyLoss is a standard loss for classification problems
                .addEvaluator(new Accuracy()) // Use accuracy so we humans can understand how accurate the model is
                .addTrainingListeners(TrainingListener.Defaults.logging());

        // Now that we have our training configuration, we should create a new trainer for our model
        Trainer trainer = model.newTrainer(config);
        trainer.initialize(new Shape(1, inputSize));


        DQNTrainer dqnTrainer = new DQNTrainer();
        List<Experience> replayBuffer = dqnTrainer.train(1000, model);
        //System.out.println(replayBuffer);

        GameDataset dataset = new GameDataset.Builder()
                .setData(replayBuffer)
                .setSampling(32, true)
                .build();


        // Now we can train our model
        int epoch = 2;
        EasyTrain.fit(trainer, epoch, dataset, null);

        Path modelDir = Paths.get("model/");
        Files.createDirectories(modelDir);

        model.setProperty("Epoch", String.valueOf(epoch));

        model.save(modelDir, "mlp");
        System.out.println(model);



    }
}
