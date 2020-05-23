package ru.otus;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.exceptions.IncorrectFormatAmountException;
import ru.otus.exceptions.NotEnoughMoneyException;

import java.util.*;

public class ATMTest {

    private ATM atmWithEmptyCells;
    private ATM atmWithFilledCells;

    @Before
    public void initEmptyATM() {
        Map<BanknoteAmountEnum, ATMCell> emptyCells = new HashMap<>();

        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            emptyCells.put(banknoteAmount, new ATMCell(new ArrayList<>()));
        }
        atmWithEmptyCells = new ATM(emptyCells);
    }

    @Before
    public void initFilledATM() {
        Map<BanknoteAmountEnum, ATMCell> cells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            List<Banknote> banknotes = new ArrayList<>();
            if (!banknoteAmount.equals(BanknoteAmountEnum.HUNDRED)) {
                banknotes.add(new Banknote(banknoteAmount));
                banknotes.add(new Banknote(banknoteAmount));
                banknotes.add(new Banknote(banknoteAmount));
            }

            ATMCell cell = new ATMCell(banknotes);
            cells.put(banknoteAmount, cell);
        }

        atmWithFilledCells = new ATM(cells);
    }

    @Test
    public void acceptBanknotesForEmptyCells() {
        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(new Banknote(BanknoteAmountEnum.HUNDRED));
        banknotes.add(new Banknote(BanknoteAmountEnum.FIVE_THOUSAND));
        banknotes.add(new Banknote(BanknoteAmountEnum.TWO_HUNDRED));

        atmWithEmptyCells.putMoney(banknotes);
        Map<BanknoteAmountEnum, ATMCell> actualResult = atmWithEmptyCells.getCells();

        Map<BanknoteAmountEnum, ATMCell> expectedResult = new HashMap<>();
        expectedResult.put(BanknoteAmountEnum.FIFTY, new ATMCell(new ArrayList<>()));
        expectedResult.put(BanknoteAmountEnum.HUNDRED, new ATMCell(Arrays.asList(new Banknote(BanknoteAmountEnum.HUNDRED))));
        expectedResult.put(BanknoteAmountEnum.TWO_HUNDRED, new ATMCell(Arrays.asList(new Banknote(BanknoteAmountEnum.TWO_HUNDRED))));
        expectedResult.put(BanknoteAmountEnum.FIVE_HUNDRED, new ATMCell(new ArrayList<>()));
        expectedResult.put(BanknoteAmountEnum.THOUSAND, new ATMCell(new ArrayList<>()));
        expectedResult.put(BanknoteAmountEnum.FIVE_THOUSAND, new ATMCell(Arrays.asList(new Banknote(BanknoteAmountEnum.FIVE_THOUSAND))));

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void acceptBanknotesForNotEmptyCells() {
        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(new Banknote(BanknoteAmountEnum.HUNDRED));
        banknotes.add(new Banknote(BanknoteAmountEnum.FIVE_THOUSAND));
        banknotes.add(new Banknote(BanknoteAmountEnum.TWO_HUNDRED));

        atmWithFilledCells.putMoney(banknotes);
        Map<BanknoteAmountEnum, ATMCell> actualResult = atmWithFilledCells.getCells();

        Map<BanknoteAmountEnum, ATMCell> expectedResult = new HashMap<>();
        expectedResult.put(BanknoteAmountEnum.FIFTY,
                new ATMCell(Arrays.asList(new Banknote(BanknoteAmountEnum.FIFTY),
                        new Banknote(BanknoteAmountEnum.FIFTY),
                        new Banknote(BanknoteAmountEnum.FIFTY))));
        expectedResult.put(BanknoteAmountEnum.HUNDRED, new ATMCell(Arrays.asList(new Banknote(BanknoteAmountEnum.HUNDRED))));
        expectedResult.put(BanknoteAmountEnum.TWO_HUNDRED, new ATMCell(Arrays.asList(new Banknote(BanknoteAmountEnum.TWO_HUNDRED),
                new Banknote(BanknoteAmountEnum.TWO_HUNDRED),
                new Banknote(BanknoteAmountEnum.TWO_HUNDRED),
                new Banknote(BanknoteAmountEnum.TWO_HUNDRED))));
        expectedResult.put(BanknoteAmountEnum.FIVE_HUNDRED, new ATMCell(Arrays.asList(new Banknote(BanknoteAmountEnum.FIVE_HUNDRED),
                new Banknote(BanknoteAmountEnum.FIVE_HUNDRED),
                new Banknote(BanknoteAmountEnum.FIVE_HUNDRED))));
        expectedResult.put(BanknoteAmountEnum.THOUSAND, new ATMCell(Arrays.asList(new Banknote(BanknoteAmountEnum.THOUSAND),
                new Banknote(BanknoteAmountEnum.THOUSAND),
                new Banknote(BanknoteAmountEnum.THOUSAND))));
        expectedResult.put(BanknoteAmountEnum.FIVE_THOUSAND, new ATMCell(Arrays.asList(new Banknote(BanknoteAmountEnum.FIVE_THOUSAND),
                new Banknote(BanknoteAmountEnum.FIVE_THOUSAND),
                new Banknote(BanknoteAmountEnum.FIVE_THOUSAND),
                new Banknote(BanknoteAmountEnum.FIVE_THOUSAND))));
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void giveOutBanknotesTooMuchSum() throws NotEnoughMoneyException, IncorrectFormatAmountException {
        atmWithEmptyCells.withdrawMoney(1000);
    }

    @Test(expected = IncorrectFormatAmountException.class)
    public void giveOutBanknotesIncorrectFormatSum() throws IncorrectFormatAmountException, NotEnoughMoneyException {
        atmWithFilledCells.withdrawMoney(12345);
    }

    @Test
    public void giveOutBanknotes() throws IncorrectFormatAmountException, NotEnoughMoneyException {
        List<Banknote> actualBanknotes = atmWithFilledCells.withdrawMoney(1300);

        List<Banknote> expectedBanknotes = Arrays.asList(
                new Banknote(BanknoteAmountEnum.TWO_HUNDRED),
                new Banknote(BanknoteAmountEnum.FIFTY),
                new Banknote(BanknoteAmountEnum.FIFTY),
                new Banknote(BanknoteAmountEnum.THOUSAND)
        );

        Assert.assertEquals(actualBanknotes, expectedBanknotes);

        long actualBalance = atmWithFilledCells.giveOutBalance();
        long expectedBalance = 5000 * 3 + 1000 * 3 + 500 * 3 + 200 * 3 + 50 * 3 - 200 - 50 - 50 - 1000;

        Assert.assertEquals(actualBalance, expectedBalance);
    }

    @Test
    public void giveOutBalanceFromATMWithEmptyCells() {
        long actualBalance = atmWithEmptyCells.giveOutBalance();
        long expectedBalance = 0;

        Assert.assertEquals(actualBalance, expectedBalance);
    }

    @Test
    public void giveOutBalanceFromATMWithFilledCells() {
        long actualBalance = atmWithFilledCells.giveOutBalance();
        long expectedBalance = 5000 * 3 + 1000 * 3 + 500 * 3 + 200 * 3 + 50 * 3;

        Assert.assertEquals(actualBalance, expectedBalance);
    }
}