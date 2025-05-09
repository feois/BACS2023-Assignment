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
            Cart cart;
            var history = new ArrayList<Order>();

            reader.readLine(); // skip "Cart" line

            cart = Cart.readFrom(reader);

            while (reader.hasLine() && reader.peekLine().startsWith("Order")) {
                var date = Instant.parse(reader.readLine().substring(6));
                var orderCart = Cart.readFrom(reader);

                history.add(new Order(orderCart, date));
            }

            UserManager.register(new Customer(username, password, cart, history));
        }

        reader.close();
    }

    public static void saveAdministratorsTo(Writer out) throws IOException {
        var writer = new BufferedWriter(out);

        for (var user : users.values()) {
            if (user instanceof Administrator) {
                user.saveTo(writer);
            }
        }

        writer.close();
    }

    private static void writeCart(BufferedWriter writer, Cart cart) throws IOException {
        try {
            cart.getProducts().forEach((product, count) -> {
                try {
                    writer.write('\t');
                    writer.write(product.getProductID());
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
                user.saveTo(writer);
                writer.write("Cart");
                writer.newLine();

                writeCart(writer, customer.getCart());

                for (var order : customer.getOrderHistory()) {
                    writer.write("Order " + order.getTime());
                    writer.newLine();

                    writeCart(writer, order.getCart());
                }
            }
        }

        writer.close();
    }
}

