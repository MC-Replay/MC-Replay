package mc.replay.common.dispatcher.test;

public class Test {

    public static void main(String[] args) {
        DisPacketTest test = new DisPacketTest();

        System.out.printf(test.getInputClass().getSimpleName());

//        ParameterizedType parameterizedType = (ParameterizedType) test.getClass().getGenericInterfaces()[0];
//        Type[] typeArguments = parameterizedType.getActualTypeArguments();
//        Class<?> typeArgument = (Class<?>) typeArguments[0];
//        System.out.println(typeArgument.getSimpleName());
    }
}
