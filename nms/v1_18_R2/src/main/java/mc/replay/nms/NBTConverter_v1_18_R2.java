package mc.replay.nms;

import com.github.steveice10.opennbt.tag.builtin.*;

public final class NBTConverter_v1_18_R2 {

    private NBTConverter_v1_18_R2() {
    }

    public static CompoundTag convertFromNMS(String name, net.minecraft.nbt.CompoundTag compound) {
        if (compound == null || compound.isEmpty()) {
            return new CompoundTag(name);
        }

        CompoundTag compoundTag = new CompoundTag(name);
        for (String key : compound.getAllKeys()) {
            net.minecraft.nbt.Tag tag = compound.get(key);
            if (tag == null) continue;

            compoundTag.put(convertTagFromNMS(key, tag));
        }

        return compoundTag;
    }

    public static Tag convertTagFromNMS(String key, net.minecraft.nbt.Tag base) {
        if (base instanceof net.minecraft.nbt.ByteTag byteTag) {
            return new ByteTag(key, byteTag.getAsByte());
        } else if (base instanceof ByteArrayTag byteArrayTag) {
            return new ByteArrayTag(key, byteArrayTag.getValue());
        } else if (base instanceof net.minecraft.nbt.CompoundTag compoundTag) {
            return convertFromNMS(key, compoundTag);
        } else if (base instanceof net.minecraft.nbt.DoubleTag doubleTag) {
            return new DoubleTag(key, doubleTag.getAsDouble());
        } else if (base instanceof net.minecraft.nbt.FloatTag floatTag) {
            return new FloatTag(key, floatTag.getAsFloat());
        } else if (base instanceof net.minecraft.nbt.IntTag intTag) {
            return new IntTag(key, intTag.getAsInt());
        } else if (base instanceof net.minecraft.nbt.IntArrayTag intArrayTag) {
            return new IntArrayTag(key, intArrayTag.getAsIntArray());
        } else if (base instanceof net.minecraft.nbt.ListTag listTag) {
            ListTag list = new ListTag(key);

            for (net.minecraft.nbt.Tag listBase : listTag) {
                if (listBase == null) continue;

                list.add(convertTagFromNMS("", listBase));
            }

            return list;
        } else if (base instanceof net.minecraft.nbt.LongTag longTag) {
            return new LongTag(key, longTag.getAsLong());
        } else if (base instanceof net.minecraft.nbt.LongArrayTag longArrayTag) {
            return new LongArrayTag(key, longArrayTag.getAsLongArray());
        } else if (base instanceof net.minecraft.nbt.ShortTag shortTag) {
            return new ShortTag(key, shortTag.getAsShort());
        } else if (base instanceof net.minecraft.nbt.StringTag stringTag) {
            return new StringTag(key, stringTag.getAsString());
        }

        throw new IllegalArgumentException("Unknown NBTBase type: " + base.getClass().getSimpleName());
    }

    public static net.minecraft.nbt.CompoundTag convertToNMS(CompoundTag compound) {
        if (compound == null || compound.isEmpty()) {
            return new net.minecraft.nbt.CompoundTag();
        }

        net.minecraft.nbt.CompoundTag compoundTag = new net.minecraft.nbt.CompoundTag();
        for (String key : compound.keySet()) {
            Tag tag = compound.get(key);
            if (tag == null) continue;

            compoundTag.put(key, convertTagToNMS(tag));
        }

        return compoundTag;
    }

    public static net.minecraft.nbt.Tag convertTagToNMS(Tag tag) {
        if (tag instanceof ByteTag byteTag) {
            return net.minecraft.nbt.ByteTag.valueOf(byteTag.getValue());
        } else if (tag instanceof ByteArrayTag byteArrayTag) {
            return new net.minecraft.nbt.ByteArrayTag(byteArrayTag.getValue());
        } else if (tag instanceof CompoundTag compoundTag) {
            return convertToNMS(compoundTag);
        } else if (tag instanceof DoubleTag doubleTag) {
            return net.minecraft.nbt.DoubleTag.valueOf(doubleTag.getValue());
        } else if (tag instanceof FloatTag floatTag) {
            return net.minecraft.nbt.FloatTag.valueOf(floatTag.getValue());
        } else if (tag instanceof IntTag intTag) {
            return net.minecraft.nbt.IntTag.valueOf(intTag.getValue());
        } else if (tag instanceof IntArrayTag intArrayTag) {
            return new net.minecraft.nbt.IntArrayTag(intArrayTag.getValue());
        } else if (tag instanceof ListTag listTag) {
            net.minecraft.nbt.ListTag list = new net.minecraft.nbt.ListTag();

            for (int i = 0; i < listTag.size(); i++) {
                Tag listBase = listTag.get(i);
                if (listBase == null) continue;

                list.add(convertTagToNMS(listBase));
            }

            return list;
        } else if (tag instanceof LongTag longTag) {
            return net.minecraft.nbt.LongTag.valueOf(longTag.getValue());
        } else if (tag instanceof LongArrayTag longArrayTag) {
            return new net.minecraft.nbt.LongArrayTag(longArrayTag.getValue());
        } else if (tag instanceof ShortTag shortTag) {
            return net.minecraft.nbt.ShortTag.valueOf(shortTag.getValue());
        } else if (tag instanceof StringTag stringTag) {
            return net.minecraft.nbt.StringTag.valueOf(stringTag.getValue());
        }

        throw new IllegalArgumentException("Unknown Tag type: " + tag.getClass().getSimpleName());
    }
}