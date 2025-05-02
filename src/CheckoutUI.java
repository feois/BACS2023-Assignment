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
        println("Amount: " + customer.cart.calculateAmount());
        newLine();

        char c = noReject().readCharOptions("Confirm? yY/nN ", "yYnN");

        if (c == 'y' || c == 'Y') {
            newLine();

            if (customer.checkout() == null) {
                println("Order failed to proceed!");
            }
            else {
                println("Order placed successfully!");
            }

            readEnter();
        }

        return null;
    }
}
