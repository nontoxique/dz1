import org.Methods;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;
@Tag("dz3")
public class dz3Task2TestClass {
    @RepeatedTest(10)
    void isEvenTest() {
        Random random = new Random();
        int n = random.nextInt(0, 100);
        boolean expected = (n % 2 == 0);
        boolean result = Methods.isEven(n);
        Assertions.assertEquals(expected, result, "Ошибка для числа " + n + "! Результат не совпадает с ожидаемым.");
    }

    @RepeatedTest(10)
    void checkAccessTest() {
        Random random = new Random();
        int age = random.nextInt(0, 100);
        assertThat(Methods.checkAccess(age))
                .as("Возраст меньше минимально необходимого")
                .isEqualTo("Allowed");

    }

    @RepeatedTest(10)
    void isPostitveTest() {
        Random random = new Random();
        int n = random.nextInt(-100, 100);
        assertThat(Methods.isPositive(n))
                .as("Число отрицательное")
                .isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "-1,  Error",
            "0,   E",
            "10,  E",
            "20,  E",
            "21,  D",
            "30,  D",
            "40,  D",
            "41,  C",
            "50,  C",
            "60,  C",
            "61,  B",
            "70,  B",
            "80,  B",
            "81,  A",
            "90,  A",
            "100, A",
            "101, Error"})
    void getGradeTest(int score, String expected) {
        {
            String actualGrade = Methods.getGrade(score);

            assertThat(actualGrade)
                    .as("Ошибка при проверке балла: " + score)
                    .isEqualTo(expected);
        }
    }

    @RepeatedTest(10)
    void blastOffTest() {
        Random random = new Random();
        int Start = random.nextInt(11) - 5;
        String result = Methods.blastOff(Start);
        assertThat(result.trim())
                .as("Неверный текст")
                .endsWith("1 Поехали!");

    }

    @RepeatedTest(10)
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
            assertThat(result)
                    .as("Сумма не сходится")
                    .isEqualTo(expected);
        }
    }

    final Random random = new Random();
    final String[] WORDS_POOL = {"Info", "Warning", "Feature", "Fix", "bug", "Biba", "Boba"};

    @RepeatedTest(10)
    void hasBugTest() {

        int length = random.nextInt(11);
        String[] randomMessages = new String[length];

        for (int i = 0; i < length; i++) {
            if (random.nextDouble() < 0.15) {
                randomMessages[i] = "Bug";
            } else {
                randomMessages[i] = WORDS_POOL[random.nextInt(WORDS_POOL.length)];
            }
        }

        boolean expected = false;
        if (randomMessages != null) {
            for (String msg : randomMessages) {
                if ("Bug".equals(msg)) {
                    expected = true;
                    break;
                }
            }
        }
        boolean result = Methods.hasBug(randomMessages);
        assertThat(result)
                .as("Результат метода не совпал с эталонным для массива: " + java.util.Arrays.toString(randomMessages))
                .isEqualTo(expected);
    }

    @RepeatedTest(10)
    void GetEvenInRangeTest() {
        int teststart = random.nextInt(41) - 20;
        int testend = random.nextInt(41) - 20;
        int end = testend;
        int start = teststart;
        String result = "";
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }
        {
            for (int i = start; i <= end; i++) {
                if (i % 2 == 0) {
                    if (result.isEmpty()) {
                        result = result + i;

                    } else {
                        result = result + " " + i;
                    }
                }
            }
            String actual = Methods.getEvenInRange(start, end);
            assertThat(actual)
                    .as("Проверка провалена для" + teststart + "  " + testend)
                    .isEqualTo(result);
        }
    }

    @RepeatedTest(10)
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
        assertThat(max)
                .as("Неверный максимум среди данных чисел:" + java.util.Arrays.toString(arr))
                .isEqualTo(result);
    }

    @RepeatedTest(10)
    void reverseTest() {
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
            j++;
        }
        assertThat(result)
                .as("Неверный обратный порядок для" + java.util.Arrays.toString(arr))
                .isEqualTo(rev);
    }

    @RepeatedTest(10)
    void calcAverageTest() {
        Random random = new Random();
        int size = random.nextInt(11);
        List<Integer> list = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < size; i++) {
            int randomNumber = random.nextInt(100);
            list.add(randomNumber);
            sum += randomNumber;
        }
        double expected = (double) (sum / size);
        double result = Methods.calcAverage(list);

        assertThat(result)
                .as("Неверное среднее арифметическое для" + list)
                .isEqualTo(expected);

    }

    @RepeatedTest(10)
    void removeSpecificNameTest() {
        final Random random = new Random();
        final String[] WORDS_POOL = {"Маша", "Петя", "Коля", "Вася", "Ваня", "Таня", "Вова"};
        String nameToRemove = "Маша";
        int length = random.nextInt(11);
        List<String> originalList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            originalList.add(WORDS_POOL[random.nextInt(WORDS_POOL.length)]);
        }
        List<String> expectedList = new ArrayList<>();
        for (String name : originalList) {
            if (!name.equals(nameToRemove)) {
                expectedList.add(name);
            }
        }
        List<String> result = Methods.removeSpecificName(originalList, nameToRemove);
        assertThat(result)
                .as("Ошибка при удалении имени '" + nameToRemove + "' из списка: " + originalList)
                .isEqualTo(expectedList);
    }
}