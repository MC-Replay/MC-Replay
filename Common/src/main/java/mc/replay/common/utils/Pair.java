package mc.replay.common.utils;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public final class Pair<K, V> {

    public static <K, V> @NotNull Pair<K, V> of(K key, V value) {
        return new Pair<>(key, value);
    }

    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}