package me.predatorray.jdbc.test;

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
        Thread blockingJobThread = new Thread(blockingJob);
        blockingJobThread.start();
        try {
            Thread.sleep(millis);
            if (blockingJob.isDone()) {
                throw (msg == null)
                        ? new AssertionError()
                        : new AssertionError(msg);
            }
        } finally {
            blockingJobThread.interrupt();
        }
    }
}
