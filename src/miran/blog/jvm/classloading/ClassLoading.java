package miran.blog.jvm.classloading;

public class ClassLoading {

    static class SSClass
    {
        static
        {
            System.out.println("SSClass");
        }
    }
    static class SuperClass extends SSClass
    {
        static
        {
            System.out.println("SuperClass init!");
        }

        public static int value = 123;

        public SuperClass()
        {
            System.out.println("init SuperClass");
        }
    }
    static class SubClass extends SuperClass
    {
        static
        {
            System.out.println("SubClass init");
        }

        static int a;

        public SubClass()
        {
            System.out.println("init SubClass");
        }
    }

    public static void main(String[] args)
    {
        System.out.println(SubClass.value);
    }

}
