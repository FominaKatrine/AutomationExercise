package TestsUI.Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private static final SelenideElement signupForm = $(".signup-form");
    private static final SelenideElement nameSignupField = $(".signup-form input[type='text']");
    private static final SelenideElement emailSignupField = $(".signup-form input[type='email']");
    private static final SelenideElement signupBtn = $(".signup-form button[type='submit']");
    private static final SelenideElement errorRegisterMsg = $(".signup-form p[style='color: red;']");

    private static final SelenideElement loginForm = $(".login-form");
    private static final SelenideElement emailLoginField = $(".login-form input[type='email']");
    private static final SelenideElement passwordField = $(".login-form input[type='password']");
    private static final SelenideElement loginBtn = $(".login-form button[type='submit']");
    private static final SelenideElement errorLoginMsg = $(".login-form p[style='color: red;']");


    public boolean checkVisibleTitleSignup(String title) {
        return signupForm.should(Condition.text(title)).exists();
    }

    public boolean checkVisibleTitleLogin(String title) {
        return loginForm.should(Condition.text(title)).exists();
    }

    public void signup(String name, String email) {
        nameSignupField.should(Condition.visible).setValue(name);
        emailSignupField.should(Condition.visible).setValue(email);
        signupBtn.click();
    }

    public void login(String email, String password){
        emailLoginField.should(Condition.visible).setValue(email);
        passwordField.should(Condition.visible).setValue(password);
        loginBtn.click();
    }

    public boolean msgErrorLoginVisible(String txtError){
        return errorLoginMsg.should(Condition.visible).text().contains(txtError);
    }

    public boolean msgErrorRegisterVisible(String txtError){
        return errorRegisterMsg.should(Condition.visible).text().contains(txtError);
    }

}
