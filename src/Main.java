import java.io.File;
import java.io.IOException;

public class Main {
    public static void readFiles() {
        try {
            CategoryManager.readFromFile(new File(""));
        }
        catch (IOException e) {

        }

        try {
            ProductManager.readFromFile(new File(""));
        }
        catch (IOException e) {

        }
    }

    public static void saveFiles() {

    }

    public static void main(String[] args) {
        readFiles();

        var ui = new LoginUI();

        ui.run();

        saveFiles();
    }
}
