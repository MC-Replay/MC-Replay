package mc.replay.replay;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mc.replay.api.recording.recordables.Recordable;

import java.util.*;

@Getter
@RequiredArgsConstructor
public final class Replay {

    private final String id;
    private final UUID suspect;
    private final long createdAt;

    private final Map<Long, Collection<Recordable>> recordables;
    private final List<UUID> reportedBy = new ArrayList<>();

    public void addReporter(UUID playerUUID) {
        if (!this.reportedBy.contains(playerUUID)) {
            this.reportedBy.add(playerUUID);
        }
    }
}