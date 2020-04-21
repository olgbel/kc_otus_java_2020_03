package ru.otus.reports;

public class Report {
    private int succeeded;
    private int failed;
    private int all;

    public void getStatistics(){
        System.out.println("-----------------------");
        System.out.println("Total tests: " + all);
        System.out.println("Failed tests: " + failed);
        System.out.println("Succeeded tests: " + succeeded);
    }

    public void increaseAll() {
        all++;
    }

    public void increaseFailed() {
        failed++;
    }

    public void increaseSucceeded() {
        succeeded++;
    }
}
