package sharpener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * A HashMap that keeps time stamps for key-value pairs and removes them after they
 * were not accessed for a certain time.
 */
public class TimeOrderedMap<S,T> {

	private static class Entry<S,T> {

		private final S key;
		private final T entry;
		private Entry<S,T> prev = null;
		private Entry<S,T> next = null;
		private long lastTime = new Date().getTime();


		Entry(S key, T entry) {
			this.entry = entry;
			this.key = key;
		}

	}

	private final long expirationTime;

	// doubly-linked queue ordered by time
	private Entry<S,T> head = new Entry<S,T>(null, null);
	private Entry<S,T> tail = head;

	// underlying map
	private final Map<S,Entry<S,T>> map = new HashMap<S,Entry<S,T>>();
	

	TimeOrderedMap(long expirationTime) {
		super();
		this.expirationTime = expirationTime;
	}


	/**
	 * Returns true if this map contains a mapping for the specified key.
	 */
	synchronized boolean containsKey(final S key) {
		return map.containsKey(key);
	}


	/**
	 * Returns the value to which the specified key is mapped, or null if this map
	 * contains no mapping for the key.
	 */
	synchronized T get(final S key) {
		Entry<S,T> entry = map.get(key);
		if (entry == null) {
			return null;
		}

		if (entry != tail) {
			// move to the end of the queue
			entry.lastTime = new Date().getTime();
			entry.prev.next = entry.next;
			entry.next.prev = entry.prev;
			entry.next = null;
			entry.prev = tail;
			tail.next = entry;
			tail = entry;
		}
		return entry.entry;
	}


	/**
	 * Associates the specified value with the specified key in this map.
	 */
	synchronized void put(final S key, final T value) {

		final Entry<S,T> entry = new Entry<S,T>(key, value);
		map.put(key, entry);

		// put new entry to the end of the queue
		entry.prev = tail;
		tail.next = entry;
		tail = entry;
		
		// remove old entries in the front of the queue
		final long now = new Date().getTime();
		Entry<S,T> e = head.next;
		while (now - e.lastTime > expirationTime && e.next != null) {
			map.remove(e.key);
			e = e.next;
			head.next = e;
		}
	}
}
