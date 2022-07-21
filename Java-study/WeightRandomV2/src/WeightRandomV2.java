import java.util.HashMap;
import java.util.Map;

public class WeightRandomV2 {
    /*权重随机算法*/
    public static final Map<String, Integer> WEIGHT_LIST = new HashMap<String, Integer>();
    static {
        // 权重之和为50
        WEIGHT_LIST.put("192.168.0.1", 1);
        WEIGHT_LIST.put("192.168.0.2", 8);
        WEIGHT_LIST.put("192.168.0.3", 3);
        WEIGHT_LIST.put("192.168.0.4", 6);
        WEIGHT_LIST.put("192.168.0.5", 5);
        WEIGHT_LIST.put("192.168.0.6", 5);
        WEIGHT_LIST.put("192.168.0.7", 4);
        WEIGHT_LIST.put("192.168.0.8", 7);
        WEIGHT_LIST.put("192.168.0.9", 2);
        WEIGHT_LIST.put("192.168.0.10", 9);
    }

        public static String getServer() {
            int totalWeight = 0;
            boolean sameWeight = true; // 如果所有权重都相等，那么随机一个ip就好了
            Object[] weights = ServerIps.WEIGHT_LIST.values().toArray();
            for (int i = 0; i < weights.length; i++) {
                Integer weight = (Integer) weights[i];
                totalWeight += weight;
                if (sameWeight && i > 0 && !weight.equals(weights[i - 1])) {
                    sameWeight = false;
                }
            }
            java.util.Random random = new java.util.Random();
            int randomPos = random.nextInt(totalWeight);
            if (!sameWeight) {
                for (String ip : ServerIps.WEIGHT_LIST.keySet()) {
                    Integer value = ServerIps.WEIGHT_LIST.get(ip);
                    if (randomPos < value) {
                        return ip;
                    }
                    randomPos = randomPos - value;
                }
            }
            return (String) ServerIps.WEIGHT_LIST.keySet().toArray()[new java.util.Random().nextInt(ServerIps.WEIGHT_LIST.size())];
        }
        public static void main(String[] args) {
            // 连续调用10次
            for (int i = 0; i < 10; i++) {
                System.out.println(getServer());
            }
        }
}
