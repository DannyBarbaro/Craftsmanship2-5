package parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

// Author: Daniel Barbaro
// This class acts as a cache for the package
public final class Cache<T, V> {

    private Map<T, V> cache = new HashMap<>();

    public V get(T key, Function<? super T, ? extends V> constructor) {
        Objects.requireNonNull(key, "Cannot process a null key");
        Objects.requireNonNull(constructor, "Cannot process a null constructor");
        return cache.computeIfAbsent(key, constructor);
    }
}