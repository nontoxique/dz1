import org.Methods;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;

@Tag("smoke")
public class Testdz2Class {
    @BeforeAll
    static void start () {
        System.out.println("======================== Test method start");
    }
    @AfterAll
    static void finish () {
        System.out.println("Test method end ========================");
    };
    public static int [] randomArray() {
        Random random = new Random();
        int size = random.nextInt(0,99);
        int[] array = new int[size];
        Random random2 = new Random();

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(100);
        }
        return array;
    }
    @Test
    void isEvenTest() {
        Random random = new Random();
        int n = random.nextInt(0, 100);
    }
    @RepeatedTest(20)
    void checkAccess () {
        Random random = new Random();
        int age = random.nextInt(0,99);
    }
    @ParameterizedTest
    @MethodSource ("randomArray")
    void getGrade (int score) {
        String result = Methods.getGrade(score);
    }
}
