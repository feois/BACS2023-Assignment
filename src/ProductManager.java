import java.io.*;
import java.util.*;

public class ProductManager {
    private static final List<Product> products = new ArrayList<>();
    private static final Map<Category, List<Product>> categories = new HashMap<>();

    public static void readFromFile(File file) throws IOException {
        var reader = new BufferedReader(new FileReader(file));
        var s = reader.readLine();

        while (s != null) {
            var id = s;
            var name = reader.readLine();
            var desc = reader.readLine();
            var price = Double.parseDouble(reader.readLine());
            var category = CategoryManager.getCategory(reader.readLine().charAt(0));

            addProduct(new Product(id, name, desc, price, category));

            s = reader.readLine();
        }

        reader.close();
    }

    public static void addProduct(Product product) {
        products.add(product);
        categories.putIfAbsent(product.category, new ArrayList<>());
        categories.get(product.category).add(product);
    }

    public static List<Product> getProducts(Category category) {
        return Collections.unmodifiableList(categories.get(category));
    }

    public static List<Product> searchProduct(String name) {
        return products.stream().filter(p -> p.productName.contains(name)).toList();
    }

    public static List<Product> searchProduct(String name, Category category) {
        return searchProduct(name).stream().filter(p -> p.category == category).toList();
    }

    public static void saveToFile(File file) throws IOException {
        var writer = new BufferedWriter(new FileWriter(file));

        for (var prod : products) {
            writer.write(prod.getProductID());
            writer.newLine();
            writer.write(prod.productName);
            writer.newLine();
            writer.write(prod.description);
            writer.newLine();
            writer.write(Double.toString(prod.price));
            writer.newLine();
            writer.write(String.valueOf(prod.category.getCategoryCode()));
        }

        writer.close();
    }
}
