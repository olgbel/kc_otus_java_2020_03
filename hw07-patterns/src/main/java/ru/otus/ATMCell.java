package ru.otus;

public interface ATMCell {
    int getBanknotesCount();

    void put(Banknote banknote);

    Banknote withdraw();
}
