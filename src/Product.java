import java.util.*;

public class Product {
    private static final Map<Category, Integer> categoryNewID = new HashMap<>();

    private final String productID;
    public String productName;
    public String description;
    public double price;
    public Category category;

    public Product(String productName, String description, double price, Category category) {
        int newID = categoryNewID.getOrDefault(category, 1000);
        categoryNewID.put(category, newID + 1);

        productID = category.getCategoryCode() + String.format("%05d", newID);
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public String getProductID() {
        return productID;
    }
}
