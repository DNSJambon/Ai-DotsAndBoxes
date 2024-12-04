package dqn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReplayBuffer {
    private List<Experience> buffer;
    private int capacity;

    public ReplayBuffer(int capacity) {
        this.buffer = new ArrayList<>();
        this.capacity = capacity;
    }

    public void addExperience(Experience experience) {
        if (buffer.size() >= capacity) {
            buffer.clear();
        }
        buffer.add(experience);
    }

    public List<Experience> sampleBatch(int batchSize) {
        Collections.shuffle(buffer);
        return buffer.subList(0, Math.min(batchSize, buffer.size()));
    }

    public int size() {
        return buffer.size();
    }

    public int getCapacity(){
        return capacity;
    }

}


    

