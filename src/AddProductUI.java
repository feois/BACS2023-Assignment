public class AddProductUI extends UI {
    @Override
    public UI run() {
        clear();
        println("Add new product");
        newLine();
        printCategories();

        String productID;

        while (true) {
            productID = readStringOrDefault("Enter new product's ID (Empty to cancel): ", "");

            if (productID.isEmpty()) {
                return null;
            }
            else if (Product.validateID(productID)) {
                if (ProductManager.getProduct(productID) == null) {
                    acceptInput();
                    break;
                }
                else {
                    rejectInput("Product ID already exists!");
                }
            }
            else {
                rejectInput("Invalid product ID! Product ID must be a category code followed by at least four digits");
            }
        }

        var name = noReject().readString("Enter product name: ");
        var desc = noReject().readStringOrDefault("Enter product description: ", "");
        double price;

        while (true) {
            price = readDouble("Enter product price: RM ");

            if (price > 0) {
                acceptInput();
                break;
            }
            else {
                rejectInput("Price cannot be lower than or equal to 0!");
            }
        }

        var product = new Product(productID, name, desc, price);

        ProductManager.addProduct(product);

        newLine();
        println("Product successfully added!");
        readEnter();

        return null;
    }
}
