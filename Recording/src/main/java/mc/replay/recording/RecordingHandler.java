package mc.replay.recording;

import com.mojang.authlib.properties.Property;
import lombok.Getter;
import mc.replay.api.recording.IRecordingHandler;
import mc.replay.api.recording.Recording;
import mc.replay.api.recording.RecordingSession;
import mc.replay.api.recording.contestant.RecordingContestant;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.nms.NMSCore;
import mc.replay.nms.global.recordable.RecPlayerSpawn;
import mc.replay.recording.chunk.ChunkRecordingHandler;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
public final class RecordingHandler implements IRecordingHandler {

    private final Map<UUID, RecordingSession> recordingSessions = new HashMap<>();

    private final NMSCore nmsCore;
    private final ChunkRecordingHandler chunkRecordingHandler;

    public RecordingHandler(NMSCore nmsCore) {
        this.nmsCore = nmsCore;
        this.chunkRecordingHandler = new ChunkRecordingHandler();
    }

    @Override
    public @NotNull RecordingSession startRecording(@NotNull RecordingContestant contestant) {
        RecordingSessionImpl recordingSession = new RecordingSessionImpl(this, contestant);
        this.recordingSessions.put(recordingSession.getSessionUuid(), recordingSession);

        this.chunkRecordingHandler.startTask(recordingSession);

        // TODO move somewhere else
        for (Player player : Bukkit.getOnlinePlayers()) {
            EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
            Property skinTexture = entityPlayer.getProfile().getProperties().get("textures").stream()
                    .findFirst()
                    .orElse(null);

            EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());

            recordingSession.addRecordables(List.of(RecPlayerSpawn.of(entityId, player.getName(), skinTexture, player.getLocation())));
        }

        return recordingSession;
    }

    @Override
    public @NotNull Recording stopRecording(@NotNull UUID sessionUuid) {
        RecordingSession recordingSession = this.recordingSessions.remove(sessionUuid);
        if (recordingSession == null) {
            throw new IllegalStateException("No recording session found for session uuid '" + sessionUuid + "'");
        }

        return new RecordingImpl(recordingSession.getStartTime(), new TreeMap<>(recordingSession.getRecordables()));
    }

    @Override
    public boolean cancelRecording(@NotNull UUID sessionUuid) {
        return this.recordingSessions.remove(sessionUuid) != null;
    }

    public boolean shouldRecord() {
        return !this.recordingSessions.isEmpty();
    }
}