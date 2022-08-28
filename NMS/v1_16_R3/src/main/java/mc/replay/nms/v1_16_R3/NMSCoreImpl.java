package mc.replay.nms.v1_16_R3;

import com.mojang.authlib.properties.Property;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.nms.NMSCore;
import mc.replay.nms.global.recordable.RecEntityDestroy;
import mc.replay.nms.global.recordable.RecEntitySpawn;
import mc.replay.nms.global.recordable.RecPlayerDestroy;
import mc.replay.nms.global.recordable.RecPlayerSpawn;
import mc.replay.nms.player.RecordingFakePlayer;
import mc.replay.nms.v1_16_R3.player.RecordingFakePlayerImpl;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

public final class NMSCoreImpl implements NMSCore {

    private Consumer<Object> packetOutDispatcher = null;

    @Override
    public void setPacketOutDispatcher(@NotNull Consumer<Object> consumer) {
        this.packetOutDispatcher = consumer;
    }

    @Override
    public @NotNull RecordingFakePlayer createFakeRecordingPlayer(@NotNull Player target) {
        RecordingFakePlayerImpl recordingFakePlayer = new RecordingFakePlayerImpl(target);
        if (this.packetOutDispatcher != null) {
            recordingFakePlayer.setPacketOutDispatcher(this.packetOutDispatcher);
        }
        return recordingFakePlayer;
    }

    @Override
    public @Nullable Recordable<? extends Function<?, ?>> createDestroyPlayerRecordable(@NotNull Player player) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        if (entityPlayer instanceof RecordingFakePlayerImpl) return null;

        return RecPlayerDestroy.of(EntityId.of(player.getUniqueId(), player.getEntityId()));
    }

    @Override
    public @Nullable Recordable<? extends Function<?, ?>> createSpawnPlayerRecordable(@NotNull Player player) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        if (entityPlayer instanceof RecordingFakePlayerImpl) return null;

        Property skinTexture = entityPlayer.getProfile().getProperties().get("textures").stream()
                .findFirst()
                .orElse(null);

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return RecPlayerSpawn.of(entityId, player.getName(), skinTexture, player.getLocation());
    }

    @Override
    public @Nullable Recordable<? extends Function<?, ?>> createDestroyEntityRecordable(@NotNull Entity entity) {
        net.minecraft.server.v1_16_R3.Entity craftEntity = ((CraftEntity) entity).getHandle();
        if (entity instanceof Player || entity instanceof NPC || craftEntity instanceof RecordingFakePlayerImpl)
            return null;

        return RecEntityDestroy.of(EntityId.of(entity.getEntityId()));
    }

    @Override
    public @Nullable Recordable<? extends Function<?, ?>> createSpawnEntityRecordable(@NotNull Entity entity) {
        net.minecraft.server.v1_16_R3.Entity craftEntity = ((CraftEntity) entity).getHandle();
        if (entity instanceof Player || entity instanceof NPC || craftEntity instanceof RecordingFakePlayerImpl)
            return null;

        EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());
        return RecEntitySpawn.of(entityId, entity.getType(), entity.getLocation());
    }
}