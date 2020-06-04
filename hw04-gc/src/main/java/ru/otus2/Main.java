package ru.otus2;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();

        while (true) {
            int count = 500000;
            for (int i = 0; i < count; i++) {
                list.add(random.nextInt());
            }

            for (int i = list.size() - count / 2; i < list.size(); i++) {
                list.remove(i);
            }
        }
    }


}
