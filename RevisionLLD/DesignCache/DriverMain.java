package DesignCache;

public class DriverMain {
    public static void main(String[] args) {
        Cache<String, Integer> cache = new LRUCache<>(3);

        cache.put("key1", 10, System.currentTimeMillis() + 2000);
        System.out.println("get : key1 - "+cache.get("key1"));
        cache.put("key2", 20, System.currentTimeMillis() + 1000);
        System.out.println("get : key2 - "+cache.get("key2"));
        cache.put("key3", 30, System.currentTimeMillis() + 1500);
        System.out.println("get : key3 - "+cache.get("key3"));
        cache.put("key4", 40, System.currentTimeMillis() + 1500);
        System.out.println("get : key4 - "+cache.get("key4"));
        System.out.println("get : key1 - "+cache.get("key1"));
        System.out.println("get : key2 - "+cache.get("key2"));
        cache.put("key5", 50, System.currentTimeMillis() + 1500);
        System.out.println("get : key5 - "+cache.get("key5"));
        System.out.println("get : key3 - "+cache.get("key3"));
        
    }
}
