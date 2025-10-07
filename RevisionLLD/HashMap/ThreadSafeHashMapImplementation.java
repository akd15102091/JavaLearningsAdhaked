package HashMap;

import java.util.Objects;

class ThreadSafeHashMap<K,V> {
    private static final int DEFAULT_CAPACITY = 16;

    private final Node<K, V>[] buckets;
    private final Object[] locks;

    @SuppressWarnings("unchecked")
    public ThreadSafeHashMap(int capacity) {
        buckets = new Node[capacity];
        locks = new Object[capacity];
        for (int i = 0; i < capacity; i++) {
            locks[i] = new Object();
        }
    }

    public ThreadSafeHashMap() {
        this(DEFAULT_CAPACITY);
    }

    // Node class for storing key-value pairs
    private static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // Hashing function to get the index
    private int getIndex(Object key) {
        return Math.abs(Objects.hashCode(key)) % buckets.length;
    }

    // Put key-value pair
    public void put(K key, V value) {
        int index = getIndex(key);
        synchronized (locks[index]) {
            Node<K, V> node = buckets[index];
            Node<K, V> prev = null;

            while (node != null) {
                if (node.key.equals(key)) {
                    node.value = value; // update value
                    return;
                }
                prev = node;
                node = node.next;
            }

            Node<K, V> newNode = new Node<>(key, value);
            if (prev == null) {
                buckets[index] = newNode;
            } else {
                prev.next = newNode;
            }
        }
    }

    // Get value by key
    public V get(K key) {
        int index = getIndex(key);
        synchronized (locks[index]) {
            Node<K, V> node = buckets[index];
            while (node != null) {
                if (node.key.equals(key)) {
                    return node.value;
                }
                node = node.next;
            }
            return null;
        }
    }

    // Remove a key
    public void remove(K key) {
        int index = getIndex(key);
        synchronized (locks[index]) {
            Node<K, V> node = buckets[index];
            Node<K, V> prev = null;

            while (node != null) {
                if (node.key.equals(key)) {
                    if (prev == null) {
                        buckets[index] = node.next;
                    } else {
                        prev.next = node.next;
                    }
                    return;
                }
                prev = node;
                node = node.next;
            }
        }
    }    
}

public class ThreadSafeHashMapImplementation {
    public static void main(String[] args) throws InterruptedException {
        ThreadSafeHashMap<String, Integer> map = new ThreadSafeHashMap<>();

        Runnable writer = () -> {
            for (int i = 0; i < 100; i++) {
                map.put("key" + i, i);
            }
        };

        Runnable reader = () -> {
            for (int i = 0; i < 100; i++) {
                Integer val = map.get("key" + i);
                if (val != null) {
                    System.out.println(Thread.currentThread().getName() + " read: key" + i + " -> " + val);
                }
            }
        };

        Thread t1 = new Thread(writer, "Writer-1");
        Thread t2 = new Thread(writer, "Writer-2");
        Thread t3 = new Thread(reader, "Reader-1");
        Thread t4 = new Thread(reader, "Reader-2");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t1.join();
        t2.join();

        
        t3.join();
        t4.join();
    }
}
