public class ThreadPool {
    private final BlockingTaskQueue queue;
    private final Worker[] workers;
    private volatile boolean running = true;

    private class Worker extends Thread {
        public void run() {
            try {
                while (true) {
                    Runnable task;
                    synchronized (ThreadPool.this) {
                        if (!running && queue.size() == 0) break;
                    }
                    task = queue.take();
                    try {
                        task.run();
                    } catch (Throwable t) {
                        System.err.println("[Worker-" + getName() + "] Task error: " + t);
                    }
                }
            } catch (InterruptedException ie) {
            }
        }
    }

    public ThreadPool(int poolSize, int queueCapacity) {
        if (poolSize <= 0) throw new IllegalArgumentException("poolSize must be > 0");
        this.queue = new BlockingTaskQueue(queueCapacity);
        this.workers = new Worker[poolSize];
        for (int i = 0; i < poolSize; i++) {
            workers[i] = new Worker();
            workers[i].setName(String.valueOf(i));
            workers[i].start();
        }
    }

    // Adds a task to the queue. Blocks if the queue is full
    public void execute(Runnable r) throws InterruptedException {
        if (!running) throw new IllegalStateException("ThreadPool is shut down");
        queue.put(r);
    }

    public void shutdown() { 
        synchronized (this) {
            running = false;
        }
        for (Worker w : workers) {
            w.interrupt();
        }
    }

    public void awaitTermination() { // Wait for all workers to finish
        for (Worker w : workers) {
            try {
                w.join();
            } catch (InterruptedException ignored) {}
        }
    }
}
