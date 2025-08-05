package one.nio.util;

import one.nio.gen.BytecodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaVersion {

    private static Integer parsedJavaVersion() {
        String version = System.getProperty("java.specification.version");
        if (version == null) {
            return null;
        } else {
            int index = version.indexOf('.');
            try {
                return Integer.parseInt(version.substring(index + 1));
            } catch (Throwable e) {
                Logger logger = LoggerFactory.getLogger(JavaVersion.class);
                logger.error("Can't parse java version:{}", version, e);
                return null;
            }
        }
    }

    public static final Integer JAVA_VERSION = parsedJavaVersion();

    public static boolean isJava8() {
        return JAVA_VERSION != null && JAVA_VERSION == 8;
    }

    public static boolean isJava9Plus() {
        return JAVA_VERSION != null && JAVA_VERSION >= 9;
    }

}
