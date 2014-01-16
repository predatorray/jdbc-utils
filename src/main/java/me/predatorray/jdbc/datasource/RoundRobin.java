package me.predatorray.jdbc.datasource;

import me.predatorray.jdbc.Check;

import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobin implements LoadBalancingStrategy {

    private final int maxIndex;
    private AtomicInteger counter = new AtomicInteger(0);

    public RoundRobin(int maxIndex) {
        Check.argumentIsPositive(maxIndex,
                "maxIndex must be a positive number");
        this.maxIndex = maxIndex;
    }

    @Override
    public int next() {
        while (true) {
            int current = counter.get();
            int next = (current + 1) % maxIndex;
            if (counter.compareAndSet(current, next)) {
                return current;
            }
        }
    }
}
