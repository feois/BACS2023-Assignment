public class MainMenuUI extends UI {
    private final User loginUser;

    public MainMenuUI(User loginUser) {
        this.loginUser = loginUser;
    }

    private UI adminUI(Administrator admin) {
        println("Options");

        println("1) List categories");
        println("2) Add categories");
        println("3) Search product");
        println("4) Add product");
        println("5) Log out");
        println("6) Exit");
        newline();

        var opt = noReject().readCharOptions("Enter options: ", "123456");

        return switch (opt) {
            case '1', '2', '3', '4' -> throw new RuntimeException(); // TODO
            case '5' -> new LoginUI();
            case '6' -> null;
            default -> throw new RuntimeException(); // should never happen
        };
    }

    private UI customerUI(Customer customer) {

        println("Options");

        println("1) View cart");
        println("2) Search product");
        println("3) Check out");
        println("4) Log out");
        println("5) Exit");
        newline();

        var opt = noReject().readCharOptions("Enter options: ", "12345");

        return switch (opt) {
            case '1', '2', '3' -> throw new RuntimeException(); // TODO
            case '4' -> new LoginUI();
            case '5' -> null;
            default -> throw new RuntimeException(); // should never happen
        };
    }

    @Override
    public UI run() {
        clear();

        println("Main Menu");
        newline();
        println("Currently login as " + loginUser.getUsername() + " (" + loginUser.getUserType() + ")");
        newline();

        if (loginUser instanceof Administrator admin) {
            return adminUI(admin);
        }
        else if (loginUser instanceof Customer customer) {
            return customerUI(customer);
        }

        return null;
    }
}
