package miran.blog.jvm.classloading;

public class ClassReloading {
    static class Reloading {
        private final String GREETINGS = "Hi";
        public void greeting(String name) {
            System.out.println(GREETINGS + " " + name);
        }
    }

    public static void main(String[] args) throws Exception {
        Singleton singleton = new Singleton();
        while(true) {
            Reloading r = new Reloading();
            r.greeting("Helen");

            Thread.sleep(1000L);
        }
    }
}
