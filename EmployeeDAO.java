import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public void addEmployee(Employee emp) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO employees (name, designation, salary) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, emp.getName());
            stmt.setString(2, emp.getDesignation());
            stmt.setDouble(3, emp.getSalary());
            stmt.executeUpdate();
            System.out.println("Employee added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM employees";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setDesignation(rs.getString("designation"));
                emp.setSalary(rs.getDouble("salary"));
                list.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteEmployee(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM employees WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Employee deleted.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Employee getEmployeeById(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM employees WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("designation"),
                    rs.getDouble("salary")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateEmployee(Employee emp) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE employees SET name = ?, designation = ?, salary = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, emp.getName());
            stmt.setString(2, emp.getDesignation());
            stmt.setDouble(3, emp.getSalary());
            stmt.setInt(4, emp.getId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
