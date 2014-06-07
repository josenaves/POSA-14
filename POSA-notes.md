Pattern-Oriented Software Architectures: Programming Mobile Services for Android Handheld Systems
-------------------------------------------------------------------------------------------------

# Java Synchronization & Scheduling Classes

ReentrantLock: a reentrant mutual exclusion lock that extends the built-in monitor lock capabilities
ReentrantReadWriteLock: improves performance when resources are read much more often than written 
Semaphore: a non-negative integer that controls the access of multiple threads to a limited number of shared resources
ConditionObject: block thread(s) until some condition(s) becomes true
CountDownLatch: allows one or more threads to wait until a set of operations being performed in other threads complete

These classes are distinct from Java’s built-in monitor object mechanisms, e.g., the synchronized keyword & the wait(), notify(), & notifyAll() methods

Some issues to consider when choosing between classes: 
* ReentrantLocks have lower overhead than ReentrantReadWriteLocks 
* ConditionObjects & Semaphores have higher overhead than ReentrantLocks & ReentrantReadWriteLocks 
* ConditionObjects have a different purpose than other locks 
* A thread uses a lock to keep other threads out of a critical section 
* A thread uses a ConditionObject to keep itself out of a critical section until it can make forward progress 



Mutual Exclusion Locks
----------------------

Mutual exclusion locks define a “critical section” that can only be executed by one thread at a time.

http://en.wikipedia.org/wiki/Mutual_exclusion


A human known use of mutual exclusion is an airplane restroom protocol.

http://tutorials.jenkov.com/java-concurrency/locks.html#finally

# ReentrantLock

ReentrantLock implements the Lock interface to provide mutual exclusion.

ReentrantLock uses the Bridge pattern.
http://en.wikipedia.org/wiki/Bridge_pattern

Inherits functionality from the AbstractQueuedSynchronizer class
http://developer.android.com/reference/java/util/concurrent/locks/AbstractQueuedSynchronizer.html


ReentrantLock provides a lightweight mutual exclusion mechanism.

Optionally implement fair or non-fair lock acquisition model.

Similar to built-in monitor lock used in Java’s synchronized methods & statements.

It key methods are variants of lock() & unlock().

lock() acquires the lock if it’s available.
unlock() attempts to release the lock.

tryLock() aquires the lock only if it is hot held by another thread at the time of invocation.
lockInterruptibly() acquires the lock unless thread is interrupted.

These methods simply forward to their implementations. Non-fair implementations are optimized.

http://en.wikipedia.org/wiki/Spinlock

Spinlock is a lock which causes a thread trying to acquire it to simply wait in a loop ("spin") while repeatedly checking if the lock is available. Since the thread remains active but is not performing a useful task, the use of such a lock is a kind of busy waiting.


It must be used via a “fully bracketed” protocol (the thread that locks is responsible for unlock).

ReentrantLocks can be tedious and error-prone to program due to common mistakes:
* acquiring a lock and forgetting to release it
* releasing a lock that was never requested
* holding a lock for a long time without needing it
* accessing a resource without acquiring a lock for it first (or after releasing it)


# ReentrantReadWriteLock

http://en.wikipedia.org/wiki/Readers-writer_lock

A readers-writer lock is a synchronization mechanism often used in contexts with many concurrent threads.

It allows access to a shared resource either by:

* Multiple threads that have read-only access or
* Only one thread that has write access

They may help improve performance when resources are read much more often than written. Especially on multi- 
core & multi-processor platforms.

http://developer.android.com/reference/java/util/concurrent/locks/ReadWriteLock.html

A ReadWriteLock maintains a pair of associated locks, one for read-only operations and one for writing. The read lock may be held simultaneously by multiple reader threads, so long as there are no writers. The write lock is exclusive.

A human known use of the readers-writer locking protocol is formal political debates. 

ReentrantReadWriteLock uses the Bridge pattern.


# Semaphores

A semaphore can be atomically incremented & decremented to control access to a shared resource.
They are used to synchronize & schedule the interactions between multiple concurrent threads.

