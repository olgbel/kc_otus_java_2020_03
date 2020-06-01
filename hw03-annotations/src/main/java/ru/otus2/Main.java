package ru.otus2;

import ru.otus2.reports.Report;
import ru.otus2.tests.TestClassWithFailedAfterAndMainTests;
import ru.otus2.tests.TestClassWithFailedBeforeTest;
import ru.otus2.tests.TestClassWithFailedMainTest;
import ru.otus2.tests.TestClassWithFailedAfterTest;

public class Main {

    public static void main(String[] args) {
        Report report = new Report();
        TestFramework testFramework = new TestFramework(TestClassWithFailedBeforeTest.class, report);
        testFramework.runTests();
        report.printStatistics();

        Report report2 = new Report();
        TestFramework testFramework2 = new TestFramework(TestClassWithFailedMainTest.class, report2);
        testFramework2.runTests();
        report2.printStatistics();

        Report report3 = new Report();
        TestFramework testFramework3 = new TestFramework(TestClassWithFailedAfterTest.class, report3);
        testFramework3.runTests();
        report3.printStatistics();

        Report report4 = new Report();
        TestFramework testFramework4 = new TestFramework(TestClassWithFailedAfterAndMainTests.class, report4);
        testFramework4.runTests();
        report4.printStatistics();
    }
}
