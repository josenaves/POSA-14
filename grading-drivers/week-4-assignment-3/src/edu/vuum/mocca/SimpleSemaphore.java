package edu.vuum.mocca;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @class SimpleSemaphore
 * 
 * @brief This class provides a simple counting semaphore
 *        implementation using Java a ReentrantLock and a
 *        ConditionObject (which is accessed via a Condition). It must
 *        implement both "Fair" and "NonFair" semaphore semantics,
 *        just liked Java Semaphores.
 */
public class SimpleSemaphore {
    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO (DONE) - you fill in here
	private ReentrantLock lock;

    /**
     * Define a Condition that waits while the number of permits is 0.
     */
    // TODO (DONE) - you fill in here
	private Condition condition;

    /**
     * Define a count of the number of available permits.
     */
	private int counter;
    // TODO (DONE) - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..

    public SimpleSemaphore(int permits, boolean fair) {
        // TODO (DONE) - you fill in here to initialize the SimpleSemaphore,
        // making sure to allow both fair and non-fair Semaphore
        // semantics.
    	this.lock = new ReentrantLock(fair);
    	this.condition = this.lock.newCondition();
    	this.counter = permits;
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO (DONE) - you fill in here.
    	this.lock.lock();
    	try {
    		while (availablePermits() == 0)
    			this.condition.await();
    		this.counter--;
    	} finally {
    		this.lock.unlock();
    	}
    }

    /**
     * Acquire one permit from the semaphore in a manner that cannot be
     * interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO (DONE) - you fill in here.
    	this.lock.lock();
    	try {
    		while (availablePermits() == 0)
    			this.condition.awaitUninterruptibly();
    		this.counter--;
    	} finally {
    		this.lock.unlock();
    	}
    }

    /**
     * Return one permit to the semaphore.
     */
    void release() {
        // TODO - you fill in here.
    	this.lock.lock();
    	try {
    		this.counter++;
    		this.condition.signal();
    	} finally {
    		this.lock.unlock();
    	}
    }

    /**
     * Return the number of permits available.
     */
    public int availablePermits() {
        // TODO (DONE) - you fill in here by changing null to the appropriate
        // return value.
        return this.counter;
    }
}