package ru.otus;

import java.util.concurrent.atomic.AtomicInteger;

public class NumberSequencePrinter {
    private final Thread thread1;
    private final Thread thread2;

    private final AtomicInteger counter1 = new AtomicInteger(1);
    private final AtomicInteger counter2 = new AtomicInteger(1);

    private boolean ascending_order = true;

    public NumberSequencePrinter() {
        thread1 = new Thread(() -> print(counter1), "Thread#1");
        thread2 = new Thread(() -> print(counter2), "Thread#2");
    }

    public void print() {
        thread1.start();
        thread2.start();
    }

    private synchronized void print(AtomicInteger counter) {
        while (true) {
            if (counter1.get() == 11 && counter2.get() == 11) {
                counter1.set(9);
                counter2.set(9);
                ascending_order = false;
            }

            if (counter.get() == 1) {
                ascending_order = true;
            }

            int currentNumber = ascending_order ? counter.getAndIncrement() : counter.getAndDecrement();
            System.out.println(Thread.currentThread().getName() + ": " + currentNumber);

            if (Thread.State.WAITING.equals(thread1.getState()) || Thread.State.WAITING.equals(thread2.getState())) {
                notifyAll();
            }

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
