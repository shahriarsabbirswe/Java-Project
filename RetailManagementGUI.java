import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class RetailManagementGUI {

    private JFrame frame;
    private ArrayList<Product> products = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RetailManagementGUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Areej Al-Iman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 650);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome to Areej Al-Iman", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(6, 2, 15, 15));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),
                "Select an Operation", TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16)));

        addButton(panel, "1. Add New Product", this::addNewProduct);
        addButton(panel, "2. Update Retail Price", this::updateRetailPrice);
        addButton(panel, "3. Update Sell Price", this::updateSellPrice);
        addButton(panel, "4. View Quantity", this::viewQuantity);
        addButton(panel, "5. Add Quantity", this::addQuantity);
        addButton(panel, "6. Record Sale", this::recordSale);
        addButton(panel, "7. View Today's Profit", this::viewDailyProfit);
        addButton(panel, "8. View 30-Day Average Profit", this::view30DayAverageProfit);
        addButton(panel, "9. Advance to Next Day", this::advanceDay);
        addButton(panel, "10. List All Products", this::listAllProducts);
        addButton(panel, "11. Exit", () -> System.exit(0));

        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
        wrapperPanel.add(panel);

        frame.add(wrapperPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void addButton(JPanel panel, String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBackground(new Color(240, 248, 255));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        button.addActionListener(e -> action.run());
        panel.add(button);
    }

    private Product findProductByName(String name) {
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    private void addNewProduct() {
        try {
            String name = JOptionPane.showInputDialog(frame, "Enter Product Name:");
            if (name == null || name.isBlank()) return;

            double retailPrice = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter Retail Price:"));
            double sellPrice = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter Sell Price:"));
            int quantity = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Initial Quantity:"));

            products.add(new Product(name, retailPrice, sellPrice, quantity));
            showMessage("Product added successfully.");
        } catch (Exception e) {
            showError("Invalid input. Please try again.");
        }
    }

    private void updateRetailPrice() {
        String name = promptProductName();
        if (name == null) return;

        Product product = findProductByName(name);
        if (product == null) return;

        try {
            double newPrice = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter New Retail Price:"));
            product.setRetailPrice(newPrice);
            showMessage("Retail price updated.");
        } catch (Exception e) {
            showError("Invalid price.");
        }
    }

    private void updateSellPrice() {
        String name = promptProductName();
        if (name == null) return;

        Product product = findProductByName(name);
        if (product == null) return;

        try {
            double newPrice = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter New Sell Price:"));
            product.setSellPrice(newPrice);
            showMessage("Sell price updated.");
        } catch (Exception e) {
            showError("Invalid price.");
        }
    }

    private void viewQuantity() {
        String name = promptProductName();
        if (name == null) return;

        Product product = findProductByName(name);
        if (product != null) {
            showMessage("Current quantity for " + product.getName() + ": " + product.getQuantity());
        }
    }

    private void addQuantity() {
        String name = promptProductName();
        if (name == null) return;

        Product product = findProductByName(name);
        if (product == null) return;

        try {
            int qty = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Quantity to Add:"));
            product.addQuantity(qty);
            showMessage("Quantity updated. New quantity: " + product.getQuantity());
        } catch (Exception e) {
            showError("Invalid quantity.");
        }
    }

    private void recordSale() {
        String name = promptProductName();
        if (name == null) return;

        Product product = findProductByName(name);
        if (product == null) return;

        try {
            int sold = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Quantity Sold:"));

            if (sold > product.getQuantity()) {
                showError("Not enough stock available!");
                return;
            }

            product.recordSale(sold);
            showMessage(sold + " units of " + product.getName() + " sold. Remaining: " + product.getQuantity());
        } catch (Exception e) {
            showError("Invalid input.");
        }
    }

    private void viewDailyProfit() {
        if (products.isEmpty()) {
            showMessage("No products available.");
            return;
        }

        StringBuilder sb = new StringBuilder("Today's Profit Report:\n");
        double total = 0;
        for (Product p : products) {
            double profit = p.calculateDailyProfit();
            sb.append(String.format("%-15s $%.2f%n", p.getName(), profit));
            total += profit;
        }
        sb.append(String.format("\nTotal: $%.2f", total));
        showLargeMessage(sb.toString());
    }

    private void view30DayAverageProfit() {
        if (products.isEmpty()) {
            showMessage("No products available.");
            return;
        }

        StringBuilder sb = new StringBuilder("30-Day Average Profit:\n");
        double total = 0;
        for (Product p : products) {
            double avg = p.calculate30DayAverageProfit();
            sb.append(String.format("%-15s $%.2f%n", p.getName(), avg));
            total += avg;
        }
        sb.append(String.format("\nOverall Avg: $%.2f", total));
        showLargeMessage(sb.toString());
    }

    private void advanceDay() {
        for (Product p : products) {
            p.advanceDay();
        }
        showMessage("Advanced to next day.");
    }

    private void listAllProducts() {
        if (products.isEmpty()) {
            showMessage("No products available.");
            return;
        }

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Name", "Retail Price", "Sell Price", "Quantity"}, 0);

        for (Product p : products) {
            model.addRow(new Object[]{p.getName(), p.getRetailPrice(), p.getSellPrice(), p.getQuantity()});
        }

        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        JOptionPane.showMessageDialog(frame, scrollPane, "All Products", JOptionPane.INFORMATION_MESSAGE);
    }

    private String promptProductName() {
        if (products.isEmpty()) {
            showMessage("No products available.");
            return null;
        }

        String name = JOptionPane.showInputDialog(frame, "Enter Product Name:");
        if (name == null || name.isBlank()) return null;

        Product product = findProductByName(name);
        if (product == null) {
            showError("Product not found.");
            return null;
        }

        return name;
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(frame, msg);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showLargeMessage(String msg) {
        JTextArea textArea = new JTextArea(msg);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(frame, scrollPane, "Report", JOptionPane.INFORMATION_MESSAGE);
    }
}
