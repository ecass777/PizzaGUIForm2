import javax.swing.*;
import java.awt.*;

public class PizzaGUIFrame extends JFrame {
    private JRadioButton thinCrust, regularCrust, deepDishCrust;
    private JComboBox<String> sizeBox;
    private JCheckBox[] toppings;
    private JTextArea orderSummary;
    private ButtonGroup crustGroup;

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crust Panel
        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(BorderFactory.createTitledBorder("Crust Type"));
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep-Dish");
        crustGroup = new ButtonGroup();
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);
        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDishCrust);

        // Size Panel
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Pizza Size"));
        String[] sizes = {"Small ($8)", "Medium ($12)", "Large ($16)", "Super ($20)"};
        sizeBox = new JComboBox<>(sizes);
        sizePanel.add(sizeBox);

        // Toppings Panel
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Toppings ($1 each)"));
        toppings = new JCheckBox[6];
        toppings[0] = new JCheckBox("Cheese");
        toppings[1] = new JCheckBox("Pepperoni");
        toppings[2] = new JCheckBox("Mushrooms");
        toppings[3] = new JCheckBox("Olives");
        toppings[4] = new JCheckBox("Onions");
        toppings[5] = new JCheckBox("Bacon");
        for (JCheckBox topping : toppings) {
            toppingsPanel.add(topping);
        }

        // Main Panel to hold crust, size, and toppings panels
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(crustPanel);
        mainPanel.add(sizePanel);
        mainPanel.add(toppingsPanel);

        // Order Summary Panel
        JPanel orderPanel = new JPanel();
        orderSummary = new JTextArea(20, 20);
        JScrollPane scrollPane = new JScrollPane(orderSummary);
        orderPanel.setBorder(BorderFactory.createTitledBorder("Order Summary"));
        orderPanel.add(scrollPane);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        JButton orderButton = new JButton("Order");
        JButton clearButton = new JButton("Clear");
        JButton quitButton = new JButton("Quit");

        quitButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to quit?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        orderButton.addActionListener(e -> calculateOrder());

        clearButton.addActionListener(e -> clearForm());

        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        // Add Panels to Frame
        add(mainPanel, BorderLayout.CENTER);
        add(orderPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void calculateOrder() {
        StringBuilder order = new StringBuilder();
        double subtotal = 0;

        if (thinCrust.isSelected()) {
            order.append("Crust: Thin\n");
        } else if (regularCrust.isSelected()) {
            order.append("Crust: Regular\n");
        } else if (deepDishCrust.isSelected()) {
            order.append("Crust: Deep-Dish\n");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a crust type.");
            return;
        }

        String size = (String) sizeBox.getSelectedItem();
        switch (size) {
            case "Small ($8)":
                order.append("Size: Small\n");
                subtotal += 8;
                break;
            case "Medium ($12)":
                order.append("Size: Medium\n");
                subtotal += 12;
                break;
            case "Large ($16)":
                order.append("Size: Large\n");
                subtotal += 16;
                break;
            case "Super ($20)":
                order.append("Size: Super\n");
                subtotal += 20;
                break;
            default:
                JOptionPane.showMessageDialog(this, "Please select a size.");
                return;
        }

        boolean toppingSelected = false;
        order.append("\nToppings:\n");
        for (JCheckBox topping : toppings) {
            if (topping.isSelected()) {
                order.append(topping.getText()).append("\n");
                subtotal += 1;
                toppingSelected = true;
            }
        }

        if (!toppingSelected) {
            JOptionPane.showMessageDialog(this, "Please select at least one topping.");
            return;
        }

        double tax = subtotal * 0.07;
        double total = subtotal + tax;

        order.append("\nSub-total: $").append(String.format("%.2f", subtotal)).append("\n");
        order.append("Tax: $").append(String.format("%.2f", tax)).append("\n");
        order.append("Total: $").append(String.format("%.2f", total)).append("\n");

        orderSummary.setText(order.toString());
    }

    private void clearForm() {
        crustGroup.clearSelection();
        sizeBox.setSelectedIndex(0);

        for (JCheckBox topping : toppings) {
            topping.setSelected(false);
        }

        orderSummary.setText("");
    }
}
