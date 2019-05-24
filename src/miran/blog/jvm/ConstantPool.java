package miran.blog.jvm;

public class ConstantPool {
    Integer i1=40;
    int y[];
    int y1[] = new int[0];
    String b;
    String a = "a";
    String s1 = new String("xyz");
    Inner inner;
    Inner inner1 = new Inner();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            System.out.println();
        }
    };

    class Inner extends Thread {

    }

    public static final String A;
    public static final String B;
    static {
        A = "ab";
        B = "cd";
    }
    public static void main(String[] args) {
        String s = A + B;
        String t = "abcd";
        if (s == t) {
            System.out.println("s == t");
        } else {
            System.out.println("s != t");
        }
    }
}
