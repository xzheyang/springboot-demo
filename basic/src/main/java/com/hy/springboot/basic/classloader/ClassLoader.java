package com.hy.springboot.basic.classloader;

/**
 *
 *  类调用构造方法写在在默认赋值前,所以count2=0
 *  这是特例,详情见思维导图,静态声明之后,马上就是静态默认赋值,之后才是构造方法
 *
 *  详解:
 *  https://blog.csdn.net/zjkC050818/article/details/78376195
 *
 **/
public class ClassLoader {

    private static ClassLoader singleTon = new ClassLoader();
    public static int count1;
    public static int count2 = 0;

    private ClassLoader() {
        count1++;
        count2++;
    }

    public static ClassLoader getInstance() {
        return singleTon;
    }

    public static void main(String[] args) {
        ClassLoader singleTon = ClassLoader.getInstance();
        System.out.println("count1=" + ClassLoader.count1);
        System.out.println("count2=" + ClassLoader.count2);
    }


}
