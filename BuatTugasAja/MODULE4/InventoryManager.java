import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InventoryManager {

    private Map<String, Product> inventory = new HashMap<>();

    private Set<String> categories = new HashSet<>();

    public void addProduct(Product product) {
        inventory.put(product.getProductID(), product);
        categories.add(product.getCategory());
    }

    public void removeProduct(String productID) {
        Product removed = inventory.remove(productID);
        if (removed == null) {
            System.out.println("No product found with ID: " + productID);
        } else {
            System.out.println("Removed: " + removed.getName());
        }
    }

    public Product findProductByName(String name) {
        for (Product product : inventory.values()) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    public void displayAllProducts() {
        System.out.println("--- All Products ---");
        for (Product product : inventory.values()) {
            product.displayInfo();
        }
    }

    public void displayUniqueCategories() {
        System.out.println("--- Unique Categories ---");
        for (String category : categories) {
            System.out.println("- " + category);
        }
    }

    public void displayProductsSortedByName() {
        List<Product> products = new ArrayList<>(inventory.values());
        Collections.sort(products);

        System.out.println("--- Products Sorted By Name ---");
        for (Product product : products) {
            product.displayInfo();
        }
    }

    public void displayProductsSortedByPrice(Comparator<Product> comparator) {
        List<Product> products = new ArrayList<>(inventory.values());
        Collections.sort(products, comparator);

        System.out.println("--- Products Sorted By Price ---");
        for (Product product : products) {
            product.displayInfo();
        }
    }
}