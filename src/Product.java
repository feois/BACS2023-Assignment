public class Product {
    private final String productID;
    private final String productName;
    private final String description;
    private final Category category;
    private final double price;

    public Product(String productID, String productName, String description, double price) {
        if (!validateID(productID)) {
            throw new IllegalArgumentException("Invalid Product ID");
        }

        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        category = getCategoryFromID(productID);
    }

    private static Category getCategoryFromID(String productID) {
        return productID.isEmpty() ? null : CategoryManager.getCategory(productID.charAt(0));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean validateID(String productID) {
        if (productID.length() > 4 && getCategoryFromID(productID) != null) {
            try {
                Integer.parseUnsignedInt(productID.substring(1));
                return true;
            }
            catch (Exception ignored) {}
        }

        return false;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }
}
