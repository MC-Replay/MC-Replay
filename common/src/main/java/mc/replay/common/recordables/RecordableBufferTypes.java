package mc.replay.common.recordables;

import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public final class RecordableBufferTypes {

    public static final ReplayByteBuffer.Type<Pos> ENTITY_POSITION = new TypeImpl<>(Pos.class,
            (buffer, pos) -> {
                double[] values = new double[]{pos.x(), pos.y(), pos.z()};
                for (double value : values) {
                    double positiveValue = Math.abs(value);
                    double fractionalPart = (value < 0) ? -(positiveValue % 1) : positiveValue % 1;

                    long fractionalBits = Double.doubleToLongBits(fractionalPart);
                    short halfFloatingPoint = Short.reverseBytes((short) (fractionalBits >> 48));

                    buffer.write(VAR_INT, (int) positiveValue);
                    buffer.write(SHORT, halfFloatingPoint);
                }
                return -1;
            },
            buffer -> {
                double[] values = new double[3];
                for (int i = 0; i < values.length; i++) {
                    int positiveValue = buffer.read(VAR_INT);
                    short halfFloatingPoint = Short.reverseBytes(buffer.read(SHORT));
                    double fractionalPart = Double.longBitsToDouble(((long) (halfFloatingPoint & 0xFFFF)) << 48);

                    if (fractionalPart < 0) positiveValue = -positiveValue;
                    values[i] = positiveValue + fractionalPart;
                }
                return new Pos(values[0], values[1], values[2], 0f, 0f);
            }
    );

    public static final ReplayByteBuffer.Type<Float> SINGLE_ENTITY_ROTATION = new TypeImpl<>(Float.class,
            (buffer, boxed) -> {
                float value = boxed;
                if (value < 0) value += 360;
                double radians = Math.toRadians(value);
                int multipliedRadians = (int) (radians * 160);
                short shortValue = (short) ((multipliedRadians & 0x3FF) << 6);

                buffer.write(BYTE, (byte) (shortValue >>> 8));
                buffer.write(BYTE, (byte) shortValue);
                return -1;
            },
            buffer -> {
                byte[] bytes = new byte[]{
                        buffer.read(BYTE),
                        buffer.read(BYTE)
                };

                short shortValue = (short) (((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF));
                int intValue = (shortValue >>> 6) & 0x3FF;
                return (float) Math.toDegrees(intValue / 160.0);
            }
    );

    public static final ReplayByteBuffer.Type<Pos> ENTITY_ROTATION = new TypeImpl<>(Pos.class, // TODO create different object for rotation
            (buffer, pos) -> {
                float[] values = new float[]{pos.yaw(), pos.pitch()};
                for (float value : values) {
                    buffer.write(SINGLE_ENTITY_ROTATION, value);
                }
                return -1;
            },
            buffer -> {
                float[] values = new float[2];
                for (int i = 0; i < values.length; i++) {
                    values[i] = buffer.read(SINGLE_ENTITY_ROTATION);
                }
                return new Pos(0, 0, 0, values[0], values[1]);
            }
    );

    public static final ReplayByteBuffer.Type<Pos> ENTITY_POSITION_WITH_ROTATION = new TypeImpl<>(Pos.class,
            (buffer, pos) -> {
                buffer.write(ENTITY_POSITION, pos);
                buffer.write(ENTITY_ROTATION, pos);
                return -1;
            },
            buffer -> {
                Pos pos = buffer.read(ENTITY_POSITION);
                Pos rotation = buffer.read(ENTITY_ROTATION);
                return pos.withRotation(rotation.yaw(), rotation.pitch());
            }
    );

    record TypeImpl<T>(@NotNull Class<T> type,
                       @NotNull ReplayByteBuffer.TypeWriter<T> writer,
                       @NotNull ReplayByteBuffer.TypeReader<T> reader) implements ReplayByteBuffer.Type<T> {
    }
}