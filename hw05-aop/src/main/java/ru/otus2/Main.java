package ru.otus2;

public class Main {

    public static void main(String[] args) {
        TestLoggingInterface myClass = Ioc.createMyClass();
        myClass.calculation(6);
        myClass.doUselessWork(33);
        myClass.calculation(3, 4);

        myClass.calculation(77);
        myClass.doUselessWork(44);
        myClass.calculation(7, 8);
    }
}
