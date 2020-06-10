package ru.otus;

import java.util.Set;

public class Person {
    private String name;
    private int age;
    private Set<Pet> pets;

    public Person(String name, int age, Set<Pet> pets) {
        this.name = name;
        this.age = age;
        this.pets = pets;
    }
}
