package ru.otus;

import java.util.List;

public class Department implements IDepartment {
    private final List<ATM> atms;

    public Department(List<ATM> atms) {
        this.atms = atms;
    }

    public void restoreATMs() {
        atms.forEach(ATM::restore);
    }

    public List<ATM> getATMs() {
        return atms;
    }
}
