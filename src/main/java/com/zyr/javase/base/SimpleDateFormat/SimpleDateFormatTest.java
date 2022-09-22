package com.zyr.javase.base.SimpleDateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SimpleDateFormatTest {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {

        testParse();
        testFormat();
    }

    public static void testParse(){
        /**
         * SimpleDateFormat线程不安全，没有保证线程安全(没有加锁)的情况下，禁止使用全局SimpleDateFormat,否则报错 NumberFormatException
         *
         * private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         */
        for (int i = 0; i < 20; ++i) {
            Thread thread = new Thread(() -> {
                try {
                    // 错误写法会导致线程安全问题
                    System.out.println(Thread.currentThread().getName() + "--" + SIMPLE_DATE_FORMAT.parse("2020-06-01 11:35:00"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "Thread-" + i);
            thread.start();
        }
    }

    public static void testFormat(){
        // 创建线程池执行任务
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                10, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));

        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            // 执行任务
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Date date = new Date(finalI * 1000); // 得到时间对象
                    formatAndPrint(date); // 执行时间格式化
                }
            });
        }
        threadPool.shutdown(); // 线程池执行完任务之后关闭
    }

    /**
     * 格式化并打印时间
     */
    private static void formatAndPrint(Date date) {
        String result = SIMPLE_DATE_FORMAT.format(date); // 执行格式化
        System.out.println("时间：" + result); // 打印最终结果
    }
}
