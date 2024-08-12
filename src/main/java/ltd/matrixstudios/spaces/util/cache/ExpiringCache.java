package ltd.matrixstudios.spaces.util.cache;

import java.util.HashMap;
import java.util.Map;

public class ExpiringCache<K, V> {
    private final Map<K, V> cache = new HashMap<>();

    public ExpiringCache(Long duration) {
        Thread thread = new Thread(() -> {
            try {
                cache.clear();
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread.start();
    }

    public void set(K key, V value) {
        cache.put(key, value);
    }

    public Map<K, V> get() {
        return cache;
    }

    public boolean clear() {
        return cache.isEmpty();
    }
}
