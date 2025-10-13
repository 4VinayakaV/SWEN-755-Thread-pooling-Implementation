import java.util.concurrent.atomic.AtomicLong;

/**
 * Demo:
 * - Creates a ThreadPool of 10 threads.
 * - Splits [2..MAX] into 120 chunks to ensure reuse.
 * - Counts primes across chunks in parallel and reports timing.
 *   MAX = 5_000_000  
 *   CHUNKS = 120   
 */
public class Main {
    public static void main(String[] args) throws Exception {
        long MAX = 5_000_000L;
        int CHUNKS = 120;

        final int POOL_SIZE = 10;
        final int QUEUE_CAP = 2 * POOL_SIZE; 

        System.out.println("Thread Pool Prime Counter Demo");
        System.out.println("Range: [2.." + MAX + "], chunks=" + CHUNKS + ", poolSize=" + POOL_SIZE);

        ThreadPool pool = new ThreadPool(POOL_SIZE, QUEUE_CAP);
        AtomicLong totalPrimes = new AtomicLong(0);

        long t0 = System.currentTimeMillis();

        long span = MAX - 1; 
        long chunkSize = Math.max(1, span / CHUNKS);
        long start = 2;
        for (int i = 0; i < CHUNKS; i++) {
            long end = (i == CHUNKS - 1) ? MAX : Math.min(MAX, start + chunkSize - 1);
            PrimeCounterTask task = new PrimeCounterTask(start, end, totalPrimes);
            pool.execute(task);
            start = end + 1;
            if (start > MAX) break;
        }

        pool.shutdown();
        pool.awaitTermination();

        long t1 = System.currentTimeMillis();
        System.out.println("Total primes in [2.." + MAX + "] = " + totalPrimes.get());
        System.out.println("Elapsed: " + (t1 - t0) + " ms");
    }
}
