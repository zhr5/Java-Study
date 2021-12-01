package dynamicProxy.jdkDynamicProxy;

public class Test {
    public static  void main(String args[]) {
        SmsService smsService = new SmsServiceImpl();
        SmsService smsServiceImplProxy = (SmsService) JdkProxyFactory.getProxy(smsService);
        smsServiceImplProxy.send("jdkDynamicProxy");
    }
}
