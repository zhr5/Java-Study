package main.java.com.zyr.base.polymorphic;

public class SingleClass {
    //孩子1：
    public String child(){
        System.out.println("child1");
        return "child1";
    }
    //孩子2：与孩子1参数个数不同
    public String child(String a){
        System.out.println("child2");
        return "child2";
    }
    //孩子3：与孩子4参数顺序不同
    public String child(int a,String s){
        System.out.println("child3");
        return "child3";
    }
    //孩子4：与孩子3参数顺序不同
    public String child(String s,int a){
        System.out.println("child4");
        return "child4";
    }
    public static void main(String[] args){
        //重载方法调用：略
    }
}
