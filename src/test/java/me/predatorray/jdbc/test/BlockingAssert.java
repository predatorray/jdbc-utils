package me.predatorray.jdbc.test;

import java.util.concurrent.*;

public final class BlockingAssert {

    private BlockingAssert() {}

    public static void assertBlockingAtLeast(BlockingJob blockingJob,
                                             long millis)
            throws InterruptedException {
        assertBlockingAtLeast(blockingJob, millis, null);
    }

    public static void assertBlockingAtLeast(final BlockingJob blockingJob,
                                             long millis, String msg)
            throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> task = executorService.submit(blockingJob);
        boolean blocked = false;
        try {
            task.get(millis, TimeUnit.MILLISECONDS);
        } catch (ExecutionException ex) {
            throw (msg == null)
                    ? new AssertionError(ex.getCause())
                    : new AssertionError(msg);
        } catch (TimeoutException ex) {
            blocked = true;
        } finally {
            task.cancel(true);
        }

        if (!blocked) {
            throw (msg == null)
                    ? new AssertionError()
                    : new AssertionError(msg);
        }
    }
}
