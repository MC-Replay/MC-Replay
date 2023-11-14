package mc.replay.nms.inventory;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.IntTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;

import java.util.HashSet;
import java.util.List;

public final class ItemNBTTags {

    private ItemNBTTags() {
    }

    private static final List<String> TAG_NAMES = List.of(
            "Enchantments",
            "StoredEnchantments",
            "EntityTag",
            "display",
            "AttributeModifiers",
            "Unbreakable",
            "SkullOwner",
            "HideFlags",
            "CanDestroy",
            "PickupDelay",
            "Age",
            "generation",
            "title",
            "author",
            "pages",
            "Fireworks",
            "CanPlaceOn",
            "BlockEntityTag",
            "BlockStateTag",
            "Potion",
            "CustomPotionColor",
            "custom_potion_effects",
            "map",
            "map_scale_direction",
            "map_to_lock",
            "Decorations"
    );

    public static void cleanCompoundTag(CompoundTag compoundTag) {
        if (compoundTag == null || compoundTag.isEmpty()) return;

        // Remove all custom tags
        for (String key : new HashSet<>(compoundTag.keySet())) {
            if (!TAG_NAMES.contains(key)) {
                compoundTag.remove(key);
            }
        }

        // Remove unnecessary tags
        compoundTag.remove("PickupDelay");
        compoundTag.remove("Age");
        compoundTag.remove("pages");

        // Some other checks
        checkHideFlags(compoundTag);
        checkPotion(compoundTag);
        checkMap(compoundTag);
    }

    private static void checkHideFlags(CompoundTag compoundTag) {
        IntTag hideFlags = compoundTag.get("HideFlags");
        if (hideFlags == null) return;

        Integer value = hideFlags.getValue();
        if (value == null || value == 0) return;

        if ((value & 0x01) == 0x01) { // Hide enchantments
            ListTag enchantmentsTag = compoundTag.get("Enchantments");
            if (enchantmentsTag != null && enchantmentsTag.size() > 1) {
                // If multiple enchantments are present, remove all but the first, so that we still have the glint
                enchantmentsTag.setValue(enchantmentsTag.getValue().subList(0, 1));
            } else {
                // Else, remove enchantments tag and hide flag
                compoundTag.remove("Enchantments");
                value &= ~0x01;
            }
        }

        if ((value & 0x02) == 0x02) { // Hide attributes
            // Remove attributes tag and hide flag
            compoundTag.remove("AttributeModifiers");
            value &= ~0x02;
        }

        if ((value & 0x04) == 0x04) { // Hide unbreakable
            // Remove unbreakable tag and hide flag
            compoundTag.remove("Unbreakable");
            value &= ~0x04;
        }

        if ((value & 0x08) == 0x08) { // Hide can destroy
            // Remove can destroy tag and hide flag
            compoundTag.remove("CanDestroy");
            value &= ~0x08;
        }

        if ((value & 0x10) == 0x10) { // Hide can place on
            // Remove can place on tag and hide flag
            compoundTag.remove("CanPlaceOn");
            value &= ~0x10;
        }

        if ((value & 0x20) == 0x20) { // Hide other information
            // Remove all tags that are not needed for the item to function and hide flag

            compoundTag.remove("StoredEnchantments"); // enchanted book
            compoundTag.remove("generation"); // written book
            compoundTag.remove("author"); // written book

            value &= ~0x20;
        }

        // 0x40 = dyed, no need to remove this, since the armor color must still be applied
        // 0x80 = upgrades, no need to remove this, since the upgrade data must still be applied

        if (value == 0) {
            compoundTag.remove("HideFlags");
        } else {
            hideFlags.setValue(value);
        }
    }

    private static void checkPotion(CompoundTag compoundTag) {
        compoundTag.remove("custom_potion_effects");
    }

    private static void checkMap(CompoundTag compoundTag) {
        compoundTag.remove("map_scale_direction");
        compoundTag.remove("map_to_lock");
    }
}