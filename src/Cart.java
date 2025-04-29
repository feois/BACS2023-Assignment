import java.util.*;

public class Cart {
    private Map<Product,Integer> products;

    public Cart(){
        this.products = new HashMap<>();
    }

    public Map<Product,Integer> getProducts(){
        return Collections.unmodifiableMap(products);
    }

    public void addProduct(Product product){
        products.put(product, 1);
    }

    public void addProduct(Product product, int quantity){
        products.put(product,products.getOrDefault(product,0)+ quantity);
    }

    public void reduceProduct(Product product){
        if (products.containsKey(product)){
            reduceProduct(product,1);
        }
    }

    public void reduceProduct(Product product, int quantity){
        if (products.containsKey(product)){
            int currentQuantity = products.get(product);
            if (currentQuantity >quantity){
                products.put(product,currentQuantity-quantity);
            }
            else{
                removeProduct(product);
            }
        }
    }

    public void removeProduct(Product product){
        if (products.containsKey(product)){
            products.remove(product);
        }
    }

    public Cart clone(){
        Cart cloneCart = new Cart();
        cloneCart.products = new HashMap<>(this.products);
        return cloneCart;
    }
}