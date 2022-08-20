package mc.replay.dispatcher.packet.converters.in;

import mc.replay.dispatcher.packet.converters.ReplayPacketInConverter;
import mc.replay.common.replay.EntityId;
import mc.replay.nms.v1_16_5.recordable.entity.miscellaneous.RecEntitySwingHandAnimation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

import java.lang.reflect.Field;

public record ArmAnimationPacketInConverter() implements ReplayPacketInConverter<RecEntitySwingHandAnimation> {

    @Override
    public String packetClassName() {
        return "PacketPlayInArmAnimation";
    }

    @Override
    public RecEntitySwingHandAnimation recordableFromPacket(Player player, Object packet) {
        try {
            Field field = packet.getClass().getDeclaredField("a");
            field.setAccessible(true);

            Object enumHandObject = field.get(packet);
            String enumHand = (String) enumHandObject.getClass().getMethod("name").invoke(enumHandObject);

            EquipmentSlot hand = (enumHand.equalsIgnoreCase("off_hand")) ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND;

            EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
            return RecEntitySwingHandAnimation.of(entityId, hand);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}