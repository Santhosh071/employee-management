public class Employee {

    private int employeeId;
    private String employeeName;
    private int employeeAge;
    private String department;
    private String address;
    private String contactNumber;

    public Employee(int employeeId, String employeeName, int employeeAge,
                    String department, String address, String contactNumber) {
        this.employeeId    = employeeId;
        this.employeeName  = employeeName;
        this.employeeAge   = employeeAge;
        this.department    = department;
        this.address       = address;
        this.contactNumber = contactNumber;
    }

    // Getter
    public int    getEmployeeId()            { return employeeId;     }
    public String getEmployeeName()          { return employeeName;   }
    public int    getEmployeeAge()           { return employeeAge;    }
    public String getEmployeeDepartment()    { return department;     }
    public String getEmployeeAddress()       { return address;        }
    public String getEmployeeContactNumber() { return contactNumber;  }

    // Setters
    public void setEmployeeName(String employeeName)          { this.employeeName  = employeeName;  }
    public void setEmployeeAge(int employeeAge)               { this.employeeAge   = employeeAge;   }
    public void setEmployeeDepartment(String department)      { this.department    = department;    }
    public void setEmployeeAddress(String address)            { this.address       = address;       }
    public void setEmployeeContactNumber(String contactNumber){ this.contactNumber = contactNumber; }
    public void displayEmployee() {
        System.out.println("--------------------------------------------------");
        System.out.println("  Employee ID      : " + employeeId);
        System.out.println("  Name             : " + employeeName);
        System.out.println("  Age              : " + employeeAge);
        System.out.println("  Department       : " + department);
        System.out.println("  Address          : " + address);
        System.out.println("  Contact Number   : " + contactNumber);
        System.out.println("--------------------------------------------------");
    }

    public String toFileString() {
        return employeeId + "|" + employeeName + "|" + employeeAge + "|"
             + department + "|" + address + "|" + contactNumber;
    }

    public static Employee fromFileString(String line) {
        String[] parts = line.split("\\|");
        int    id      = Integer.parseInt(parts[0].trim());
        String name    = parts[1].trim();
        int    age     = Integer.parseInt(parts[2].trim());
        String dept    = parts[3].trim();
        String address = parts[4].trim();
        String contact = parts[5].trim();

        return new Employee(id, name, age, dept, address, contact);
    }
}