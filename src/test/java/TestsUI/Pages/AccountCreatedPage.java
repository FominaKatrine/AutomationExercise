package TestsUI.Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class AccountCreatedPage {
    private static final SelenideElement form = $("section#form");
    private static final SelenideElement continueBtn = $("a[data-qa='continue-button']");

    public boolean checkVisibleTitle(String title) {
        return form.should(Condition.text(title)).exists();
    }

    public void clickContinue(){
        continueBtn.click();
    }
}
