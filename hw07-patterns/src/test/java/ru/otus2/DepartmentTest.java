package ru.otus2;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.*;

import java.util.*;

public class DepartmentTest {

    @Test
    public void restoreATMsTest() {
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
        List<IATM> actualATMs = department.getATMs();

        Map<BanknoteAmountEnum, ATMCell> emptyCells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            emptyCells.put(banknoteAmount, new ATMCell(new ArrayList<>()));
        }
        List<IATM> expectedATMs = new Department(Collections.singletonList(new ATM(emptyCells))).getATMs();
        System.out.println("actual atm: " + actualATMs);
        Assert.assertEquals(actualATMs, expectedATMs);
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
        long actualResult = department.getBalance();

        long expectedResult = 5000 + 1000 + 500 + 200 + 100 + 50;
        Assert.assertEquals(actualResult, expectedResult);
    }

}