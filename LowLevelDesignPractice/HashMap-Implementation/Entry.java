@SuppressWarnings({"rawtypes"})
public class Entry<K,V>{ 
    K key;
    V value;
    Entry next;
    public K getKey() {
        return key;
    }
    public void setKey(K key) {
        this.key = key;
    }
    public Entry(K key, V value, Entry next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }
    public V getValue() {
        return value;
    }
    public void setValue(V value) {
        this.value = value;
    }
    public Entry getNext() {
        return next;
    }
    public void setNext(Entry next) {
        this.next = next;
    }
}