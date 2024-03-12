package TestsAPI.POJOClass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Products {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private String price;
    @JsonProperty("brand")
    private String brand;
    private String category;
    private String usertype;

    public Products() {
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("category")
    private void unpack(Map<String, Object> category) {
        this.category = (String) category.get("category");
        Map<String, String> type = (Map<String, String>) category.get("usertype");
        this.usertype = type.get("usertype");
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id + ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", usertype='" + usertype + '\'' +
                '}';
    }

    //region Getter-Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    //endregion
}
