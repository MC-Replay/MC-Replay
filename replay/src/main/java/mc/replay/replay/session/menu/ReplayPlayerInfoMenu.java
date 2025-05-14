package mc.replay.replay.session.menu;

import lombok.AllArgsConstructor;
import mc.replay.common.utils.item.ItemBuilder;
import mc.replay.nms.entity.player.RPlayer;
import mc.replay.nms.inventory.RItem;
import nl.odalitadevelopments.menus.annotations.Menu;
import nl.odalitadevelopments.menus.contents.MenuContents;
import nl.odalitadevelopments.menus.menu.providers.PlayerMenuProvider;
import nl.odalitadevelopments.menus.menu.type.MenuType;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Menu(
        title = "Player Info",
        type = MenuType.CHEST_6_ROW
)
public final class ReplayPlayerInfoMenu implements PlayerMenuProvider {

    private final RPlayer entity;

    @Override
    public void onLoad(@NotNull Player player, @NotNull MenuContents contents) {
        this.setItem(contents, 10, EquipmentSlot.HEAD);
        this.setItem(contents, 19, EquipmentSlot.CHEST);
        this.setItem(contents, 28, EquipmentSlot.LEGS);
        this.setItem(contents, 37, EquipmentSlot.FEET);

        this.setItem(contents, 21, EquipmentSlot.HAND);
        this.setItem(contents, 30, EquipmentSlot.OFF_HAND);
    }

    private void setItem(MenuContents contents, int slot, EquipmentSlot equipmentSlot) {
        contents.setUpdatable(slot, () -> {
            RItem equipment = this.entity.getEquipment(equipmentSlot);
            ItemStack itemStack = equipment == null ? null : equipment.toItemStack();

            if (itemStack == null || itemStack.getType().isAir()) {
                return new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .displayName("&cEquipment slot is empty")
                        .lore(
                                "&8&o" + StringUtils.capitalize(equipmentSlot.name().toLowerCase().replace("_", " "))
                        )
                        .build();
            }

            return itemStack;
        }, (event) -> {}, 1);
    }
}