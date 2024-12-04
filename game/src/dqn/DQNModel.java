package dqn;

import ai.djl.*;
import ai.djl.nn.*;
import ai.djl.nn.core.*;
import ai.djl.training.*;

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

    public static void main(String[] args) {
        int N = 3;
        int inputSize = (N-1) * (N-1) * 2;
        int outputSize = (N-1) * N + N * (N-1);
        Model model = buildModel(inputSize, outputSize);
        System.out.println(model);
    }
}
