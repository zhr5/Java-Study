package com.zyr.javase.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CreateThread2 {
    public static class MyCallable implements Callable<String> {
        public String call() throws ExecutionException{
            return "实现 Callable 接口使用线程";
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable mc = new MyCallable();
        FutureTask<String> ft = new FutureTask<String>(mc);
        Thread thread = new Thread(ft);
        thread.start();
        System.out.println(ft.get());
    }

}
