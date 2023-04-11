package mc.replay.classgenerator.generator;

import javassist.*;
import mc.replay.classgenerator.ClassGenerator;
import mc.replay.classgenerator.ClassGeneratorReflections;
import mc.replay.classgenerator.generated.Generated;
import mc.replay.classgenerator.objects.FakePlayerHandler;
import mc.replay.classgenerator.objects.IRecordingFakePlayer;
import mc.replay.packetlib.utils.Reflections;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public final class RecordingFakePlayerGenerator implements GeneratorTemplate {

    public static final String FAKE_PLAYER_CLASS_NAME = "mc.replay.classgenerator.generated.RecordingFakePlayer";

    private final ClassPool pool;

    public RecordingFakePlayerGenerator(ClassPool pool) {
        this.pool = pool;
    }

    @Override
    public Class<?> generate() throws Exception {
        Class<?> entityPlayer = Reflections.ENTITY_PLAYER;
        CtClass superclass = this.pool.get(entityPlayer.getName());

        CtClass generated = this.pool.makeClass(FAKE_PLAYER_CLASS_NAME);
        generated.setSuperclass(superclass);

        CtClass iRecordingFakePlayerClass = this.pool.get(IRecordingFakePlayer.class.getName());
        generated.addInterface(iRecordingFakePlayerClass);

        this.importPackages(generated);
        this.makeFields(generated);
        this.makeConstructor(generated);
        this.makeMethods(generated);

        return generated.toClass(Generated.class);
    }

    @Override
    public void importPackages(CtClass generated) {
        this.pool.importPackage(UUID.class.getName());
        this.pool.importPackage(AtomicInteger.class.getName());
        this.pool.importPackage(Player.class.getName());
        this.pool.importPackage(Location.class.getName());
        this.pool.importPackage(ClassGenerator.class.getName());
        this.pool.importPackage(FakePlayerHandler.class.getName());
        this.pool.importPackage(IRecordingFakePlayer.class.getName());
        this.pool.importPackage("com.mojang.authlib.GameProfile");
        this.pool.importPackage(ClassGeneratorReflections.MINECRAFT_SERVER.getName());
        this.pool.importPackage(ClassGeneratorReflections.CRAFT_WORLD.getName());
        this.pool.importPackage(ClassGeneratorReflections.PLAYER_INTERACT_MANAGER.getName());
        this.pool.importPackage(ClassGeneratorReflections.ENUM_GAME_MODE.getName());
        this.pool.importPackage(ClassGeneratorReflections.CRAFT_PLAYER.getName());
        this.pool.importPackage(Reflections.NETWORK_MANAGER.getName());
        this.pool.importPackage(Reflections.PLAYER_CONNECTION.getName());
        this.pool.importPackage(CreatureSpawnEvent.class.getName());
    }

    @Override
    public void makeFields(CtClass generated) throws Exception {
        generated.addField(CtField.make("private static final AtomicInteger FAKE_PLAYER_COUNT = new AtomicInteger();", generated));
        generated.addField(CtField.make("private final FakePlayerHandler fakePlayerHandler;", generated));
        generated.addField(CtField.make("private final Player target;", generated));
        generated.addField(CtField.make("private boolean recording = false;", generated));
    }

    @Override
    public void makeConstructor(CtClass generated) throws Exception {
        generated.addConstructor(CtNewConstructor.make(
                "public RecordingFakePlayer(FakePlayerHandler fakePlayerHandler, Player target) throws Exception {\n" +
                        "   super(\n" +
                        "       MinecraftServer.getServer(),\n" +
                        "       ((CraftWorld) target.getWorld()).getHandle(),\n" +
                        "       new GameProfile(UUID.randomUUID(), \"RecFakePlayer\" + FAKE_PLAYER_COUNT.getAndIncrement()),\n" +
                        "       new PlayerInteractManager(((CraftWorld) target.getWorld()).getHandle())\n" +
                        "   );\n" +
                        "\n" +
                        "   this.fakePlayerHandler = fakePlayerHandler;\n" +
                        "   this.target = target;\n" +
                        "   new PlayerConnection(MinecraftServer.getServer(), (NetworkManager) ClassGenerator.createNetworkManager(this), this);\n" +
                        "}",
                generated
        ));
    }

    @Override
    public void makeMethods(CtClass generated) throws Exception {
        generated.addMethod(CtMethod.make(
                "public Player target() {\n" +
                        "   return this.target;\n" +
                        "}",
                generated
        ));

        generated.addMethod(CtMethod.make(
                "public UUID uuid() {\n" +
                        "   return super.getUniqueID();\n" +
                        "}",
                generated
        ));

        generated.addMethod(CtMethod.make(
                "public String name() {\n" +
                        "   return super.getName();\n" +
                        "}",
                generated
        ));

        generated.addMethod(CtMethod.make(
                "public void spawn() {\n" +
                        "   this.fakePlayerHandler.addFakePlayer(this);" +
                        "\n" +
                        "   Location location = this.target.getLocation().clone();\n" +
                        "   this.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());\n" +
                        "\n" +
                        "   this.server.getPlayerList().players.add(this);\n" +
                        "   this.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);\n" +
                        "\n" +
                        "   this.playerInteractManager.setGameMode(EnumGamemode.SPECTATOR);\n" +
                        "   this.setSpectatorTarget(((CraftPlayer) this.target).getHandle());\n" +
                        "}",
                generated
        ));

        generated.addMethod(CtMethod.make(
                "public void remove() {\n" +
                        "   this.setSpectatorTarget(null);\n" +
                        "   this.getWorldServer().unregisterEntity(this);\n" +
                        "   this.server.getPlayerList().players.remove(this);\n" +
                        "   this.fakePlayerHandler.removeFakePlayer(this);\n" +
                        "}",
                generated
        ));

        generated.addMethod(CtMethod.make(
                "public void setRecording(boolean recording) {\n" +
                        "   this.recording = recording;\n" +
                        "}",
                generated
        ));

        generated.addMethod(CtMethod.make(
                "public boolean isRecording() {\n" +
                        "   return this.recording;\n" +
                        "}",
                generated
        ));

        this.overrideTickMethod(generated);
        this.overrideEmptyTickMethods(generated);
    }

    private void overrideTickMethod(CtClass generated) throws Exception {
        generated.addMethod(CtMethod.make(
                "public void tick() {\n" +
                        "   if (!this.valid) return;\n" +
                        "\n" +
                        "   if (this.joining) {\n" +
                        "       this.joining = false;\n" +
                        "   }\n" +
                        "\n" +
                        "   if(this.target == null || !this.target.isOnline() || this.target.isDead()) {\n" +
                        "       this.remove();\n" +
                        "   } else {\n" +
                        "       Location location = this.target.getLocation().clone();\n" +
                        "       this.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());\n" +
                        "\n" +
                        "       this.getWorldServer().getChunkProvider().movePlayer(this);\n" +
                        "   }\n" +
                        "}",
                generated
        ));
    }

    // TODO make compatible with other versions
    private void overrideEmptyTickMethods(CtClass generated) throws Exception {
        generated.addMethod(CtMethod.make("public void playerTick() { }", generated));
        generated.addMethod(CtMethod.make("public void movementTick() { }", generated));
        generated.addMethod(CtMethod.make("public void tickPotionEffects() { }", generated));
        generated.addMethod(CtMethod.make("public void tickWeather() { }", generated));
        generated.addMethod(CtMethod.make("public void doPortalTick() { }", generated));
        generated.addMethod(CtMethod.make("public void doTick() { }", generated));
        generated.addMethod(CtMethod.make("public void entityBaseTick() { }", generated));
        generated.addMethod(CtMethod.make("public void inactiveTick() { }", generated));
        generated.addMethod(CtMethod.make("public void passengerTick() { }", generated));
        generated.addMethod(CtMethod.make("public void postTick() { }", generated));
    }
}