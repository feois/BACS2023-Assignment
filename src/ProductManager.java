import java.io.*;
import java.util.*;

public class ProductManager {
    private final List<Product> products;
    private final Map<Category, List<Product>> categories;

    public ProductManager() {
        products = new ArrayList<>();
        categories = new HashMap<>();
    }

    public ProductManager(File file) throws FileNotFoundException {
        this();

        var reader = new BufferedReader(new FileReader(file));

        // TODO
    }

    public void addProduct(Product product) {
        products.add(product);
        categories.putIfAbsent(product.category, new ArrayList<>());
        categories.get(product.category).add(product);
    }

    public List<Product> getProducts(Category category) {
        return Collections.unmodifiableList(categories.get(category));
    }

    public List<Product> searchProduct(String name) {
        return products.stream().filter(p -> p.productName.contains(name)).toList();
    }

    public List<Product> searchProduct(String name, Category category) {
        return searchProduct(name).stream().filter(p -> p.category == category).toList();
    }

    public void saveToFile(File file) {
        // TODO
    }
}
