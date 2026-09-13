import org.Methods;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
public class dz2TestClass2 {
    @Test
    void isEvenTest() {
        Random random = new Random();
        int n = random.nextInt(0, 100);
        boolean expected = (n % 2 == 0);
        boolean result = Methods.isEven(n);
        if (expected == result) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    void checkAccessTest() {
        Random random = new Random();
        int age = random.nextInt(-100, 100);
        String expected = age > 18 ? "Allowed" : "Denied";
        if (expected.equals(Methods.checkAccess(age))) {
            System.out.println(age + " " + "TEST PASSED");
        } else {
            System.out.println(age + " " + "TEST FAILED");
        }
    }

    @RepeatedTest(10)
    void isPostitveTest() {
        Random random = new Random();
        int n = random.nextInt(-100, 100);
        Boolean expected = n >= 0 ? true : false;
        if (expected == Methods.isPositive(n)) {
            System.out.println(n + " " + "TEST PASSED");
        } else {
            System.out.println(n + " " + "TEST FAILED");
        }

    }

    @RepeatedTest(5)
    void getGradeTest() {
        Random random = new Random();
        int score = random.nextInt(-100, 150);
        String expected;
        if (score <= 20 && score >= 0) {
            expected = "E";
        } else if (score >= 21 && score <= 40) {
            expected = "D";
        } else if (score >= 41 && score <= 60) {
            expected = "C";
        } else if (score >= 61 && score <= 80) {
            expected = "B";
        } else if (score >= 81 && score <= 100) {
            expected = "A";
        } else expected = "Error";
        if (expected.equals(Methods.getGrade(score))) {
            System.out.println(score + " " + "TEST PASSED");
        } else {
            System.out.println(score + " " + "TEST FAILED");

        }
    }

    @Test
    void blastOffTest() {
        Random random = new Random();
        int Start = random.nextInt(11) - 5;
        String result = Methods.blastOff(Start);
        if (result.trim().endsWith("1 Поехали!")) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

    }

    @Test
    void sumToNTest() {
        Random random = new Random();
        int n = random.nextInt(-100, 100);
        int expected = 0;
        if (n >= 0) {
            for (int i = n; i >= 1; i--) expected += i;
        } else {
            for (int i = n; i <= 1; i++) expected += i;
        }
        int result = Methods.sumToN(n);
        if (result == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
            "'Bug,Fix,Feature,Test', true",
            "'Aboba,Boba,5646,Bug', true",
            "'Biba,bug,kekus,Lolol', false"
    })
    void hasBugTest(String messages, boolean expected) {
        String[] msgs = messages.split(",");
        boolean result = Methods.hasBug(msgs);
        if (result != expected) {
            System.out.println("TEST FAILED");
        } else {
            System.out.println("TEST PASSED");
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,  5, '2 4'",
            "6,  2, '2 4 6'",
            "-4, 0, '-4 -2 0'",
            "2,  2, '2'"
    })
    void GetEvenInRangeTest(int start, int end, String expected) {
        String actual = Methods.getEvenInRange(start, end);
        if (actual.equals(expected)) {
            System.out.println(start + "..." + end + " " + "TEST PASSED");
        } else {
            System.out.println(start + "..." + end + " " + "TEST FAILED");
        }
    }

    @RepeatedTest(5)
    void findMaxTest() {
        Random random = new Random();
        int size = random.nextInt(1, 7);
        int[] arr = new int[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(-50, 50);
        }
        int result = Methods.findMax(arr);
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
            if (result == max) {
                System.out.println("TEST PASSED");
            } else {
                System.out.println("TEST FAILED");
            }
        }
        @RepeatedTest(3)
    void reverseTest () {
            Random random = new Random();
            int size = random.nextInt(1, 7);
            String[] arr = new String[size];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = String.valueOf(random.nextInt(-50, 50));
            }
            String[] result = Methods.reverse(arr);
            String[] rev = new String[arr.length];
            int j = 0;
            for (int i = arr.length - 1; i >= 0; i--) {
                rev[j] = arr[i];
                j++;}
                if (Arrays.equals (result,rev)) {
                    System.out.println("TEST PASSED");
                } else {
                    System.out.println("TEST FAILED");
                }
            }
            @ParameterizedTest
    @CsvSource (value ={
            "'1 2 3 4 5', 3.0",
            "'10 20 30',  20.0",
            "'5 5 5 5',   5.0",
            "'1 2',       1.0"
    })
    void calcAverageTest (String inputNumbers, double expected) {
                    List<Integer> list = new ArrayList<>();
                    for (String number : inputNumbers.split(" ")) {
                        list.add(Integer.parseInt(number));
                    }
                    double result = Methods.calcAverage(list);

                    if (result == expected) {
                        System.out.println("TEST PASSED" + list);
                    } else {
                        System.out.println("TEST FAILED!"+ list);
                    }

            }
            @ParameterizedTest
    @CsvSource (value = {
            "Иван, Олег",
            "Анна, Мария",
            "Петр, Ольга"
    })
    void removeSpecificNameTest (String nameToRemove, String nameToKeep) {
                    List<String> list2 = List.of(nameToRemove, nameToKeep);
                    List<String> result = Methods.removeSpecificName(list2, nameToRemove);
                    List<String> expectedList = List.of(nameToKeep);
                    if (!result.equals(expectedList)) {
                        System.out.println("TEST FAILED");
                    } else {
                        System.out.println("TEST PASSED");

                }
                }
            }
