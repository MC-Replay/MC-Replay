package mc.replay.nms;

import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.github.steveice10.opennbt.tag.builtin.*;
import net.minecraft.server.v1_16_R3.*;

public final class NBTConverter_v1_16_R3 {

    private NBTConverter_v1_16_R3() {
    }

    public static CompoundTag convertFromNMS(String name, NBTTagCompound compound) {
        if (compound == null || compound.isEmpty()) {
            return new CompoundTag(name);
        }

        CompoundTag compoundTag = new CompoundTag(name);
        for (String key : compound.getKeys()) {
            NBTBase tag = compound.get(key);
            if (tag == null) continue;

            compoundTag.put(convertTagFromNMS(key, tag));
        }

        return compoundTag;
    }

    public static Tag convertTagFromNMS(String key, NBTBase base) {
        if (base instanceof NBTTagByte byteTag) {
            return new ByteTag(key, byteTag.asByte());
        } else if (base instanceof NBTTagByteArray byteArrayTag) {
            return new ByteArrayTag(key, byteArrayTag.getBytes());
        } else if (base instanceof NBTTagCompound compoundTag) {
            return convertFromNMS(key, compoundTag);
        } else if (base instanceof NBTTagDouble doubleTag) {
            return new DoubleTag(key, doubleTag.asDouble());
        } else if (base instanceof NBTTagFloat floatTag) {
            return new FloatTag(key, floatTag.asFloat());
        } else if (base instanceof NBTTagInt intTag) {
            return new IntTag(key, intTag.asInt());
        } else if (base instanceof NBTTagIntArray intArrayTag) {
            return new IntArrayTag(key, intArrayTag.getInts());
        } else if (base instanceof NBTTagList listTag) {
            ListTag list = new ListTag(key);

            for (NBTBase listBase : listTag) {
                if (listBase == null) continue;

                list.add(convertTagFromNMS("", listBase));
            }

            return list;
        } else if (base instanceof NBTTagLong longTag) {
            return new LongTag(key, longTag.asLong());
        } else if (base instanceof NBTTagLongArray longArrayTag) {
            return new LongArrayTag(key, longArrayTag.getLongs());
        } else if (base instanceof NBTTagShort shortTag) {
            return new ShortTag(key, shortTag.asShort());
        } else if (base instanceof NBTTagString stringTag) {
            return new StringTag(key, stringTag.asString());
        }

        throw new IllegalArgumentException("Unknown NBTBase type: " + base.getClass().getSimpleName());
    }

    public static NBTTagCompound convertToNMS(CompoundTag compound) {
        if (compound == null || compound.isEmpty()) {
            return new NBTTagCompound();
        }

        NBTTagCompound compoundTag = new NBTTagCompound();
        for (String key : compound.keySet()) {
            Tag tag = compound.get(key);
            if (tag == null) continue;

            compoundTag.set(key, convertTagToNMS(tag));
        }

        return compoundTag;
    }

    public static NBTBase convertTagToNMS(Tag tag) {
        if (tag instanceof ByteTag byteTag) {
            return NBTTagByte.a(byteTag.getValue());
        } else if (tag instanceof ByteArrayTag byteArrayTag) {
            return new NBTTagByteArray(byteArrayTag.getValue());
        } else if (tag instanceof CompoundTag compoundTag) {
            return convertToNMS(compoundTag);
        } else if (tag instanceof DoubleTag doubleTag) {
            return NBTTagDouble.a(doubleTag.getValue());
        } else if (tag instanceof FloatTag floatTag) {
            return NBTTagFloat.a(floatTag.getValue());
        } else if (tag instanceof IntTag intTag) {
            return NBTTagInt.a(intTag.getValue());
        } else if (tag instanceof IntArrayTag intArrayTag) {
            return new NBTTagIntArray(intArrayTag.getValue());
        } else if (tag instanceof ListTag listTag) {
            NBTTagList list = new NBTTagList();

            for (int i = 0; i < listTag.size(); i++) {
                Tag listBase = listTag.get(i);
                if (listBase == null) continue;

                list.add(convertTagToNMS(listBase));
            }

            return list;
        } else if (tag instanceof LongTag longTag) {
            return NBTTagLong.a(longTag.getValue());
        } else if (tag instanceof LongArrayTag longArrayTag) {
            return new NBTTagLongArray(longArrayTag.getValue());
        } else if (tag instanceof ShortTag shortTag) {
            return NBTTagShort.a(shortTag.getValue());
        } else if (tag instanceof StringTag stringTag) {
            return NBTTagString.a(stringTag.getValue());
        }

        throw new IllegalArgumentException("Unknown Tag type: " + tag.getClass().getSimpleName());
    }
}