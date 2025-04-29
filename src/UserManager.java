/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.*;
import java.util.HashMap;

public class UserManager {
    private static final HashMap<String, User> users = new HashMap<>();

    private static void loadFromFile(File file) {
    }
    
    public static boolean register(User user) {
        if (users.containsKey(user.getUsername())) {
            return false;
        } else {
            users.put(user.getUsername(), user);
            return true;
        }
    }

    private static void saveToFile(File file) {
    }
}

