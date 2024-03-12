package TestsUI;

import TestsUI.Pages.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class TestAE extends AbstractTest {
    private static String baseUrl = "https://automationexercise.com";
    private static HashMap user;
    private static String pathResources = "./src/test/resources/user.yml";
    private static String txtErrorLogin = "Your email or password is incorrect!";
    private static String txtErrorRegister = "Email Address already exist!";
    //region List Titles
    private static String titleSignup = "New User Signup!";
    private static String titleAccountInfo = "ENTER ACCOUNT INFORMATION";
    private static String titleAccountCreated = "ACCOUNT CREATED!";
    private static String titleAccountDeleted = "ACCOUNT DELETED!";
    private static String titleLogin = "Login to your account";
    //endregion

    private HomePage homePage;


    @BeforeAll
    public static void setUp() {
        try {
            File fileYaml = new File(pathResources);
            ObjectMapper mapper = new YAMLMapper(new YAMLFactory());
            user = mapper.readValue(fileYaml, HashMap.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @BeforeEach
    public void openBasePage() {
        open(baseUrl);
        homePage = new HomePage();
    }

    @Test
    @DisplayName("Register new User")
    @Tag("SignupLogin")
    void registerUser() {
        homePage.loginSignup();
        logger.info("создание аккаунта");
        createAccount();
        logger.info("проверка авторизации");
        assertTrue(homePage.checkLogged(user.get("name").toString()));
        logger.info("удаление аккаунта");
        homePage.deleteAccount();
        deleteAccount();
    }

    @Test
    @DisplayName("Register User with existing email")
    @Tag("SignupLogin")
    void registerUserInValidEmail() {
        homePage.loginSignup();
        logger.info("создание аккаунта");
        createAccount();
        assertTrue(homePage.checkLogged(user.get("name").toString()));
        logger.info("выход из аккаунта");
        homePage.logoutAccount();
        assertTrue(url().contains("login"));
        logger.info("регистрация с сущ email");
        LoginPage loginPage = new LoginPage();
        assumeTrue(loginPage.checkVisibleTitleSignup(titleSignup));
        loginPage.signup(user.get("name").toString(), user.get("email").toString());
        logger.info("проверка сообщения об ошибке");
        assertTrue(loginPage.msgErrorRegisterVisible(txtErrorRegister));
    }

    @Test
    @DisplayName("Login User with correct email and password")
    @Tag("E2E")
    void loginUserValid() {
        logger.info("создание аккаунта");
        homePage.loginSignup();
        createAccount();
        logger.info("выход из аккаунта");
        homePage.logoutAccount();
        logger.info("авторизация");
        homePage.loginSignup();
        LoginPage loginPage = new LoginPage();
        assumeTrue(loginPage.checkVisibleTitleLogin(titleLogin));
        loginPage.login(user.get("email").toString(), user.get("password").toString());
        logger.info("проверка авторизации");
        assertTrue(homePage.checkLogged(user.get("name").toString()));
        logger.info("удаление аккаунта");
        homePage.deleteAccount();
        deleteAccount();
    }

    @Test
    @DisplayName("Login User with incorrect email and password")
    @Tag("SignupLogin")
    void loginUserInValid() {
        String passIncorrect = "incorrect" + user.get("password");
        String emailIncorrect = "incorrect" + user.get("email");
        logger.info("вход с некорректным email");
        homePage.loginSignup();
        LoginPage loginPage = new LoginPage();
        assumeTrue(loginPage.checkVisibleTitleLogin(titleLogin));
        loginPage.login(emailIncorrect, passIncorrect);
        logger.info("проверка сообщения об ошибке");
        assertTrue(loginPage.msgErrorLoginVisible(txtErrorLogin));
    }
    //endregion

    //region Private methods
    private void deleteAccount() {
        DeleteAccountPage deleteAccountPage = new DeleteAccountPage();
        logger.info("подтверждение удаления акаунта");
        assertTrue(deleteAccountPage.checkVisibleTitle(titleAccountDeleted));
        deleteAccountPage.clickContinue();
    }

    private void createAccount() {
        LoginPage loginPage = new LoginPage();
        assumeTrue(loginPage.checkVisibleTitleSignup(titleSignup));
        loginPage.signup(user.get("name").toString(), user.get("email").toString());
        SignupPage signupPage = new SignupPage();
        assumeTrue(signupPage.checkVisibleTitleAccount(titleAccountInfo));
        assumeTrue(user.get("email").equals(signupPage.getEmail()));
        signupPage.fillFieldAccount(user); //заполняем поля
        //устанавливаем чекбоксы
        signupPage.choiceOptinCheckbox();
        signupPage.choiceNewsLetterCheckbox();
        signupPage.clickCreateAccount();
        // окно с подтверждением создания аккаунта
        AccountCreatedPage accountCreatedPage = new AccountCreatedPage();
        assertTrue(accountCreatedPage.checkVisibleTitle(titleAccountCreated));
        accountCreatedPage.clickContinue();
    }
    //endregion
}
