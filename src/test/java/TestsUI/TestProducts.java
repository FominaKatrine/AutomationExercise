package TestsUI;

import TestsUI.Pages.ProductsPage;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


public class TestProducts extends AbstractTest {

    private static String baseUrl = "https://automationexercise.com/products";
    private static String titleAllProduct = " ALL PRODUCTS";
    private static String titleSearchProduct = "SEARCHED PRODUCTS";
    private static String titleSearchWomenDress = "WOMEN - DRESS PRODUCTS";
    private static String titleSearchKidsDress = "KIDS - DRESS PRODUCTS";
    private static String titleCategory = "CATEGORY";
    private static String titleBrands = "BRANDS";
    private static String brand = "BABYHUG";
    private String productNameSearch = "jeans";

    private ProductsPage productsPage;

    @BeforeEach
    public void openBasePage() {
        logger.info("открытие страницы:" + baseUrl);
        open(baseUrl);
        productsPage = new ProductsPage();
    }

    @Test
    @DisplayName("Search Products by name")
    @Tag("Products")
    void searchProductByName() {
        assumeTrue(productsPage.isVisibleTitleListProduct(titleAllProduct));
        logger.info("получение списка товаров содержащих в наименовании: " + productNameSearch);
        productsPage.searchProductByName(productNameSearch);
        assumeTrue(productsPage.isVisibleTitleListProduct(titleSearchProduct));
        List<String> listResult = productsPage.getListName();
        logger.info("проверка полученных товаров");
        assertTrue(listResult.stream().allMatch(x -> x.contains(productNameSearch)));
    }

    @Test
    @DisplayName(" View Category Products")
    @Tag("Products")
    void viewCategoryProduct() {
        assumeTrue(productsPage.isVisibleTitleListProduct(titleAllProduct));
        assumeTrue(productsPage.isVisibleTitleLeftBar(titleCategory));

        logger.info("выброр товаров из категории Women -> Dress");
        productsPage.getListWomenDress();
        assumeTrue(productsPage.isVisibleTitleListProduct(titleSearchWomenDress));
        List<String> listResult = productsPage.getListInfo()
                .stream().map(item -> Arrays.stream(item.split("\n"))
                        .filter(x -> x.contains("Category"))
                        .findFirst().orElse("null")).toList();

        logger.info("проверка принадлежности товаров категории Women -> Dress");
        assertTrue(listResult.stream().allMatch(x -> x.contains("Dress") && x.contains("Women")));
    }

    @Test
    @DisplayName(" View Brand Products")
    @Tag("Products")
    void viewBrandProduct() {
        assumeTrue(productsPage.isVisibleTitleListProduct(titleAllProduct));
        assumeTrue(productsPage.isVisibleTitleLeftBar(titleCategory));
        logger.info("выброр товаров из Бренда: " + brand);
        productsPage.choiceBrand(brand);
        assumeTrue(productsPage.isVisibleTitleListProduct(titleBrands));

        List<String> listResult = productsPage.getListInfo()
                .stream().map(item -> Arrays.stream(item.split("\n"))
                        .filter(x -> x.contains("Brand"))
                        .findFirst().orElse("null")).toList();
        //TODO delete
        System.out.println(listResult);
        logger.info("проверка принадлежности товаров бренду: " + brand);
        assertTrue(listResult.stream().allMatch(x -> x.contains(brand)));
    }

    @Test
    @DisplayName(" View product detail page")
    @Tag("Products")
    void viewProductDetail() {
        logger.info("Проверка отображения карточки товара");
        assumeTrue(productsPage.isVisibleTitleListProduct(titleAllProduct));
        productsPage.choiceProduct(0);
        assertTrue(productsPage.isVisibleName());
        assertTrue(productsPage.isVisiblePrice());
    }
}
