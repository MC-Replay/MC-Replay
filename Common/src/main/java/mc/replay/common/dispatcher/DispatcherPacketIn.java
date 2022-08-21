package mc.replay.common.dispatcher;

import mc.replay.common.recordables.Recordable;
import mc.replay.common.utils.PacketConverter;
import org.bukkit.entity.Player;

import java.util.List;

public interface DispatcherPacketIn<T> extends Dispatcher<T> {

    default PacketConverter.ConvertedPacket convert(T packet) {
        return PacketConverter.convert(packet);
    }

    //Use getRecordables(Player player, T obj) instead
    default List<Recordable> getRecordables(T obj) {
        return null;
    }

    List<Recordable> getRecordables(Player player, T obj);
}