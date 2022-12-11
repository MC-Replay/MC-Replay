package mc.replay.common.dispatcher;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.packetlib.network.packet.ServerboundPacket;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Function;

public interface DispatcherPacketIn<T extends ServerboundPacket> extends Dispatcher<T> {

    //Use getRecordables(Player player, T obj) instead
    default List<Recordable<? extends Function<?, ?>>> getRecordables(T obj) {
        return null;
    }

    List<Recordable<? extends Function<?, ?>>> getRecordables(Player player, T obj);
}