import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Order {
    private final Cart cart;
    public final Instant time;
    public final double amount;

    public Order(Cart cart, Instant time) {
        this.cart = new Cart(cart); // copy cart
        this.time = time;
        this.amount = cart.calculateAmount();
    }

    public Cart getCart() {
        return new Cart(cart); // copy cart
    }

    public String getLocalDateTime() {
        return LocalDateTime.ofInstant(time, ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
    }
}
