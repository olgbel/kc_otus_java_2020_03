package ru.otus;

public interface TestLoggingInterface {
    @Log
    void calculation(int param);

    @Log
    int calculation(int a, int b);

    void doUselessWork(int a);
}
