package ru.otus2;

import java.util.List;

public class Department implements IDepartment {
    private final List<IATM> atms;

    public Department(List<IATM> atms) {
        this.atms = atms;
    }

    public void restoreATMs() {
        for (int i = 0; i < atms.size(); i++) {
            ATMProcessor current = (ATMProcessor) atms.get(i);
            if (atms.size() > 1 && i != atms.size() - 1) {
                ATMProcessor next = (ATMProcessor) atms.get(i + 1);
                current.setNext(next);
            }
            current.process((IATM) current);
        }
    }

    public List<IATM> getATMs() {
        return atms;
    }

    public long getBalance() {
        return atms.stream().mapToLong(IATM::giveOutBalance).sum();
    }

    @Override
    public String toString() {
        return atms.toString();
    }
}
