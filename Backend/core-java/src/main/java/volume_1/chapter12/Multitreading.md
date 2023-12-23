# Concurrency

A thread is a peace of code we want to run in concurrently.
To construct a thread we use its constructor and pass

- Instance of class that implements Runnable interface
- Lambda expression of Runnable functional interface

```java
public interface Runnable {
    void run();
}

class FirstThread implements Runnable {
    public void run() {
        // code here 
    }
}

class TestMain {
    public main() {
        Runnable secondThread = () -> {
            // task code
        };
        new Thread(new FirstThread()).start();
        new Thread(secondThread).start();
    }
}
```

## Thread
- **Thread(Runnable target)**
constructs a new thread that calls the run() method of the specified target.

- **void start()**
starts this thread, causing the run() method to be called. This method will return immediately. The new thread runs concurrently.

- **void run()**
calls the run method of the associated Runnable.

- **static void sleep(long millis)**
sleeps for the given number of milliseconds.

We should always call Thread.start() because Thread run will execute in the current thread and would not construct a new one

## Thread states 

- New
- Runnable
- Blocked
- Waiting
- Timed waiting
- Terminated

TO get current state call **getState()** of **Thread**.

### New 

When thread is just created it is in new state.

### Runnable

Once you invoke the start method, the thread is in the runnable state.
Always keep in mind that a runnable thread may or may not be running at any given time
Scheduling and time slicing of the processes is done by processors but we can stop thread by calling yield on mobile devices.

- **static void yield()** causes the currently executing thread to yield to another thread. Note that this is a static method.

### Blocked and Waiting Threads

- When the thread tries to acquire an intrinsic object lock that is currently held by another thread, it becomes blocked. 
The thread becomes unblocked when all other threads have relinquished the lock and the thread scheduler has allowed this thread to hold it.
- When the thread waits for another thread to notify the scheduler of a condition, it enters the waiting state.
In practice, the difference between the blocked and waiting state is not significant
- Several methods have a timeout parameter. Calling them causes the thread to enter the timed waiting state. This state persists either until the timeout expires or the appropriate notification has been received. Methods with timeout include Thread.sleep and the timed versions of Object.wait, Thread.join, Lock.tryLock, and Condition.await


### Terminated

A thread is terminated for one of two reasons:
- It dies a natural death because the run method exits normally.
- It dies abruptly because an uncaught exception terminates the run method.

In particular, you can kill a thread by invoking its stop method. That method throws a ThreadDeath error object that kills the thread. However, the stop method is deprecated, and you should never call it in your own code.


- **void join()** waits for the specified thread to terminate.

- **void join(long millis)** waits for the specified thread to die or for the specified number of milliseconds to pass.

- **Thread.State getState()** gets the state of this thread: one of **NEW**, **RUNNABLE**, **BLOCKED**, **WAITING**, **TIMED_WAITING**, or **TERMINATED**.

### Thread properties
