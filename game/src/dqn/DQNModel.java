package dqn;

import ai.djl.*;
import ai.djl.nn.*;
import ai.djl.nn.core.*;
import ai.djl.training.*;

public class DQNModel {
    public Model buildModel(int inputSize, int outputSize) {
        SequentialBlock net = new SequentialBlock()
                .add(Linear.builder().setUnits(128).build()) // Hidden layer
                .add(Activation.reluBlock())
                .add(Linear.builder().setUnits(128).build())
                .add(Activation.reluBlock())
                .add(Linear.builder().setUnits(outputSize).build()); // Output layer

        Model model = Model.newInstance("DQN");
        model.setBlock(net);
        return model;
    }
}
