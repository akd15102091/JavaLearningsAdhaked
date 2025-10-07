@SuppressWarnings({ "unchecked"})
public class HashMap<K,V>{
    private static final int CAPACITY = 1<< 16;
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    public Entry<K,V>[] hashTable;
    
    public HashMap(){
        hashTable = new Entry[CAPACITY];
    }

    public HashMap(int cap){
        int newCap = getCapacity(cap);
        hashTable = new Entry[newCap];
    }

   private int getCapacity(int cap){
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    public void put(K key,V val){
        int hashCode = key.hashCode()%hashTable.length;
        Entry<K,V> node = hashTable[hashCode];
        if(node==null){
            Entry<K,V> e = new Entry<>(key,val,null);
            hashTable[hashCode] = e;
        }
        else{
            while(node!=null && node.next!=null){
                // handle duplicate key
                if(node.getKey().equals(key)){
                    node.setValue(val);
                    return ;
                }
                
                node = node.next;
            }
            // handle duplicate key
            if(node.getKey().equals(key)){
                node.setValue(val);
                return ;
            }

            Entry<K,V> e = new Entry<>(key, val, null);
            node.next =e;
        }
    }

    public V get(K key){
        int hashCode = key.hashCode()%hashTable.length;
        Entry<K,V> node = hashTable[hashCode];
        while(node!=null){
            if(key==node.getKey()){
                return (V) node.getValue();
            }
            node = node.next;
        }
        
        return null;
    }

    public static void main(String[] args) {
        HashMap<Integer,String> map = new HashMap<>(7);
        map.put(1, "hi");
        map.put(2, "my");
        map.put(3, "name");
        map.put(4, "is");
        map.put(5, "Shrayansh");
        map.put(6, "how");
        map.put(7, "are");
        map.put(8, "you");
        map.put(9, "friends");
        map.put(10, "?");

        System.out.println("-----------------");
        String value = map.get(5);
        System.out.println(value);
        map.put(5, "AKD");
        System.out.println(map.get(5));

    }
}
