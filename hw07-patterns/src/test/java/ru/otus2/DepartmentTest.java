package ru.otus2;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.*;

public class DepartmentTest {

    @Test
    public void restoreATMsTest() {
        Map<BanknoteAmountEnum, ATMCell> emptyCells = new HashMap<>();
        for (BanknoteAmountEnum banknoteAmount : BanknoteAmountEnum.values()) {
            emptyCells.put(banknoteAmount, new ATMCell(new ArrayList<>()));
        }

        ATM atm = new ATM(emptyCells);
        Department department = new Department(Collections.singletonList(atm));
        System.out.println("initial department: " + department.getATMs());

        ArrayList<Banknote> banknotes = new ArrayList<>();
        banknotes.add(new Banknote(BanknoteAmountEnum.THOUSAND));
        banknotes.add(new Banknote(BanknoteAmountEnum.FIFTY));
        banknotes.add(new Banknote(BanknoteAmountEnum.FIVE_THOUSAND));
        atm.putMoney(banknotes);
        System.out.println("changed department: " + department.getATMs());

        department.restoreATMs();
        System.out.println("restored department: " + department.getATMs());
    }

}