package mc.replay.storage;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import mc.replay.api.recordable.Recordable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.TimeUnit;

public final class ReplayStorage {

    private static final long CLEANUP_TIME = 20 * 60 * 10; // Every 10 minutes

    private long startTime = -1;
    private final Cache<Long, List<Recordable>> recordableCache;

    public ReplayStorage(JavaPlugin javaPlugin) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(javaPlugin, () -> {
            this.startTime = System.currentTimeMillis();
        });

        this.startTime = System.currentTimeMillis();

        this.recordableCache = CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();

        Bukkit.getScheduler().runTaskTimerAsynchronously(javaPlugin, this.recordableCache::cleanUp, CLEANUP_TIME, CLEANUP_TIME);
    }

    public void addRecordable(long millis, Recordable... recordables) {
        if (this.startTime == -1) return;

        long time = millis - this.startTime;
        List<Recordable> list = this.recordableCache.getIfPresent(time);
        if (list == null) list = new ArrayList<>();

        list.addAll(Arrays.asList(recordables));
        this.recordableCache.put(time, list);
    }

    public void addRecordable(int tick, Recordable... recordables) {
        if (this.startTime == -1) return;

        long time = this.startTime + tick * 50L;
        this.addRecordable(time, recordables);
    }

    public void addRecordable(Recordable... recordables) {
        this.addRecordable(System.currentTimeMillis(), recordables);
    }

    public <T extends Recordable> NavigableMap<Long, List<Recordable>> getTypeRecordables(Map<Long, List<Recordable>> recordables, Class<T> clazz, @Nullable Object matcher) {
        NavigableMap<Long, List<Recordable>> map = new TreeMap<>();

        for (Map.Entry<Long, List<Recordable>> entry : recordables.entrySet()) {
            for (Recordable recordable : entry.getValue()) {
                if (clazz.isAssignableFrom(recordable.getClass()) && (matcher == null || recordable.match(matcher))) {
                    map.putIfAbsent(entry.getKey(), new ArrayList<>());
                    map.get(entry.getKey()).add(clazz.cast(recordable));
                }
            }
        }

        return map;
    }

    public <T extends Recordable> NavigableMap<Long, List<Recordable>> getTypeRecordables(Map<Long, List<Recordable>> recordables, Class<T> clazz) {
        return this.getTypeRecordables(recordables, clazz, null);
    }

    public <T extends Recordable> NavigableMap<Long, List<Recordable>> getTypeRecordables(Class<T> clazz, @Nullable Object matcher) {
        return this.getTypeRecordables(this.recordableCache.asMap(), clazz, matcher);
    }

    public <T extends Recordable> NavigableMap<Long, List<Recordable>> getTypeRecordables(Class<T> clazz) {
        return this.getTypeRecordables(this.recordableCache.asMap(), clazz, null);
    }
}