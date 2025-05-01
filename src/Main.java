import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.file.Path;

public class Main {
    private static final String CATEGORY_FILE = "categories.db";
    private static final String PRODUCT_FILE = "products.db";
    private static final String INVENTORY_FILE = "inventory.db";
    private static final String ADMINISTRATORS_FILE = "admins.db";
    @SuppressWarnings("SpellCheckingInspection")
    private static final String CUSTOMERS_FILE = "cust.db";

    public static void readFiles(Path path) {
        try { CategoryManager.readFrom(new FileReader(path.resolve(CATEGORY_FILE).toFile())); }
        catch (IOException ignored) {}

        try { ProductManager.readFrom(new FileReader(path.resolve(PRODUCT_FILE).toFile())); }
        catch (IOException ignored) {}

        try { Inventory.readFrom(new FileReader(path.resolve(INVENTORY_FILE).toFile())); }
        catch (IOException ignored) {}

        try { UserManager.readAdministratorsFrom(new FileReader(path.resolve(ADMINISTRATORS_FILE).toFile())); }
        catch (IOException ignored) {}

        try { UserManager.readCustomersFrom(new FileReader(path.resolve(CUSTOMERS_FILE).toFile())); }
        catch (IOException ignored) {}
    }

    public static void saveFiles(Path path) {
        try { CategoryManager.saveTo(new FileWriter(path.resolve(CATEGORY_FILE).toFile())); }
        catch (IOException ignored) {}

        try { ProductManager.saveTo(new FileWriter(path.resolve(PRODUCT_FILE).toFile())); }
        catch (IOException ignored) {}

        try { Inventory.saveTo(new FileWriter(path.resolve(INVENTORY_FILE).toFile())); }
        catch (IOException ignored) {}

        try { UserManager.saveAdministratorsTo(new FileWriter(path.resolve(ADMINISTRATORS_FILE).toFile())); }
        catch (IOException ignored) {}

        try { UserManager.saveCustomersTo(new FileWriter(path.resolve(CUSTOMERS_FILE).toFile())); }
        catch (IOException ignored) {}
    }

    public static void main(String[] args) {
        Path path = FileSystemView.getFileSystemView().getDefaultDirectory().toPath();

        path = path.resolve("JavaAssignment");

        if ((!path.toFile().isDirectory()) && !path.toFile().mkdirs()) {
            path = null;
        }

        if (path != null) {
            readFiles(path);
        }

        UI ui = new LoginUI();

        while (ui != null) {
            ui = ui.run();
        }

        new ExitUI().run();

        if (path != null) {
            saveFiles(path);
        }
    }
}
