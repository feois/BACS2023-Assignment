@SuppressWarnings("ClassCanBeRecord")
public class Category {
    private final char categoryCode;
    private final String categoryName;

    public Category(char categoryCode, String categoryName) {
        if (!validateCode(categoryCode)) {
            throw new IllegalArgumentException("Invalid category code!");
        }

        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
    }

    public static boolean validateCode(char code) {
        return code >= 'A' && code <= 'Z';
    }

    public char getCategoryCode() {
        return categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
