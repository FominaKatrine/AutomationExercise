package TestsUI;

import TestsUI.Pages.CartPage;
import TestsUI.Pages.HomePage;
import TestsUI.Pages.ProductsPage;
import TestsUI.Pages.TableRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCart extends AbstractTest {
    private static String baseUrl = "https://automationexercise.com";

    @BeforeEach
    public void openBasePage() {
        open(baseUrl);
    }

    @Test
    @DisplayName("Add 2 position in cart without authorization")
    @Tag("Cart")
    void addTwoProductWithoutAuto() {
        HomePage homePage = new HomePage();
        homePage.products();
        ProductsPage productsPage = new ProductsPage();
        logger.info("Добавляем в корзину 2 разных товара");
        productsPage.addToCartInList(0);
        productsPage.continueShoppingAfterAdd();
        productsPage.addToCartInList(1);
        productsPage.viewCartAfterAdd();
        CartPage cartPage = new CartPage();
        int sizeTable = cartPage.getSizeTableShop();
        System.out.println(sizeTable);
        assertEquals(2, sizeTable);
        //TODO проверить состав таблицы
        List<TableRow> cart = cartPage.getTableRow();
        logger.info("проверка цены, кол-ва, суммы построчно");
        for (TableRow item : cart) {
            assertEquals(item.getTotal(), item.getCount() * item.getPrice());
        }

    }


}
