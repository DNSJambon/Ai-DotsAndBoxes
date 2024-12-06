package dqn;

import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.translate.Batchifier;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;

public class MyTranslator implements Translator<float[], NDArray> {

    @Override
    public NDList processInput(TranslatorContext ctx, float[] input) {
        NDManager manager = ctx.getNDManager();
        float[] floatInput = new float[input.length];
        System.arraycopy(input, 0, floatInput, 0, input.length);
        return new NDList(manager.create(floatInput));
    }

    @Override
    public NDArray processOutput(TranslatorContext ctx, NDList list) {
        return list.singletonOrThrow();
    }

    @Override
    public Batchifier getBatchifier() {
        return null;
    }
}