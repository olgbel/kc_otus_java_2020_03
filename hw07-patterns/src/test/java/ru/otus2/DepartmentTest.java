package ru.otus2;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.*;
import ru.otus.command.GiveATMBalance;

import java.util.*;

public class DepartmentTest {

    @Test
    public void restoreSingleATMTest() {
        Map<BanknoteAmountEnum, ATMCellImpl> cells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            cells.put(banknoteAmount, new ATMCellImpl(new ArrayList<>()));
        }

        ATMImpl atm = new ATMImpl(cells);
        DepartmentImpl department = new DepartmentImpl(Collections.singletonList(atm));
        atm.putMoney(Arrays.asList(new Banknote(BanknoteAmountEnum.THOUSAND),
                new Banknote(BanknoteAmountEnum.FIFTY),
                new Banknote(BanknoteAmountEnum.FIVE_THOUSAND)));

        department.restoreATMs();

        List<ATMImpl> actualATMs = department.getATMs();

        Map<BanknoteAmountEnum, ATMCellImpl> emptyCells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            emptyCells.put(banknoteAmount, new ATMCellImpl(new ArrayList<>()));
        }
        List<ATMImpl> expectedATMs = new DepartmentImpl(Collections.singletonList(new ATMImpl(emptyCells))).getATMs();
        Assert.assertEquals(expectedATMs, actualATMs);
    }

    @Test
    public void restoreATMsTest(){
        Map<BanknoteAmountEnum, ATMCellImpl> emptyCells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            emptyCells.put(banknoteAmount, new ATMCellImpl(new ArrayList<>()));
        }
        ATMImpl emptyATM = new ATMImpl(emptyCells);

        Map<BanknoteAmountEnum, ATMCellImpl> cells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            List<Banknote> banknotes = new ArrayList<>();
            banknotes.add(new Banknote(banknoteAmount));
            cells.put(banknoteAmount, new ATMCellImpl(banknotes));
        }
        ATMImpl atm = new ATMImpl(cells);

        DepartmentImpl department = new DepartmentImpl(Arrays.asList(emptyATM, atm));

        emptyATM.putMoney(Arrays.asList(new Banknote(BanknoteAmountEnum.THOUSAND),
                new Banknote(BanknoteAmountEnum.FIFTY),
                new Banknote(BanknoteAmountEnum.FIVE_THOUSAND)));
        atm.putMoney(Arrays.asList(new Banknote(BanknoteAmountEnum.THOUSAND),
                new Banknote(BanknoteAmountEnum.FIFTY),
                new Banknote(BanknoteAmountEnum.FIVE_THOUSAND)));

        department.restoreATMs();
        List<ATMImpl> actualATMs = department.getATMs();

        Map<BanknoteAmountEnum, ATMCellImpl> expectedEmptyCells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            expectedEmptyCells.put(banknoteAmount, new ATMCellImpl(new ArrayList<>()));
        }
        Map<BanknoteAmountEnum, ATMCellImpl> expectedCells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            expectedCells.put(banknoteAmount, new ATMCellImpl(Collections.singletonList(new Banknote(banknoteAmount))));
        }

        List<ATMImpl> expectedATMs = new DepartmentImpl(Arrays.asList(new ATMImpl(expectedEmptyCells), new ATMImpl(expectedCells))).getATMs();

        Assert.assertEquals(expectedATMs, actualATMs);
    }

    @Test
    public void getBalanceTest() {
        Map<BanknoteAmountEnum, ATMCellImpl> cells1 = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            cells1.put(banknoteAmount, new ATMCellImpl(new ArrayList<>()));
        }
        ATMImpl atm1 = new ATMImpl(cells1);

        Map<BanknoteAmountEnum, ATMCellImpl> cells2 = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            List<Banknote> banknotes = new ArrayList<>();
            banknotes.add(new Banknote(banknoteAmount));
            cells2.put(banknoteAmount, new ATMCellImpl(banknotes));
        }
        ATMImpl atm2 = new ATMImpl(cells2);

        DepartmentImpl department = new DepartmentImpl(Arrays.asList(atm1, atm2));

        department.addCommand(new GiveATMBalance());

        long actualResult = department.getBalance();
        long expectedResult = 5000 + 1000 + 500 + 200 + 100 + 50;
        Assert.assertEquals(expectedResult, actualResult);
    }

}