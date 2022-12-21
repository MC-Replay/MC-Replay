package mc.replay.classgenerator;

import javassist.ClassPool;
import javassist.LoaderClassPath;
import mc.replay.classgenerator.generator.RecordingFakePlayerGenerator;
import mc.replay.classgenerator.generator.RecordingFakePlayerNetworkManagerGenerator;
import mc.replay.classgenerator.objects.IRecordingFakePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class ClassGenerator {

    private ClassGenerator() {
    }

    private static Class<?> FAKE_PLAYER_CLASS;
    private static Class<?> NETWORK_MANAGER_CLASS;

    public static IRecordingFakePlayer createFakePlayer(Player target) {
        try {
            return (IRecordingFakePlayer) FAKE_PLAYER_CLASS.getConstructor(Player.class).newInstance(target);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static <T extends IRecordingFakePlayer> Object createNetworkManager(T fakePlayer) {
        try {
            return NETWORK_MANAGER_CLASS.getConstructor(FAKE_PLAYER_CLASS).newInstance(fakePlayer);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void generate() {
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new LoaderClassPath(Bukkit.class.getClassLoader())); // Bukkit class loader
        pool.insertClassPath(new LoaderClassPath(ClassGenerator.class.getClassLoader())); // MCReplay class loader

        try {
            FAKE_PLAYER_CLASS = new RecordingFakePlayerGenerator(pool).generate();
            NETWORK_MANAGER_CLASS = new RecordingFakePlayerNetworkManagerGenerator(pool).generate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}