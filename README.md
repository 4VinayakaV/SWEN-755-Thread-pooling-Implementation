# SWEN-755-Thread-pooling-Implementation

## Team Members: 
1. Dan Corcoran
2. Vinayaka Viswanatha

## Summary
This project demonstrates the Thread Pooling architectural tactic using Java.
In this implementation:
1. A custom ThreadPool and BlockingTaskQueue are built
2. The program counts all prime numbers between 2 and a given maximum (MAX = 5,000,000) by dividing the work into smaller chunks (120)
3. The 10 worker threads in the pool continuously fetch tasks from the shared queue and execute them, demonstrating thread reuse
4. After all tasks complete, the total number of primes and execution time are displayed

## Class Diagram
![class diargam](/docs/thread_pool_class_diagram.png)
Above is the class diagram detailing the various code classes being utilized in this exercise. Of note, AtomicLong is utilized inside of PrimeCounterTask, which keeps a continuous count of primes across all threads. This could be modeled as its own class, but for clarity of matching this document to the codebase, we decided to leave its mention inside of the variables belonging to PrimeCounterTask. Main has been modeled to show the total connectivity between ThreadPool and PrimeCounterTask, as it handles the orchestration of the thread executions. ThreadPools are aggregations of workers and utilizes the BlockingTaskQueue to hand out tasks to each individual worker as they become available.

## Sequence Diagram
![class diargam](/docs/thread_pool_sequence_diagram.png)
Above is the sequence diagram detailing the runtime of our implementation of this exercise. A user beings the program by running main, which will create a thread pool and calculates the chunks that will be required, depending on the programmed maximum upper value for prime checking. For each of these chunkcs, a new PrimeCounterTask will be created and the thread pool will add the task to the queue. Workers will then request tasks from the queue and run them when available, incrementing a prime counter that is kept concurrent by AtomicLong. Once all workers are finished executing tasks, the thread pool is terminated, the total prime count is retrieved, and the user is informed of the results (number range, chunks, thread count, prime count, and time elapsed).

## Libraries Used

1. `java.util.ArrayDeque` - Used as the underlying data structure for the blocking task queue
2. `java.util.Deque` - Provides a double-ended queue interface for ArrayDeque
3. `java.util.concurrent.atomic.AtomicLong` - Allows thread-safe updates to the shared prime counter across multiple threads
4. `java.lang.Thread` - Base class for creating and managing threads
5. `java.lang.Runnable` - Interface implemented by tasks that can be executed by worker threads

## How to Execute the program

1. Ensure all four .java files are in the same directory
2. run -  `javac *.java` in the terminal to compile all the files
3. run - `java Main`