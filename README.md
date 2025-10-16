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
![class diargam](docs)

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