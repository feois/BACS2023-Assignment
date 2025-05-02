public class UpdateInventoryUI extends UI {
    @Override
    public UI run() {
        clear();

        println("Update inventory");
        newLine();

        Product product;

        while (true) {
            var productID = readStringOrDefault("Enter product ID (Empty to cancel): ", "");

            if (productID.isEmpty()) {
                return null;
            }
            else if (Product.validateID(productID)) {
                product = ProductManager.getProduct(productID);

                if (product == null) {
                    rejectInput("Product ID does not exist!");
                }
                else {
                    acceptInput();
                    break;
                }
            }
            else {
                rejectInput("Invalid ID!");
            }
        }

        println("Current stock count: " + Inventory.getQuantity(product));

        var quantity = noReject().readInt("Enter new quantity: ");

        Inventory.updateQuantity(product, quantity);

        newLine();
        println("Inventory successfully updated");
        readEnter();

        return null;
    }
}
