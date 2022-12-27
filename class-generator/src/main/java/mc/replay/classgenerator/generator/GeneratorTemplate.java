package mc.replay.classgenerator.generator;

import javassist.CtClass;

interface GeneratorTemplate {

    Class<?> generate() throws Exception;

    void importPackages(CtClass generated) throws Exception;

    void makeFields(CtClass generated) throws Exception;

    void makeConstructor(CtClass generated) throws Exception;

    void makeMethods(CtClass generated) throws Exception;
}