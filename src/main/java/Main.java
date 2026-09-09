import java.util.ArrayList;import java.util.Arrays;
import java.util.List;

import static org.Methods.*;

public class Main {
    public static void main(String[] args) {
        isEven(8);
        checkAccess(23);
        isPositive(-10);
        getGrade(50);
        blastOff(10);
        sumToN(1);
        String[] messages = {"aboba", "test", "Bug"};
        hasBug(messages);
        System.out.println(hasBug(messages));
        getEvenInRange(10, 15);
        int[] arr = {3218, 321312, 32, 464543, 0};
        findMax(arr);
        String[] arr2 = {"Uno","Dos", "Tres", "Quatro" };
        reverse(arr2);
        List<Integer> list = new ArrayList<>(List.of(13, 67, -3213, 0, 1239129));
        calcAverage (list);
        List <String> list2 = new ArrayList<>(List.of("Aboba", "Joe Peach", "Pierre Dunn", "Dick Rider"));
        String nametoRemove = ("Joe Peach");
        removeSpecificName(list2,"Joe Peach");

    }

    }