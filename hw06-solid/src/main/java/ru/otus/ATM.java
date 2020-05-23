package ru.otus;

import ru.otus.exceptions.IncorrectFormatAmountException;
import ru.otus.exceptions.NotEnoughMoneyException;

import java.util.*;

public class ATM {
    private final Map<BanknoteAmountEnum, ATMCell> cells;

    public ATM(Map<BanknoteAmountEnum, ATMCell> cells) {
        this.cells = cells;
    }

    public void putMoney(List<Banknote> banknotes) {
        banknotes.forEach(banknote -> cells.get(banknote.getAmount()).put(banknote));
    }

    public List<Banknote> withdrawMoney(int amount) throws NotEnoughMoneyException, IncorrectFormatAmountException {
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
                actualBanknotesCount = cells.get(banknoteAmount).getBanknotes().size();
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
        return cells.get(banknoteAmount).getBanknotes().size() >= banknotesCount;
    }

    private void checkAmount(int amount) throws NotEnoughMoneyException, IncorrectFormatAmountException {
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
            balance += cell.getKey().getAmount() * cell.getValue().getBanknotes().size();
        }
        return balance;
    }

    public Map<BanknoteAmountEnum, ATMCell> getCells() {
        return cells;
    }

    public String toString() {
        return this.cells.toString();
    }
}
