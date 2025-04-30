public class Administrator extends User {
    public Administrator(String username, String password) {
        super(username, password);
    }

    @Override
    public String getUserType() {
        return "Administrator";
    }
}
