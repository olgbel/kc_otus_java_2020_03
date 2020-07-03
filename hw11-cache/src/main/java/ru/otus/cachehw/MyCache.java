package ru.otus.cachehw;

import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {
    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notifyListeners(key, value, ActionType.PUT);
    }

    @Override
    public void remove(K key) {
        V value = cache.remove(key);
        notifyListeners(key, value, ActionType.REMOVE);
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        notifyListeners(key, value, ActionType.GET);
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listenerToRemove) {
        listeners.removeIf(listenerToRemove::equals);
    }

    private void notifyListeners(K key, V value, ActionType actionType) {
        listeners.forEach(listener -> listener.notify(key, value, actionType.getType()));
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}
