import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 */
public class Message implements Delayed {
    /**
     *触发时间
     */
    private long time;

    /**
     *名称
     */
    String name;
    public Message(String name,long time,TimeUnit unit){
        this.name = name;
        this.time = System.currentTimeMillis() + (time > 0 ? unit.toMillis(time) : 0);
    }
    @Override
    public long getDelay(TimeUnit unit) {
        return time - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        Message item = (Message) o;
        long diff = this.time - item.time;
        if (diff <= 0){
            return  -1;
        }else{
            return 1;
        }
    }

    @Override
    public String toString() {
        return DelayQueueDemo.printDate() + "Message{" + "time=" + time + ", name=" + name + "/" + "}";
    }
}


