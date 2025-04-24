import java.util.ArrayList;
import java.util.Scanner;

public class RetailManagementSystem {
    private static ArrayList<Product> products = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Retail Management System ===");

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addNewProduct();
                    break;
                case 2:
                    updateRetailPrice();
                    break;
                case 3:
                    updateSellPrice();
                    break;
                case 4:
                    viewQuantity();
                    break;
                case 5:
                    addQuantity();
                    break;
                case 6:
                    recordSale();
                    break;
                case 7:
                    viewDailyProfit();
                    break;
                case 8:
                    view30DayAverageProfit();
                    break;
                case 9:
                    advanceDay();
                    break;
                case 10:
                    listAllProducts();
                    break;
                case 11:
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Add New Product");
        System.out.println("2. Update Retail Price");
        System.out.println("3. Update Sell Price");
        System.out.println("4. View Quantity");
        System.out.println("5. Add Quantity");
        System.out.println("6. Record Sale");
        System.out.println("7. View Today's Profit");
        System.out.println("8. View 30-Day Average Profit");
        System.out.println("9. Advance to Next Day");
        System.out.println("10. List All Products");
        System.out.println("11. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addNewProduct() {
        System.out.println("\n--- Add New Product ---");
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter retail price: ");
        double retailPrice = scanner.nextDouble();

        System.out.print("Enter sell price: ");
        double sellPrice = scanner.nextDouble();

        System.out.print("Enter initial quantity: ");
        int quantity = scanner.nextInt();

        Product product = new Product(name, retailPrice, sellPrice, quantity);
        products.add(product);

        System.out.println("Product added successfully!");
    }

    private static Product findProduct(String name) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    private static void updateRetailPrice() {
        System.out.println("\n--- Update Retail Price ---");
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        Product product = findProduct(name);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.print("Enter new retail price: ");
        double newPrice = scanner.nextDouble();
        product.setRetailPrice(newPrice);

        System.out.println("Retail price updated successfully!");
    }

    private static void updateSellPrice() {
        System.out.println("\n--- Update Sell Price ---");
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        Product product = findProduct(name);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.print("Enter new sell price: ");
        double newPrice = scanner.nextDouble();
        product.setSellPrice(newPrice);

        System.out.println("Sell price updated successfully!");
    }

    private static void viewQuantity() {
        System.out.println("\n--- View Quantity ---");
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        Product product = findProduct(name);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.println("Current quantity for " + product.getName() + ": " + product.getQuantity());
    }

    private static void addQuantity() {
        System.out.println("\n--- Add Quantity ---");
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        Product product = findProduct(name);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.print("Enter quantity to add: ");
        int amount = scanner.nextInt();
        product.addQuantity(amount);

        System.out.println("Quantity updated successfully! New quantity: " + product.getQuantity());
    }

    private static void recordSale() {
        System.out.println("\n--- Record Sale ---");
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        Product product = findProduct(name);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.print("Enter quantity sold: ");
        int amount = scanner.nextInt();
        product.recordSale(amount);

        System.out.printf("%d units of %s sold. Remaining quantity: %d%n",
                amount, product.getName(), product.getQuantity());
    }

    private static void viewDailyProfit() {
        System.out.println("\n--- Today's Profit ---");
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        double totalProfit = 0;
        for (Product product : products) {
            double profit = product.calculateDailyProfit();
            System.out.printf("%s: $%.2f%n", product.getName(), profit);
            totalProfit += profit;
        }

        System.out.printf("Total profit for today: $%.2f%n", totalProfit);
    }

    private static void view30DayAverageProfit() {
        System.out.println("\n--- 30-Day Average Profit ---");
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        double totalAverage = 0;
        for (Product product : products) {
            double avg = product.calculate30DayAverageProfit();
            System.out.printf("%s: $%.2f%n", product.getName(), avg);
            totalAverage += avg;
        }

        System.out.printf("Overall 30-day average profit: $%.2f%n", totalAverage);
    }

    private static void advanceDay() {
        for (Product product : products) {
            product.advanceDay();
        }
        System.out.println("Advanced to the next day. All daily sales records have been updated.");
    }

    private static void listAllProducts() {
        System.out.println("\n--- All Products ---");
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        for (Product product : products) {
            System.out.println(product);
        }
    }
}