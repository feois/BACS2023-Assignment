import java.io.*;
import java.util.*;

public class ProductManager {
    private static final List<Product> products = new ArrayList<>();
    private static final Map<String, Product> productIDs = new HashMap<>();
    private static final Map<Category, List<Product>> categories = new HashMap<>();

    public static void addProduct(Product product) {
        if (productIDs.containsKey(product.productID)) {
            throw new IllegalArgumentException("Product ID already exist!");
        }

        products.add(product);
        productIDs.put(product.productID, product);
        categories.putIfAbsent(product.category, new ArrayList<>());
        categories.get(product.category).add(product);
    }

    public static Product getProduct(String productID) {
        return productIDs.get(productID);
    }

    public static List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public static List<Product> getProducts(Category category) {
        var products = categories.get(category);

        return Collections.unmodifiableList(products == null ? new ArrayList<>() : products);
    }

    public static List<Product> searchProduct(String name) {
        return products.stream().filter(p -> p.productName.contains(name)).toList();
    }

    public static List<Product> searchProduct(String name, Category category) {
        return searchProduct(name).stream().filter(p -> p.category == category).toList();
    }

    public static void readFrom(Reader in) throws IOException {
        var reader = new PeekableReader(in);

        while (reader.hasLine()) {
            var id = reader.readLine();
            var name = reader.readLine();
            var desc = reader.readLine();
            var price = Double.parseDouble(reader.readLine());

            addProduct(new Product(id, name, desc, price));
        }

        reader.close();
    }

    public static void saveTo(Writer out) throws IOException {
        var writer = new BufferedWriter(out);

        for (var prod : getProducts()) {
            writer.write(prod.productID);
            writer.newLine();
            writer.write(prod.productName);
            writer.newLine();
            writer.write(prod.description);
            writer.newLine();
            writer.write(Double.toString(prod.price));
            writer.newLine();
        }

        writer.close();
    }
}
