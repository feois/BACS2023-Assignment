import java.util.HashSet;

public class CheckoutUI extends UI {
    private final Customer customer;

    public CheckoutUI(Customer customer) {
        this.customer = customer;
    }

    @Override
    public UI run() {
        clear();

        println("Checkout");
        newLine();

        if (customer.cart.getProducts().isEmpty()) {
            println("Cart is empty");
            newLine();
            readEnter();
        }
        else {
            println("Amount: " + formatCurrency(customer.cart.calculateAmount()));
            newLine();

            char c = noReject().readCharOptions("Confirm? yY/nN ", "yYnN");

            if (c == 'y' || c == 'Y') {
                newLine();

                if (customer.checkout() == null) {
                    println("Order failed to proceed due to invalid stock quantity!");
                    println("The cart has been adjusted to fit the current stock quantity, please review the cart again!");

                    var products = new HashSet<>(customer.cart.getProducts().keySet());
                    var cart = customer.cart;

                    for (var product : products) {
                        var quantity = cart.getProducts().get(product);

                        if (quantity > Inventory.getQuantity(product)) {
                            cart.reduceProduct(product, quantity - Inventory.getQuantity(product));
                        }
                    }
                }
                else {
                    println("Order placed successfully!");
                }

                readEnter();
            }
        }

        return null;
    }
}
