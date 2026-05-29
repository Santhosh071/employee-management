import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
public class EmployeeManagementApp {
    private static Scanner sc = new Scanner(System.in);
    private static EmployeeService service = new EmployeeService();
    public static void main(String[] args) {
        printBanner();
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("  Enter your choice : ");
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewAllEmployees();
                    break;
                case 3:
                    searchByName();
                    break;
                case 4:
                    searchById();
                    break;
                case 5:
                    updateEmployee();
                    break;
                case 6:
                    deleteEmployee();
                    break;
                case 7:
                    System.out.println("\n  Thank you for using Employee Management System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("\n  [WARNING] Invalid choice. Please enter a number between 1 and 7.");
                    System.out.println();
            }
        }
        sc.close();
    }
    private static void printBanner() {
        System.out.println();
        System.out.println("  =====================================================");
        System.out.println("       EMPLOYEE MANAGEMENT SYSTEM  v1.0");
        System.out.println("       Core Java | Collections | OOP");
        System.out.println("  =====================================================");
        System.out.println();
    }
    private static void printMenu() {
        System.out.println("  -------------------------------------------------");
        System.out.println("                   MAIN MENU");
        System.out.println("  -------------------------------------------------");
        System.out.println("  1. Add Employee");
        System.out.println("  2. View All Employees");
        System.out.println("  3. Search Employee by Name");
        System.out.println("  4. Search Employee by ID");
        System.out.println("  5. Update Employee Details");
        System.out.println("  6. Delete Employee");
        System.out.println("  7. Exit");
        System.out.println("  -------------------------------------------------");
    }
    private static void addEmployee() {
        System.out.println("\n  -- Add New Employee --");
        try {
            int id         = readInt   ("  Enter Employee ID            : ");
            String name    = readString("  Enter Employee Name          : ");
            int age        = readInt   ("  Enter Employee Age           : ");
            String dept    = readString("  Enter Employee Department    : ");
            String address = readString("  Enter Employee Address       : ");
            String contact = readString("  Enter Employee Contact Number: ");
            boolean saved = service.addEmployee(id, name, age, dept, address, contact);
            if (saved) {
                System.out.println("\n  Employee Details Saved Successfully!");
            }
        } catch (Exception e) {
            System.out.println("\n  [ERROR] Something went wrong: " + e.getMessage());
        }
        continuePrompt();
    }
    private static void viewAllEmployees() {
        System.out.println("\n  -- All Employee Records --");
        service.viewAllEmployees();
        continuePrompt();
    }
    private static void searchByName() {
        System.out.println("\n  -- Search Employee by Name --");
        String name = readString("  Enter Employee Name to search : ");
        List<Employee> results = service.searchByName(name);
        if (results.isEmpty()) {
            System.out.println("\n  [INFO] No employees found with name containing: " + name);
        } else {
            System.out.println("\n  Found " + results.size() + " record(s):");
            System.out.println();
            for (int i = 0; i < results.size(); i++) {
                results.get(i).displayEmployee();
                System.out.println();
            }
        }
        continuePrompt();
    }
    private static void searchById() {
        System.out.println("\n  -- Search Employee by ID --");
        int id = readInt("  Enter Employee ID to search : ");
        Employee emp = service.searchById(id);
        if (emp == null) {
            System.out.println("\n  [INFO] No employee found with ID: " + id);
        } else {
            System.out.println("\n  Employee found:");
            System.out.println();
            emp.displayEmployee();
        }
        continuePrompt();
    }
    private static void updateEmployee() {
        System.out.println("\n  -- Update Employee Details --");
        int id = readInt("  Enter Employee ID to update : ");
        Employee existing = service.searchById(id);
        if (existing == null) {
            System.out.println("\n  [ERROR] Employee with ID " + id + " not found.");
            continuePrompt();
            return;
        }
        System.out.println("\n  Current details:");
        existing.displayEmployee();
        System.out.println("\n  Press Enter to skip a field and keep the current value.");
        try {
            String newName    = readString("  New Name           [" + existing.getEmployeeName()          + "] : ");
            String ageInput   = readString("  New Age            [" + existing.getEmployeeAge()           + "] : ");
            String newDept    = readString("  New Department     [" + existing.getEmployeeDepartment()    + "] : ");
            String newAddress = readString("  New Address        [" + existing.getEmployeeAddress()       + "] : ");
            String newContact = readString("  New Contact Number [" + existing.getEmployeeContactNumber() + "] : ");
            int newAge = 0;
            if (!ageInput.isBlank()) {
                try {
                    newAge = Integer.parseInt(ageInput.trim());
                } catch (NumberFormatException e) {
                    System.out.println("\n  [ERROR] Age must be a valid number. Update cancelled.");
                    continuePrompt();
                    return;
                }
            }
            boolean updated = service.updateEmployee(id, newName, newAge, newDept, newAddress, newContact);
            if (updated) {
                System.out.println("\n  Employee Details Updated Successfully!");
            }
        } catch (Exception e) {
            System.out.println("\n  [ERROR] Something went wrong: " + e.getMessage());
        }
        continuePrompt();
    }
    private static void deleteEmployee() {
        System.out.println("\n  -- Delete Employee --");
        System.out.println("  Delete by:  1. Employee ID     2. Employee Name");
        int option = readInt("  Enter option (1 or 2) : ");
        if (option == 1) {
            int id = readInt("  Enter Employee ID to delete : ");
            boolean deleted = service.deleteById(id);
            if (deleted) {
                System.out.println("\n  Employee with ID " + id + " deleted successfully!");
            }
        } else if (option == 2) {
            String name = readString("  Enter Employee Name to delete : ");
            int count = service.deleteByName(name);
            if (count == 0) {
                System.out.println("\n  [INFO] No employee found with name: " + name);
            } else {
                System.out.println("\n  " + count + " employee record(s) deleted successfully!");
            }
        } else {
            System.out.println("\n  [WARNING] Invalid option. Please enter 1 or 2.");
        }
        continuePrompt();
    }
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = sc.nextInt();
                sc.nextLine();
                return value;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("  [ERROR] Invalid input. Please enter a whole number.");
            }
        }
    }
    private static String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
    private static void continuePrompt() {
        System.out.println();
        System.out.print("  Do you want to continue? (Y / N) : ");
        String input = sc.nextLine().trim();

        if (input.equalsIgnoreCase("N")) {
            System.out.println("\n  Thank you for using Management System. Goodbye!");
            sc.close();
            System.exit(0);
        }

        System.out.println();
    }
}