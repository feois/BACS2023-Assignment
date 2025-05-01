public class Category {
    private final char categoryCode;
    private final String categoryName;

    public Category(char categoryCode, String categoryName) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
    }

    public char getCategoryCode() {
        return categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
