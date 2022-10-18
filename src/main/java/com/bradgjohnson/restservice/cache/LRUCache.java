package com.bradgjohnson.restservice.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class LRUCache<K, V> {
    private final long maxSize;
    private final AtomicLong currentSize;
    private final Map<K, Node<CacheElement<K,V>>> cache;

    private Node<CacheElement<K, V>> lruRoot;
    private Node<CacheElement<K, V>> mruTail;

    public LRUCache(long maxSize) {
        this.maxSize = maxSize;
        this.currentSize = new AtomicLong();
        this.cache = new ConcurrentHashMap<>();
        this.lruRoot = null;
        this.mruTail = null;
    }

    public V get(K key) {
        if (!contains(key)) {
            return null;
        }
        Node<CacheElement<K, V>> node = cache.get(key);
        if (node != mruTail) {
            if (node != lruRoot) {
                node.prev.next = node.next;
            } else {
                lruRoot = node.next;
            }
            node.next.prev = node.prev;
            mruTail.next = node;
            node.prev = mruTail;
            node.next = null;
            mruTail = node;
        }
        return node.value.getValue();
    }

    public boolean contains(K key) {
        return cache.containsKey(key);
    }

    public boolean put(K key, V value) {
        if (this.currentSize.get() >= this.maxSize) {
            removeLRUItem();
        }

        putItem(new CacheElement<>(key, value));
        return true;
    }

    public void remove(K key) {
        cache.remove(key);
        this.currentSize.addAndGet(-1);
    }

    private void removeLRUItem() {
        if (lruRoot == null) {
            this.currentSize.set(0);
            this.mruTail = null;
            return;
        }

        CacheElement<K, V> item = lruRoot.value;
        if (lruRoot == mruTail) {
            this.lruRoot = null;
            this.mruTail = null;
            this.currentSize.set(0);
        } else {
            this.lruRoot = this.lruRoot.next;
            this.lruRoot.prev = null;
        }

        remove(item.getKey());
    }

    private void putItem(CacheElement<K, V> item) {
        if (this.lruRoot == null) {
            this.lruRoot = new Node<>();
            this.lruRoot.value = item;
            this.mruTail = this.lruRoot;
        } else {
            this.mruTail.next = new Node<>();
            this.mruTail.next.prev = this.mruTail;
            this.mruTail = this.mruTail.next;
            this.mruTail.value = item;
        }
        cache.put(item.getKey(), this.mruTail);
        this.currentSize.addAndGet(1);
    }

    public String toString() {
        Node<CacheElement<K, V>> tmp = lruRoot;
        StringBuilder sb = new StringBuilder();
        sb.append("LRU->[");
        while (tmp != null) {
            sb.append("\n");
            if (tmp.prev != null) {
                sb.append(tmp.prev.value.getKey());
            } else {
                sb.append("null");
            }
            sb.append(" <-- ").append(tmp.value.getKey()).append(" --> ");
            if (tmp.next != null) {
                sb.append(tmp.next.value.getKey());
            } else {
                sb.append("null");
            }
            if (tmp == mruTail) {
                sb.append(" <-- MRU tail");
                break;
            }
            tmp = tmp.next;
        }
        sb.append("\n]");
        sb.append("\nMap->[");
        cache.values().forEach(ce -> sb.append("\n").append(ce.value.getKey()));
        sb.append("\n]");

        return sb.toString();
    }
}
