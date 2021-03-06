package ru.otus;

import ru.otus.exceptions.IncorrectFormatAmountException;
import ru.otus.exceptions.NotEnoughMoneyException;
import ru.otus.memento.ATMMemento;

import java.util.*;

public class ATMImpl extends ATMProcessor implements ATM, Listener {
    private Map<BanknoteAmountEnum, ATMCellImpl> cells;
    private final ATMMemento atmInitialSnapshot;

    public ATMImpl(Map<BanknoteAmountEnum, ATMCellImpl> cells) {
        this.cells = cells;
        atmInitialSnapshot = new ATMMemento(cells);
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
        long currentBalance = 0;
        for (Map.Entry<BanknoteAmountEnum, ATMCellImpl> cell : cells.entrySet()) {
            currentBalance += cell.getKey().getAmount() * cell.getValue().getBanknotesCount();
        }
        if (amount < 1 || amount > currentBalance) {
            throw new NotEnoughMoneyException("There is no such money in ATM");
        }
        if (amount % BanknoteAmountEnum.FIFTY.getAmount() != 0) {
            throw new IncorrectFormatAmountException("Amount should be multiple of 50.");
        }
    }

    public Map<BanknoteAmountEnum, ATMCellImpl> getCells() {
        return cells;
    }

    @Override
    protected void restore() {
        cells = atmInitialSnapshot.getState();
    }

    @Override
    public void onUpdate(String updateInfo) {
        System.out.println(updateInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cells);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) return false;

        ATMImpl atm = (ATMImpl) obj;
        if (atm.cells.size() != cells.size())
            return false;

        return atm.cells.entrySet().stream()
                .allMatch(e -> e.getValue().equals(cells.get(e.getKey())));
    }

    @Override
    public String toString() {
        return this.cells.toString();
    }
}
