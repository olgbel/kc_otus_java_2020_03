package ru.otus2;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.*;
import ru.otus.command.GiveATMBalance;

import java.util.*;

public class DepartmentTest {

    @Test
    public void restoreSingleATMTest() {
        Map<BanknoteAmountEnum, ATMCell> cells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            cells.put(banknoteAmount, new ATMCell(new ArrayList<>()));
        }

        ATM atm = new ATM(cells);
        Department department = new Department(Collections.singletonList(atm));
        atm.putMoney(Arrays.asList(new Banknote(BanknoteAmountEnum.THOUSAND),
                new Banknote(BanknoteAmountEnum.FIFTY),
                new Banknote(BanknoteAmountEnum.FIVE_THOUSAND)));

        department.restoreATMs();

        List<ATM> actualATMs = department.getATMs();

        Map<BanknoteAmountEnum, ATMCell> emptyCells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            emptyCells.put(banknoteAmount, new ATMCell(new ArrayList<>()));
        }
        List<ATM> expectedATMs = new Department(Collections.singletonList(new ATM(emptyCells))).getATMs();
        Assert.assertEquals(expectedATMs, actualATMs);
    }

    @Test
    public void restoreATMsTest(){
        Map<BanknoteAmountEnum, ATMCell> emptyCells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            emptyCells.put(banknoteAmount, new ATMCell(new ArrayList<>()));
        }
        ATM emptyATM = new ATM(emptyCells);

        Map<BanknoteAmountEnum, ATMCell> cells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            List<Banknote> banknotes = new ArrayList<>();
            banknotes.add(new Banknote(banknoteAmount));
            cells.put(banknoteAmount, new ATMCell(banknotes));
        }
        ATM atm = new ATM(cells);

        Department department = new Department(Arrays.asList(emptyATM, atm));

        emptyATM.putMoney(Arrays.asList(new Banknote(BanknoteAmountEnum.THOUSAND),
                new Banknote(BanknoteAmountEnum.FIFTY),
                new Banknote(BanknoteAmountEnum.FIVE_THOUSAND)));
        atm.putMoney(Arrays.asList(new Banknote(BanknoteAmountEnum.THOUSAND),
                new Banknote(BanknoteAmountEnum.FIFTY),
                new Banknote(BanknoteAmountEnum.FIVE_THOUSAND)));

        department.restoreATMs();
        List<ATM> actualATMs = department.getATMs();

        Map<BanknoteAmountEnum, ATMCell> expectedEmptyCells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            expectedEmptyCells.put(banknoteAmount, new ATMCell(new ArrayList<>()));
        }
        Map<BanknoteAmountEnum, ATMCell> expectedCells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            expectedCells.put(banknoteAmount, new ATMCell(Collections.singletonList(new Banknote(banknoteAmount))));
        }

        List<ATM> expectedATMs = new Department(Arrays.asList(new ATM(expectedEmptyCells), new ATM(expectedCells))).getATMs();

        Assert.assertEquals(expectedATMs, actualATMs);
    }

    @Test
    public void getBalanceTest() {
        Map<BanknoteAmountEnum, ATMCell> cells1 = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            cells1.put(banknoteAmount, new ATMCell(new ArrayList<>()));
        }
        ATM atm1 = new ATM(cells1);

        Map<BanknoteAmountEnum, ATMCell> cells2 = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            List<Banknote> banknotes = new ArrayList<>();
            banknotes.add(new Banknote(banknoteAmount));
            cells2.put(banknoteAmount, new ATMCell(banknotes));
        }
        ATM atm2 = new ATM(cells2);

        Department department = new Department(Arrays.asList(atm1, atm2));

        department.addCommand(new GiveATMBalance());

        long actualResult = department.getBalance();
        long expectedResult = 5000 + 1000 + 500 + 200 + 100 + 50;
        Assert.assertEquals(expectedResult, actualResult);
    }

}