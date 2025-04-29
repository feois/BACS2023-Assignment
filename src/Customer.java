/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer extends User {
    public Cart cart;
    private List<Order>  history;

    public Customer(String username, String password) {
        super(username, password);
        cart = new Cart();
        history = new ArrayList<>();
    }
    
    public Order checkout(){
        return null;
    }
    
    public List<Order> getOrderHistory(){
        return Collections.unmodifiableList(history);
    }
}

