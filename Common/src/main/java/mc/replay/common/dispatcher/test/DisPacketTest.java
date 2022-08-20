package mc.replay.common.dispatcher.test;

import mc.replay.common.recordables.Recordable;
import mc.replay.common.dispatcher.DispatcherPacket;
import net.minecraft.server.v1_16_R3.PacketPlayInAbilities;

public class DisPacketTest implements DispatcherPacket<PacketPlayInAbilities> {

    @Override
    public Recordable getRecordable(PacketPlayInAbilities packet) {
        return null;
    }
}
