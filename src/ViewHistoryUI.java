public class ViewHistoryUI extends UI {
    private final Customer customer;

    public ViewHistoryUI(Customer customer) {
        this.customer = customer;
    }

    @Override
    public UI run() {
        clear();

        println("Order History");
        newLine();

        var history = customer.getOrderHistory();

        if (history.isEmpty()) {
            println("No order history");
            newLine();
            readEnter();
            return null;
        }

        for (int i = 0; i < history.size(); i++) {
            var order = history.get(i);

            print(i + 1);
            println(") Order placed on " + order.getLocalDateTime());
        }

        newLine();

        int index;

        while (true) {
            index = readInt("View order #");

            if (index <= 0 || index > history.size()) {
                rejectInput("Invalid index!");
            }
            else {
                acceptInput();
                break;
            }
        }

        clear();

        var order = history.get(--index);

        println("Order History: Order placed on " + order.getLocalDateTime());
        newLine();
        println("Total amount: " + formatCurrency(order.getAmount()));
        newLine();

        order.getCart().getProducts().forEach(((product, quantity) -> {
            printProduct(product);
            println("\tQuantity:    " + quantity);
        }));

        newLine();
        readEnter();

        return null;
    }
}
