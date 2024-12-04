package crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class crud {

    // connection method
    private static final String URL = "jdbc:mysql://localhost:3306/db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("User Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 20));

        JLabel customerIdLabel = new JLabel("Customer ID:");
        JTextField customerIdField = new JTextField();

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();

        JLabel postalCodeLabel = new JLabel("Postal Code:");
        JTextField postalCodeField = new JTextField();

        JLabel cityLabel = new JLabel("City:");
        JTextField cityField = new JTextField();

        JButton insertButton = new JButton("Insert");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton viewButton = new JButton("View");

        panel.add(customerIdLabel);
        panel.add(customerIdField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(postalCodeLabel);
        panel.add(postalCodeField);
        panel.add(cityLabel);
        panel.add(cityField);
        panel.add(insertButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(viewButton);

        frame.add(panel, BorderLayout.NORTH);

       
        JTable table = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(table);
        frame.add(tableScrollPane, BorderLayout.CENTER);

        frame.setVisible(true);

        // Insert Button 
        insertButton.addActionListener((ActionEvent e) -> {
            String customerId = customerIdField.getText();
            String name = nameField.getText();
            String address = addressField.getText();
            String postalCode = postalCodeField.getText();
            String city = cityField.getText();

            try (Connection conn = getConnection()) {
                String query = "INSERT INTO customerr (customerid, name, address, postalcode, city) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, customerId);
                pstmt.setString(2, name);
                pstmt.setString(3, address);
                pstmt.setString(4, postalCode);
                pstmt.setString(5, city);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(frame, "User inserted successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Update Button
        updateButton.addActionListener((ActionEvent e) -> {
            String customerId = customerIdField.getText();
            String name = nameField.getText();
            String address = addressField.getText();
            String postalCode = postalCodeField.getText();
            String city = cityField.getText();

            try (Connection conn = getConnection()) {
                String query = "UPDATE customerr SET name = ?, address = ?, postalcode = ?, city = ? WHERE customerid = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, name);
                pstmt.setString(2, address);
                pstmt.setString(3, postalCode);
                pstmt.setString(4, city);
                pstmt.setString(5, customerId);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, "User updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "User not found.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Delete Button 
        deleteButton.addActionListener((ActionEvent e) -> {
            String customerId = customerIdField.getText();

            try (Connection conn = getConnection()) {
                String query = "DELETE FROM customerr WHERE customerid = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, customerId);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, "User deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "User not found.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        

        // View Button 
        viewButton.addActionListener((ActionEvent e) -> {
            try (Connection conn = getConnection()) {
                String query = "SELECT * FROM customerr";
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();

                // Create a table model and set it to the JTable
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Customer ID");
                model.addColumn("Name");
                model.addColumn("Address");
                model.addColumn("Postal Code");
                model.addColumn("City");

                // Populate the table model with data from the ResultSet
                while (rs.next()) { 
                    model.addRow(new Object[]{
                            rs.getString("customerid"),
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("postalcode"),
                            rs.getString("city")
                    });
                }

                table.setModel(model);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}

               
