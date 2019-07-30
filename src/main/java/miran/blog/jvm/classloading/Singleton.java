package miran.blog.jvm.classloading;


public class Singleton {
    private final String X = "X";
    static class Lazy {

        private Lazy() {}

        // 内部类会延迟加载
        private static class SingletonFactory {
            private static Lazy single = new Lazy();
        }

        public static Lazy getInstance() {
            //访问静态变量，触发类加载，虚拟机会保证一个类的加载，只会发生一次，因此single的赋值是线程安全的
            return SingletonFactory.single;
        }
    }

    static class Eager {

        private static Eager single = new Eager();

        private Eager() {}

        //调用静态方法，触发类加载，虚拟机会保证一个类的加载，只会发生一次，因此single的赋值是线程安全的
        public static Eager getInstance() {
            return single;
        }
    }
}
