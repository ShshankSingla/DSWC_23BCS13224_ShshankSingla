import java.util.LinkedHashMap;
import java.util.Map;

public class VideoCache<K, V>
        extends LinkedHashMap<K, V> {

    private final int capacity;

    public VideoCache(int capacity) {

        super(
                capacity,
                0.75f,
                true
        );

        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(
            Map.Entry<K, V> eldest) {

        return size() > capacity;
    }

    public static void main(String[] args) {

        VideoCache<Integer, String> cache =
                new VideoCache<>(3);

        cache.put(1, "Movie A");
        cache.put(2, "Movie B");
        cache.put(3, "Movie C");

        cache.get(1);

        cache.put(4, "Movie D");

        System.out.println(cache);
    }
}