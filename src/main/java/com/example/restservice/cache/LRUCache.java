package main.java.com.example.restservice.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class LRUCache<K, V extends ItemWithSize> {
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
        while (cacheTooFullFor(value)) {
            if (currentSize.get() == 0) {
                // Item is too big for cache
                System.err.println("Item is too big for cache: " + value + "/" + maxSize);
                return false;
            }
            removeLRUItem();
        }

        putItem(new CacheElement<>(key, value));
        return true;
    }

    private boolean cacheTooFullFor(V value) {
        return (this.currentSize.get() + value.getSizeBytes()) > this.maxSize;
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

        cache.remove(item.getKey());
        this.currentSize.addAndGet(-item.getValue().getSizeBytes());
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
        this.currentSize.addAndGet(item.getValue().getSizeBytes());
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
        cache.values().forEach(ce -> sb.append("\n").append(ce.value.getKey()).append(" : ").append(ce.value.getValue().getSizeBytes()));
        sb.append("\n]");

        return sb.toString();
    }
}
