package mc.replay.common.dispatcher;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.packetlib.network.packet.serverbound.ServerboundPacket;
import org.bukkit.entity.Player;

import java.util.List;

public interface DispatcherPacketIn<T extends ServerboundPacket> extends Dispatcher<T> {

    // Use getRecordables(Player player, T obj) instead
    default List<Recordable> getRecordables(T obj) {
        return null;
    }

    List<Recordable> getRecordables(Player player, T obj);
}