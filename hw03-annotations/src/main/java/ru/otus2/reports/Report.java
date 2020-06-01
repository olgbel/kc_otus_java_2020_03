package ru.otus2.reports;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Report {
    private final Map<Method, TestResult> summary = new HashMap<>();

    public void putRecord(Method method, TestResult testResult) {
        summary.put(method, testResult);
    }

    public void printStatistics() {
        System.out.println("-----------------------");
        System.out.println("Total tests: " + summary.size());
        System.out.println("Failed tests: " + summary.entrySet().stream().filter(test -> TestResult.FAILED.equals(test.getValue())).count());
        System.out.println("Succeeded tests: " + summary.entrySet().stream().filter(test -> TestResult.SUCCESS.equals(test.getValue())).count());
        System.out.println("-----------------------");
    }
}

