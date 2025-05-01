import java.io.*;
import java.util.*;

public class CategoryManager {
    private static final List<Category> categories = new ArrayList<>();

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

    public static void readFrom(Reader in) throws IOException {
        var reader = new PeekableReader(in);

        while (reader.hasLine()) {
            var s = reader.readLine();

            addCategory(new Category(s.charAt(0), s.substring(1)));
        }

        reader.close();
    }

    public static void saveTo(Writer out) throws IOException {
        var writer = new BufferedWriter(out);

        for (var cat : categories) {
            writer.write(cat.getCategoryCode() + cat.getCategoryName());
            writer.newLine();
        }

        writer.close();
    }
}
