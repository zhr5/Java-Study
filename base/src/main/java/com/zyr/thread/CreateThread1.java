package main.java.com.zyr.thread;

public class CreateThread1 {
    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            // ...
            System.out.println("实现 Runnable 接口使用线程");
        }
    }
    public static void main(String[] args) {
        MyRunnable instance = new MyRunnable();
        Thread thread = new Thread(instance);
        thread.start();
    }

}
