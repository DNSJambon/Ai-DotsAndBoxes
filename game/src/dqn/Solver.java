package dqn;

import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.translate.TranslateException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static dqn.DQNModel.buildModel;

public class Solver {
    Model model;
    Predictor<float[], float[]> predictor;

    public Solver(int N){
        model = buildModel(2 * N * (N-1), 2 * N * (N-1));
        try {
            Path modelDir = Paths.get("model/");
            model.load(modelDir, "mlp");
        } catch (MalformedModelException | IOException e) {
            throw new RuntimeException(e);
        }

        predictor = model.newPredictor(new MyTranslator());
    }

    public int solve(float[] state) throws TranslateException {
        float[] input = new float[state.length];
        System.arraycopy(state, 0, input, 0, state.length);
        float[] output = predictor.predict(input);
        int action = 0;
        for (int i = 0; i < output.length; i++) {
            if (output[i] > output[action]) {
                action = i;
            }
        }
        return action;
    }

    public void ShowQValues(float[] state) throws TranslateException {
        float[] input = new float[state.length];
        System.arraycopy(state, 0, input, 0, state.length);
        float[] output = predictor.predict(input);
        System.out.println("Q values: ");
        for (int i = 0; i < output.length; i++) {
            System.out.print(i + ": " + output[i] + "\n");
        }
        System.out.println();
    }

}
