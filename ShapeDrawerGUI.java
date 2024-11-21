import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShapeDrawerGUI extends JFrame {
    private JComboBox<String> shapeComboBox;
    private JTextField dimensionField1, dimensionField2;
    private JButton drawButton;
    private JPanel drawPanel;

    public ShapeDrawerGUI() {
        // Frame settings
        setTitle("Shape Drawer");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ComboBox for shape selection
        String[] shapes = {"Circle (A)", "Triangle (B)", "Rectangle (C)"};
        shapeComboBox = new JComboBox<>(shapes);

        // Text fields for input dimensions
        dimensionField1 = new JTextField(10);
        dimensionField2 = new JTextField(10);
        dimensionField2.setEnabled(false);  // Initially disable the second field

        // Button to draw the shape
        drawButton = new JButton("Draw Shape");

        // Panel to draw the shape
        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawShape(g);
            }
        };

        drawPanel.setPreferredSize(new Dimension(500, 300));

        // Panel to hold controls (ComboBox, TextFields, Button)
        JPanel controlsPanel = new JPanel();
        controlsPanel.add(new JLabel("Select Shape:"));
        controlsPanel.add(shapeComboBox);
        controlsPanel.add(new JLabel("Enter Dimension 1:"));
        controlsPanel.add(dimensionField1);
        controlsPanel.add(new JLabel("Enter Dimension 2:"));
        controlsPanel.add(dimensionField2);
        controlsPanel.add(drawButton);

        add(controlsPanel, BorderLayout.NORTH);
        add(drawPanel, BorderLayout.CENTER);

        // Action listener for shape selection to update input fields
        shapeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateInputFields();
            }
        });

        // Action listener for the button to trigger drawing
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate inputs before drawing
                if (validateInputs()) {
                    drawPanel.repaint();  // Repaint the panel when button is clicked
                }
            }
        });

        // Initial setup for input fields based on selected shape
        updateInputFields();
    }

    // Method to update the input fields based on selected shape
    private void updateInputFields() {
        String selectedShape = (String) shapeComboBox.getSelectedItem();

        if (selectedShape.equals("Circle (A)")) {
            // For Circle, we only need one dimension (radius)
            dimensionField2.setEnabled(false);
            dimensionField2.setText("");
        } else {
            // For Triangle and Rectangle, we need two dimensions (e.g., base & height)
            dimensionField2.setEnabled(true);
        }
    }

    // Method to validate user inputs
    private boolean validateInputs() {
        String selectedShape = (String) shapeComboBox.getSelectedItem();
        boolean isValid = true;

        // Check if the dimensions are valid numbers
        try {
            int dimension1 = Integer.parseInt(dimensionField1.getText());
            if (dimension1 <= 0) {
                JOptionPane.showMessageDialog(this, "Dimension 1 must be a positive number.");
                isValid = false;
            }

            if (selectedShape.equals("Triangle (B)") || selectedShape.equals("Rectangle (C)")) {
                int dimension2 = Integer.parseInt(dimensionField2.getText());
                if (dimension2 <= 0) {
                    JOptionPane.showMessageDialog(this, "Dimension 2 must be a positive number.");
                    isValid = false;
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for the dimensions.");
            isValid = false;
        }

        // Ensure a valid shape is selected
        if (selectedShape == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid shape.");
            isValid = false;
        }

        return isValid;
    }

    // Method to draw the selected shape
    private void drawShape(Graphics g) {
        String selectedShape = (String) shapeComboBox.getSelectedItem();
        g.setColor(Color.PINK);

        try {
            if (selectedShape.equals("Circle (A)")) {
                int radius = Integer.parseInt(dimensionField1.getText());
                drawCircle(g, radius);
            } else if (selectedShape.equals("Triangle (B)")) {
                int base = Integer.parseInt(dimensionField1.getText());
                int height = Integer.parseInt(dimensionField2.getText());
                drawTriangle(g, base, height);
            } else if (selectedShape.equals("Rectangle (C)")) {
                int width = Integer.parseInt(dimensionField1.getText());
                int height = Integer.parseInt(dimensionField2.getText());
                drawRectangle(g, width, height);
            }
        } catch (NumberFormatException e) {
            // JOptionPane.showMessageDialog(this, "Please enter valid numbers for the dimensions.");
        }
    }

    // Method to draw a circle using Graphics.fillOval
    private void drawCircle(Graphics g, int radius) {
        int scale = 2;  // Scaling factor
        int diameter = radius * 2 * scale;
        int x = drawPanel.getWidth() / 2 - diameter / 2;
        int y = drawPanel.getHeight() / 2 - diameter / 2;
        g.fillOval(x, y, diameter, diameter);
    }

    // Method to draw a triangle using Graphics.fillPolygon
    private void drawTriangle(Graphics g, int base, int height) {
        int scale = 2;  // Scaling factor
        int[] xPoints = {drawPanel.getWidth() / 2 - base * scale / 2, 
                         drawPanel.getWidth() / 2 + base * scale / 2, 
                         drawPanel.getWidth() / 2};
        int[] yPoints = {drawPanel.getHeight() / 2 + height * scale / 2, 
                         drawPanel.getHeight() / 2 + height * scale / 2, 
                         drawPanel.getHeight() / 2 - height * scale / 2};
        g.fillPolygon(xPoints, yPoints, 3);
    }

    // Method to draw a rectangle using Graphics.fillRect
    private void drawRectangle(Graphics g, int width, int height) {
        int scale = 2;  // Scaling factor
        int x = drawPanel.getWidth() / 2 - (width * scale / 2);
        int y = drawPanel.getHeight() / 2 - (height * scale / 2);
        g.fillRect(x, y, width * scale, height * scale);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ShapeDrawerGUI().setVisible(true);
            }
        });
    }
}
