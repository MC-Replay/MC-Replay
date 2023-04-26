package mc.replay.recording;

import lombok.RequiredArgsConstructor;
import mc.replay.api.recording.IRecordingSession;
import mc.replay.api.recording.RecordingSessionBuilder;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.RecPlayerSpawn;
import mc.replay.wrapper.entity.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@RequiredArgsConstructor
final class RecordingSessionBuilderImpl implements RecordingSessionBuilder {

    private final RecordingHandler recordingHandler;
    private World world = null;

    @Override
    public @NotNull RecordingSessionBuilder world(@NotNull World world) {
        this.world = world;
        return this;
    }

    @Override
    public @NotNull RecordingSessionBuilder world(@NotNull String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            throw new IllegalArgumentException("There was no world found with the name '" + worldName + "'");
        }

        return this.world(world);
    }

    @Override
    public @NotNull IRecordingSession startRecording() {
        if (this.world == null) {
            throw new IllegalStateException("No world was set for this recording session");
        }

        RecordingSession recordingSession = new RecordingSession(this.world);
        this.recordingHandler.getRecordingSessions().put(recordingSession.getSessionUuid(), recordingSession);

        // TODO move somewhere else
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerWrapper playerWrapper = new PlayerWrapper(player);
            EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());

            recordingSession.addRecordables(List.of(new RecPlayerSpawn(entityId, playerWrapper)));
        }

        return recordingSession;
    }
}