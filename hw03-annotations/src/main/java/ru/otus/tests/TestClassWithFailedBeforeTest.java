package ru.otus.tests;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestClassWithFailedBeforeTest {

    @Before
    public void beforeTest1() {
        System.out.println("execute beforeTest1");
    }

    @Before
    public void beforeTest2() {
        System.out.println("execute beforeTest2");
        throw new IllegalArgumentException();
    }

    @Test
    public void doJob1() {
        System.out.println("execute doJob1");
    }

    @Test
    public void doJob2() {
        System.out.println("execute doJob2");
    }

    @After
    public void afterTest1() {
        System.out.println("execute afterTest1");
    }

    @After
    public void afterTest2() {
        System.out.println("execute afterTest2");
    }
}
