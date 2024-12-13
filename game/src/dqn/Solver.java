package dqn;

import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.translate.TranslateException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Solver {
    private Predictor<float[], float[]> predictor;

    public Solver(int N) {
        try {
            System.setProperty("ai.djl.default_engine", "OnnxRuntime");
            Path modelDir = Paths.get("../DQNPython/dqn/");
            Model model = Model.newInstance("onnxruntime");
            model.load(modelDir, "model"+N+"good");
            predictor = model.newPredictor(new MyTranslator());

        } catch (MalformedModelException | IOException e) {
            throw new RuntimeException(e);
        }
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

    public void showQValues(float[] state) throws TranslateException {
        float[] input = new float[state.length];
        System.arraycopy(state, 0, input, 0, state.length);
        float[] output = predictor.predict(input);
        System.out.println("Q values: ");
        for (int i = 0; i < output.length; i++) {
            System.out.print(i + ": " + output[i] + "\n");
        }
        System.out.println();
    }

    public void close() {
        if (predictor != null) {
            predictor.close();
        }
    }
}