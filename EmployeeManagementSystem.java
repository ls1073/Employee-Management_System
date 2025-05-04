import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EmployeeManagementSystem extends JFrame {
    private JTextField nameField, designationField, salaryField, idField, leaveField, searchField;
    private JButton addButton, refreshButton, deleteButton, searchButton;
    private JTable table;
    private DefaultTableModel tableModel;

    Connection con;

    public EmployeeManagementSystem() {
        setTitle("Employee Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(new BorderLayout(10, 10));

        Color bgColor = new Color(245, 248, 250);
        Color headerColor = new Color(33, 150, 243);
        Color buttonColor = new Color(100, 181, 246);

        getContentPane().setBackground(bgColor);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 5, 10, 10));
        inputPanel.setBorder(new TitledBorder("Employee Details"));
        inputPanel.setBackground(bgColor);

        nameField = new JTextField();
        designationField = new JTextField();
        salaryField = new JTextField();
        leaveField = new JTextField();
        idField = new JTextField();
        searchField = new JTextField();

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Designation:"));
        inputPanel.add(designationField);
        inputPanel.add(new JLabel("Salary:"));
        inputPanel.add(salaryField);
        inputPanel.add(new JLabel("Leave Days:"));
        inputPanel.add(leaveField);
        inputPanel.add(new JLabel("Employee ID (for Delete):"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Search by ID:"));
        inputPanel.add(searchField);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bgColor);

        addButton = new JButton("Add Employee");
        refreshButton = new JButton("Refresh Table");
        deleteButton = new JButton("Delete Employee");
        searchButton = new JButton("Search");

        for (JButton btn : new JButton[]{addButton, deleteButton, searchButton, refreshButton}) {
            btn.setBackground(buttonColor);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            buttonPanel.add(btn);
        }

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Designation", "Salary", "Leave Days"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(table);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Connect to database
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees_db", "root", "Khushisharma@3005");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed.");
            System.exit(1);
        }

        // Button Actions
        addButton.addActionListener(e -> addEmployee());
        refreshButton.addActionListener(e -> loadTableData());
        deleteButton.addActionListener(e -> deleteEmployee());
        searchButton.addActionListener(e -> searchEmployee());

        loadTableData();
        setVisible(true);
    }

    private void addEmployee() {
        String name = nameField.getText();
        String designation = designationField.getText();
        String salaryStr = salaryField.getText();
        String leaveStr = leaveField.getText();

        try {
            double salary = Double.parseDouble(salaryStr);
            int leaveDays = Integer.parseInt(leaveStr);

            PreparedStatement pst = con.prepareStatement("INSERT INTO employees(name, designation, salary, leave_days) VALUES (?, ?, ?, ?)");
            pst.setString(1, name);
            pst.setString(2, designation);
            pst.setDouble(3, salary);
            pst.setInt(4, leaveDays);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Employee added successfully.");
            clearFields();
            loadTableData();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add employee.");
        }
    }

    private void deleteEmployee() {
        String id = idField.getText();
        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM employees WHERE id = ?");
            pst.setInt(1, Integer.parseInt(id));
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Employee deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found.");
            }
            loadTableData();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete employee.");
        }
    }

    private void searchEmployee() {
        String id = searchField.getText();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM employees WHERE id = ?");
            pst.setInt(1, Integer.parseInt(id));
            ResultSet rs = pst.executeQuery();

            tableModel.setRowCount(0);
            if (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("designation"),
                        rs.getDouble("salary"),
                        rs.getInt("leave_days")
                });
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Search failed.");
        }
    }

    private void loadTableData() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM employees");
            tableModel.setRowCount(0);

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("designation"),
                        rs.getDouble("salary"),
                        rs.getInt("leave_days")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        nameField.setText("");
        designationField.setText("");
        salaryField.setText("");
        leaveField.setText("");
        idField.setText("");
        searchField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeManagementSystem::new);
    }
}
