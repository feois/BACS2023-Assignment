import java.io.*;
import java.util.*;

public class Inventory {
    private static final Map<Product, Integer> products = new HashMap<>();

    public static int getQuantity(Product product) {
        return products.getOrDefault(product, 0);
    }

    public static void updateQuantity(Product product, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Invalid stock count!");
        }

        products.put(product, quantity);
    }

    public static void readFrom(Reader in) throws IOException {
        var reader = new PeekableReader(in);

        while (reader.hasLine()) {
            var product = ProductManager.getProduct(reader.readLine());
            var stock = Integer.parseUnsignedInt(reader.readLine());

            updateQuantity(product, stock);
        }

        reader.close();
    }

    public static void saveTo(Writer out) throws IOException {
        var writer = new BufferedWriter(out);

        try {
            products.forEach((product, stock) -> {
                try {
                    writer.write(product.getProductID());
                    writer.newLine();
                    writer.write(Integer.toString(stock));
                    writer.newLine();
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        catch (RuntimeException e) {
            throw new IOException(e.getCause());
        }

        writer.close();
    }
}
