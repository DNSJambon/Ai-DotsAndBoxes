package dqn;

import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.translate.*;

public class StateTranslator implements Translator<int[], NDArray> {
    @Override
    public NDList processInput(TranslatorContext ctx, int[] state) throws Exception {
        return (NDList) ctx.getNDManager().create(state);
    }



    @Override
    public Batchifier getBatchifier() {
        return null; // No batching needed for single prediction
    }

    @Override
    public NDArray processOutput(TranslatorContext translatorContext, NDList ndList) throws Exception {
        return null;
    }
}