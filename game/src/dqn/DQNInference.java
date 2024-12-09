package dqn;

import ai.djl.Model;
import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.translate.TranslateException;

import java.io.IOException;
import java.nio.file.Paths;

public class DQNInference {
    public static void main(String[] args) throws ModelException, TranslateException {
        try (Model model = Model.newInstance("dqn")) {
            model.load(Paths.get("../../../DQNPython/dqn/policy_net.onnx"));

            NDManager manager = NDManager.newBaseManager();
            NDArray input = manager.create(new float[] { 0, 1,0,1,0,1,0,1,0,1,0,1 });
            try (Predictor<float[], float[]> predictor = model.newPredictor(new MyTranslator())) {
                float[] output = predictor.predict(new float[] { 0, 1,0,1,0,1,0,1,0,1,0,1 });
                System.out.println("Predicted Q-values: " + output);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
