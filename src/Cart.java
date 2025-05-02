import java.util.*;

public class Cart {
    private final Map<Product,Integer> products;

    public Cart() {
        this.products = new HashMap<>();
    }

    public Cart(Cart cart) {
        this.products = new HashMap<>(cart.products);
    }

    public void clear() {
        products.clear();
    }

    public Map<Product,Integer> getProducts() {
        return Collections.unmodifiableMap(products);
    }

    @SuppressWarnings("unused")
    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, int quantity) {
        quantity += products.getOrDefault(product, 0);

        if (quantity > Inventory.getQuantity(product)) {
            throw new IllegalArgumentException("Order quantity more than stock!");
        }

        products.put(product, quantity);
    }

    @SuppressWarnings("unused")
    public void reduceProduct(Product product) {
        reduceProduct(product, 1);
    }

    public void reduceProduct(Product product, int quantity) {
        if (products.containsKey(product)){
            int currentQuantity = products.get(product);

            if (currentQuantity > quantity){
                products.put(product,currentQuantity-quantity);
            }
            else{
                removeProduct(product);
            }
        }
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public double calculateAmount() {
        var amount = 0.0;

        for (var entry : getProducts().entrySet()) {
            var product = entry.getKey();
            var quantity = entry.getValue();

            amount += product.price * quantity;
        }

        return amount;
    }
}