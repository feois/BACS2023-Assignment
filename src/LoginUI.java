public class LoginUI extends UI {
    private UI login() {
        clear();
        println("Login");
        newLine();

        String username;
        User user;

        while (true) {
            username = readString("Enter username: ");

            if (!User.validateUsername(username)) {
                rejectInput("Invalid username!");
            }
            else if (UserManager.hasUser(username)) {
                acceptInput();
                break;
            }
            else {
                rejectInput("Username does not exist!");
            }
        }

        while (true) {
            var password = readStringOrDefault("Enter password: ", "");

            user = UserManager.login(username, password);

            if (user != null) {
                acceptInput();
                break;
            }

            rejectInput("Invalid password!");
        }

        return new MainMenuUI(user);
    }

    private UI register() {
        clear();
        println("Register");
        newLine();

        var type = noReject().readCharOptions("Select type (a/A for administrator, c/C for customer): ", "aAcC");

        String username;

        while (true) {
            username = readString("Enter username: ");

            if (User.validateUsername(username)) {
                if (UserManager.hasUser(username)) {
                    rejectInput("Username already exists!");
                }
                else {
                    acceptInput();
                    break;
                }
            }
            else {
                rejectInput("Invalid username!");
            }
        }

        var password = noReject().readStringOrDefault("Enter password: ", "");
        User user = switch (type) {
            case 'a', 'A' -> new Administrator(username, password);
            case 'c', 'C' -> new Customer(username, password);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };

        UserManager.register(user);

        return new MainMenuUI(user);
    }

    @Override
    public UI run() {
        if (UserManager.hasUser()) {
            clear();
            println("Login/Register");
            newLine();

            var opt = noReject().readCharOptions("Select l/L to login or r/R to register: ", "lLrR");

            return switch (opt) {
                case 'l', 'L' -> login();
                case 'r', 'R' -> register();
                default -> throw new IllegalStateException("Unexpected value: " + opt); // should never happen
            };
        }
        else {
            return register();
        }
    }
}
