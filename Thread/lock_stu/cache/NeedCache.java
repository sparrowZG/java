package cache;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 按需加载缓存:
 * 如果缓存中没有就去数据库中查
 */
public class NeedCache<K, V> {
	private HashMap<K, V> hashMap = new HashMap<>();

	private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
	private Lock r = reentrantReadWriteLock.readLock();
	private Lock w = reentrantReadWriteLock.writeLock();

	public V get(K k) {
		V v = null;
		r.lock();
		try {
			v = hashMap.get(k);
		} finally {
			r.unlock();
		}
		if (v != null) {
			return v;
		}
		//查缓存,并将查出来的结果写入缓存
		w.lock();
		try {
			v = hashMap.get(k);
			if (v == null) {
				//开始查数据库
				//v= ?
				hashMap.put(k,v);
			}
		} finally {
			w.unlock();
		}
		return v;
	}
}
