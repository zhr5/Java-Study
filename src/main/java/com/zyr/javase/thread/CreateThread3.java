package com.zyr.javase.thread;

public class CreateThread3 {
    public static class MyThread extends Thread {
        @Override
        public void run() {
            // ...
            System.out.println("继承 Thread 类使用线程");
        }
    }
    public static void main(String[] args) {
        MyThread mt = new MyThread();
        mt.start();
    }


}
