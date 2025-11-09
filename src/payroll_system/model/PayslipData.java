package payroll_system.model;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PayslipData {
    private Employee employee;
    private Payroll payroll;
    private Date periodStart;
    private Date periodEnd;
    private double daysWorked;
    
    // Earnings
    private double ratePerDay;
    private double grossPay;
    
    // Deductions
    private double tax;
    private double pagibig;
    private double sss;
    private double philhealth;
    private double absences;
    private double totalDeductions;
    private double netPay;
    
    // Getters and Setters
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    
    public Payroll getPayroll() { return payroll; }
    public void setPayroll(Payroll payroll) { this.payroll = payroll; }
    
    public Date getPeriodStart() { return periodStart; }
    public void setPeriodStart(Date periodStart) { this.periodStart = periodStart; }
    
    public Date getPeriodEnd() { return periodEnd; }
    public void setPeriodEnd(Date periodEnd) { this.periodEnd = periodEnd; }
    
    public double getDaysWorked() { return daysWorked; }
    public void setDaysWorked(double daysWorked) { this.daysWorked = daysWorked; }
    
    public double getRatePerDay() { return ratePerDay; }
    public void setRatePerDay(double ratePerDay) { this.ratePerDay = ratePerDay; }
    
    public double getGrossPay() { return grossPay; }
    public void setGrossPay(double grossPay) { this.grossPay = grossPay; }
    
    public double getTax() { return tax; }
    public void setTax(double tax) { this.tax = tax; }
    
    public double getPagibig() { return pagibig; }
    public void setPagibig(double pagibig) { this.pagibig = pagibig; }
    
    public double getSss() { return sss; }
    public void setSss(double sss) { this.sss = sss; }
    
    public double getPhilhealth() { return philhealth; }
    public void setPhilhealth(double philhealth) { this.philhealth = philhealth; }
    
    public double getAbsences() { return absences; }
    public void setAbsences(double absences) { this.absences = absences; }
    
    public double getTotalDeductions() { return totalDeductions; }
    public void setTotalDeductions(double totalDeductions) { this.totalDeductions = totalDeductions; }
    
    public double getNetPay() { return netPay; }
    public void setNetPay(double netPay) { this.netPay = netPay; }
    
    private double employeeSpecificDeductions;
    private List<EmployeeDeduction> employeeDeductionList = new ArrayList<>();
    
    // Getters and setters
    public double getEmployeeSpecificDeductions() { return employeeSpecificDeductions; }
    public void setEmployeeSpecificDeductions(double employeeSpecificDeductions) { 
        this.employeeSpecificDeductions = employeeSpecificDeductions; 
    }
    
    public List<EmployeeDeduction> getEmployeeDeductionList() { return employeeDeductionList; }
    public void setEmployeeDeductionList(List<EmployeeDeduction> employeeDeductionList) { 
        this.employeeDeductionList = employeeDeductionList; 
    }
}


