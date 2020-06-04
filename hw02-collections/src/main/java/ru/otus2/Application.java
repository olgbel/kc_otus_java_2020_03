package ru.otus2;

import java.util.Collections;
import java.util.Comparator;

public class Application {

    public static void main(String[] args) {
        DIYArrayList<String> diyArr = new DIYArrayList<>();
        Collections.addAll(diyArr, "1");
        Collections.addAll(diyArr, "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
        printArray(diyArr);

        DIYArrayList<String> diyArr2 = new DIYArrayList<>(3);
        Collections.addAll(diyArr2, "1");
        Collections.addAll(diyArr2, "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
        printArray(diyArr2);

        DIYArrayList<String> destArr = new DIYArrayList<>(diyArr.size());
        for (int i = 0; i < diyArr.size(); i++) {
            destArr.add(String.valueOf(i + 100));
        }
        Collections.copy(destArr, diyArr);
        printArray(destArr);

        Collections.sort(diyArr2, Comparator.nullsFirst(Comparator.naturalOrder()));
        printArray(diyArr2);

    }

    private static <T> void printArray(DIYArrayList<T> srcArr) {
        System.out.println("start printing");
        for (int i = 0; i < srcArr.size(); i++) {
            System.out.println(srcArr.get(i));
        }
    }
}
