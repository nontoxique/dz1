package Config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigProvider {

    public static final ProjectConfig CONFIG = ConfigFactory.create(ProjectConfig.class);

    public static void logConfig() {
        System.out.println("==================================================");
        System.out.println("[CONFIG] ПАРАМЕТРЫ ЗАПУСКА АВТОТЕСТОВ:");
        System.out.println("==================================================");
        System.out.println("URL стенда:      " + CONFIG.baseUrl());
        System.out.println("URL API:         " + CONFIG.apiUrl());
        System.out.println("Браузер:         " + CONFIG.browser());
        System.out.println("Тайм-аут поиска: " + CONFIG.timeout() + " мс");
        System.out.println("Режим логов:     " + CONFIG.loggingMode());
        System.out.println("Стартовый товар: " + CONFIG.firstItemName());
        System.out.println("Цена товара:     " + CONFIG.firstItemPrice() + " руб.");
        System.out.println("==================================================");
    }
}
