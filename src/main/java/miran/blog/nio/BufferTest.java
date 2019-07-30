package miran.blog.nio;

import sun.misc.Unsafe;

import java.lang.reflect.Field;


public class BufferTest {
    protected static Unsafe unsafe;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (Throwable t) {
            // do nothing
        }
    }

    public static void main(String[] args) {
        unsafe.allocateMemory(1024 * 1024 * 10);
        System.gc();
    }
}
