package ru.otus;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class MyGsonTest {
    MyGson myGson;
    Gson gson;

    @Before
    public void init() {
        myGson = new MyGson();
        gson = new Gson();
    }

    @Test
    public void testNullObject() {
        Object nullObject = null;

        String actualResult = myGson.toJson(nullObject);
        String expectedResult = gson.toJson(nullObject);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testEmptyObject() {
        Object emptyObject = new Object();

        String actualResult = myGson.toJson(emptyObject);
        String expectedResult = gson.toJson(emptyObject);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testWrapperString() {
        String string = "TEST JSON";

        String actualResult = myGson.toJson(string);
        String expectedResult = gson.toJson(string);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testWrapperInteger() {
        Integer integer = 678678;

        String actualResult = myGson.toJson(integer);
        String expectedResult = gson.toJson(integer);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testWrapperCharacter() {
        Character character = 'A';

        String actualResult = myGson.toJson(character);
        String expectedResult = gson.toJson(character);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testSimplyObject(){
        Pet pet = new Pet("comeon", "taksa", 9);

        String actualResult = myGson.toJson(pet);
        String expectedResult = gson.toJson(pet);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testComplexObject() {
        Set<Pet> pets = new HashSet<>();
        pets.add(new Pet("kitty", "scottish cat", 3));
        pets.add(new Pet("roundy", "dog", 5));
        Person person = new Person("Alex", 32, pets);

        String actualResult = myGson.toJson(person);
        String expectedResult = gson.toJson(person);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testIntPrimitive() {
        int intPrimitive = 1;

        String actualResult = myGson.toJson(intPrimitive);
        String expectedResult = gson.toJson(intPrimitive);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFloatPrimitive() {
        float floatPrimitive = 1.11f;

        String actualResult = myGson.toJson(floatPrimitive);
        String expectedResult = gson.toJson(floatPrimitive);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testBooleanPrimitive() {
        boolean booleanPrimitive = true;

        String actualResult = myGson.toJson(booleanPrimitive);
        String expectedResult = gson.toJson(booleanPrimitive);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testCharPrimitive() {
        char charPrimitive = 'a';

        String actualResult = myGson.toJson(charPrimitive);
        String expectedResult = gson.toJson(charPrimitive);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testEmptyArray() {
        char[] emptyArray = {};

        String actualResult = myGson.toJson(emptyArray);
        String expectedResult = gson.toJson(emptyArray);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testPrimitiveArray() {
        int[] intArray = {1, 2, 3};

        String actualResult = myGson.toJson(intArray);
        String expectedResult = gson.toJson(intArray);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testEmptyCollection() {
        LinkedHashSet<Character> linkedHashSet = new LinkedHashSet<>();

        String actualResult = myGson.toJson(linkedHashSet);
        String expectedResult = gson.toJson(linkedHashSet);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testSetCollection() {
        Set<Long> set = new HashSet<>();
        set.add(99999999L);
        set.add(787878787L);
        set.add(66666666L);

        String actualResult = myGson.toJson(set);
        String expectedResult = gson.toJson(set);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testListCollection() {
        List<String> list = new ArrayList<>();
        list.add("aaaa");
        list.add("bbbb");
        list.add("cccc");

        String actualResult = myGson.toJson(list);
        String expectedResult = gson.toJson(list);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testPersonCollection() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("Paul",
                1,
                new HashSet<>()));
        list.add(new Person("Klara",
                38,
                Set.of(new Pet("Sweety", "None", 2),
                        new Pet("MyPumpkin", "simplyGinger", 10))));

        String actualResult = myGson.toJson(list);
        String expectedResult = gson.toJson(list);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testMixedCollection() {
        Set<Object> mixed = new HashSet<>();
        mixed.add(new Object());
        mixed.add(new Pet("Mixxy", "none", 11));
        mixed.add('e');

        String actualResult = myGson.toJson(mixed);
        String expectedResult = gson.toJson(mixed);

        Assert.assertEquals(expectedResult, actualResult);
    }
}