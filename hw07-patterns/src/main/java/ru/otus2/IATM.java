package ru.otus2;

import java.util.List;

public interface IATM {
    void putMoney(List<Banknote> banknotes);

    List<Banknote> withdrawMoney(int amount);
}
