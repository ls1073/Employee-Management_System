import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmployeeDAO dao = new EmployeeDAO();
        int choice;

        do {
            System.out.println("\n--- Employee Management System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Delete Employee");
            System.out.println("4. Search Employee by ID");
            System.out.println("5. Update Employee");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    sc.nextLine(); // consume newline
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Designation: ");
                    String desig = sc.nextLine();
                    System.out.print("Enter Salary: ");
                    double sal = sc.nextDouble();

                    Employee emp = new Employee();
                    emp.setName(name);
                    emp.setDesignation(desig);
                    emp.setSalary(sal);
                    dao.addEmployee(emp);
                    break;

                case 2:
                    List<Employee> list = dao.getAllEmployees();
                    for (Employee e : list) {
                        System.out.println("ID: " + e.getId() + ", Name: " + e.getName() +
                                           ", Designation: " + e.getDesignation() +
                                           ", Salary: " + e.getSalary());
                    }
                    break;

                case 3:
                    System.out.print("Enter ID to delete: ");
                    int id = sc.nextInt();
                    dao.deleteEmployee(id);
                    break;

                case 4:
                    System.out.print("Enter ID to search: ");
                    int searchId = sc.nextInt();
                    Employee found = dao.getEmployeeById(searchId);
                    if (found != null) {
                        System.out.println("ID: " + found.getId() + ", Name: " + found.getName() +
                                           ", Designation: " + found.getDesignation() +
                                           ", Salary: " + found.getSalary());
                    } else {
                        System.out.println("Employee not found.");
                    }
                    break;

                case 5:
                    System.out.print("Enter ID to update: ");
                    int updateId = sc.nextInt();
                    sc.nextLine(); // clear buffer
                    Employee updateEmp = dao.getEmployeeById(updateId);
                    if (updateEmp != null) {
                        System.out.print("Enter New Name: ");
                        updateEmp.setName(sc.nextLine());
                        System.out.print("Enter New Designation: ");
                        updateEmp.setDesignation(sc.nextLine());
                        System.out.print("Enter New Salary: ");
                        updateEmp.setSalary(sc.nextDouble());

                        dao.updateEmployee(updateEmp);
                    } else {
                        System.out.println("Employee not found.");
                    }
                    break;

                case 6:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        sc.close();
    }
}
