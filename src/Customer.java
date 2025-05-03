import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer extends User {
    private final Cart cart;
    private final List<Order> history;

    public Customer(String username, String password) {
        this(username, password, new Cart(), new ArrayList<>());
    }

    public Customer(String username, String password, Cart cart, List<Order> history) {
        super(username, password);

        this.cart = new Cart(cart);
        this.history = new ArrayList<>(history);
    }

    public Cart getCart() {
        return cart;
    }

    public Order checkout() {
        if (cart.getProducts().isEmpty()) {
            return null;
        }

        for (var entry : cart.getProducts().entrySet()) {
            var product = entry.getKey();
            var quantity = entry.getValue();

            if (quantity > Inventory.getQuantity(product)) {
                return null;
            }
        }

        var order = new Order(cart, Instant.now());

        history.add(order);
        cart.getProducts().forEach((product, quantity) -> Inventory.updateQuantity(product, Inventory.getQuantity(product) - quantity));
        cart.clear();

        return order;
    }
    
    public List<Order> getOrderHistory(){
        return Collections.unmodifiableList(history);
    }

    @Override
    public String getUserType() {
        return "Customer";
    }
}

