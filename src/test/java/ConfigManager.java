import static Config.config.CONFIG;
    public class ConfigManager {
        public static void printConfigToConsole() {
            System.out.println("===== ЗАПУСК АВТОТЕСТОВ: КОНФИГУРАЦИЯ =====");
            System.out.println("URL стенда: " + CONFIG.baseUrl());
            System.out.println("API URL: " + CONFIG.apiUrl());
            System.out.println("Браузер: " + CONFIG.browser());
            System.out.println("Тайм-аут поиска элементов: " + CONFIG.timeout() + " мс");
            System.out.println("Режим логирования: " + CONFIG.loggingMode());
            System.out.println("Стартовый товар (Имя): " + CONFIG.firstItemName());
            System.out.println("Стартовый товар (Цена): " + CONFIG.firstItemPrice());
            System.out.println("===========================================");
        }
    }
