import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer extends User {
    public final Cart cart;
    private final List<Order> history;

    public Customer(String username, String password) {
        this(username, password, new Cart(), new ArrayList<>());
    }

    public Customer(String username, String password, Cart cart, List<Order> history) {
        super(username, password);

        this.cart = cart;
        this.history = history;
    }
    
    public Order checkout(){
        if (cart.getProducts().isEmpty()) {
            return null;
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

