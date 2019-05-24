package miran.blog.jvm;

/**
 * 运行结果：
 * 2
 * 3
 * a=110,b=0
 * 1
 * 4
 *
 * 1，静态变量先于静态块执行，因为静态变量st声明在静态块之前
 * 2，static StaticTest st = new StaticTest();触发了实例化
 * 3，实例化：1）父类成员变量赋值；2）执行父类构造方法；3）子类成员变量赋值；4）实例化代码块；5）执行子类构造方法
 * 4，执行静态块
 * 5，执行main方法
 */
public class StaticTest {
    public static void main(String[] args)
    {
        staticFunction();
    }

    static StaticTest st = new StaticTest();

    static
    {
        System.out.println("1");
    }

    {//实例化代码块，先于构造方法执行
        System.out.println("2");
    }

    StaticTest()
    {
        System.out.println("3");
        System.out.println("a="+a+",b="+b);
    }

    public static void staticFunction(){
        System.out.println("4");
    }

    int a = 110;
    static int b = 112;
}
