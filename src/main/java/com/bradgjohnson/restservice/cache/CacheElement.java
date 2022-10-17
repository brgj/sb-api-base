package com.bradgjohnson.restservice.cache;

public class CacheElement<K, V> {
    private K key;

    private V value;

    public CacheElement(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }


}
