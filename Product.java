import java.util.ArrayList;
import java.util.Scanner;

class Product {
    private String name;
    private double retailPrice;
    private double sellPrice;
    private int quantity;
    private int[] dailySales = new int[30]; // Stores sales for last 30 days
    private int currentDay = 0; // Tracks current day (0-29)

    public Product(String name, double retailPrice, double sellPrice, int quantity) {
        this.name = name;
        this.retailPrice = retailPrice;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int amount) {
        this.quantity += amount;
    }

    public void recordSale(int amountSold) {
        if (amountSold > quantity) {
            System.out.println("Error: Not enough stock available!");
            return;
        }

        quantity -= amountSold;
        dailySales[currentDay] += amountSold;
    }

    public void advanceDay() {
        currentDay = (currentDay + 1) % 30;
        dailySales[currentDay] = 0; // Reset for new day
    }

    public double calculateCurrentProfit() {
        return (sellPrice - retailPrice) * quantity;
    }

    public double calculateDailyProfit() {
        return (sellPrice - retailPrice) * dailySales[currentDay];
    }

    public double calculate30DayAverageProfit() {
        double totalProfit = 0;
        int daysWithSales = 0;

        for (int i = 0; i < 30; i++) {
            if (dailySales[i] > 0) {
                totalProfit += (sellPrice - retailPrice) * dailySales[i];
                daysWithSales++;
            }
        }

        return daysWithSales > 0 ? totalProfit / daysWithSales : 0;
    }

    public int getDailySales() {
        return dailySales[currentDay];
    }

    @Override
    public String toString() {
        return String.format("Product: %s | Retail Price: $%.2f | Sell Price: $%.2f | Quantity: %d",
                name, retailPrice, sellPrice, quantity);
    }
}

