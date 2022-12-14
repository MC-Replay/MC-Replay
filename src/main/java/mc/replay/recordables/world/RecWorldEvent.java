package mc.replay.recordables.world;

import mc.replay.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldEvent;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public record RecWorldEvent(int effectId, Vector position, int data,
                            boolean disableRelativeVolume) implements WorldEventRecordable {

    public static RecWorldEvent of(int effectId, Vector position, int data, boolean disableRelativeVolume) {
        return new RecWorldEvent(
                effectId,
                position,
                data,
                disableRelativeVolume
        );
    }

    @Override
    public void play(Player viewer) {
        MinecraftPlayerNMS.sendPacket(viewer, this.createPacket());
    }

    private Object createPacket() {
        BlockPosition position = new BlockPosition(
                this.position.getBlockX(),
                this.position.getBlockY(),
                this.position.getBlockZ()
        );

        return new PacketPlayOutWorldEvent(
                this.effectId,
                position,
                this.data,
                this.disableRelativeVolume
        );
    }
}