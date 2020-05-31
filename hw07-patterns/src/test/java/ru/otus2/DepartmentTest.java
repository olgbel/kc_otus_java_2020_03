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
        List<ATM> actualATMs = department.getATMs();

        Map<BanknoteAmountEnum, ATMCell> emptyCells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            emptyCells.put(banknoteAmount, new ATMCell(new ArrayList<>()));
        }
        List<ATM> expectedATMs = new Department(Collections.singletonList(new ATM(emptyCells))).getATMs();
        System.out.println("actual atm: " + actualATMs);
        Assert.assertEquals(actualATMs, expectedATMs);
    }

}