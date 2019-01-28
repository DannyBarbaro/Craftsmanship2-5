import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//Author: Daniel Barbaro
//This class acts as a cache for the package
public final class Cache<T, V> {

    private Map<T, V> cache = new HashMap<>();

    public V get(T key, Function<? super T, ? extends V> constructor) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException("Cannot proccess a null key value");
        } else if (cache.get(key) != null) {
            return cache.get(key);
        } else if (constructor == null) {
            throw new NullPointerException("Cannot create new cache item with a null constructor");
        } else {
            V value = constructor.apply(key);
            cache.put(key, value);
            return value;
        }
    }
}