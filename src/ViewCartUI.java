import java.util.HashSet;

public class ViewCartUI extends UI {
    private final Customer customer;

    public ViewCartUI(Customer customer) {
        this.customer = customer;
    }

    public void addProduct() {
        var products = customer.cart.getProducts();

        Product product;
        int quantityLeft;

        while (true) {
            var productID = readStringOrDefault("Enter product ID (Empty to cancel): ", "");

            if (productID.isEmpty()) {
                return;
            }
            else if (Product.validateID(productID)) {
                product = ProductManager.getProduct(productID);

                if (product == null) {
                    rejectInput("Unknown ID " + productID);
                }
                else {
                    quantityLeft = Inventory.getQuantity(product) - products.getOrDefault(product, 0);

                    if (quantityLeft < 1) {
                        rejectInput("This product does not currently have any stock left");
                    }
                    else{
                        acceptInput();
                        break;
                    }
                }
            }
            else {
                rejectInput("Invalid ID!");
            }
        }

        println("Quantity left (Stock count - quantity in cart): " + quantityLeft);

        int quantity;

        while (true) {
            quantity = readIntOrDefault("Enter quantity (Default = 1): ", 1);

            if (quantity < 1) {
                rejectInput("Cannot add less than 1 product!");
            }
            else if (quantity > quantityLeft) {
                rejectInput("Stock count not enough!");
            }
            else {
                acceptInput();
                break;
            }
        }

        customer.cart.addProduct(product, quantity);
    }

    public void reduceProduct() {
        var products = customer.cart.getProducts();
        Product product;

        while (true) {
            var productID = readStringOrDefault("Enter product ID (Empty to cancel): ", "");

            if (productID.isEmpty()) {
                return;
            }
            else if (Product.validateID(productID)) {
                product = ProductManager.getProduct(productID);

                if (product == null) {
                    rejectInput("Unknown ID " + productID);
                }
                else if (products.containsKey(product)) {
                    acceptInput();
                    break;
                }
                else {
                    rejectInput("Cart does not contain product with ID " + productID);
                }
            }
            else {
                rejectInput("Invalid ID!");
            }
        }

        println("Product's quantity in cart: " + products.get(product));

        int quantity;

        while (true) {
            quantity = readIntOrDefault("Quantity to reduce (Default = 1): ", 1);

            if (quantity > products.get(product)) {
                rejectInput("Input more than quantity in cart!");
            }
            else if (quantity < 1) {
                rejectInput("Input cannot be smaller than 1!");
            }
            else {
                acceptInput();
                break;
            }
        }

        customer.cart.reduceProduct(product, quantity);
    }

    private void checkout() {
        println("Checkout");
        newLine();

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

    @Override
    public UI run() {
        while (true) {
            clear();

            println("Cart");
            newLine();

            var products = customer.cart.getProducts();

            if (products.isEmpty()) {
                println("Cart is empty");
            }
            else {
                products.forEach((product, quantity) -> {
                    printProduct(product);
                    println("\tQuantity:    " + quantity);
                });
            }

            newLine();
            println("a/A: Add product into cart");
            if (!products.isEmpty()) {
                println("r/R: Reduce quantity of products in cart");
                println("c/C: Check out");
            }
            println("e/E: Exit and return to main menu");
            var c = noReject().readCharOptions("Select option: ", products.isEmpty() ? "aAeE" : "aArRcCeE");

            newLine();

            switch (c) {
                case 'a', 'A' -> addProduct();
                case 'r', 'R' -> reduceProduct();
                case 'c', 'C' -> checkout();
                case 'e', 'E' -> { return null; }
                default -> {}
            }
        }
    }
}
