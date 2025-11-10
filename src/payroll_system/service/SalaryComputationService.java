package payroll_system.service;
import payroll_system.dao.*;
import payroll_system.model.*;
import java.util.*;

public class SalaryComputationService {
    private EmployeeDAO employeeDAO;
    private Scanner scanner;
    
    public SalaryComputationService() {
        this.employeeDAO = new EmployeeDAO();
        this.scanner = new Scanner(System.in);
    }
    
    // COMPUTE SALARY WITH MANUAL DEDUCTION INPUT
    public SalaryComputation computeSalaryWithManualDeductions(int empId, double daysWorked) {
        SalaryComputation computation = new SalaryComputation();
        
        try {
            // Get employee information
            Employee employee = employeeDAO.getEmployeeById(empId);
            if (employee == null) {
                System.out.println("Employee not found!");
                return null;
            }
            
            // Set basic employee info
            computation.setEmployee(employee);
            computation.setDaysWorked(daysWorked);
            
            // Calculate gross salary
            double ratePerDay = employee.getRatePerDay();
            double grossSalary = ratePerDay * daysWorked;
            computation.setGrossSalary(grossSalary);
            
            // MANUALLY INPUT DEDUCTIONS
            manuallyInputDeductions(computation, grossSalary);
            
            // Calculate net pay
            double netPay = grossSalary - computation.getTotalDeductions();
            computation.setNetPay(netPay);
            
        } catch (Exception e) {
            System.err.println("Error computing salary: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        
        return computation;
    }
    
    private void manuallyInputDeductions(SalaryComputation computation, double grossSalary) {
        System.out.println("\n--- ENTER DEDUCTION AMOUNTS ---");
        System.out.printf("Gross Salary: ₱%,.2f%n", grossSalary);
        System.out.println();
        
        // TAX
        System.out.printf("Enter Tax Amount (3%% would be ₱%,.2f): ₱", grossSalary * 0.03);
        double tax = scanner.nextDouble();
        scanner.nextLine(); // Clear buffer
        computation.setTax(tax);
        
        // PAG-IBIG
        System.out.print("Enter PAG-IBIG Amount: ₱");
        double pagibig = scanner.nextDouble();
        scanner.nextLine(); // Clear buffer
        computation.setPagibig(pagibig);
        
        // SSS
        System.out.print("Enter SSS Amount: ₱");
        double sss = scanner.nextDouble();
        scanner.nextLine(); // Clear buffer
        computation.setSss(sss);
        
        // LOAN
        System.out.print("Enter LOAN Amount: ₱");
        double loan = scanner.nextDouble();
        scanner.nextLine(); // Clear buffer
        computation.setLoan(loan);
        
        // OTHER DEDUCTIONS
        System.out.print("Enter OTHER DEDUCTIONS Amount: ₱");
        double other = scanner.nextDouble();
        scanner.nextLine(); // Clear buffer
        computation.setOtherDeductions(other);
        
        // Calculate total deductions
        double totalDeductions = tax + pagibig + sss + loan + other;
        computation.setTotalDeductions(totalDeductions);
        
        System.out.printf("%nTotal Deductions: ₱%,.2f%n", totalDeductions);
    }
}