package payroll_system.service;

import payroll_system.model.SalaryComputation;
import java.text.DecimalFormat;

public class SalaryComputationPrinter {
    
    public void printSalaryComputation(SalaryComputation computation) {
        if (computation == null) {
            System.out.println("No computation data available!");
            return;
        }
        
        DecimalFormat df = new DecimalFormat("#,##0.00");
        
        System.out.println("\n");
        System.out.println("========================================================");
        System.out.println("           BARANGAY SAN ROQUE, CAINTA, RIZAL           ");
        System.out.println("========================================================");
        System.out.println("               Payroll Management System               ");
        System.out.println("========================================================");
        System.out.println();
        System.out.println("           COMPUTATION OF SALARY");
        System.out.println();
        System.out.println("========================================================");
        
        // Employee Information
        System.out.printf("Employee ID: %-40s%n", computation.getEmployee().getEmpId());
        System.out.printf("Employee Name: %-37s%n", computation.getEmployee().getEmpName());
        System.out.printf("Employee Position: %-33s%n", computation.getEmployee().getEmpPosition());
        System.out.printf("Rate Per Day: ₱%-36s%n", df.format(computation.getEmployee().getRatePerDay()));
        System.out.printf("Number of Days Worked: %-28s%n", computation.getDaysWorked());
        System.out.println();
        
        // Earnings and Deductions Table
        System.out.println("+--------------------------+---------------------+");
        System.out.println("| EARNINGS & DEDUCTIONS    | AMOUNT              |");
        System.out.println("+--------------------------+---------------------+");
        System.out.printf("| Gross Salary            | ₱%17s |%n", df.format(computation.getGrossSalary()));
        System.out.println("+--------------------------+---------------------+");
        System.out.printf("| Tax                     | ₱%17s |%n", df.format(computation.getTax()));
        System.out.printf("| PAG-IBIG                | ₱%17s |%n", df.format(computation.getPagibig()));
        System.out.printf("| SSS                     | ₱%17s |%n", df.format(computation.getSss()));
        System.out.printf("| LOAN                    | ₱%17s |%n", df.format(computation.getLoan()));
        
        // Show Other Deductions only if > 0
        if (computation.getOtherDeductions() > 0) {
            System.out.printf("| OTHER DEDUCTIONS        | ₱%17s |%n", df.format(computation.getOtherDeductions()));
        }
        
        System.out.println("+--------------------------+---------------------+");
        System.out.printf("| TOTAL DEDUCTIONS        | ₱%17s |%n", df.format(computation.getTotalDeductions()));
        System.out.println("+--------------------------+---------------------+");
        System.out.println();
        
        // Net Pay
        System.out.println("========================================================");
        System.out.printf("               NET PAY: ₱%17s%n", df.format(computation.getNetPay()));
        System.out.println("========================================================");
        System.out.println();
    }
}