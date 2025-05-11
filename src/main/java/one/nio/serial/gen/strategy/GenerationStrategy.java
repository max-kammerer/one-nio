package one.nio.serial.gen.strategy;

import one.nio.serial.FieldDescriptor;
import one.nio.util.JavaVersion;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.lang.invoke.MethodHandleInfo;
import java.lang.reflect.Field;

public abstract class GenerationStrategy {

    public abstract String getBaseClassName();

    public abstract void generateStatics(ClassWriter cv, Class cls, String className, FieldDescriptor[] fds, FieldDescriptor[] defaultFields);

    public abstract void emitReadSerialField(MethodVisitor mv, Class clazz, Field field, String serializerClassName);

    public abstract void emitWriteSerialField(MethodVisitor mv, Class clazz, Field field, String serializerClassName);

    public abstract void emitWriteObjectCall(MethodVisitor mv, Class clazz, MethodHandleInfo methodType);

    public abstract void emitReadObjectCall(MethodVisitor mv, Class clazz, MethodHandleInfo methodType);

    public static GenerationStrategy createStrategy() {
        if (JavaVersion.isJava9Plus()) { //TODO: also check runtime flag
            return new HandlesStrategy();
        } else {
            return new MagicAccessorStrategy();
        }
    }
}
