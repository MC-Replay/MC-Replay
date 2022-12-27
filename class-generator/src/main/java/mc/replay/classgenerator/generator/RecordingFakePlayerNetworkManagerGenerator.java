package mc.replay.classgenerator.generator;

import io.netty.util.concurrent.GenericFutureListener;
import javassist.*;
import mc.replay.api.MCReplayAPI;
import mc.replay.classgenerator.generated.Generated;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.utils.PacketUtils;
import mc.replay.packetlib.utils.Reflections;

public final class RecordingFakePlayerNetworkManagerGenerator implements GeneratorTemplate {

    public static final String NETWORK_MANAGER_CLASS_NAME = "mc.replay.classgenerator.generated.RecordingFakePlayerNetworkManager";

    private final ClassPool pool;

    public RecordingFakePlayerNetworkManagerGenerator(ClassPool pool) {
        this.pool = pool;
    }

    @Override
    public Class<?> generate() throws Exception {
        Class<?> networkManager = Reflections.NETWORK_MANAGER;
        CtClass superclass = this.pool.get(networkManager.getName());

        CtClass generated = this.pool.makeClass(NETWORK_MANAGER_CLASS_NAME);
        generated.setSuperclass(superclass);

        this.importPackages(generated);
        this.makeFields(generated);
        this.makeConstructor(generated);
        this.makeMethods(generated);

        return generated.toClass(Generated.class);
    }

    @Override
    public void importPackages(CtClass generated) {
        this.pool.importPackage(MCReplayAPI.class.getName());
        this.pool.importPackage(RecordingFakePlayerGenerator.FAKE_PLAYER_CLASS_NAME);
        this.pool.importPackage(PacketUtils.class.getName());
        this.pool.importPackage(ClientboundPacket.class.getName());
        this.pool.importPackage(GenericFutureListener.class.getName());
        this.pool.importPackage(Reflections.PACKET.getName());
    }

    @Override
    public void makeFields(CtClass generated) throws Exception {
        generated.addField(CtField.make("private final RecordingFakePlayer fakePlayer;", generated));
    }

    @Override
    public void makeConstructor(CtClass generated) throws Exception {
        generated.addConstructor(CtNewConstructor.make(
                "public RecordingFakePlayerNetworkManager(RecordingFakePlayer fakePlayer) {\n" +
                        "   super(null);\n" +
                        "\n" +
                        "   this.fakePlayer = fakePlayer;\n" +
                        "}",
                generated
        ));
    }

    @Override
    public void makeMethods(CtClass generated) throws Exception {
        generated.addMethod(CtMethod.make(
                "public void sendPacket(Packet packet) {\n" +
                        "   this.sendPacket(packet, null);\n" +
                        "}",
                generated
        ));

        generated.addMethod(CtMethod.make(
                " public void sendPacket(Packet packet, GenericFutureListener futureListener) {\n" +
                        "   if (!this.fakePlayer.isRecording()) return;\n" +
                        "\n" +
                        "   ClientboundPacket clientboundPacket = PacketUtils.readClientboundPacket(packet);\n" +
                        "   if (clientboundPacket != null) {\n" +
                        "       MCReplayAPI.getPacketLib().getPacketListener().publishClientbound(clientboundPacket);\n" +
                        "   }\n" +
                        "}",
                generated
        ));
    }
}