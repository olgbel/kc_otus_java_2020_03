package ru.otus;

import java.util.List;

public class Department implements IDepartment {
    private final List<IATM> atms;

    public Department(List<IATM> atms) {
        this.atms = atms;
    }

    public void restoreATMs() {
        atms.forEach(IATM::restore);
    }

    public List<IATM> getATMs() {
        return atms;
    }

    public long getBalance() {
        return atms.stream().mapToLong(IATM::giveOutBalance).sum();
    }
}
