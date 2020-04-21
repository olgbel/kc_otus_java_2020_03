package ru.otus;

import ru.otus.reports.Report;
import ru.otus.tests.TestClassWithFailedBeforeTest;
import ru.otus.tests.TestClassWithFailedMainTest;
import ru.otus.tests.TestClassWithFailedAfterTest;

public class Main {

    public static void main(String[] args) {
        Report report = new Report();
        TestFramework testFramework = new TestFramework(TestClassWithFailedBeforeTest.class, report);
        testFramework.runTests();
        report.getStatistics();

        Report report2 = new Report();
        TestFramework testFramework2 = new TestFramework(TestClassWithFailedMainTest.class, report2);
        testFramework2.runTests();
        report2.getStatistics();

        Report report3 = new Report();
        TestFramework testFramework3 = new TestFramework(TestClassWithFailedAfterTest.class, report3);
        testFramework3.runTests();
        report3.getStatistics();

    }
}
