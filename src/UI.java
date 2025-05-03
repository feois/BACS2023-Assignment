import java.util.Scanner;

@SuppressWarnings("unused")
public abstract class UI {
    private static final Scanner scanner = new Scanner(System.in);
    private static String screen = "";
    private static String pendingInput = "";
    private static boolean autoAccept = false;

    private void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        }
        catch (Exception ignored) {}
    }

    public final String getScreen() {
        return screen;
    }

    public final void clear() {
        screen = "";
        clearScreen();
    }

    public final void print(char c) {
        screen += c;
        System.out.print(c);
    }

    public final void print(String s) {
        screen += s;
        System.out.print(s);
    }

    public final void print(int i) {
        screen += i;
        System.out.print(i);
    }

    public final void print(double d) {
        screen += d;
        System.out.print(d);
    }

    public final void newLine() {
        screen += "\r\n";
        System.out.println();
    }

    public final void println(char c) {
        print(c);
        newLine();
    }

    public final void println(String s) {
        print(s);
        newLine();
    }

    public final void println(int i) {
        print(i);
        newLine();
    }

    public final void println(double d) {
        print(d);
        newLine();
    }

    public final void acceptInput() {
        screen += pendingInput;
        screen += "\r\n";

        clearScreen();
        System.out.print(screen);
    }

    private void checkAutoAccept() {
        if (autoAccept) {
            autoAccept = false;
            acceptInput();
        }
    }

    private int checkAutoAccept(String prompt, int i) {
        pendingInput = prompt + i;

        checkAutoAccept();

        return i;
    }

    private double checkAutoAccept(String prompt, double d) {
        pendingInput = prompt + d;

        checkAutoAccept();

        return d;
    }

    private String checkAutoAccept(String prompt, String s) {
        pendingInput = prompt + s;

        checkAutoAccept();

        return s;
    }

    private char checkAutoAccept(String prompt, char c) {
        pendingInput = prompt + c;

        checkAutoAccept();

        return c;
    }

    public final void rejectInput(String error) {
        clearScreen();
        System.out.print(screen);
        System.out.println(error);
    }

    public final void emptyInput() {
        rejectInput("Cannot enter empty input!");
    }

    public final void invalidInput() {
        rejectInput("Invalid input!");
    }

    public final UI noReject() {
        autoAccept = true;

        return this;
    }

    private String readLine(String prompt) {
        System.out.print(prompt);

        return scanner.nextLine();
    }

    public final int readInt(String prompt) {
        while (true) {
            var s = readLine(prompt);

            if (s.isBlank()) {
                emptyInput();
            }
            else {
                try {
                    return checkAutoAccept(prompt, Integer.parseInt(s));
                }
                catch (Exception e) {
                    invalidInput();
                }
            }
        }
    }

    public final int readIntOrDefault(String prompt, int defaultInt) {
        while (true) {
            var s = readLine(prompt);

            try {
                return checkAutoAccept(prompt, s.isBlank() ? defaultInt : Integer.parseInt(s));
            }
            catch (Exception e) {
                invalidInput();
            }
        }
    }

    public final double readDouble(String prompt) {
        while (true) {
            var s = readLine(prompt);

            if (s.isBlank()) {
                emptyInput();
            }
            else {
                try {
                    return checkAutoAccept(prompt, Double.parseDouble(s));
                }
                catch (Exception e) {
                    invalidInput();
                }
            }
        }
    }

    public final double readDoubleOrDefault(String prompt, Double defaultDouble) {
        while (true) {
            var s = readLine(prompt);

            try {
                return checkAutoAccept(prompt, s.isBlank() ? defaultDouble : Double.parseDouble(s));
            }
            catch (Exception e) {
                invalidInput();
            }
        }
    }

    public final String readString(String prompt) {
        while (true) {
            var s = readLine(prompt);

            if (s.isBlank()) {
                emptyInput();
            }
            else {
                return checkAutoAccept(prompt, s);
            }
        }
    }

    public final String readStringOrDefault(String prompt, String defaultString) {
        var s = readLine(prompt);

        return checkAutoAccept(prompt, s.isBlank() ? defaultString : s);
    }

    public final char readChar(String prompt) {
        while (true) {
            var s = readLine(prompt).strip();

            if (s.isBlank()) {
                emptyInput();
            }
            else if (s.length() > 1) {
                rejectInput("You can input only a single character!");
            }
            else {
                return checkAutoAccept(prompt, s.charAt(0));
            }
        }
    }

    public final char readCharOrDefault(String prompt, char defaultChar) {
        while (true) {
            var s = readLine(prompt).strip();

            if (s.length() > 1) {
                rejectInput("You can input only a single character");
            }
            else {
                return checkAutoAccept(prompt, s.isEmpty() ? defaultChar : s.charAt(0));
            }
        }
    }

    private static boolean checkOptions(CharSequence options, char opt) {
        return options.chars().anyMatch(c -> c == opt);
    }

    public final char readCharOptions(String prompt, CharSequence options) {
        var accept = autoAccept;

        autoAccept = false;

        while (true) {
            var c = readChar(prompt);

            if (checkOptions(options, c)) {
                if (accept) {
                    acceptInput();
                }

                return c;
            }

            rejectInput("Unknown option '" + c + "'!");
        }
    }

    public final char readCharOptionsOrDefault(String prompt, CharSequence options, char defaultOption) {
        var accept = autoAccept;

        autoAccept = false;

        while (true) {
            var c = readCharOrDefault(prompt, defaultOption);

            if (checkOptions(options, c)) {
                if (accept) {
                    acceptInput();
                }

                return c;
            }

            rejectInput("Unknown option '" + c + "'!");
        }
    }

    public final void invalidUsername() {
        rejectInput("Invalid username! Username must consist of only alphabets, digits, underscore and dash!");
    }

    public final void readEnter() {
        readEnter("Press Enter to continue");
    }

    public final void readEnter(String prompt) {
        noReject().readStringOrDefault(prompt, "");
    }

    public final void printCategories() {
        println("Categories");
        newLine();

        for (var cat : CategoryManager.getCategories()) {
            print(cat.getCategoryCode());
            println(": " + cat.getCategoryName());
        }

        newLine();
    }

    public final void printProduct(Product product) {
        println(product.getProductID() + '\t' + product.getProductName());
        println("\tDescription: " + product.getDescription());
        println("\tCategory:    " + product.getCategory().getCategoryName());
        println("\tPrice:       " + formatCurrency(product.getPrice()));
    }

    public static String formatCurrency(Double currency) {
        return "RM " + String.format("%.2f", currency);
    }

    public abstract UI run();
}
