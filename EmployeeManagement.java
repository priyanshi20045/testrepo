import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple console application for managing employee records.
 * Compile with: javac EmployeeManagement.java
 * Run with:     java EmployeeManagement
 */
public class EmployeeManagement {
    private static final List<Employee> employees = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Employee Management ===");
            System.out.println("1. Add employee");
            System.out.println("2. Display all employees");
            System.out.println("3. Search employee by ID");
            System.out.println("4. Update employee details");
            System.out.println("5. Delete employee");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            switch (scanner.nextLine().trim()) {
                case "1":
                    addEmployee();
                    break;
                case "2":
                    displayEmployees();
                    break;
                case "3":
                    searchEmployee();
                    break;
                case "4":
                    updateEmployee();
                    break;
                case "5":
                    deleteEmployee();
                    break;
                case "6":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1–6.");
            }
        }

        scanner.close();
    }

    private static void addEmployee() {
        System.out.print("Employee ID: ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Employee ID cannot be empty.");
            return;
        }
        if (findEmployeeById(id) != null) {
            System.out.println("An employee with that ID already exists.");
            return;
        }

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Department: ");
        String department = scanner.nextLine().trim();
        double salary = readSalary();

        employees.add(new Employee(id, name, department, salary));
        System.out.println("Employee added successfully.");
    }

    private static void updateEmployee() {
        System.out.print("Enter the ID of the employee to update: ");
        String id = scanner.nextLine().trim();
        Employee employee = findEmployeeById(id);

        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
            return;
        }

        System.out.println("Current details: " + employee);
        System.out.print("New name: ");
        String name = scanner.nextLine().trim();
        System.out.print("New department: ");
        String department = scanner.nextLine().trim();
        double salary = readSalary();

        employee.updateDetails(name, department, salary);
        System.out.println("Employee details updated successfully.");
    }

    private static void deleteEmployee() {
        System.out.print("Enter the ID of the employee to delete: ");
        String id = scanner.nextLine().trim();
        Employee employee = findEmployeeById(id);

        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
            return;
        }

        System.out.println("Employee to delete: " + employee);
        System.out.print("Are you sure you want to delete this employee? (y/n): ");
        String confirmation = scanner.nextLine().trim();
        if (confirmation.equalsIgnoreCase("y")) {
            employees.remove(employee);
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static double readSalary() {
        while (true) {
            System.out.print("Salary: ");
            String input = scanner.nextLine().trim();
            try {
                double salary = Double.parseDouble(input);
                if (Double.isFinite(salary) && salary >= 0) {
                    return salary;
                }
            } catch (NumberFormatException ignored) {
                // Show the same helpful prompt for non-numeric input.
            }
            System.out.println("Please enter a valid, non-negative salary.");
        }
    }

    private static void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees have been added yet.");
            return;
        }

        System.out.println("\nEmployee records:");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    private static void searchEmployee() {
        System.out.print("Enter employee ID to search: ");
        String id = scanner.nextLine().trim();
        Employee employee = findEmployeeById(id);

        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
        } else {
            System.out.println(employee);
        }
    }

    private static Employee findEmployeeById(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equalsIgnoreCase(id)) {
                return employee;
            }
        }
        return null;
    }

    private static class Employee {
        private final String id;
        private String name;
        private String department;
        private double salary;

        private Employee(String id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        private String getId() {
            return id;
        }

        private void updateDetails(String name, String department, double salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return String.format(
                    "ID: %s | Name: %s | Department: %s | Salary: %.2f",
                    id, name, department, salary);
        }
    }
}
