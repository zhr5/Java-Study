package com.zyr.javase.base.SimpleDateFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SimpleDateFormatTest1 {


    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final ThreadLocal<DateFormat> SAFE_SIMPLE_DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static void method1(){
        for (int i = 0; i < 20; ++i) {
            Thread thread = new Thread(() -> {
                try {
                    // 每个线程都new一个
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    System.out.println(Thread.currentThread().getName() + "--" + simpleDateFormat.parse("2020-06-01 11:35:00"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "Thread-" + i);
            thread.start();
        }
    }

    public static void method2(){
        for (int i = 0; i < 20; ++i) {
            Thread thread = new Thread(() -> {
                try {
                    synchronized (SIMPLE_DATE_FORMAT) {
                        System.out.println(Thread.currentThread().getName() + "--" + SIMPLE_DATE_FORMAT.parse("2020-06-01 11:35:00"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "Thread-" + i);
            thread.start();
        }
    }

    public static void method3(){
        for (int i = 0; i < 20; ++i) {
            Thread thread = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "--" + SAFE_SIMPLE_DATE_FORMAT.get().parse("2020-06-01 11:35:00"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "Thread-" + i);
            thread.start();
        }

    }
    public static void main(String[] args) {
        method1();
    }

}
