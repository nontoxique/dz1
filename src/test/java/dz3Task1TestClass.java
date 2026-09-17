import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;


public class dz3Task1TestClass {
    public String abbreviate(String firstName, String lastName) {
        char n = firstName.charAt(0);
        String result = n + lastName;
        return result;

    }

    @Test
    public void testAbbreviate() {
        String result = abbreviate("Ivan", "Ivanov");

        assertEquals("IIvanov", result);
    }

    public boolean isWeekend(String dayOfWeek, boolean isVacation) {
        if (isVacation) {
            return true;
        }

        if (dayOfWeek.equals("Суббота") || dayOfWeek.equals("Воскресенье")) {
            return true;
        }
        return false;
        }

    @Test
    public void testisWeekend () {
        String dayOfWeek = "Вторник";
        boolean isVacation = false;
        boolean result = isWeekend(dayOfWeek,isVacation);
        assertThat(result)
                .as (dayOfWeek + " " + "РАБОТАТЬ")
                .isTrue();
    }
    public List<Integer> getPremiumProducts(List<Integer> allPrices, int premiumThreshold) {
        List<Integer> premiumList = new ArrayList<>();
        for (int price : allPrices) {
            if (price > premiumThreshold) {
            premiumList.add(price);
        }
    }
        return premiumList;
}
@Test public void testgetPremiumProducts () {
    List<Integer> prices = new ArrayList<>();
    prices.add(500);
    prices.add(1200);
    prices.add(3000);
    prices.add(800);
    prices.add(5000);
    List <Integer> result = getPremiumProducts(prices,1000);
    assertThat(result)
            .as ("Указаны не все значения")
            .containsExactlyInAnyOrder(1200,3000,5000);
}
    public List<String> maskEmails(List<String> originalEmails) {
        List<String> maskedList = new ArrayList<>();
        for (String email: originalEmails) {
            String masked = email.split("@")[0] + "***";
            maskedList.add(masked);
        }
        return maskedList;
        }
    @Test
    public void testMaskEmails() {
        List<String> emails = new ArrayList<>();
        emails.add("mikhail@gmail.com");
        emails.add("tatiana@mail.ru");

        List<String> result = maskEmails(emails);
        assertThat(result)
                .as("Неверный формат маскирования")
                .containsExactly("mikhail***", "tatiana***");
    }
}

