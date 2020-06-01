package ru.otus;

public abstract class ATMProcessor {
    private ATMProcessor next;

    private ATMProcessor getNext() {
        return next;
    }

    void setNext(ATMProcessor next) {
        this.next = next;
    }

    void process(IATM atm) {
        restore();
        if (getNext() != null) {
            getNext().process(atm);
        }
    }

    protected abstract void restore();
}
