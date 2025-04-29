public class LoginUI extends UI {
    @Override
    public void run() {
        clear();
        println("Login system");
        newline();

        char opt = readCharOptions("Select L(ogin) or R(egister): ", "LRlr");

        if (opt == 'l' || opt == 'L') {
            println("Login");
        }
        else {
            println("Register");
        }
    }
}
