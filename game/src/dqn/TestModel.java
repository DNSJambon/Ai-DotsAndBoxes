package dqn;

import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.translate.TranslateException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static dqn.DQNModel.buildModel;

public class TestModel {
    public static void main(String[] args) throws TranslateException {
        Model modelLoaded = Model.newInstance("DQN");
        Model model = buildModel(2 * 3 * (3-1), 2 * 3 * (3-1));
        try {
            Path modelDir = Paths.get("model/");
            model.load(modelDir, "mlp");
        } catch (MalformedModelException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        var predictor = model.newPredictor(new MyTranslator());
        float[] input = new float[2 * 3 * (3-1)];
        var classifications = predictor.predict(input);
        System.out.println(classifications);
    }
}
