package us.hcheng.javaio.thread.jcu.concurrent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CopyOnWriteHashMap<K, V> implements Map<K, V> {

	private HashMap<K, V> map;

	private final Object lock = new Object();

	public CopyOnWriteHashMap() {
		this.map = new HashMap<>();
	}

	@Override
	public int size() {
		return this.map.size();
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return this.map.get(key);
	}

	@Override
	public V put(K key, V value) {
		synchronized (lock) {
			HashMap<K, V> newMap = new HashMap<>(this);
			newMap.put(key, value);
			setMap(newMap);
			return value;
		}
	}

	@Override
	public V remove(Object key) {
		synchronized (lock) {
			HashMap<K, V> newMap = new HashMap<>(this);
			V ret = newMap.remove(key);
			setMap(newMap);
			return ret;
		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		synchronized (lock) {
			HashMap<K, V> newMap = new HashMap<>(this);
			newMap.putAll(m);
			setMap(newMap);
		}
	}

	@Override
	public void clear() {
		synchronized (lock) {
			setMap(new HashMap<>());
		}
	}

	@Override
	public Set<K> keySet() {
		return this.map.keySet();
	}

	@Override
	public Collection<V> values() {
		return this.map.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return this.entrySet();
	}

	private synchronized void setMap(HashMap<K, V> newMap) {
		this.map = newMap;
	}

}
