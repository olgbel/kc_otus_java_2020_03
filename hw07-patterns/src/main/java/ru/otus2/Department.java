package ru.otus2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Department {
    private final Map<ATM, ATMMemento> mementos = new HashMap<>();
    private final List<ATM> atms;

    public Department(List<ATM> atms) {
        this.atms = atms;
        atms.forEach(atm -> mementos.put(atm, atm.save()));
    }

    public void restoreATMs() {
        atms.forEach(atm -> atm.restore(mementos.get(atm)));
    }

    public List<ATM> getATMs(){
        return atms;
    }
}
