package com.ml.print.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Mr.ml
 * @date 2021/3/7
 *
 * 使用阻塞队列做同步操作实现交替打印ABC
 */
public class PrintABC {

    private BlockingQueue<Character> blockingQueue1;
    private BlockingQueue<Character> blockingQueue2;
    private BlockingQueue<Character> blockingQueue3;

    public PrintABC() {
        blockingQueue1 = new ArrayBlockingQueue<>(1);
        blockingQueue2 = new ArrayBlockingQueue<>(1);
        blockingQueue3 = new ArrayBlockingQueue<>(1);
        blockingQueue1.add('A');
    }

    public void printA() throws InterruptedException {
        if (blockingQueue1.take() == 'A') {
            System.out.println("A");
            blockingQueue2.put('B');
        }
    }

    public void printB() throws InterruptedException {
        if (blockingQueue2.take() == 'B') {
            System.out.println("B");
            blockingQueue3.put('C');
        }
    }

    public void printC() throws InterruptedException {
        if (blockingQueue3.take() == 'C') {
            System.out.println("C");
            blockingQueue1.put('A');
        }
    }

    public static void main(String[] args) {
        PrintABC printABC = new PrintABC();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    printABC.printA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    printABC.printB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    printABC.printC();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();
    }

}
