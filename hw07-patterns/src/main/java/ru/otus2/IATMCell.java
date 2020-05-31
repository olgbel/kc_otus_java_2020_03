package ru.otus2;

public interface IATMCell {
    int getBanknotesCount();

    void put(Banknote banknote);

    Banknote withdraw();
}
