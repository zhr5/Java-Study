import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 */
public class DelayQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        Message item1 = new Message("消息1",5, TimeUnit.SECONDS);
        Message item2 = new Message("消息2",10, TimeUnit.SECONDS);
        Message item3 = new Message("消息3",15, TimeUnit.SECONDS);

        DelayQueue<Message> queue  = new DelayQueue<Message>();
        queue.add(item1);
        queue.add(item2);
        queue.add(item3);

        int queueLengh = queue.size();
        System.out.println(printDate() + "开始!");
        for (int i = 0; i < queueLengh; i++) {
            Message take = queue.take();
            System.out.format(printDate() + " 消息出队，属性name=%s%n",take.name);
        }

        System.out.println(printDate() + "结束!");
    }
    static String printDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}

