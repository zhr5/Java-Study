package dynamicProxy.cglibDynamicProxy;

public class Test {
        public static void main(String[] args) {
            AliSmsService aliSmsService = (AliSmsService) CglibProxyFactory.getProxy(AliSmsService.class);
            aliSmsService.send("cglibDynamicProxy");
        }
}
