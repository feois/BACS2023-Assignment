import java.io.*;
import java.util.*;

public class CategoryManager {
    private static final List<Category> categories = new ArrayList<>();

    public static void readFromFile(File file) throws IOException {
        var reader = new BufferedReader(new FileReader(file));
        var s = reader.readLine();

        while (s != null) {
            addCategory(new Category(s.substring(1), s.charAt(0)));
            s = reader.readLine();
        }

        reader.close();
    }

    public static List<Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public static Category getCategory(char code) {
        for (var cat : getCategories()) {
            if (cat.getCategoryCode() == code) {
                return cat;
            }
        }

        return null;
    }

    public static void addCategory(Category category) {
        categories.add(category);
    }

    public static void saveToFile(File file) throws IOException {
        var writer = new BufferedWriter(new FileWriter(file));

        for (var cat : categories) {
            writer.write(cat.getCategoryCode() + cat.getCategoryName());
        }

        writer.close();
    }
}
