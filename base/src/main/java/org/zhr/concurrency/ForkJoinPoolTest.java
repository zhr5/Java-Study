package org.zhr.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 *
 * Fork/join计算1-1000累加值.
 *
 */
public class ForkJoinPoolTest {
    private static final Integer DURATION_VALUE = 100;
    static class ForkJoinSubTask extends RecursiveTask<Integer> {
        // 子任务开始计算的值
        private Integer startValue;
        // 子任务结束计算的值
        private Integer endValue;
        private ForkJoinSubTask(Integer startValue , Integer endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }
        @Override
        protected Integer compute() {
            //小于一定值DURATION,才开始计算
            if(endValue - startValue < DURATION_VALUE) {
                System.out.println("执行子任务计算：开始值 = " + startValue + ";结束值 = " + endValue);
                Integer totalValue = 0;
                for (int index = this.startValue; index <= this.endValue; index++) {
                    totalValue += index;
                }
                return totalValue;
            } else {
                // 将任务拆分，拆分成两个任务
                ForkJoinSubTask subTask1 = new ForkJoinSubTask(startValue, (startValue + endValue) / 2);
                subTask1.fork();
                ForkJoinSubTask subTask2 = new ForkJoinSubTask((startValue + endValue) / 2 + 1 , endValue);
                subTask2.fork();
                return subTask1.join() + subTask2.join();
            }
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Fork/Join框架的线程池
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> taskFuture =  pool.submit(new ForkJoinSubTask(1,1000));
        Integer result = taskFuture.get();
        System.out.println("累加结果是:" + result);
    }
}
