package ru.otus2.tests;

import ru.otus2.annotations.After;
import ru.otus2.annotations.Before;
import ru.otus2.annotations.Test;

public class TestClassWithFailedAfterAndMainTests {

    @Before
    public void beforeTest1() {
        System.out.println("execute beforeTest1");
    }

    @Before
    public void beforeTest2() {
        System.out.println("execute beforeTest2");
    }

    @Test
    public void doJob1() {
        System.out.println("execute doJob1");
    }

    @Test
    public void doJob2() {
        System.out.println("execute doJob2");
        throw new IllegalArgumentException();
    }

    @After
    public void afterTest1() {
        System.out.println("execute afterTest1");
    }

    @After
    public void afterTest2() {
        System.out.println("execute afterTest2");
        throw new IllegalArgumentException();

    }
}
