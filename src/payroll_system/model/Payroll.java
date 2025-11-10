package payroll_system.model;

import java.time.LocalDate;

public class Payroll {
    private int payrollId;
    private int empId;
    private int periodId;
    private int daysWorkedId;
    private double grossPay;
    private double totalDeductions;
    private double netPay;
    
    // Change to LocalDate
    private String empName;
    private String empPosition;
    private LocalDate periodStart;  
    private LocalDate periodEnd;   
    
    // Constructors
    public Payroll() {}
    
    public Payroll(int empId, int periodId, int daysWorkedId, double grossPay, double totalDeductions, double netPay) {
        this.empId = empId;
        this.periodId = periodId;
        this.daysWorkedId = daysWorkedId;
        this.grossPay = grossPay;
        this.totalDeductions = totalDeductions;
        this.netPay = netPay;
    }
    
    // Getters and Setters - Updated for LocalDate
    public int getPayrollId() { return payrollId; }
    public void setPayrollId(int payrollId) { this.payrollId = payrollId; }
    
    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }
    
    public int getPeriodId() { return periodId; }
    public void setPeriodId(int periodId) { this.periodId = periodId; }
    
    public int getDaysWorkedId() { return daysWorkedId; }
    public void setDaysWorkedId(int daysWorkedId) { this.daysWorkedId = daysWorkedId; }
    
    public double getGrossPay() { return grossPay; }
    public void setGrossPay(double grossPay) { this.grossPay = grossPay; }
    
    public double getTotalDeductions() { return totalDeductions; }
    public void setTotalDeductions(double totalDeductions) { this.totalDeductions = totalDeductions; }
    
    public double getNetPay() { return netPay; }
    public void setNetPay(double netPay) { this.netPay = netPay; }
    
    public String getEmpName() { return empName; }
    public void setEmpName(String empName) { this.empName = empName; }
    
    public String getEmpPosition() { return empPosition; }
    public void setEmpPosition(String empPosition) { this.empPosition = empPosition; }
    
    public LocalDate getPeriodStart() { return periodStart; }
    public void setPeriodStart(LocalDate periodStart) { this.periodStart = periodStart; }
    
    public LocalDate getPeriodEnd() { return periodEnd; }
    public void setPeriodEnd(LocalDate periodEnd) { this.periodEnd = periodEnd; }
}