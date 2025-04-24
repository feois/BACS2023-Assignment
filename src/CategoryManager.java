import java.io.*;
import java.util.*;

public class CategoryManager {
    public final List<Category> categories;

    public CategoryManager() {
        categories = new ArrayList<>();
    }

    public CategoryManager(File file) throws FileNotFoundException {
        this();

        var br = new BufferedReader(new FileReader(file));

        // TODO
    }

    public List<Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void saveToFile(File file) {
        // TODO
    }
}
