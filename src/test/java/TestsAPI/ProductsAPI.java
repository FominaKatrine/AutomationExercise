package TestsAPI;

import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static io.restassured.RestAssured.given;

public class ProductsAPI  {

    private static String baseUrl = " https://automationexercise.com/api";
    private static Logger logger = LoggerFactory.getLogger("fileAPI");

    public JsonPath getAllProduct() {
        logger.info("Получение листа всех товаров");
        JsonPath response = given()
                .when()
                .get(baseUrl + "/productsList")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();

        return response;
    }

    public JsonPath getAllBrands() {
        logger.info("Получение списка всех брендов");
        JsonPath response = given()
                .when()
                .get(baseUrl + "/brandsList")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();

        return response;
    }

    public JsonPath searchProduct(String txtSearch) {
        logger.info("получение выборки товаров по содержанию в имени");
        JsonPath response = given()
                .formParam("search_product", txtSearch)
                .when()
                .post(baseUrl + "/searchProduct")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();
        return response;
    }

    public JsonPath searchProductNegative() {
        logger.info("обработка пустого запроса поиска товаров");
        JsonPath response = given()
                .when()
                .post(baseUrl + "/searchProduct")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();
        return response;
    }



}
