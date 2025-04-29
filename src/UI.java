import java.util.Scanner;

public abstract class UI {
    private static final Scanner scanner = new Scanner(System.in);
    private static String screen = "";
    private static String pendingInput = "";

    private static void clearScreen() {
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

    public static void clear() {
        screen = "";
        clearScreen();
    }

    public static void print(String s) {
        screen += s;
        System.out.print(s);
    }

    public static void print(int i) {
        screen += i;
        System.out.print(i);
    }

    public static void print(double d) {
        screen += d;
        System.out.print(d);
    }

    public static void newline() {
        screen += "\r\n";
        System.out.println();
    }

    public static void println(String s) {
        print(s);
        newline();
    }

    public static void println(int i) {
        print(i);
        newline();
    }

    public static void println(double d) {
        print(d);
        newline();
    }

    public static void acceptInput() {
        screen += pendingInput;
        screen += "\r\n";
    }

    public static void rejectInput(String error) {
        clearScreen();
        System.out.print(screen);
        System.out.println(error);
    }

    public static void invalidInput() {
        rejectInput("Invalid input!");
    }

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                int i = scanner.nextInt();

                pendingInput = prompt + i;

                return i;
            }
            catch (Exception ignored) {}

            invalidInput();
        }
    }

    public static int readValidInt(String prompt) {
        int i = readInt(prompt);

        acceptInput();

        return i;
    }

    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                double d = scanner.nextDouble();

                pendingInput = prompt + d;

                return d;
            }
            catch (Exception ignored) {}

            invalidInput();
        }
    }

    public static double readValidDouble(String prompt) {
        double d = readDouble(prompt);

        acceptInput();

        return d;
    }

    public static String readString(String prompt) {
        System.out.print(prompt);

        String s = scanner.nextLine();

        pendingInput = prompt + s;

        return s;
    }

    public static String readValidString(String prompt) {
        String s = readString(prompt);

        acceptInput();

        return s;
    }

    public static char readCharOptions(String prompt, String options) {
        while (true) {
            String s = readString(prompt);

            if (!s.isEmpty()) {
                char c = s.charAt(0);

                if (options.contains(String.valueOf(c))) {
                    acceptInput();

                    return c;
                }

                rejectInput("Unknown option '" + c + "'!");
            }
            else {
                rejectInput("Please select an option");
            }
        }
    }

    public abstract void run();
}
