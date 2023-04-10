package mc.replay.replay;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mc.replay.api.recording.recordables.Recordable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public final class Replay {

    private final String id;
    private final UUID suspect;
    private final long createdAt;

    private final Map<Integer, List<Recordable>> recordables;
    private final List<UUID> reportedBy = new ArrayList<>();

    public void addReporter(UUID playerUUID) {
        if (!this.reportedBy.contains(playerUUID)) {
            this.reportedBy.add(playerUUID);
        }
    }
}