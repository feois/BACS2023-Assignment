import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Order {
    private final Cart cart;
    private final Instant time;
    private final double amount;

    public Order(Cart cart, Instant time) {
        this.cart = new Cart(cart); // copy cart
        this.time = time;
        amount = cart.calculateAmount();
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

    public String getLocalDateTime() {
        return LocalDateTime.ofInstant(time, ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
    }
}
