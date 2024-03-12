package TestsAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAccountUserAE {

    private static String pathResources = "./src/test/resources/userApi.json";
    private static HashMap user;

    private AccountUserAPI account;
    private String userCreatedMsg = "User created!";
    private String statusCreated = "201";
    private String userDeletedMsg = "Account deleted!";
    private String statusDeleted = "200";
    private String errorSignupMsg = "Email already exists!";
    private String statusSignupErr = "400";
    private String userLoginMSG = "User exists!";
    private String statusLogin = "200";
    private String userNotFoundMSG = "User not found!";
    private String statusLoginErr = "404";
    private String userUpdateMsg = "User updated!";
    private String statusUpdate = "200";
    private String userLoginBadMsg = "Bad request, email or password parameter is missing in POST request.";
    private String statusLoginBad = "400";
    private String statusGetDetail = "200";

    @BeforeAll
    public static void setUp() {

        File json = new File(pathResources);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            user = objectMapper.readValue(json, HashMap.class);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @BeforeEach
    void init() {
        account = new AccountUserAPI();
    }

    @Test
    @DisplayName("Register User and delete Account")
    void signupUser() {
        JsonPath resSignup = account.signupUser(user);
        assertEquals(statusCreated, resSignup.get("responseCode").toString());
        assertEquals(userCreatedMsg, resSignup.get("message"));

        JsonPath resDelete = account.deleteAccount(user.get("email").toString(), user.get("password").toString());
        assertEquals(statusDeleted, resDelete.get("responseCode").toString());
        assertEquals(userDeletedMsg, resDelete.get("message"));
    }

    @Test
    @DisplayName("Register User with existing email")
    void registerUserInValidEmail() {
        JsonPath resSignup = account.signupUser(user);
        assertEquals(statusCreated, resSignup.get("responseCode").toString());
        assertEquals(userCreatedMsg, resSignup.get("message"));

        //повторная регистрация с тем же именем
        JsonPath resDoubleSignup = account.signupUser(user);
        assertEquals(statusSignupErr, resSignup.get("responseCode").toString());
        assertEquals(errorSignupMsg, resSignup.get("message"));

        JsonPath resDelete = account.deleteAccount(user.get("email").toString(), user.get("password").toString());
        assertEquals(statusDeleted, resDelete.get("responseCode").toString());
        assertEquals(userDeletedMsg, resDelete.get("message"));
    }

    @Test
    @DisplayName("Verify Login with valid details")
    void loginValid() {
        JsonPath resSignup = account.signupUser(user);
        assertEquals(statusCreated, resSignup.get("responseCode").toString());
        assertEquals(userCreatedMsg, resSignup.get("message"));
        // авторизация с валидным логином
        JsonPath resLogin = account.loginUser(user.get("email").toString(), user.get("password").toString());
        assertEquals(statusLogin, resLogin.get("responseCode").toString());
        assertEquals(userLoginMSG, resLogin.get("message"));

        JsonPath resDelete = account.deleteAccount(user.get("email").toString(), user.get("password").toString());
        assertEquals(statusDeleted, resDelete.get("responseCode").toString());
        assertEquals(userDeletedMsg, resDelete.get("message"));
    }

    @Test
    @DisplayName("Verify Login with invalid details")
    void loginInValid() {
        String emailInvalid = "invalid" + user.get("email").toString();
        String passInvalid = "invalid" + user.get("password").toString();

        // авторизация с невалидным логином
        JsonPath resLogin = account.loginUser(emailInvalid, passInvalid);
        assertEquals(statusLoginErr, resLogin.get("responseCode").toString());
        assertEquals(userNotFoundMSG, resLogin.get("message"));

    }

    @Test
    @DisplayName("Login without email parameter")
    void loginWithoutEmail(){
        JsonPath resLogin = account.loginUserBad( user.get("password").toString());
        assertEquals(statusLoginBad, resLogin.get("responseCode").toString());
        assertEquals(userLoginBadMsg, resLogin.get("message"));
    }

    @Test
    @DisplayName("Update User Account")
    void updateAccount() {
        JsonPath resSignup = account.signupUser(user);
        assertEquals(statusCreated, resSignup.get("responseCode").toString());
        assertEquals(userCreatedMsg, resSignup.get("message"));

        //создадим обновленные данные для изменения
        HashMap<String, String> updateUser = new HashMap<>();
        updateUser.putAll(user);
        String newAddress1 = "Update Address";
        String newCompany = "new Company after update";
        updateUser.put("address1", newAddress1);
        updateUser.put("company", newCompany);
        //обновляем
        JsonPath resUpdate = account.updateAccount(updateUser);
        assertEquals(statusUpdate, resUpdate.get("responseCode").toString());
        assertEquals(userUpdateMsg, resUpdate.get("message"));

        //получаем детализацию аккаунта
        JsonPath detailUser = account.getDetailAccount(user.get("email").toString());
        assertEquals(statusGetDetail, detailUser.get("responseCode").toString());
        assertEquals(newAddress1, detailUser.get("user.address1").toString());
        assertEquals(newCompany, detailUser.get("user.company").toString());

        JsonPath resDelete = account.deleteAccount(user.get("email").toString(), user.get("password").toString());
        assertEquals(statusDeleted, resDelete.get("responseCode").toString());
        assertEquals(userDeletedMsg, resDelete.get("message"));
    }

}
