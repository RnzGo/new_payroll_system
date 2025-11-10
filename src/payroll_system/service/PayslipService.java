 
package payroll_system.service;


import payroll_system.dao.*;
/*import payroll_system.model.*;
import java.util.*;
import java.sql.Date;

public class PayslipService {
    private EmployeeDAO employeeDAO;
    private PayrollDAO payrollDAO;
    private AttendanceDAO attendanceDAO;
    private PayrollPeriodDAO payrollPeriodDAO;
    private DeductionsDAO deductionsDAO;
    
    public PayslipService() {
        this.employeeDAO = new EmployeeDAO();
        this.payrollDAO = new PayrollDAO();
        this.attendanceDAO = new AttendanceDAO();
        this.payrollPeriodDAO = new PayrollPeriodDAO();
        this.deductionsDAO = new DeductionsDAO();
    }
    
    // ENHANCED PAYSLIP GENERATION WITH REAL DEDUCTIONS
    public PayslipData generatePayslip(int empId, int periodId) {
        PayslipData payslip = new PayslipData();
        
        try {
            // Get employee basic info
            Employee employee = employeeDAO.getEmployeeById(empId);
            if (employee == null) {
                System.out.println("Employee not found!");
                return null;
            }
            
            // Get payroll record
            List<Payroll> payrollRecords = payrollDAO.getPayrollByPeriod(periodId);
            Payroll payroll = payrollRecords.stream()
                .filter(p -> p.getEmpId() == empId)
                .findFirst()
                .orElse(null);
                
            if (payroll == null) {
                System.out.println("No payroll record found for this period!");
                return null;
            }
            
            // Get period info
            List<String[]> periods = payrollPeriodDAO.getAllPayrollPeriods();
            String[] periodInfo = periods.stream()
                .filter(p -> Integer.parseInt(p[0]) == periodId)
                .findFirst()
                .orElse(new String[]{"", "Unknown", "Unknown"});
            
            // Calculate days worked
            double daysWorked = attendanceDAO.getDaysWorked(payroll.getDaysWorkedId());
            
            // Set payslip data
            payslip.setEmployee(employee);
            payslip.setPayroll(payroll);
            payslip.setPeriodStart(Date.valueOf(periodInfo[1]));
            payslip.setPeriodEnd(Date.valueOf(periodInfo[2]));
            payslip.setDaysWorked(daysWorked);
            
            // Calculate earnings and REAL deductions from database
            calculateEarningsAndRealDeductions(payslip);
            
        } catch (Exception e) {
            System.err.println("Error generating payslip: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        
        return payslip;
    }
    
    private void calculateEarningsAndRealDeductions(PayslipData payslip) {
        Employee employee = payslip.getEmployee();
        double daysWorked = payslip.getDaysWorked();
        
        // EARNINGS
        double ratePerDay = employee.getRatePerDay();
        double grossPay = ratePerDay * daysWorked;
        
        // GET REAL DEDUCTIONS FROM DATABASE
        List<Deductions> allDeductions = deductionsDAO.getAllDeductions();
        Map<String, Double> deductionAmounts = new HashMap<>();
        
        double totalDeductions = 0;
        
        for (Deductions deduction : allDeductions) {
            double amount = 0;
            
            switch (deduction.getDeductionName().toLowerCase()) {
                case "tax":
                    amount = grossPay * (deduction.getDeductionRate() / 100);
                    break;
                case "sss":
                    amount = grossPay * (deduction.getDeductionRate() / 100);
                    break;
                case "pagibig":
                case "pag-ibig":
                    amount = deduction.getDeductionRate(); // Fixed amount
                    break;
                case "philhealth":
                    amount = grossPay * (deduction.getDeductionRate() / 100);
                    break;
                default:
                    amount = deduction.getDeductionRate(); // Fixed amount or custom calculation
            }
            
            deductionAmounts.put(deduction.getDeductionName(), amount);
            totalDeductions += amount;
        }
        
        double netPay = grossPay - totalDeductions;
        
        // Set calculated values
        payslip.setRatePerDay(ratePerDay);
        payslip.setGrossPay(grossPay);
        payslip.setTax(deductionAmounts.getOrDefault("Tax", 0.0));
        payslip.setPagibig(deductionAmounts.getOrDefault("PagIBIG", 0.0));
        payslip.setSss(deductionAmounts.getOrDefault("SSS", 0.0));
        payslip.setPhilhealth(deductionAmounts.getOrDefault("PhilHealth", 0.0));
        payslip.setAbsences(0.0); // You can calculate this based on attendance
        payslip.setTotalDeductions(totalDeductions);
        payslip.setNetPay(netPay);
        
        // Store all deductions for detailed display
        payslip.setAllDeductions(deductionAmounts);
    }
}
*/