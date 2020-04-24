package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.reports.Report;
import ru.otus.reports.TestResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestFramework {

    private final Class clazz;
    private final Report report;
    private final List<Method> beforeMethods = new ArrayList<>();
    private final List<Method> testMethods = new ArrayList<>();
    private final List<Method> afterMethods = new ArrayList<>();

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
        Object testClassInstance = getInstance();
        try {
            runBeforeMethods(testClassInstance);
            testMethod.invoke(testClassInstance);
            runAfterMethods(testClassInstance);

            report.putRecord(testMethod, TestResult.SUCCESS);
        } catch (InvocationTargetException | IllegalAccessException e) {
            System.out.println("exception " + e.getClass());
            report.putRecord(testMethod, TestResult.FAILED);
        } finally {
            try {
                runAfterMethods(testClassInstance);
            } catch (IllegalAccessException | InvocationTargetException exception) {
                System.out.println("exception in finally after method" + exception);
            }
        }
    }

    private void runAfterMethods(Object testClassInstance) throws InvocationTargetException, IllegalAccessException {
        for (Method afterMethod : afterMethods) {
            afterMethod.invoke(testClassInstance);
        }
    }

    private void runBeforeMethods(Object testClassInstance) throws InvocationTargetException, IllegalAccessException {
        for (Method beforeMethod : beforeMethods) {
            beforeMethod.invoke(testClassInstance);
        }
    }

    private Object getInstance() {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.out.println("Cannon create new instance " + e);
        }
        return null;
    }
}
