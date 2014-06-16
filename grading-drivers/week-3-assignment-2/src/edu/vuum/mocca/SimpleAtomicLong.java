package edu.vuum.mocca;

// Import the necessary Java synchronization and scheduling classes.


import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

/**
 * @class SimpleAtomicLong
 *
 * @brief This class implements a subset of the
 *        java.util.concurrent.atomic.SimpleAtomicLong class using a
 *        ReentrantReadWriteLock to illustrate how they work.
 */
class SimpleAtomicLong
{
    /**
     * The value that's manipulated atomically via the methods.
     */
    private long mValue;
    
    /**
     * The ReentrantReadWriteLock used to serialize access to mValue.
     */

    // TODO -- you fill in here by replacing the null with an
    // initialization of ReentrantReadWriteLock.
    private ReentrantReadWriteLock mRWLock = new ReentrantReadWriteLock();
    private Lock mRLock = mRWLock.readLock();
    private Lock mWLock = mRWLock.writeLock();

    /**
     * Creates a new SimpleAtomicLong with the given initial value.
     */
    public SimpleAtomicLong(long initialValue)
    {
        // TODO -- you fill in here
    	try {
    		mWLock.lock();
    		mValue = initialValue;
    	} finally {
    		mWLock.unlock();
    	}
    }

    /**
     * @brief Gets the current value.
     * 
     * @returns The current value
     */
    public long get()
    {
        long value = 0;

        // TODO -- you fill in here
        try {
        	mRLock.lock();
        	value = mValue;
        } finally {
        	mRLock.unlock();
        }

        return value;
    }

    /**
     * @brief Atomically decrements by one the current value
     *
     * @returns the updated value
     */
    public long decrementAndGet()
    {
        long value = 0;

        // TODO -- you fill in here
        try {
        	mWLock.lock();
        	value = --mValue;
        } finally {
        	mWLock.unlock();
        }

        return value;
    }

    /**
     * @brief Atomically increments by one the current value
     *
     * @returns the previous value
     */
    public long getAndIncrement()
    {
        long value = 0;

        // TODO -- you fill in here
        try {
        	mWLock.lock();
        	value = mValue++;
        } finally {
        	mWLock.unlock();
        }
        return value;
    }

    /**
     * @brief Atomically decrements by one the current value
     *
     * @returns the previous value
     */
    public long getAndDecrement()
    {
        long value = 0;

        // TODO -- you fill in here
        try {
        	mWLock.lock();
        	value = mValue--;
        } finally {
        	mWLock.unlock();
        }
        return value;
    }

    /**
     * @brief Atomically increments by one the current value
     *
     * @returns the updated value
     */
    public long incrementAndGet()
    {
        long value = 0;

        // TODO -- you fill in here
        try {
        	mWLock.lock();
        	value = ++mValue;
        } finally {
        	mWLock.unlock();
        }
        return value;
    }
}
