package dqn.dataset;

import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.training.dataset.RandomAccessDataset;
import ai.djl.training.dataset.Record;
import ai.djl.util.Progress;

import java.util.List;

public class GameDataset extends RandomAccessDataset {

    private final List<Experience> data;

    protected GameDataset(Builder builder) {
        super(builder);
        this.data = builder.data;
    }

    @Override
    public Record get(NDManager manager, long index){
        Experience sample = data.get((int) index);

        float[] stateArray = sample.state();
        float[] data = new float[stateArray.length];
        System.arraycopy(stateArray, 0, data, 0, stateArray.length);

        NDArray state = manager.create(data);
        NDArray action = manager.create(Float.valueOf(2 ));

        return new Record(
                new NDList(state), // Input: State
                new NDList(action)         // Label: Action
        );
    }

    
    @Override
    protected long availableSize() {
        return data.size();
    }

    @Override
    public void prepare(Progress progress) {

    }

    public static final class Builder extends BaseBuilder<Builder> {
        private List<Experience> data;

        public Builder setData(List<Experience> data) {
            this.data = data;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        public GameDataset build() {
            return new GameDataset(this);
        }
    }
}
