package ru.otus;

public interface IATMCell {
    int getBanknotesCount();

    void put(Banknote banknote);

    Banknote withdraw();
}
