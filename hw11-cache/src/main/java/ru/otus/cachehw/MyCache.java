package ru.otus.cachehw;

import java.lang.ref.WeakReference;
import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {
    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();

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
        listeners.add(new WeakReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.removeIf(weakReference -> listener.equals(weakReference.get()));
    }

    private void notifyListeners(K key, V value, ActionType actionType) {
        for (WeakReference<HwListener<K, V>> weakReference : listeners) {
            Objects.requireNonNull(weakReference.get()).notify(key, value, actionType.getType());
        }
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}
