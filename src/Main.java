import java.io.*;

public class Main {
    public static void readFiles() {
        try {
            CategoryManager.readFromFile(new File(""));
        }
        catch (IOException ignored) {}

        try {
            ProductManager.readFromFile(new File(""));
        }
        catch (IOException ignored) {}

        try {
            UserManager.readFromFile(new File(""));
        }
        catch (IOException ignored) {}
    }

    public static void saveFiles() {

    }

    public static void main(String[] args) {
        readFiles();

        UI ui = new LoginUI();

        while (ui != null) {
            ui = ui.run();
        }

        new ExitUI().run();

        saveFiles();
    }
}
