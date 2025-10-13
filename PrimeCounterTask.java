import java.util.concurrent.atomic.AtomicLong;

public class PrimeCounterTask implements Runnable {
    private final long start;
    private final long endInclusive;
    private final AtomicLong globalSum;

    public PrimeCounterTask(long start, long endInclusive, AtomicLong globalSum) {
        this.start = Math.max(2, start);
        this.endInclusive = endInclusive;
        this.globalSum = globalSum;
    }

    @Override
    public void run() {
        long local = 0;
        for (long n = start; n <= endInclusive; n++) {
            if (isPrime(n)) local++;
        }
        globalSum.addAndGet(local);
    }
    
    private static boolean isPrime(long n) {
        if (n < 2) return false;
        if (n % 2 == 0) return n == 2;
        long limit = (long)Math.sqrt(n);
        for (long d = 3; d <= limit; d += 2) {
            if (n % d == 0) return false;
        }
        return true;
    }
}
