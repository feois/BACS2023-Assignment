/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public abstract class User {
    private String username;
    private String password;

    protected User(String username, String password) {
        if (!validateUsername(username)){
            throw new IllegalArgumentException("Invalid username");
        }
        
        this.username = username;
        this.password = password;
    }
    public static boolean validateUsername(String username){
        return true;
    }
    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}





