package dqn;

import ai.djl.engine.Engine;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;
import ai.djl.translate.Batchifier;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;

public class MyTranslator implements Translator<float[], float[]> {
    NDManager manager = NDManager.newBaseManager("PyTorch");

    @Override
    public NDList processInput(TranslatorContext ctx, float[] input) {
        NDArray array = manager.create(input).reshape(new Shape(1, input.length));
        return new NDList(array);
    }

    @Override
    public float[] processOutput(TranslatorContext ctx, NDList list) {
        NDArray array = list.singletonOrThrow();
        return array.toFloatArray();
    }

    @Override
    public Batchifier getBatchifier() {
        return null;
    }
}