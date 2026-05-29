import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeService {
    private HashMap<Integer, Employee> employeeMap = new HashMap<Integer, Employee>();
    public boolean addEmployee(int id, String name, int age,
                               String department, String address,
                               String contactNumber) {
        if (employeeMap.containsKey(id)) {
            System.out.println("\n  [ERROR] Employee ID " + id + " already exists. Please use a unique ID.");
            return false;
        }
        if (age < 18 || age > 65) {
            System.out.println("\n  [ERROR] Age must be between 18 and 65.");
            return false;
        }
        if (!contactNumber.matches("\\d{10}")) {
            System.out.println("\n  [ERROR] Contact number must be exactly 10 digits.");
            return false;
        }
        Employee emp = new Employee(id, name, age, department, address, contactNumber);
        employeeMap.put(id, emp);
        return true;
    }
    public void viewAllEmployees() {
        if (employeeMap.isEmpty()) {
            System.out.println("\n  [INFO] No employee records found.");
            return;
        }
        System.out.println("\n  Total Employees : " + employeeMap.size());
        System.out.println();
        for (Map.Entry<Integer, Employee> entry : employeeMap.entrySet()) {
            Employee emp = entry.getValue();
            emp.displayEmployee();
            System.out.println();
        }
    }
    public List<Employee> searchByName(String name) {
        List<Employee> result = new ArrayList<Employee>();
        for (Employee emp : employeeMap.values()) {
            String empNameLower    = emp.getEmployeeName().toLowerCase();
            String searchNameLower = name.trim().toLowerCase();
            if (empNameLower.contains(searchNameLower)) {
                result.add(emp);
            }
        }
        return result;
    }
    public Employee searchById(int id) {
        return employeeMap.get(id);
    }
    public boolean updateEmployee(int id, String newName, int newAge,
                                  String newDept, String newAddress,
                                  String newContact) {
        Employee emp = employeeMap.get(id);
        if (emp == null) {
            System.out.println("\n  [ERROR] Employee with ID " + id + " not found.");
            return false;
        }
        if (!newName.isBlank()) {
            emp.setEmployeeName(newName);
        }
        if (newAge > 0) {
            if (newAge < 18 || newAge > 65) {
                System.out.println("\n  [ERROR] Age must be between 18 and 65.");
                return false;
            }
            emp.setEmployeeAge(newAge);
        }
        if (!newDept.isBlank()) {
            emp.setEmployeeDepartment(newDept);
        }
        if (!newAddress.isBlank()) {
            emp.setEmployeeAddress(newAddress);
        }
        if (!newContact.isBlank()) {
            if (!newContact.matches("\\d{10}")) {
                System.out.println("\n  [ERROR] Contact number must be exactly 10 digits.");
                return false;
            }
            emp.setEmployeeContactNumber(newContact);
        }
        return true;
    }
    public boolean deleteById(int id) {
        if (!employeeMap.containsKey(id)) {
            System.out.println("\n  [ERROR] Employee with ID " + id + " not found.");
            return false;
        }
        employeeMap.remove(id);
        return true;
    }
    public int deleteByName(String name) {
        List<Integer> idsToRemove = new ArrayList<Integer>();
        for (Map.Entry<Integer, Employee> entry : employeeMap.entrySet()) {
            String empName    = entry.getValue().getEmployeeName();
            String searchName = name.trim();
            if (empName.equalsIgnoreCase(searchName)) {
                idsToRemove.add(entry.getKey());
            }
        }
        for (int id : idsToRemove) {
            employeeMap.remove(id);
        }
        return idsToRemove.size();
    }
    public int getTotalEmployees() {
        return employeeMap.size();
    }
}