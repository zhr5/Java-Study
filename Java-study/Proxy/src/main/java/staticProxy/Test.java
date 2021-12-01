package staticProxy;

public class Test {
    public static  void main(String args[]) {
        SmsService smsService = new SmsServiceImpl();
        SmsProxy smsProxy = new SmsProxy(smsService);
        smsProxy.send("static proxy");
    }
}
