public class ListCategoriesUI extends UI {
    @Override
    public UI run() {
        clear();

        println("All categories:");
        newLine();

        var categories = CategoryManager.getCategories();

        for (int i = 0; i < categories.size(); i++) {
            var category = categories.get(i);

            print(i + 1);
            println(") " + category.getCategoryCode() + ": " + category.getCategoryName());
            print('\t');
            println("Product count: " + ProductManager.getProducts(category).size());
        }

        newLine();
        readEnter();

        return null;
    }
}
