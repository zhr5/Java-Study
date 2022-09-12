package com.zyr.javase.base.polymorphic;

public class Test {
    public static void main(String[] args) {
        Father father=new Father();
        Father sonA=new SonA();
        Father sonB=new SonB();
        father.dealHouse();
        sonA.dealHouse();
        sonB.dealHouse();
    }
}

//父亲处置房产
//大儿子处置房产
//小儿子处置房产
