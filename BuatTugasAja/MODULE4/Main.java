import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();

        Product p1 = new Product("P001", "Indomie Goreng", 3000, 50, "Makanan");
        Product p2 = new Product("P002", "Teh Botol", 5000, 30, "Minuman");
        Product p3 = new Product("P003", "Buku Tulis Sinar Dunia", 4000, 20, "Alat Tulis");
        Product p4 = new Product("P004", "Aqua 600ml", 4500, 40, "Minuman");
        Product p5 = new Product("P005", "Rinso Anti Noda", 12000, 15, "Kebutuhan Rumah");

        manager.addProduct(p1);
        manager.addProduct(p2);
        manager.addProduct(p3);
        manager.addProduct(p4);
        manager.addProduct(p5);

        manager.displayAllProducts();
        System.out.println();

        manager.displayUniqueCategories();
        System.out.println();

        System.out.println("--- Find Product ---");
        Product found = manager.findProductByName("Teh Botol");
        if (found != null) {
            System.out.println("Found: " + found);
        } else {
            System.out.println("Product not found.");
        }
        System.out.println();

        System.out.println("--- Remove Product ---");
        manager.removeProduct("P004");
        System.out.println();

        manager.displayAllProducts();
        System.out.println();

        manager.displayProductsSortedByName();
        System.out.println();

        Comparator<Product> byPriceDescending = new Comparator<Product>() {
            @Override
            public int compare(Product a, Product b) {
                return b.getPrice() - a.getPrice();
            }
        };
        manager.displayProductsSortedByPrice(byPriceDescending);
    }
}