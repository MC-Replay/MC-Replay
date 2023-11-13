package mc.replay.nms.fakeplayer;

import mc.replay.nms.MCReplayNMS;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public final class FakePlayerFilterList extends AbstractList<Object> implements RandomAccess, Serializable {

    @Serial
    private static final long serialVersionUID = 0L;

    private final MCReplayNMS nms;

    private final List<Object> fromList;

    public FakePlayerFilterList(MCReplayNMS nms, List<Object> fromList) {
        this.nms = nms;
        this.fromList = fromList;
    }

    @Override
    public void clear() {
        this.fromList.clear();
    }

    @Override
    public Object get(int index) {
        return this.filter().get(index);
    }

    @Override
    public int size() {
        return this.filter().size();
    }

    @Override
    public @NotNull Iterator<Object> iterator() {
        return this.listIterator();
    }

    @Override
    public @NotNull ListIterator<Object> listIterator() {
        return super.listIterator();
    }

    @Override
    public @NotNull ListIterator<Object> listIterator(int index) {
        return this.filter().listIterator(index);
    }

    @Override
    public @NotNull List<Object> subList(int fromIndex, int toIndex) {
        return this.filter().subList(fromIndex, toIndex);
    }

    private @NotNull List<Object> filter() {
        List<Object> list = new CopyOnWriteArrayList<>();

        for (Object object : this.fromList) {
            try {
                if (!(object instanceof IRecordingFakePlayer)) {
                    Object bukkitEntity = this.nms.getBukkitEntity(object);
                    list.add(bukkitEntity);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return list;
    }
}