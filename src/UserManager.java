import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

public class UserManager {
    private static final HashMap<String, User> users = new HashMap<>();

    public static boolean hasUser() {
        return !users.isEmpty();
    }

    public static boolean hasUser(String username) {
        return users.get(username) != null;
    }

    public static User login(String username, String password) {
        var user = users.get(username);

        if (user != null) {
            if (!user.checkPassword(password)) {
                user = null;
            }
        }

        return user;
    }
    
    public static void register(User user) {
        if (users.containsKey(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        users.put(user.getUsername(), user);
    }

    public static void readAdministratorsFrom(Reader in) throws IOException {
        var reader = new PeekableReader(in);

        while (reader.hasLine()) {
            var username = reader.readLine();
            var password = reader.readLine();

            register(new Administrator(username, password));
        }

        reader.close();
    }

    public static void readCustomersFrom(Reader in) throws IOException {
        var reader = new PeekableReader(in);

        while (reader.hasLine()) {
            var username = reader.readLine();
            var password = reader.readLine();
            var cart = new Cart();
            var history = new ArrayList<Order>();

            reader.readLine(); // skip "Cart" line

            while (reader.hasLine() && reader.peekLine().startsWith("\t")) {
                var product = ProductManager.getProduct(reader.readLine().substring(1));
                var quantity = Integer.parseUnsignedInt(reader.readLine().substring(1));

                Inventory.updateQuantity(product, quantity);
                cart.addProduct(product, quantity);
                Inventory.updateQuantity(product, 0);
            }

            while (reader.hasLine() && reader.peekLine().startsWith("Order")) {
                var date = Instant.parse(reader.readLine().substring(6));
                var orderCart = new Cart();

                while (reader.hasLine() && reader.peekLine().startsWith("\t")) {
                    var product = ProductManager.getProduct(reader.readLine().substring(1));
                    var quantity = Integer.parseUnsignedInt(reader.readLine().substring(1));

                    Inventory.updateQuantity(product, quantity);
                    orderCart.addProduct(product, quantity);
                    Inventory.updateQuantity(product, 0);
                }

                history.add(new Order(orderCart, date));
            }

            UserManager.register(new Customer(username, password, cart, history));
        }

        reader.close();
    }

    public static void saveAdministratorsTo(Writer out) throws IOException {
        var writer = new BufferedWriter(out);

        for (var user : users.values()) {
            if (user instanceof Administrator admin) {
                writer.write(admin.getUsername());
                writer.newLine();
                writer.write(admin.getPassword());
                writer.newLine();
            }
        }

        writer.close();
    }

    private static void writeCart(BufferedWriter writer, Cart cart) throws IOException {
        try {
            cart.getProducts().forEach((product, count) -> {
                try {
                    writer.write('\t');
                    writer.write(product.productID);
                    writer.newLine();
                    writer.write('\t');
                    writer.write(Integer.toString(count));
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
    }

    public static void saveCustomersTo(Writer out) throws IOException {
        var writer = new BufferedWriter(out);

        for (var user : users.values()) {
            if (user instanceof Customer customer) {
                writer.write(customer.getUsername());
                writer.newLine();
                writer.write(customer.getPassword());
                writer.newLine();
                writer.write("Cart");
                writer.newLine();

                writeCart(writer, customer.cart);

                for (var order : customer.getOrderHistory()) {
                    writer.write("Order " + order.time);
                    writer.newLine();

                    writeCart(writer, order.getCart());
                }
            }
        }

        writer.close();
    }
}

