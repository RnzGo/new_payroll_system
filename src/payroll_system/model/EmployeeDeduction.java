
package payroll_system.model;

public class EmployeeDeduction {
    private int empDeductionId;
    private int deductionId;
    private int empId;
    private double deductionAmount;
    
    // Additional fields for display (from JOIN queries)
    private String deductionName;
    private String empName;
    
    // Constructors
    public EmployeeDeduction() {}
    
    public EmployeeDeduction(int deductionId, int empId, double deductionAmount) {
        this.deductionId = deductionId;
        this.empId = empId;
        this.deductionAmount = deductionAmount;
    }
    
    // Getters and Setters
    public int getEmpDeductionId() { return empDeductionId; }
    public void setEmpDeductionId(int empDeductionId) { this.empDeductionId = empDeductionId; }
    
    public int getDeductionId() { return deductionId; }
    public void setDeductionId(int deductionId) { this.deductionId = deductionId; }
    
    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }
    
    public double getDeductionAmount() { return deductionAmount; }
    public void setDeductionAmount(double deductionAmount) { this.deductionAmount = deductionAmount; }
    
    public String getDeductionName() { return deductionName; }
    public void setDeductionName(String deductionName) { this.deductionName = deductionName; }
    
    public String getEmpName() { return empName; }
    public void setEmpName(String empName) { this.empName = empName; }
    
    @Override
    public String toString() {
        return "EmployeeDeduction{" +
                "empDeductionId=" + empDeductionId +
                ", deductionId=" + deductionId +
                ", empId=" + empId +
                ", deductionAmount=" + deductionAmount +
                '}';
    }
}
