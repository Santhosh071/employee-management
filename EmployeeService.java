import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class EmployeeService {
    private HashMap<Integer, Employee> employeeMap = new HashMap<>();
    private static final String FILE_NAME = "employees.csv";
    private static final String CSV_HEADER = "Employee ID,Name,Age,Department,Address,Contact Number";
    public EmployeeService() {
        loadFromCSV();
    }
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
        saveToCSV();
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
            entry.getValue().displayEmployee();
            System.out.println();
        }
    }
    public List<Employee> searchByName(String name) {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employeeMap.values()) {
            if (emp.getEmployeeName().toLowerCase().contains(name.trim().toLowerCase())) {
                result.add(emp);
            }
        }
        return result;
    }
    public Employee searchById(int id) {
        return employeeMap.get(id);
    }
    public boolean updateEmployee(int id, String newName, int newAge,
                                  String newDept, String newAddress, String newContact) {

        Employee emp = employeeMap.get(id);
        if (emp == null) {
            System.out.println("\n  [ERROR] Employee with ID " + id + " not found.");
            return false;
        }
        if (!newName.isBlank())    emp.setEmployeeName(newName);
        if (newAge > 0) {
            if (newAge < 18 || newAge > 65) {
                System.out.println("\n  [ERROR] Age must be between 18 and 65.");
                return false;
            }
            emp.setEmployeeAge(newAge);
        }
        if (!newDept.isBlank())    emp.setEmployeeDepartment(newDept);
        if (!newAddress.isBlank()) emp.setEmployeeAddress(newAddress);
        if (!newContact.isBlank()) {
            if (!newContact.matches("\\d{10}")) {
                System.out.println("\n  [ERROR] Contact number must be exactly 10 digits.");
                return false;
            }
            emp.setEmployeeContactNumber(newContact);
        }
        saveToCSV();
        return true;
    }
    public boolean deleteById(int id) {
        if (!employeeMap.containsKey(id)) {
            System.out.println("\n  [ERROR] Employee with ID " + id + " not found.");
            return false;
        }
        employeeMap.remove(id);
        saveToCSV();
        return true;
    }
    public int deleteByName(String name) {
        List<Integer> idsToRemove = new ArrayList<>();
        for (Map.Entry<Integer, Employee> entry : employeeMap.entrySet()) {
            if (entry.getValue().getEmployeeName().equalsIgnoreCase(name.trim())) {
                idsToRemove.add(entry.getKey());
            }
        }
        for (int id : idsToRemove) {
            employeeMap.remove(id);
        }
        if (!idsToRemove.isEmpty()) saveToCSV();
        return idsToRemove.size();
    }
    public int getTotalEmployees() {
        return employeeMap.size();
    }
    private void saveToCSV() {
        try {
            FileWriter writer = new FileWriter(FILE_NAME, false);
            writer.write(CSV_HEADER + "\n");
            for (Employee emp : employeeMap.values()) {
                writer.write(buildCSVLine(emp) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("\n  [WARNING] Could not save data: " + e.getMessage());
        }
    }
    private void loadFromCSV() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (line.trim().isEmpty()) continue;
                Employee emp = parseCSVLine(line);
                if (emp != null) {
                    employeeMap.put(emp.getEmployeeId(), emp);
                }
            }
            reader.close();

        } catch (IOException e) {
        }
    }
    private String buildCSVLine(Employee emp) {
        return csvField(String.valueOf(emp.getEmployeeId()))   + "," +
               csvField(emp.getEmployeeName())                 + "," +
               csvField(String.valueOf(emp.getEmployeeAge()))  + "," +
               csvField(emp.getEmployeeDepartment())           + "," +
               csvField(emp.getEmployeeAddress())              + "," +
               csvField(emp.getEmployeeContactNumber());
    }
    private String csvField(String value) {
        if (value.contains(",")) {
            return "\"" + value + "\"";
        }
        return value;
    }
    private Employee parseCSVLine(String line) {
        try {
            String[] parts = splitCSV(line);
            if (parts.length < 6) return null;
            int    id      = Integer.parseInt(parts[0].trim());
            String name    = parts[1].trim();
            int    age     = Integer.parseInt(parts[2].trim());
            String dept    = parts[3].trim();
            String address = parts[4].trim();
            String contact = parts[5].trim();
            return new Employee(id, name, age, dept, address, contact);
        } catch (Exception e) {
            return null;
        }
    }
    private String[] splitCSV(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                insideQuotes = !insideQuotes;
            } else if (c == ',' && !insideQuotes) {
                fields.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString());
        return fields.toArray(new String[0]);
    }
}