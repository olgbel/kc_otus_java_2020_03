package ru.otus;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.exceptions.IncorrectFormatAmountException;
import ru.otus.exceptions.NotEnoughMoneyException;

import java.util.*;

public class ATMTest {

    private ATMImpl atmWithEmptyCells;
    private ATMImpl atmWithFilledCells;

    @Before
    public void initEmptyATM() {
        Map<BanknoteAmountEnum, ATMCellImpl> emptyCells = new HashMap<>();

        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            emptyCells.put(banknoteAmount, new ATMCellImpl(new ArrayList<>()));
        }
        atmWithEmptyCells = new ATMImpl(emptyCells);
    }

    @Before
    public void initFilledATM() {
        Map<BanknoteAmountEnum, ATMCellImpl> cells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            List<Banknote> banknotes = new ArrayList<>();
            if (!banknoteAmount.equals(BanknoteAmountEnum.HUNDRED)) {
                banknotes.add(new Banknote(banknoteAmount));
                banknotes.add(new Banknote(banknoteAmount));
                banknotes.add(new Banknote(banknoteAmount));
            }

            ATMCellImpl cell = new ATMCellImpl(banknotes);
            cells.put(banknoteAmount, cell);
        }

        atmWithFilledCells = new ATMImpl(cells);
    }

    @Test
    public void putBanknoteInATM() {
        List<Banknote> banknotes = new ArrayList<>();
        ATMCellImpl cell = new ATMCellImpl(banknotes);

        cell.put(new Banknote(BanknoteAmountEnum.FIFTY));
        int actualResult = cell.getBanknotesCount();
        int expectedResult = 1;

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void withdrawFromEmptyCell() {
        List<Banknote> banknotes = new ArrayList<>();
        ATMCellImpl cell = new ATMCellImpl(banknotes);
        cell.withdraw();
    }

    @Test
    public void withdrawFromFilledCell() {
        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(new Banknote(BanknoteAmountEnum.TWO_HUNDRED));

        ATMCellImpl cell = new ATMCellImpl(banknotes);

        Banknote actualResult = cell.withdraw();
        Assert.assertNotNull(actualResult);
    }

    @Test
    public void acceptBanknotesForEmptyCells() {
        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(new Banknote(BanknoteAmountEnum.HUNDRED));
        banknotes.add(new Banknote(BanknoteAmountEnum.FIVE_THOUSAND));
        banknotes.add(new Banknote(BanknoteAmountEnum.TWO_HUNDRED));

        atmWithEmptyCells.putMoney(banknotes);
        Map<BanknoteAmountEnum, ATMCellImpl> actualResult = atmWithEmptyCells.getCells();

        Map<BanknoteAmountEnum, ATMCellImpl> expectedResult = new HashMap<>();
        expectedResult.put(BanknoteAmountEnum.FIFTY, new ATMCellImpl(new ArrayList<>()));
        expectedResult.put(BanknoteAmountEnum.HUNDRED, new ATMCellImpl(Arrays.asList(new Banknote(BanknoteAmountEnum.HUNDRED))));
        expectedResult.put(BanknoteAmountEnum.TWO_HUNDRED, new ATMCellImpl(Arrays.asList(new Banknote(BanknoteAmountEnum.TWO_HUNDRED))));
        expectedResult.put(BanknoteAmountEnum.FIVE_HUNDRED, new ATMCellImpl(new ArrayList<>()));
        expectedResult.put(BanknoteAmountEnum.THOUSAND, new ATMCellImpl(new ArrayList<>()));
        expectedResult.put(BanknoteAmountEnum.FIVE_THOUSAND, new ATMCellImpl(Arrays.asList(new Banknote(BanknoteAmountEnum.FIVE_THOUSAND))));

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void acceptBanknotesForNotEmptyCells() {
        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(new Banknote(BanknoteAmountEnum.HUNDRED));
        banknotes.add(new Banknote(BanknoteAmountEnum.FIVE_THOUSAND));
        banknotes.add(new Banknote(BanknoteAmountEnum.TWO_HUNDRED));

        atmWithFilledCells.putMoney(banknotes);
        Map<BanknoteAmountEnum, ATMCellImpl> actualResult = atmWithFilledCells.getCells();

        Map<BanknoteAmountEnum, ATMCellImpl> expectedResult = new HashMap<>();
        expectedResult.put(BanknoteAmountEnum.FIFTY,
                new ATMCellImpl(Arrays.asList(new Banknote(BanknoteAmountEnum.FIFTY),
                        new Banknote(BanknoteAmountEnum.FIFTY),
                        new Banknote(BanknoteAmountEnum.FIFTY))));
        expectedResult.put(BanknoteAmountEnum.HUNDRED, new ATMCellImpl(Arrays.asList(new Banknote(BanknoteAmountEnum.HUNDRED))));
        expectedResult.put(BanknoteAmountEnum.TWO_HUNDRED, new ATMCellImpl(Arrays.asList(new Banknote(BanknoteAmountEnum.TWO_HUNDRED),
                new Banknote(BanknoteAmountEnum.TWO_HUNDRED),
                new Banknote(BanknoteAmountEnum.TWO_HUNDRED),
                new Banknote(BanknoteAmountEnum.TWO_HUNDRED))));
        expectedResult.put(BanknoteAmountEnum.FIVE_HUNDRED, new ATMCellImpl(Arrays.asList(new Banknote(BanknoteAmountEnum.FIVE_HUNDRED),
                new Banknote(BanknoteAmountEnum.FIVE_HUNDRED),
                new Banknote(BanknoteAmountEnum.FIVE_HUNDRED))));
        expectedResult.put(BanknoteAmountEnum.THOUSAND, new ATMCellImpl(Arrays.asList(new Banknote(BanknoteAmountEnum.THOUSAND),
                new Banknote(BanknoteAmountEnum.THOUSAND),
                new Banknote(BanknoteAmountEnum.THOUSAND))));
        expectedResult.put(BanknoteAmountEnum.FIVE_THOUSAND, new ATMCellImpl(Arrays.asList(new Banknote(BanknoteAmountEnum.FIVE_THOUSAND),
                new Banknote(BanknoteAmountEnum.FIVE_THOUSAND),
                new Banknote(BanknoteAmountEnum.FIVE_THOUSAND),
                new Banknote(BanknoteAmountEnum.FIVE_THOUSAND))));
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void giveOutBanknotesTooMuchSum() {
        atmWithEmptyCells.withdrawMoney(1000);
    }

    @Test(expected = IncorrectFormatAmountException.class)
    public void giveOutBanknotesIncorrectFormatSum() {
        atmWithFilledCells.withdrawMoney(12345);
    }

    @Test
    public void giveOutBanknotes() {
        List<Banknote> actualBanknotes = atmWithFilledCells.withdrawMoney(1300);
        actualBanknotes.sort((o1, o2) -> o2.getAmount().getAmount() - o1.getAmount().getAmount());
        List<Banknote> expectedBanknotes = Arrays.asList(
                new Banknote(BanknoteAmountEnum.TWO_HUNDRED),
                new Banknote(BanknoteAmountEnum.FIFTY),
                new Banknote(BanknoteAmountEnum.FIFTY),
                new Banknote(BanknoteAmountEnum.THOUSAND)
        );
        expectedBanknotes.sort((o1, o2) -> o2.getAmount().getAmount() - o1.getAmount().getAmount());

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