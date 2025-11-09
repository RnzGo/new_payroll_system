package payroll_system.model;

public class Employee {
    private int empId;
    private String empName;
    private String empPosition;
    private double ratePerDay;
    private String empAddress;
    private String contactNumber;
    private String empStatus;
    
    //Constructor of Employee
    public Employee(){//For database operation}
    }
    
    public Employee(String empName,String empPosition, double ratePerDay){
        this.empName = empName;
        this.empPosition = empPosition;
        this.ratePerDay = ratePerDay;
        this.empStatus = "Active";
    }
    
    // Generate Getters and Setters

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpPosition() {
        return empPosition;
    }

    public void setEmpPosition(String empPosition) {
        this.empPosition = empPosition;
    }

    public double getRatePerDay() {
        return ratePerDay;
    }

    public void setRatePerDay(double ratePerDay) {
        this.ratePerDay = ratePerDay;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
    } 
}
