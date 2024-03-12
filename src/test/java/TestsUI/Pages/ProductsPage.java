package TestsUI.Pages;

import com.codeborne.selenide.*;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class ProductsPage {
    //todo переименовать
    private static final SelenideElement titleListProduct = $(".features_items .title.text-center");
    private static final ElementsCollection listProductName = $$(".single-products .productinfo p"); //p
    private static final ElementsCollection listProductInfo = $$(".single-products .productinfo");
    private static final ElementsCollection listViewProduct = $$(".features_items .choose a");

    private static final SelenideElement searchProductField = $("#search_product");
    private static final SelenideElement searchProductBtn = $("#submit_search");
    private static final SelenideElement titleLeftSidebar = $(".left-sidebar");
    private static final SelenideElement womenPlusGnk = $(".left-sidebar a[href='#Women'] i.fa.fa-plus");

    private static final SelenideElement womenDressLnk = $("div#Women a[href='/category_products/1']");
    private static final SelenideElement kidsPlusGnk = $(".left-sidebar a[href='#Kids'] i.fa.fa-plus");
    private static final SelenideElement kidsDressLnk = $("div#Kids a[href='/category_products/4']");

    //product_detail page
    private static final ElementsCollection brandListLnk = $$("div.brands-name a");
    private static final SelenideElement productInfo = $(".product-information");
    private static final SelenideElement productInfoName = $(".product-information h2");
    private static final SelenideElement productInfoPrice = $(".product-information span span");

    //Modal win "added"
    private static final SelenideElement modalAddedWin = $(".modal-content");
    private static final SelenideElement modalAddedContinueBtn = $(".modal-content .modal-footer button");
    private static final SelenideElement modalAddedViewCartLnk = $(".modal-content .modal-body a[href='/view_cart']");


    public boolean isVisibleTitleListProduct(String title) {
        return titleListProduct.should(text(title)).exists();
    }


    public boolean isVisibleTitleLeftBar(String title) {
        return titleLeftSidebar.should(text(title)).exists();
    }

    public void searchProductByName(String txtSearch) {
        searchProductField.should(Condition.visible).setValue(txtSearch);
        searchProductBtn.should(Condition.visible).click();

    }

    public List<String> getListName(){
        listProductName.should(CollectionCondition.sizeGreaterThan(1));
        return listProductName.texts();
    }

    /**
     * выбрать категорию Women -> Dress
     */
    public void getListWomenDress() {
        womenPlusGnk.should(Condition.visible).click();
        womenDressLnk.should(Condition.visible).click();
    }

    /**
     * выбрать категорию Kids -> Dress
     */
    public void getListKidsDress() {
        kidsPlusGnk.should(Condition.visible).click();
        kidsDressLnk.should(Condition.visible).click();
    }

    public void choiceBrand(String brand) {
        brandListLnk.should(CollectionCondition.sizeGreaterThan(1))
                .findBy(text(brand))
                .click();
    }

    /**
     * @return возвращает лист с информацией по товарам
     */
    public List<String> getListInfo() {
        ArrayList<String> res = new ArrayList<>();
        ElementsCollection resultLinks = listViewProduct.should(CollectionCondition.sizeGreaterThan(1));
        for (int i = 0; i < resultLinks.size(); i++) {
            resultLinks.get(i).should(Condition.visible).click();
            res.add(productInfo.text());
            executeJavaScript("window.history.back()");
        }
        return res;
    }

    public void choiceProduct(int index) {
        listViewProduct.should(CollectionCondition.sizeGreaterThan(index + 1))
                .get(index)
                .click();
    }


    public boolean isVisibleName() {
        return !productInfoName.should(Condition.visible).text().isEmpty();
    }

    public boolean isVisiblePrice() {
        return !productInfoPrice.should(Condition.visible).text().isEmpty();
    }

    public void addToCartInList(int index) {
        listProductInfo.should(CollectionCondition.sizeGreaterThan(index + 1))
                .get(index)
                .find(By.cssSelector("a.btn.btn-default.add-to-cart")).click();


    }

    public void continueShoppingAfterAdd() {
        modalAddedContinueBtn.should(Condition.visible).click();
    }

    public void viewCartAfterAdd() {
        modalAddedViewCartLnk.should(Condition.visible).click();
    }

}
