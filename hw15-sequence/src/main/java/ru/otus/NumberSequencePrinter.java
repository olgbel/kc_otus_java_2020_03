package ru.otus;

public class NumberSequencePrinter {
    private static final String FIRST_THREAD_NAME = "Thread#1";
    private static final String SECOND_THREAD_NAME = "Thread#2";

    private static int counter1 = 1;
    private static int counter2 = 1;

    private boolean lock1 = false;
    private boolean lock2 = false;

    private boolean ascending_order = true;

    public static void main(String[] args) {
        NumberSequencePrinter numberSequencePrinter = new NumberSequencePrinter();
        new Thread(() -> numberSequencePrinter.print(counter1), FIRST_THREAD_NAME).start();
        new Thread(() -> numberSequencePrinter.print(counter2), SECOND_THREAD_NAME).start();
    }

    private synchronized void print(int counter) {
        String currentThreadName = Thread.currentThread().getName();
        while (true) {
            try {
                while ((FIRST_THREAD_NAME.equals(currentThreadName) && lock1) ||
                        (SECOND_THREAD_NAME.equals(currentThreadName) && lock2) ||
                        (SECOND_THREAD_NAME.equals(currentThreadName) && !lock1)) {
                    this.wait();
                }

                if (counter == 10) {
                    counter1 = 9;
                    counter2 = 9;
                    ascending_order = false;
                }

                if (counter == 1) {
                    ascending_order = true;
                }

                int currentNumber = ascending_order ? counter++ : counter--;
                System.out.println(currentThreadName + ": " + currentNumber);

                lock1 = FIRST_THREAD_NAME.equals(currentThreadName);
                lock2 = SECOND_THREAD_NAME.equals(currentThreadName);

                notifyAll();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new NotInterestingException(ex);
            }
        }
    }

    private static class NotInterestingException extends RuntimeException {
        NotInterestingException(InterruptedException ex) {
            super(ex);
        }
    }
}
