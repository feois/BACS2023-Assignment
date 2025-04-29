import java.util.*;

public class Product {
    private final String productID;
    public String productName;
    public String description;
    public double price;
    public Category category;

    public Product(String productID, String productName, String description, double price, Category category) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public String getProductID() {
        return productID;
    }
}
