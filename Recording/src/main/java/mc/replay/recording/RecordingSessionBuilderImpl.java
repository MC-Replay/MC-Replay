package mc.replay.recording;

import com.mojang.authlib.properties.Property;
import lombok.RequiredArgsConstructor;
import mc.replay.api.recording.RecordingSession;
import mc.replay.api.recording.RecordingSessionBuilder;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.nms.global.recordable.RecPlayerSpawn;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
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
    public @NotNull RecordingSession startRecording() {
        if (this.world == null) {
            throw new IllegalStateException("No world was set for this recording session");
        }

        RecordingSessionImpl recordingSession = new RecordingSessionImpl(this.world);
        this.recordingHandler.getRecordingSessions().put(recordingSession.getSessionUuid(), recordingSession);

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
}