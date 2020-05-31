package ru.otus;

import java.util.List;

public interface IATM {
    void putMoney(List<Banknote> banknotes);

    List<Banknote> withdrawMoney(int amount);

    long giveOutBalance();

    void restore();
}
