package mc.replay.common.utils.reflection;

import mc.replay.common.utils.reflection.nms.MinecraftVersionNMS;

import java.util.Optional;

public final class MinecraftReflections {

    private static final String NM_PACKAGE = "net.minecraft";
    public static final String OBC_PACKAGE = "org.bukkit.craftbukkit";
    public static final String NMS_PACKAGE = NM_PACKAGE + ".server";

    private static final boolean NMS_REPACKAGED = JavaReflections.getClassOptional(NM_PACKAGE + ".network.protocol.Packet").isPresent();

    public static boolean isRepackaged() {
        return NMS_REPACKAGED;
    }

    public static String nmsClassName(String post1_17package, String className) {
        if (NMS_REPACKAGED) {
            String classPackage = post1_17package == null || post1_17package.isEmpty() ? NM_PACKAGE : NM_PACKAGE + '.' + post1_17package;
            return classPackage + '.' + className;
        }
        return NMS_PACKAGE + '.' + MinecraftVersionNMS.getServerProtocolVersion() + '.' + className;
    }

    public static String obcClassName(String className) {
        return OBC_PACKAGE + '.' + MinecraftVersionNMS.getServerProtocolVersion() + '.' + className;
    }

    public static Class<?> nmsClass(String post1_17package, String className) throws ClassNotFoundException {
        return JavaReflections.getClass(nmsClassName(post1_17package, className));
    }

    public static Optional<Class<?>> nmsOptionalClass(String post1_17package, String className) {
        return JavaReflections.getClassOptional(nmsClassName(post1_17package, className));
    }

    public static Class<?> obcClass(String className) throws ClassNotFoundException {
        return JavaReflections.getClass(obcClassName(className));
    }

    public static Optional<Class<?>> obcOptionalClass(String className) {
        return JavaReflections.getClassOptional(obcClassName(className));
    }
}