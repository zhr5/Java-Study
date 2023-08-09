package org.zhr.java8.optional;

import java.util.Optional;

public class TestOptional {

    private static void main(String args[]){

        //1. 将可能为空的对象转换成Optional对象
        User user=new User("张三",30,1);
        Optional<User> optionalUser=Optional.ofNullable(user);
        if(user!=null){
            int age=user.getAge();
        }

        //2.Optional链式操作
        optionalUser.map(User::getAge).orElse(20);

        //3. Optional短路
        //从数据库中查出某个用户，如果不存在则向数据库中新建用户并返回
        User user1=optionalUser.orElse(new User()); //错误--new User()总是会执行
        optionalUser.orElseGet(()->new User());

        //对于orElseGet，只有Optional中的值为空时，它才会计算备选结果。这样做的好处是可以避免提前计算结果的风险。


        //4.Optional抛出异常
        optionalUser.orElseThrow(()->new RuntimeException("没有用户"));


    }




}
