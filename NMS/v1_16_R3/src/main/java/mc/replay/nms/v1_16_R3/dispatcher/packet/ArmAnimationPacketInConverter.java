package mc.replay.nms.v1_16_R3.dispatcher.packet;

import mc.replay.common.dispatcher.DispatcherPacketIn;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.replay.EntityId;
import mc.replay.nms.v1_16_R3.recordable.entity.miscellaneous.RecEntitySwingHandAnimation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

import java.lang.reflect.Field;
import java.util.List;

public class ArmAnimationPacketInConverter implements DispatcherPacketIn<RecEntitySwingHandAnimation> {

    @Override
    public List<Recordable> getRecordables(Player player, RecEntitySwingHandAnimation packet) {
        try {
            Field field = packet.getClass().getDeclaredField("a");
            field.setAccessible(true);

            Object enumHandObject = field.get(packet);
            String enumHand = (String) enumHandObject.getClass().getMethod("name").invoke(enumHandObject);

            EquipmentSlot hand = (enumHand.equalsIgnoreCase("off_hand")) ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND;

            EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
            return List.of(RecEntitySwingHandAnimation.of(entityId, hand));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}