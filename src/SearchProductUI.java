public class SearchProductUI extends UI {
    private final User loginUser;

    public SearchProductUI(User loginUser) {
        this.loginUser = loginUser;
    }

    @Override
    public UI run() {
        clear();

        println("Search product");
        newLine();
        printCategories();

        var cat = noReject().readCharOptionsOrDefault("Select category (Empty to search across all categories): ", CategoryManager.getCategoriesString() + ' ', ' ');
        var query = noReject().readString("Enter product name to search: ");
        var result = cat == ' ' ? ProductManager.searchProduct(query) : ProductManager.searchProduct(query, CategoryManager.getCategory(cat));

        newLine();

        if (result.isEmpty()) {
            println("No product found");
        }
        else {
            println("Products");

            for (var product : result) {
                printProduct(product);
                println("\tStock count: " + Inventory.getQuantity(product));
            }
        }

        newLine();
        readEnter();

        return null;
    }
}
