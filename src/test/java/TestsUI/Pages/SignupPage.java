package TestsUI.Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


import static com.codeborne.selenide.Selenide.$;

public class SignupPage {
    private static final SelenideElement loginForm = $(".login-form");
    private static final SelenideElement genderRadio = $("input[type='radio']");

    private static final SelenideElement nameField = $("input#name");
    private static final SelenideElement emailField = $("input#email");
    private static final SelenideElement passwordField = $("input#password");
    private static final SelenideElement daySelect = $("select#days");
    private static final SelenideElement monthSelect = $("select#months");
    private static final SelenideElement yearSelect = $("select#years");
    private static final SelenideElement newsLetterCheckbox = $("input#newsletter");
    private static final SelenideElement optinCheckbox = $("input#optin");
    private static final SelenideElement firstNameField = $("input#first_name");
    private static final SelenideElement lastNameField = $("input#last_name");
    private static final SelenideElement companyField = $("input#company");
    private static final SelenideElement addressField = $("input#address1");
    private static final SelenideElement addressSecondField = $("input#address2");
    private static final SelenideElement countrySelect = $("select#country");
    private static final SelenideElement stateField = $("input#state");
    private static final SelenideElement cityField = $("input#city");
    private static final SelenideElement zipcodeField = $("input#zipcode");
    private static final SelenideElement mobileNumberField = $("input#mobile_number");
    private static final SelenideElement createAccountBtn = $("button[type='submit']");


    public boolean checkVisibleTitleAccount(String title) {
        return loginForm.should(Condition.text(title)).exists();
    }

    public void fillFieldAccount(HashMap user) {
        genderRadio.selectRadio(user.get("gender").toString());
        nameField.should(Condition.visible).setValue(user.get("name").toString());
        passwordField.should(Condition.visible).setValue(user.get("password").toString());
        //дата
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dateBirth = LocalDate.parse(user.get("dateBirth").toString(), formatter);
        daySelect.should(Condition.visible).selectOptionByValue(String.valueOf(dateBirth.getDayOfMonth()));
        monthSelect.should(Condition.visible).selectOptionByValue(String.valueOf(dateBirth.getMonthValue()));
        yearSelect.should(Condition.visible).selectOptionByValue(String.valueOf(dateBirth.getYear()));
        //First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
        firstNameField.should(Condition.visible).setValue(user.get("firstName").toString());
        lastNameField.should(Condition.visible).setValue(user.get("lastName").toString());
        companyField.should(Condition.visible).setValue(user.get("company").toString());
        addressField.should(Condition.visible).setValue(user.get("address").toString());
        addressSecondField.should(Condition.visible).setValue(user.get("addressSecond").toString());
        countrySelect.should(Condition.visible).selectOptionByValue(user.get("country").toString());
        stateField.should(Condition.visible).should(Condition.visible).setValue(user.get("state").toString());
        cityField.should(Condition.visible).setValue(user.get("city").toString());
        zipcodeField.should(Condition.visible).setValue(user.get("zipCode").toString());
        mobileNumberField.should(Condition.visible).setValue(user.get("mobileNumber").toString());
    }

    public String getEmail() {
        return emailField.should(Condition.visible).getValue();
    }

    public void choiceNewsLetterCheckbox() {
        newsLetterCheckbox.should(Condition.visible).click();
    }

    public void choiceOptinCheckbox() {
        optinCheckbox.should(Condition.visible).click();
    }

    public void clickCreateAccount() {
        createAccountBtn.should(Condition.visible).click();
    }



}