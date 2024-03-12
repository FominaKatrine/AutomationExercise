package TestsAPI;

import TestsAPI.POJOClass.ListBrands;
import TestsAPI.POJOClass.ListProducts;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.core.StringContains.containsStringIgnoringCase;
import static org.junit.jupiter.api.Assertions.*;

public class TestProductAE {
    private String status200 = "200";
    private String status400 = "400";
    private String errorMsg = "Bad request, search_product parameter is missing in POST request.";


    @Test
    @DisplayName("Get All Products List")
    void getAllProduct() {
        ProductsAPI products = new ProductsAPI();
        JsonPath response = products.getAllProduct();
        assertEquals(status200, response.get("responseCode").toString());

        ListProducts listProducts = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            listProducts = objectMapper.readValue(response.prettify(), ListProducts.class);

        } catch (JsonProcessingException exception) {
            System.out.println(exception.getMessage());
        }
        assertFalse(listProducts.getProductsList().isEmpty());
    }

    @Test
    @DisplayName("Get All Brands List")
    void getAllBrand() {
        ProductsAPI productsAPI = new ProductsAPI();
        JsonPath response = productsAPI.getAllBrands();
        assertEquals(status200, response.get("responseCode").toString());

        ListBrands listBrands = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            listBrands = objectMapper.readValue(response.prettify(), ListBrands.class);

        } catch (JsonProcessingException exception) {
            System.out.println(exception.getMessage());
        }
        assertFalse(listBrands.getBrandList().isEmpty());
    }

    @Test
    @DisplayName("Search Product")
    void searchProductByName() {
        ProductsAPI productsAPI = new ProductsAPI();
        String txtSearch = "jeans";
        JsonPath response = productsAPI.searchProduct(txtSearch);
        assertEquals(status200, response.get("responseCode").toString());

        ListProducts listProducts = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            listProducts = objectMapper.readValue(response.prettify(), ListProducts.class);

        } catch (JsonProcessingException exception) {
            System.out.println(exception.getMessage());
        }
        assertThat(listProducts.getProductsList(), everyItem(hasProperty("name", containsStringIgnoringCase(txtSearch))));
    }

    @Test
    @DisplayName("Search Product without search_product parameter")
    void searchProductByNameNegative() {
        ProductsAPI productsAPI = new ProductsAPI();
        JsonPath response = productsAPI.searchProductNegative();

        assertEquals(status400, response.get("responseCode").toString());
        assertEquals(errorMsg, response.get("message"));
    }

}
