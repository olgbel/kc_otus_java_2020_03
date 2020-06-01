package ru.otus2;

public interface ATMCell {
    int getBanknotesCount();

    void put(Banknote banknote);

    Banknote withdraw();
}
