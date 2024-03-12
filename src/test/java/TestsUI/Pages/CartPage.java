package TestsUI.Pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CartPage {
    private static ElementsCollection tableShop = $$("table#cart_info_table tbody tr");


    public int getSizeTableShop() {
        return tableShop.should(CollectionCondition.sizeGreaterThan(0)).size();
    }

    public List<TableRow> getTableRow() {
        List<TableRow> list = new ArrayList<>();
        tableShop.forEach(row -> {
            list.add(new TableRow(
                    row.find(By.className("cart_price")).text(),
                    row.find(By.className("cart_quantity")).text(),
                    row.find(By.className("cart_total")).text()));

        });
        return list;
    }
}
