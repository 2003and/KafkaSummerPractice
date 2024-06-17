package uni.dstu.kafka.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheService {
    private static final int CACHE_SIZE_LIMIT = 3;
    private final LinkedList<String> cache = new LinkedList<>();

    public LinkedList<String> getCacheEntries() {
        return new LinkedList<>(cache);
    }

    public synchronized void addToCache(String json) {
        cache.add(json);
        if (cache.size() > CACHE_SIZE_LIMIT) {
            cache.removeFirst();
        }
    }

    public void deleteCacheEntry(int id) {
        cache.remove(id);
    }
}
