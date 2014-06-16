package edu.vuum.mocca;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

/**
 * @class SimpleSemaphore 
 * @brief This class provides a simple counting semaphore implementation using
 *        Java a ReentrantLock and a ConditionObject (which is accessed via a
 *        Condition). It must implement both "Fair" and "NonFair" semaphore
 *        semantics, just liked Java Semaphores.
 */
public class SimpleSemaphore {
	/**
	 * Define a ReentrantLock to protect the critical section.
	 */
	// TODO - you fill in here
	ReentrantLock reentrantLock;
	/**
	 * Define a Condition that waits while the number of permits is 0.
	 */
	// TODO - you fill in here
	final Condition condition;

	/**
	 * Define a count of the number of available permits.
	 */
	// TODO - you fill in here. Make sure that this data member will
	// ensure its values aren't cached by multiple Threads..
	volatile int count;

	public SimpleSemaphore(int permits, boolean fair) {
		// TODO - you fill in here to initialize the SimpleSemaphore,
		// making sure to allow both fair and non-fair Semaphore
		// semantics.
		reentrantLock = new ReentrantLock(fair);
		condition = reentrantLock.newCondition();
		count = permits;
		// NEw condition reentrantlock already

	}

	/**
	 * Acquire one permit from the semaphore in a manner that can be
	 * interrupted.
	 */
	public void acquire() throws InterruptedException {
		// TODO - you fill in here.
		try {
			reentrantLock.lockInterruptibly();
			while (count == 0) {
				condition.await();
			}
			count--;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			reentrantLock.unlock();
		}
	}

	/**
	 * Acquire one permit from the semaphore in a manner that cannot be
	 * interrupted.
	 */
	public void acquireUninterruptibly() {
		// TODO - you fill in here.
		try {
			reentrantLock.lock();
			while (count == 0) {
				condition.awaitUninterruptibly();
			}
			count--;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			reentrantLock.unlock();
		}
	}

	/**
	 * Return one permit to the semaphore.
	 */
	void release() {
		// TODO - you fill in here.
		try {
			reentrantLock.lock();
			count++;
			condition.signal();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			reentrantLock.unlock();
		}
	}

	/**
	 * Return the number of permits available.
	 */
	public int availablePermits() {
		// TODO - you fill in here by changing null to the appropriate
		// return value.
		return count;
	}
}
