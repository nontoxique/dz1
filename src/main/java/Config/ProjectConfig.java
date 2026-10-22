package Config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "classpath:config.properties"
})
public interface ProjectConfig extends Config {

    @Key("base.url")
    String baseUrl();

    @Key("api.url")
    String apiUrl();

    @Key("browser")
    @DefaultValue("chrome")
    String browser();

    @Key("browser.timeout")
    @DefaultValue("4000")
    int timeout();

    @Key("logging.mode")
    @DefaultValue("DEBUG")
    String loggingMode();

    @Key("admin.login")
    String adminLogin();

    @Key("admin.password")
    String adminPassword();

    @Key("first.item.name")
    @DefaultValue("good1")
    String firstItemName();

    @Key("first.item.price")
    @DefaultValue("70")
    Integer firstItemPrice();
}
