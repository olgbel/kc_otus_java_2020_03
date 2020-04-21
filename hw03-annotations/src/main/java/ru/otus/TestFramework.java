package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.reports.Report;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestFramework {

    private Class clazz;
    private Report report;
    private List<Method> beforeMethods = new ArrayList<>();
    private List<Method> testMethods = new ArrayList<>();
    private List<Method> afterMethods = new ArrayList<>();

    public TestFramework(Class clazz, Report report) {
        this.clazz = clazz;
        this.report = report;
    }

    public void runTests() {
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            }
        }

        for (Method testMethod : testMethods) {
            runTriple(testMethod);
        }
    }

    private void runTriple(Method testMethod) {
        try {
            Object testClassInstance = clazz.getDeclaredConstructor().newInstance();

            for (Method beforeMethod : beforeMethods) {
                report.increaseAll();
                beforeMethod.invoke(testClassInstance);
                report.increaseSucceeded();
            }

            report.increaseAll();
            testMethod.invoke(testClassInstance);
            report.increaseSucceeded();

            for (Method afterMethod : afterMethods) {
                report.increaseAll();
                afterMethod.invoke(testClassInstance);
                report.increaseSucceeded();
            }
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            System.out.println("exception " + e.getClass());
            report.increaseFailed();
        }
    }
}
