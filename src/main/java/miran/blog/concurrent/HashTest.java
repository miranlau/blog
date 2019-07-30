package miran.blog.concurrent;

public class HashTest {

    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash

    public static void main(String[] args) {
        System.out.println(hash(Integer.MIN_VALUE) + " " + spread(Integer.MIN_VALUE));

        System.out.println(hash(-1) + " " + spread(-1));

        System.out.println(hash(0) + " " + spread(0));

        System.out.println(hash(1) + " " + spread(1));

        System.out.println(hash(100) + " " + spread(100));

        System.out.println(hash(Integer.MAX_VALUE) + " " + spread(Integer.MAX_VALUE));
    }

    static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}
