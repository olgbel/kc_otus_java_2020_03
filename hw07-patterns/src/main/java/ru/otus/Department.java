package ru.otus;

import ru.otus.command.ATMCommand;

import java.util.ArrayList;
import java.util.List;

public class Department implements IDepartment {
    private final List<ATM> atms;
    private final List<ATMCommand> commands = new ArrayList<>();

    public Department(List<ATM> atms) {
        this.atms = atms;
    }

    public void restoreATMs() {
        for (int i = 0; i < atms.size(); i++) {
            ATMProcessor current = atms.get(i);
            if (atms.size() > 1 && i != atms.size() - 1) {
                ATMProcessor next = atms.get(i + 1);
                current.setNext(next);
            }
            current.process((IATM) current);
            atms.get(i).onUpdate("ATM has been restored: " + atms.get(i));
        }
    }

    public List<ATM> getATMs() {
        return atms;
    }

    public void addATM(ATM atm) {
        atms.add(atm);
    }

    public void removeATM(ATM atm) {
        atms.remove(atm);
    }

    public void addCommand(ATMCommand command) {
        commands.add(command);
    }

    public long getBalance() {
        long balance = 0;
        for (ATM atm : atms) {
            balance += commands.stream().map(cmd -> cmd.execute(atm))
                    .reduce(Long::sum).orElse(0L);
        }
        return balance;
    }

    @Override
    public String toString() {
        return atms.toString();
    }
}
