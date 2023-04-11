package mc.replay.classgenerator;

import javassist.ClassPool;
import javassist.LoaderClassPath;
import mc.replay.api.MCReplay;
import mc.replay.classgenerator.generator.RecordingFakePlayerGenerator;
import mc.replay.classgenerator.generator.RecordingFakePlayerNetworkManagerGenerator;
import mc.replay.classgenerator.objects.FakePlayerFilterList;
import mc.replay.classgenerator.objects.FakePlayerHandler;
import mc.replay.classgenerator.objects.IRecordingFakePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.List;

public final class ClassGenerator {

    private ClassGenerator() {
    }

    public static Class<?> FAKE_PLAYER_CLASS;
    public static Class<?> NETWORK_MANAGER_CLASS;

    public static IRecordingFakePlayer createFakePlayer(FakePlayerHandler fakePlayerHandler, Player target) {
        try {
            return (IRecordingFakePlayer) FAKE_PLAYER_CLASS.getConstructor(FakePlayerHandler.class, Player.class).newInstance(fakePlayerHandler, target);
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

    @SuppressWarnings("unchecked")
    public static void generate() {
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new LoaderClassPath(Bukkit.class.getClassLoader())); // Bukkit class loader
        pool.insertClassPath(new LoaderClassPath(ClassGenerator.class.getClassLoader())); // MCReplay class loader

        try {
            FAKE_PLAYER_CLASS = new RecordingFakePlayerGenerator(pool).generate();
            NETWORK_MANAGER_CLASS = new RecordingFakePlayerNetworkManagerGenerator(pool).generate();

            Object playerList = ClassGeneratorReflections.PLAYERS_FIELD.get(ClassGeneratorReflections.PLAYER_LIST_INSTANCE);

            Object craftServerInstance = ClassGeneratorReflections.CRAFT_SERVER_INSTANCE;
            Field playerViewField = ClassGeneratorReflections.PLAYER_VIEW_FIELD;
            playerViewField.set(craftServerInstance, new FakePlayerFilterList((List<Object>) playerList));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}