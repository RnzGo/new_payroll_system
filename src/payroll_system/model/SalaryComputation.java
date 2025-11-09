
package payroll_system.model;

public class SalaryComputation {
    private Employee employee;
    private double daysWorked;
    private double grossSalary;
    private double tax;
    private double pagibig;
    private double sss;
    private double loan;
    private double otherDeductions;  
    private double totalDeductions;
    private double netPay;
    
    // Getters and Setters
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    
    public double getDaysWorked() { return daysWorked; }
    public void setDaysWorked(double daysWorked) { this.daysWorked = daysWorked; }
    
    public double getGrossSalary() { return grossSalary; }
    public void setGrossSalary(double grossSalary) { this.grossSalary = grossSalary; }
    
    public double getTax() { return tax; }
    public void setTax(double tax) { this.tax = tax; }
    
    public double getPagibig() { return pagibig; }
    public void setPagibig(double pagibig) { this.pagibig = pagibig; }
    
    public double getSss() { return sss; }
    public void setSss(double sss) { this.sss = sss; }
    
    public double getLoan() { return loan; }
    public void setLoan(double loan) { this.loan = loan; }
    
    public double getOtherDeductions() { return otherDeductions; }
    public void setOtherDeductions(double otherDeductions) { this.otherDeductions = otherDeductions; }
    
    public double getTotalDeductions() { return totalDeductions; }
    public void setTotalDeductions(double totalDeductions) { this.totalDeductions = totalDeductions; }
    
    public double getNetPay() { return netPay; }
    public void setNetPay(double netPay) { this.netPay = netPay; }
}