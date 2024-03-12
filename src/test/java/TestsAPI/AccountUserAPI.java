package TestsAPI;

import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


import static io.restassured.RestAssured.given;

public class AccountUserAPI {

    private static String baseUrl = "https://automationexercise.com/api";
    private static Logger logger = LoggerFactory.getLogger("fileAPI");

    public JsonPath signupUser(HashMap<String, String> user) {
        logger.info("создание нового аккаутна с email: '" + user.get("email") + "'");
        JsonPath response = given()
                .formParams(user)
                .when()
                .post(baseUrl + "/createAccount")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();
        return response;
    }

    public JsonPath deleteAccount(String email, String password) {
        logger.info("удаление аккаунта с email: '" + email + "'");
        JsonPath response = given()
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .delete(baseUrl + "/deleteAccount")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();
        return response;
    }


    public JsonPath loginUser(String email, String password) {
        logger.info("вход в аккаунт с email: '" + email + "'");
        JsonPath response = given()
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .post(baseUrl + "/verifyLogin")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();
        return response;
    }

    public JsonPath loginUserBad(String password) {
        logger.info("вход в аккаунт с пустым email: '");
        JsonPath response = given()
                .formParam("password", password)
                .when()
                .post(baseUrl + "/verifyLogin")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();
        return response;
    }

    public JsonPath updateAccount(HashMap<String, String> user) {
        logger.info("обновление аккаунта с email: '" + user.get("email") + "'");
        JsonPath response = given()
                .formParams(user)
                .when()
                .put(baseUrl + "/updateAccount")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();
        return response;
    }

    public JsonPath getDetailAccount(String email) {
        logger.info("получение деталей аккаунта с email: '" + email + "'");
        JsonPath response = given()
                .param("email", email)
                .when()
                .get(baseUrl + "/getUserDetailByEmail")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();
        return response;
    }

}