http://en.wikipedia.org/wiki/Semaphore_(programming)

A semaphore can be atomically incremented & decremented to control access to a shared resource 
• They are used to synchronize & schedule the interactions between multiple concurrent threads 
• e.g., limit the number of resources devoted to a particular task 
• Consider image rendering threads accessing a fixed number of cores 
• Only allow use of two cores to ensure system responsiveness 
• A permit must be acquired from a semaphore before thread can run 
• Other threads will block, until a permit is returned to semaphore 

There are two types of semaphores 
• Counting semaphores 
• Binary semaphores which are restricted to the values 0 & 1

A human known use of counting semaphores applies them to schedule access to beach volleyball courts. 


Acquiring & releasing permits to a Semaphore need not be fully bracketed.

A Semaphore provides a flexible synchronization & scheduling mechanism 
• Its acquire & release methods need not be fully bracketed 
• It supports several types of acquire & release operations 
• Its blocking operations are implemented via “sleep” locks, which trigger a context switch 
• When used for a resource pool, it tracks how many resources are free, not which resources are free 
• Other mechanisms may be needed to select a particular free resource


Semaphores can be tedious & error-prone to program due to the following common mistakes 
• Requesting a permit & forgetting to release it 
• Releasing a permit that was never requested 
• Holding a permit for a long time without needing it 
• Accessing a resource without requesting a permit for it first (or after releasing it)

ConditionObject
---------------

http://en.wikipedia.org/wiki/Guarded_suspension.

Guarded Suspension is a software design pattern for managing operations that require both a lock to be acquired and a precondition to be satisfied before the operation can be executed. The guarded suspension pattern is typically applied to method calls in object-oriented programs, and involves suspending the method call, and the calling thread, until the precondition (acting as a guard) is satisfied.

• A thread can suspend its execution on a condition variable another thread notifies it that shared state it's waiting on may now be true 
• A condition can be arbitrarily complex 
• Waiting on a condition variable releases lock & suspends thread atomically 
• The thread suspended until another thread signals the condition 


Condition variables form the basis for synchronization & scheduling mechanisms in Java & Android.

Condition variables are powerful, but can be hard to understand & apply.

A human known use is a pizza delivery protocol.

A ConditionObject provides a flexible synchronization & scheduling mechanism 
• Allows threads to suspend & resume their execution 
• An object can have multiple ConditionObjects 
• It is always used in conjunction with a lock 
• It supports several types of wait operations 
• It should always be waited upon in a loop 
• Test state predicate being waited for 
• Guard against spurious wakeups

http://en.wikipedia.org/wiki/Spurious_wakeup

ConditionObject is used sparingly in Android 
• Mostly in java.util.concurrent & java.util.concurrent.locks 
• Thus used in Android’s concurrency frameworks

CountDownLatch
--------------

Understand how CountDownLatch allows one or more threads to wait until a set of operations being performed in other threads completes.

A barrier is a synchronization method that halts the progress of one or more threads at a particular point.

Barriers can be used in several ways 
• Defer the start of concurrent computations until an object is initialized 
• Wait until all concurrent threads are done with their processing before continuing

A human known use is the protocol used by a tour guide.

A CountDownLatch is a versatile barrier synchronization tool 
• It can be used for several purposes 
• It can be a simple on/off latch 
• It can make one thread wait until N threads have completed some action or some action has been completed N times 
• e.g., the main thread waits until the worker threads are finished converting the video 

It supports several types of await operations 
• e.g., interruptible & timed operations

CountDownLatch is used throughout Android.

---

Ping Pong solution
------------------

“Gang-of-Four” patterns are applied to enhance the framework-based solution 
• e.g., the Template Method, Strategy, & Factory Method patterns 
• Benefits include greater reusability, flexibility, & portability 
• Simplify porting to new platforms


--------

June, 7

Keyword volatile ensures that a data member is not cached for access by multiple threads.

Fairness = the longest waiting thread will get the lock

lock and lockInterruptibly -->  it allows the thread to immediately react to the interrupt signal sent to it from another thread