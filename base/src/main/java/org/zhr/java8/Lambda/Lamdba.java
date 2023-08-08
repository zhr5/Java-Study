package org.zhr.java8.Lambda;


import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;

public class Lamdba {
    public static void main(String args[]) {

        /* Runnable 接口 */
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("The runable now is using!");
            }
        }).start();
        //用lambda
        new Thread(() -> System.out.println("It's a lambda function!")).start();


        /*Comperator 接口 */
        List<Integer> list = Arrays.asList(1, 2, 3);

        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;}
        });

        //Lambda
        Collections.sort(list, (Integer o1, Integer o2) -> o1 - o2);
        //分解开
        Comparator<Integer> comperator = (Integer o1, Integer o2) -> o1 - o2;
        Collections.sort(list, comperator);


        /**Listener 接口*/
        JButton button = new JButton();
        button.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                e.getItem();
            }
        });
        //lambda
        button.addItemListener(e -> e.getItem());


        Arrays.asList( "a", "b", "d" ).forEach( e -> System.out.println( e ) );

        Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> e1.compareTo( e2 ) );

       /* 可选的大括号*/
        Arrays.asList( "a", "b", "c" ).forEach( e -> {
            System.out.println( e );
            System.out.println( e );
        } );

    }
}

