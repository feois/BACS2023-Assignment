import java.io.*;
import java.util.*;

public class Inventory {
    private final Map<Product, Integer> products;

    public Inventory() {
        products = new HashMap<>();
    }

    public Inventory(File file) throws FileNotFoundException {
        this();

        var br = new BufferedReader(new FileReader(file));

        // TODO
    }

    public int getQuantity(Product product) {
        return products.getOrDefault(product, 0);
    }

    public void updateQuantity(Product product, int quantity) {
        products.put(product, quantity);
    }

    public void saveToFile(File file) {
        // TODO
    }
}
