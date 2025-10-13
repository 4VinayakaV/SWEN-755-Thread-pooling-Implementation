import java.util.ArrayDeque;
import java.util.Deque;

public class BlockingTaskQueue {
    private final Deque<Runnable> q = new ArrayDeque<>(); // q stores runnable tasks
    private final int capacity; // maximum number of tasks allowed

    public BlockingTaskQueue(int capacity) { // Ensures a valid capacity
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.capacity = capacity;
    }

    public void put(Runnable task) throws InterruptedException { // Add tasks to the queue
        synchronized (q) {
            while (q.size() >= capacity) {
                q.wait();
            }
            q.addLast(task);
            q.notifyAll();
        }
    }

    public Runnable take() throws InterruptedException { // Fetch a task for a worker thread to execute
        synchronized (q) {
            while (q.isEmpty()) {
                q.wait();
            }
            Runnable task = q.removeFirst();
            q.notifyAll(); 
            return task;
        }
    }

    public int size() { // returns the number of pending tasks 
        synchronized (q) {
            return q.size();
        }
    }
}
