public abstract class User {
    private final String username;
    private final String password;

    protected User(String username, String password) {
        if (!validateUsername(username)){
            throw new IllegalArgumentException("Invalid username");
        }
        
        this.username = username;
        this.password = password;
    }

    public static boolean validateUsername(String username){
        return username.chars().allMatch(c ->
                (c >= 'a' && c <= 'z')
                || (c >= 'A' && c <= 'Z')
                || (c >= '0' && c <= '9')
                || c == '_'
                || c == '-'
        );
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public abstract String getUserType();
}





