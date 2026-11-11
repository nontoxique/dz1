package dz8.pages;

public class LoginPageAssert extends LoginPage{
    public AdminPageAssert loginAs(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginBtn.click();
        return new AdminPageAssert();
    }
}
