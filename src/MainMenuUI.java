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
        newLine();

        var opt = noReject().readCharOptions("Enter options: ", "123456");

        switch (opt) {
            case '1' -> new ListCategoriesUI().run();
            case '2' -> new AddCategoryUI().run();
            case '3' -> new SearchProductUI(admin).run();
            case '4' -> new AddProductUI().run();
            case '5' -> { return new LoginUI(); } // log out
            case '6' -> { return null; } // exit
        }

        return this;
    }

    private UI customerUI(Customer customer) {
        println("Options");

        println("1) View cart");
        println("2) Search product");
        println("3) Check out");
        println("4) Log out");
        println("5) Exit");
        newLine();

        var opt = noReject().readCharOptions("Enter options: ", "12345");

        switch (opt) {
            case '1' -> new ViewCartUI(customer).run();
            case '2' -> new SearchProductUI(customer).run();
            case '3' -> new CheckoutUI(customer).run();
            case '4' -> { return new LoginUI(); } // log out
            case '5' -> { return null; } // exit
        }

        return this;
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
