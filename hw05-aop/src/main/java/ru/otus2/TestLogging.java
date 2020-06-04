package ru.otus2;

public class TestLogging implements TestLoggingInterface {
    @Log
    public void calculation(int param) {
        System.out.println("execute calculation");
    }

    @Log
    public int calculation(int a, int b) {
        System.out.println("execute calculation with 2 parameters");
        return a + b;
    }

    public void doUselessWork(int a) {
        System.out.println("execute doUselessWork");
    }
}
