package me.predatorray.jdbc.test;

public abstract class BlockingJob implements Runnable {

    private volatile boolean done = false;

    protected abstract void perform() throws InterruptedException;

    @Override
    public final void run() {
        try {
            perform();
            done = true;
        } catch (InterruptedException ignored) {}
    }

    public final boolean isDone() {
        return done;
    }
}
