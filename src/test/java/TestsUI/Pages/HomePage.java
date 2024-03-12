package TestsUI.Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

public class HomePage {
    private static final SelenideElement productsLnk = $("#header a[href='/products']");
    private static final SelenideElement cartLnk = $("#header a[href='/view_cart']");
    private static final SelenideElement loginLnk = $("#header a[href='/login']");

    private static final SelenideElement logoutLnk = $("#header a[href='/logout']");
    private static final SelenideElement loggedInGnc = $("ul.nav.navbar-nav");
    private static final SelenideElement deleteAccount = $("#header a[href='/delete_account']");

    public void loginSignup() {
        loginLnk.should(exist);
        loginLnk.click();
    }

    public boolean checkLogged(String name) {
        return loggedInGnc.should(Condition.text("Logged in as " + name)).exists();
    }

    public void deleteAccount() {
        deleteAccount.should(Condition.visible).click();
    }

    public void logoutAccount() {
        logoutLnk.should(Condition.visible).click();
    }

    public void products(){
        productsLnk.should(Condition.visible).click();
    }

    public void cart(){
        cartLnk.should(exist).click();
    }


}
