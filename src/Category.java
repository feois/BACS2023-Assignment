public class Category {
    private String categoryName;
    private char categoryCode;

    public Category(String categoryName, char categoryCode) {
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public char getCategoryCode() {
        return categoryCode;
    }
}
