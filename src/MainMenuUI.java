public class MainMenuUI extends UI {
    private final User loginUser;

    public MainMenuUI(User loginUser) {
        this.loginUser = loginUser;
    }

    @SuppressWarnings("unused")
    private UI adminUI(Administrator admin) {
        println("Options");

        println("1) List categories");
        println("2) Add categories");
        println("3) Search product");
        println("4) Add product");
        println("5) Update inventory");
        println("6) Log out");
        println("7) Exit");
        newLine();

        var opt = noReject().readCharOptions("Enter options: ", "1234567");

        switch (opt) {
            case '1' -> new ListCategoriesUI().run();
            case '2' -> new AddCategoryUI().run();
            case '3' -> new SearchProductUI().run();
            case '4' -> new AddProductUI().run();
            case '5' -> new UpdateInventoryUI().run();
            case '6' -> { return new LoginUI(); } // log out
            case '7' -> { return null; } // exit
            default -> {}
        }

        return this; // run main menu again
    }

    private UI customerUI(Customer customer) {
        println("Options");

        println("1) Search product");
        println("2) View cart");
        println("3) View order history");
        println("4) Log out");
        println("5) Exit");
        newLine();

        var opt = noReject().readCharOptions("Enter options: ", "12345");

        switch (opt) {
            case '1' -> new SearchProductUI().run();
            case '2' -> new ViewCartUI(customer).run();
            case '3' -> new ViewHistoryUI(customer).run();
            case '4' -> { return new LoginUI(); } // log out
            case '5' -> { return null; } // exit
            default -> {}
        }

        return this; // run main menu again
    }

    @Override
    public UI run() {
        clear();

        println("Main Menu");
        newLine();
        println("Currently login as " + loginUser.getUsername() + " (" + loginUser.getUserType() + ")");
        newLine();

        if (loginUser instanceof Administrator admin) {
            return adminUI(admin);
        }
        else if (loginUser instanceof Customer customer) {
            return customerUI(customer);
        }

        return null;
    }
}
