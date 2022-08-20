package mc.replay.dispatcher.packet.converters;

import mc.replay.common.recordables.Recordable;
import org.bukkit.entity.Player;

public interface ReplayPacketInConverter<R extends Recordable> {

    String packetClassName();

    R recordableFromPacket(Player player, Object packet);
}