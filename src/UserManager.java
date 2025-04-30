import java.io.*;
import java.util.HashMap;

public class UserManager {
    private static final HashMap<String, User> users = new HashMap<>();

    public static boolean hasUser() {
        return !users.isEmpty();
    }

    public static boolean hasUser(String username) {
        return users.get(username) != null;
    }

    public static User login(String username, String password) {
        var user = users.get(username);

        if (user != null) {
            if (!user.checkPassword(password)) {
                user = null;
            }
        }

        return user;
    }
    
    public static void register(User user) {
        if (users.containsKey(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        users.put(user.getUsername(), user);
    }

    public static void readFromFile(File file) throws IOException {
        // TODO
    }

    public static void saveToFile(File file) throws IOException {
        // TODO
    }
}

