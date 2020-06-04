package ru.otus;

import ru.otus.command.ATMCommand;

import java.util.ArrayList;
import java.util.List;

public class DepartmentImpl implements Department {
    private final List<ATMImpl> atms;
    private final List<ATMCommand> commands = new ArrayList<>();

    public DepartmentImpl(List<ATMImpl> atms) {
        this.atms = atms;
    }

    public void restoreATMs() {
        for (int i = 0; i < atms.size(); i++) {
            ATMProcessor current = atms.get(i);
            if (atms.size() > 1 && i != atms.size() - 1) {
                ATMProcessor next = atms.get(i + 1);
                current.setNext(next);
            }
            current.process((ATM) current);
            atms.get(i).onUpdate("ATM has been restored: " + atms.get(i));
        }
    }

    public List<ATMImpl> getATMs() {
        return atms;
    }

    public void addATM(ATMImpl atm) {
        atms.add(atm);
    }

    public void removeATM(ATMImpl atm) {
        atms.remove(atm);
    }

    public void addCommand(ATMCommand command) {
        commands.add(command);
    }

    public long getBalance() {
        long balance = 0;
        for (ATMImpl atm : atms) {
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
