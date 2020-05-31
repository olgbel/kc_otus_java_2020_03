package ru.otus2;

import ru.otus2.exceptions.IncorrectFormatAmountException;
import ru.otus2.exceptions.NotEnoughMoneyException;

import java.util.*;

public class ATM implements IATM {
    private Map<BanknoteAmountEnum, ATMCell> cells;
    private ATMMemento atmInitialSnapshot;

    public ATM(Map<BanknoteAmountEnum, ATMCell> cells) {
        this.cells = cells;
        atmInitialSnapshot = save();
    }

    public void putMoney(List<Banknote> banknotes) {
        banknotes.forEach(banknote -> cells.get(banknote.getAmount()).put(banknote));
    }

    public List<Banknote> withdrawMoney(int amount) {
        checkAmount(amount);

        List<Banknote> banknotes = new ArrayList<>();
        List<BanknoteAmountEnum> sortedBanknotesEnum = Arrays.asList(BanknoteAmountEnum.values());
        sortedBanknotesEnum.sort((o1, o2) -> o2.getAmount() - o1.getAmount());
        int remain = amount;
        Map<BanknoteAmountEnum, Integer> banknotesBuffer = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : sortedBanknotesEnum) {
            int necessaryBanknotesCount = remain / banknoteAmount.getAmount();
            if (necessaryBanknotesCount == 0) {
                continue;
            }

            int actualBanknotesCount;
            if (!isEnoughBanknotesInATM(banknoteAmount, necessaryBanknotesCount)) {
                actualBanknotesCount = cells.get(banknoteAmount).getBanknotesCount();
                banknotesBuffer.put(banknoteAmount, actualBanknotesCount);
                remain -= banknoteAmount.getAmount() * actualBanknotesCount;
                continue;
            }

            banknotesBuffer.put(banknoteAmount, necessaryBanknotesCount);
            remain -= banknoteAmount.getAmount() * necessaryBanknotesCount;
            if (remain == 0) {
                break;
            }
        }
        if (remain != 0) {
            throw new NotEnoughMoneyException("Impossible for this stupid algorithm.");
        }

        for (Map.Entry<BanknoteAmountEnum, Integer> bbEntry : banknotesBuffer.entrySet()) {
            var banknotesAmount = bbEntry.getKey();
            var banknotesCount = bbEntry.getValue();
            for (int i = 0; i < banknotesCount; i++) {
                banknotes.add(cells.get(banknotesAmount).withdraw());
            }
        }
        return banknotes;
    }

    private boolean isEnoughBanknotesInATM(BanknoteAmountEnum banknoteAmount, int banknotesCount) {
        return cells.get(banknoteAmount).getBanknotesCount() >= banknotesCount;
    }

    private void checkAmount(int amount) {
        long currentBalance = giveOutBalance();
        if (amount < 1 || amount > currentBalance) {
            throw new NotEnoughMoneyException("There is no such money in ATM");
        }
        if (amount % BanknoteAmountEnum.FIFTY.getAmount() != 0) {
            throw new IncorrectFormatAmountException("Amount should be multiple of 50.");
        }
    }

    public long giveOutBalance() {
        long balance = 0;
        for (Map.Entry<BanknoteAmountEnum, ATMCell> cell : cells.entrySet()) {
            balance += cell.getKey().getAmount() * cell.getValue().getBanknotesCount();
        }
        return balance;
    }

    public String toString() {
        return this.cells.toString();
    }

    public ATMMemento save() {
        Map<BanknoteAmountEnum, ATMCell> ccc = new HashMap<>();
        for (Map.Entry<BanknoteAmountEnum, ATMCell> entry : cells.entrySet()) {
            List<Banknote> banknotes = new ArrayList<>();
            for (int i = 0; i < entry.getValue().getBanknotesCount(); i++) {
                banknotes.add(new Banknote(entry.getKey()));
            }
            ccc.put(entry.getKey(), new ATMCell(banknotes));
        }
        return new ATMMemento(ccc);
    }

    public void restore(ATMMemento m) {
        cells = m.getState();
    }
}
