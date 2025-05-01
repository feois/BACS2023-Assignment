import java.time.Instant;

public class Order {
    private final Cart cart;
    private final Instant time;
    private final double amount;

    public Order(Cart cart, Instant time) {
        this.cart = new Cart(cart); // copy cart
        this.time = time;

        var amount = 0.0;

        for (var entry : cart.getProducts().entrySet()) {
            var product = entry.getKey();
            var quantity = entry.getValue();

            amount += product.price * quantity;
        }

        this.amount = amount;
    }

    public Cart getCart() {
        return new Cart(cart); // copy cart
    }

    public Instant getTime() {
        return time;
    }

    public double getAmount() {
        return amount;
    }
}
