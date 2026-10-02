public interface Sellable {

    String getName();

    int getPrice();

    default void displayInfo() {
        System.out.println("Product: " + getName() + ", Price: Rp " + getPrice());
    }
}