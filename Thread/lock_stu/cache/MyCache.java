package cache;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用读写锁实现一个缓存
 * @param <K>
 * @param <V>
 */
public class MyCache<K, V> {
	private HashMap<K, V> hashMap;
	private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

	private Lock readLock = reentrantReadWriteLock.readLock();
	private Lock writeLock = reentrantReadWriteLock.writeLock();

	public V get(K k) {
		readLock.lock();
		try {
			return hashMap.get(k);
		}finally {
			readLock.unlock();
		}
	}

	public V put(K k, V v) {
		writeLock.lock();
		try {
			return hashMap.put(k, v);
		} finally {
			writeLock.unlock();
		}
	}
}
