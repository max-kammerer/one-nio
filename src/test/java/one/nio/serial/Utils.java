package one.nio.serial;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Utils {

    static Object clone(Object obj) throws IOException, ClassNotFoundException {
        CalcSizeStream css = new CalcSizeStream();
        css.writeObject(obj);
        int length = css.count();

        byte[] buf = new byte[length];
        SerializeStream out = new SerializeStream(buf);
        out.writeObject(obj);
        assertEquals(out.count(), length);

        DeserializeStream in = new DeserializeStream(buf);
        Object objCopy = in.readObject();
        assertEquals(in.count(), length);

        return objCopy;
    }

    static Object cloneViaPersist(Object obj) throws IOException, ClassNotFoundException {
        PersistStream out = new PersistStream();
        out.writeObject(obj);
        byte[] buf = out.toByteArray();

        DeserializeStream in = new DeserializeStream(buf);
        Object objCopy = in.readObject();
        assertEquals(in.count(), buf.length);

        return objCopy;
    }

    private static final Class[] collectionInterfaces = {SortedSet.class, NavigableSet.class, Set.class, Queue.class, List.class};
    private static final Class[] mapInterfaces = {SortedMap.class, NavigableMap.class};

    static void checkClass(Class<?> cls, Class<?> other) {
        if (other != cls) {
            if (Collection.class.isAssignableFrom(cls)) {
                for (Class<?> iface : collectionInterfaces) {
                    assertTrue(!iface.isAssignableFrom(cls) || iface.isAssignableFrom(other));
                }
            }
            if (Map.class.isAssignableFrom(cls)) {
                for (Class<?> iface : mapInterfaces) {
                    assertTrue(!iface.isAssignableFrom(cls) || iface.isAssignableFrom(other));
                }
            }
        }
    }
}
