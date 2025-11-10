package payroll_system.service;

import payroll_system.model.Attendance;
import payroll_system.dao.AttendanceDAO;
import payroll_system.dao.EmployeeDAO;
import payroll_system.model.Employee;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import payroll_system.model.SalaryComputation;
import payroll_system.dao.PayrollDAO;
import payroll_system.model.Payroll;
import payroll_system.dao.PayrollPeriodDAO;

public class PayrollService {
    
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private Scanner scanner = new Scanner(System.in);
    
    public void start() {
        while (true) {
            showMainMenu();
            int choice = getIntInput("Choose an option: ");
            
            switch (choice) {
                case 1 -> employeeManagement();
                case 2 -> payrollManagement();
                case 3 -> computeSalary();
                case 4 -> {
                    System.out.println("Exiting Program...");
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }
    
    private void showMainMenu() {
        System.out.println("\n=== PAYROLL MANAGEMENT SYSTEM ===");
        System.out.println("1. Employee Management");
        System.out.println("2. Payroll Management");
        System.out.println("3. Compute Salary & Payslip");
        System.out.println("4. Exit");
        System.out.println("================================");
    }
    
    // ========== EMPLOYEE MANAGEMENT GROUP ==========
    private void employeeManagement() {
        while (true) {
            System.out.println("\n=== EMPLOYEE MANAGEMENT ===");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Employee Information");
            System.out.println("4. Archive Employee");
            System.out.println("5. View Archived Employees");
            System.out.println("6. Record Attendance");
            System.out.println("7. View Employee Attendance");
            System.out.println("8. Delete Attendance Record");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Choose an option: ");
            
            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewAllEmployees();
                case 3 -> updateEmployee();
                case 4 -> archiveEmployee();
                case 5 -> viewArchivedEmployees();
                case 6 -> recordAttendance();
                case 7 -> viewEmployeeAttendance();
                case 8 -> deleteAttendanceRecord();
                case 0 -> { return; }
                default -> System.out.println("Invalid option");
            }
        }
    }
    
    private void addEmployee() {
        System.out.println("\n--- ADD NEW EMPLOYEE ---");
        
        System.out.print("Full Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Position: ");
        String position = scanner.nextLine();
        
        double rate = getDoubleInput("Rate Per Day: ");
        
        System.out.print("Address: ");
        String address = scanner.nextLine();
        
        System.out.print("Contact Number: ");
        String contact = scanner.nextLine();
        
        Employee employee = new Employee();
        employee.setEmpName(name);
        employee.setEmpPosition(position);
        employee.setRatePerDay(rate);
        employee.setEmpAddress(address);
        employee.setContactNumber(contact);
        employee.setEmpStatus("Active");
        
        boolean success = employeeDAO.addEmployee(employee);
        System.out.println(success ? "Employee successfully added!" : "Failed to add employee!");
    }
    
    private void viewAllEmployees() {
        System.out.println("\n--- ALL EMPLOYEES ---");
        List<Employee> employees = employeeDAO.getAllEmployees();
        
        if (employees.isEmpty()) {
            System.out.println("No Employees Found!");
            return;
        }
        
        System.out.println("ID | Name | Position | Rate Per Day | Status");
        System.out.println("--------------------------------------------");
        
        for (Employee emp : employees) {
            System.out.printf("%d | %s | %s | P%.2f | %s\n",
                emp.getEmpId(), emp.getEmpName(), emp.getEmpPosition(),
                emp.getRatePerDay(), emp.getEmpStatus());
        }
    }
    
    private void updateEmployee() {
        System.out.println("\n--- UPDATE EMPLOYEE INFORMATION ---");
        viewAllEmployees();
        
        int empId = getIntInput("Enter Employee ID: ");
        Employee employee = employeeDAO.getEmployeeById(empId);
        
        if (employee == null) {
            System.out.println("Employee with ID " + empId + " Not Found!");
            return;
        }
        
        if ("Inactive".equals(employee.getEmpStatus())) {
            System.out.println("This Employee Is Already Archived!");
            return;
        }
        
        System.out.println("Current Employee Details:");
        System.out.println("Name: " + employee.getEmpName());
        System.out.println("Position: " + employee.getEmpPosition());
        System.out.println("Rate Per Day: " + employee.getRatePerDay());
        System.out.println("Address: " + employee.getEmpAddress());
        System.out.println("Contact: " + employee.getContactNumber());
        
        System.out.println("Enter new details (press Enter to keep current value):");
        
        System.out.print("New Name [" + employee.getEmpName() + "]: ");
        String newName = scanner.nextLine();
        if (!newName.trim().isEmpty()) {
            employee.setEmpName(newName);
        }
        
        System.out.print("New Position [" + employee.getEmpPosition() + "]: ");
        String newPosition = scanner.nextLine();
        if (!newPosition.trim().isEmpty()) {
            employee.setEmpPosition(newPosition);
        }
        
        System.out.print("New Rate Per Day [P" + employee.getRatePerDay() + "]: ");
        String newRate = scanner.nextLine();
        if (!newRate.trim().isEmpty()) {
            try {
                double rate = Double.parseDouble(newRate);
                employee.setRatePerDay(rate);
            } catch (NumberFormatException e) {
                System.out.println("Invalid rate format. Keeping current rate.");
            }
        }
        
        System.out.print("New Address [" + employee.getEmpAddress() + "]: ");
        String address = scanner.nextLine();
        if (!address.trim().isEmpty()) {
            employee.setEmpAddress(address);
        }
        
        System.out.print("New Contact Number [" + employee.getContactNumber() + "]: ");
        String contact = scanner.nextLine();
        if (!contact.trim().isEmpty()) {
            employee.setContactNumber(contact);
        }
        
        System.out.println("Updated Employee Information:");
        System.out.println("Name: " + employee.getEmpName());
        System.out.println("Position: " + employee.getEmpPosition());
        System.out.println("Rate Per Day: P" + employee.getRatePerDay());
        System.out.println("Address: " + employee.getEmpAddress());
        System.out.println("Contact Number: " + employee.getContactNumber());
        
        System.out.print("Confirm update? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            boolean success = employeeDAO.updateEmployee(employee);
            if (success) {
                System.out.println("Employee Information Successfully Updated!");
            }
        } else {
            System.out.println("Update Cancelled");
        }
    }
    
    private void archiveEmployee() {
        System.out.println("\n--- ARCHIVE EMPLOYEE ---");
        List<Employee> activeEmployee = employeeDAO.getActiveEmployees();
        
        if (activeEmployee.isEmpty()) {
            System.out.println("No Active Employee Found!");
            return;
        }
        
        System.out.println("Active Employees");
        System.out.println("ID\tName\t\tPosition");
        for (Employee emp : activeEmployee) {
            System.out.printf("%d\t%-15s\t%-10s\n",
                emp.getEmpId(), emp.getEmpName(), emp.getEmpPosition());
        }
        
        int empId = getIntInput("Enter Employee ID to archive: ");
        Employee employee = employeeDAO.getEmployeeById(empId);
        
        if (employee == null) {
            System.out.println("Employee Not Found!");
            return;
        }
        
        if ("Inactive".equals(employee.getEmpStatus())) {
            System.out.println("Employee Is Already Archived!");
            return;
        }
        
        System.out.println("Employee to Archive:");
        System.out.println("ID: " + employee.getEmpId());
        System.out.println("Name: " + employee.getEmpName());
        System.out.println("Rate Per Day: " + employee.getRatePerDay());
        
        System.out.print("Are you sure you want to archive this employee? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            boolean success = employeeDAO.archiveEmployee(empId);
            System.out.println(success ? "Employee archived successfully!" : "Failed to archive employee!");
        } else {
            System.out.println("Archiving Cancelled!");
        }
    }
    
    private void viewArchivedEmployees() {
        System.out.println("\n--- ARCHIVED EMPLOYEES ---");
        List<Employee> archivedEmployees = employeeDAO.getArchivedEmployees();
        
        if (archivedEmployees.isEmpty()) {
            System.out.println("No Archived Employees Found!");
            return;
        }
        
        System.out.println("Archived Employees");
        System.out.println("ID\tName\t\tPosition\tRate Per Day\tStatus");
        
        for (Employee emp : archivedEmployees) {
            System.out.printf("%d\t%-15s\t%-10s\tP%-10.2f\t%-8s\n",
                emp.getEmpId(), emp.getEmpName(), emp.getEmpPosition(),
                emp.getRatePerDay(), emp.getEmpStatus());
        }
    }
    
    private void recordAttendance() {
        System.out.println("\n--- RECORD ATTENDANCE ---");
        List<Employee> activeEmployees = employeeDAO.getActiveEmployees();
        
        if (activeEmployees.isEmpty()) {
            System.out.println("No Active Employee Found!");
            return;
        }
        
        System.out.println("Active Employees");
        System.out.println("ID\tName\t\tPosition");
        
        for (Employee emp : activeEmployees) {
            System.out.printf("%d\t%-15s\t%-10s\n",
                emp.getEmpId(), emp.getEmpName(), emp.getEmpPosition());
        }
        
        int empId = getIntInput("Enter Employee ID: ");
        Employee employee = employeeDAO.getEmployeeById(empId);
        
        if (employee == null || "Inactive".equals(employee.getEmpStatus())) {
            System.out.println("Employee Not Found Or Inactive!");
            return;
        }
        
        int daysWorked = getIntInput("Enter Days Worked: ");
        
        if (daysWorked <= 0 || daysWorked > 31) {
            System.out.println("Invalid input. Days worked must be between 1-31");
            return;
        }
        
        System.out.print("Enter Period Start (YYYY-MM-DD): ");
        String startStr = scanner.nextLine();
        LocalDate periodStart = LocalDate.parse(startStr);
        
        System.out.print("Enter Period End (YYYY-MM-DD): ");
        String endStr = scanner.nextLine();
        LocalDate periodEnd = LocalDate.parse(endStr);
        
        if (periodEnd.isBefore(periodStart)) {
            System.out.println("Invalid input! End date must be after start date!");
            return;
        }
        
        Attendance attendance = new Attendance(empId, daysWorked, periodStart, periodEnd);
        
        System.out.println("Attendance for " + employee.getEmpName() + " | " + employee.getEmpPosition());
        System.out.println("Starting Period: " + attendance.getPeriodStart());
        System.out.println("Ending Period: " + attendance.getPeriodEnd());
        System.out.println("Days Worked: " + attendance.getDaysWorked());
        
        System.out.print("Confirm recording? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            AttendanceDAO attendanceDAO = new AttendanceDAO();
            boolean success = attendanceDAO.addAttendance(attendance);
            System.out.println(success ? "Employee's Attendance Recorded Successfully" : "Failed To Record Employee's Attendance!");
        } else {
            System.out.println("Recording Cancelled!");
        }
    }
    
    private void viewEmployeeAttendance() {
        System.out.println("\n--- VIEW EMPLOYEE ATTENDANCE ---");
        int empId = getIntInput("Enter Employee ID: ");
        Employee employee = employeeDAO.getEmployeeById(empId);
        
        if (employee == null) {
            System.out.println("No Employee Found!");
            return;
        }
        
        System.out.println("Employee Name: " + employee.getEmpName() + " | Position: " + employee.getEmpPosition());
        
        List<Attendance> attendanceRecord = new AttendanceDAO().getAllAttendanceOfEmployeeById(empId);
        
        if (attendanceRecord.isEmpty()) {
            System.out.println("No Record Exist!");
            return;
        }
        
        System.out.println("Attendance Record of " + employee.getEmpName() + " | " + employee.getEmpPosition());
        
        for (Attendance att : attendanceRecord) {
            System.out.println("Period: " + att.getPeriodStart() + " to " + att.getPeriodEnd());
            System.out.println("Days Worked: " + att.getDaysWorked());
        }
    }
    
    private void deleteAttendanceRecord() {
        System.out.println("\n--- DELETE ATTENDANCE RECORD ---");
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        
        int empId = getIntInput("Enter Employee ID to view attendance records: ");
        Employee employee = employeeDAO.getEmployeeById(empId);
        
        if (employee == null) {
            System.out.println("Employee With This ID Doesn't Exist!");
            return;
        }
        
        System.out.println("Employee: " + employee.getEmpName());
        List<Attendance> attendanceRecords = attendanceDAO.getAllAttendanceOfEmployeeById(empId);
        
        if (attendanceRecords.isEmpty()) {
            System.out.println("No Attendance Recorded For This Employee");
            return;
        }
        
        System.out.println("Attendance Record of " + employee.getEmpName());
        for (Attendance att : attendanceRecords) {
            System.out.println("Days Worked ID: " + att.getDaysWorkedId());
            System.out.println("Period: " + att.getPeriodStart() + " to " + att.getPeriodEnd());
            System.out.println("Days Worked: " + att.getDaysWorked());
        }
        
        int daysWorkedId = getIntInput("Enter Attendance ID to delete: ");
        
        boolean recordExists = false;
        for (Attendance att : attendanceRecords) {
            if (att.getDaysWorkedId() == daysWorkedId) {
                recordExists = true;
                break;
            }
        }
        
        if (!recordExists) {
            System.out.println("The Record Doesn't Exist For This Employee");
            return;
        }
        
        System.out.println("Record to Delete");
        for (Attendance att : attendanceRecords) {
            if (att.getDaysWorkedId() == daysWorkedId) {
                System.out.println("Employee: " + employee.getEmpName());
                System.out.println("Period: " + att.getPeriodStart() + " to " + att.getPeriodEnd());
                System.out.println("Days Worked: " + att.getDaysWorked());
            }
        }
        
        System.out.print("Are you sure to delete this record? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            boolean success = attendanceDAO.deleteAttendance(daysWorkedId);
            System.out.println(success ? "Attendance Deleted Successfully" : "Deletion Failed");
        } else {
            System.out.println("Deletion Cancelled");
        }
    }
    
    // ========== PAYROLL MANAGEMENT GROUP ==========
    private void payrollManagement() {
        while (true) {
            System.out.println("\n=== PAYROLL MANAGEMENT ===");
            System.out.println("1. View All Payroll Records");
            System.out.println("2. View Payroll by Period");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Choose an option: ");
            
            switch (choice) {
                case 1 -> viewAllPayroll();
                case 2 -> viewPayrollByPeriod();
                case 0 -> { return; }
                default -> System.out.println("Invalid option");
            }
        }
    }
    
    private void viewAllPayroll() {
        System.out.println("\n--- ALL PAYROLL RECORDS ---");
        PayrollDAO payrollDAO = new PayrollDAO();
        payrollDAO.viewAllPayrollWithDetails();
        
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private void viewPayrollByPeriod() {
        System.out.println("\n--- PAYROLL BY PERIOD ---");
        
        // Get available periods
        PayrollPeriodDAO periodDAO = new PayrollPeriodDAO();
        List<String[]> periods = periodDAO.getAllPayrollPeriods();
        
        if (periods.isEmpty()) {
            System.out.println("No payroll periods found!");
            return;
        }
        
        System.out.println("Available Periods:");
        System.out.println("ID | Period Start | Period End | Date Issued");
        System.out.println("---------------------------------------------");
        
        for (String[] period : periods) {
            System.out.printf("%s | %s | %s | %s\n",
                period[0], period[1], period[2], period[3]);
        }
        
        int periodId = getIntInput("Enter Period ID: ");
        
        PayrollDAO payrollDAO = new PayrollDAO();
        List<Payroll> payrollList = payrollDAO.getPayrollByPeriod(periodId);
        
        if (payrollList.isEmpty()) {
            System.out.println("No payroll records found for this period!");
            return;
        }
        
        System.out.println("\nPayroll for Period ID: " + periodId);
        System.out.println("ID | Employee Name | Position | Gross Pay | Deductions | Net Pay");
        System.out.println("----------------------------------------------------------------");
        
        for (Payroll payroll : payrollList) {
            System.out.printf("%d | %s | %s | ₱%.2f | ₱%.2f | ₱%.2f\n",
                payroll.getPayrollId(), payroll.getEmpName(), payroll.getEmpPosition(),
                payroll.getGrossPay(), payroll.getTotalDeductions(), payroll.getNetPay());
        }
    }
    
    // ========== SALARY COMPUTATION & PAYSLIP GROUP ==========
    private void computeSalary() {
        System.out.println("\n=== COMPUTE SALARY & GENERATE PAYSLIP ===");
        
        try {
            List<Employee> employees = employeeDAO.getActiveEmployees();
            System.out.println("Active Employees:");
            System.out.println("ID\tName\t\tPosition");
            System.out.println("----------------------------------------");
            for (Employee emp : employees) {
                System.out.printf("%d\t%-15s\t%-10s\n", 
                    emp.getEmpId(), emp.getEmpName(), emp.getEmpPosition());
            }
            
            int empId = getIntInput("Enter Employee ID: ");
            Employee employee = employeeDAO.getEmployeeById(empId);
            
            if (employee == null) {
                System.out.println("Employee not found!");
                return;
            }
            
            double daysWorked = getDoubleInput("Enter Number of Days Worked: ");
            
            if (daysWorked <= 0) {
                System.out.println("Days worked must be greater than 0!");
                return;
            }
            
            double grossSalary = employee.getRatePerDay() * daysWorked;
            System.out.println("Gross Salary Calculation:");
            System.out.printf("Rate per Day: ₱%,.2f × Days Worked: %.1f = Gross Salary: ₱%,.2f\n", 
                employee.getRatePerDay(), daysWorked, grossSalary);
            
            SalaryComputationService computationService = new SalaryComputationService();
            SalaryComputation computation = computationService.computeSalaryWithManualDeductions(empId, daysWorked);
            
            if (computation != null) {
                SalaryComputationPrinter printer = new SalaryComputationPrinter();
                printer.printSalaryComputation(computation);
                
                System.out.print("Save this computation to payroll? (y/n): ");
                String save = scanner.nextLine();
                
                if (save.equalsIgnoreCase("y")) {
                    saveToPayroll(computation);
                    
                    System.out.print("Would you like to view all payroll records? (y/n): ");
                    String view = scanner.nextLine();
                    if (view.equalsIgnoreCase("y")) {
                        viewAllPayroll();
                    }
                }
            } else {
                System.out.println("Failed to compute salary!");
            }
            
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            
        } catch (Exception e) {
            System.err.println("Error computing salary: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private int getOrCreatePayrollPeriod() {
        PayrollPeriodDAO periodDAO = new PayrollPeriodDAO();
        
        try {
            LocalDate periodStart = LocalDate.now().minusDays(14);
            LocalDate periodEnd = LocalDate.now();
            
            int periodId = periodDAO.getOrCreatePayrollPeriod(periodStart, periodEnd);
            return periodId;
            
        } catch (Exception e) {
            System.err.println("Error creating payroll period: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
    
    private void saveToPayroll(SalaryComputation computation) {
        try {
            System.out.println("\n--- SAVING TO PAYROLL ---");
            AttendanceDAO attendanceDAO = new AttendanceDAO();
            
            LocalDate periodStart = LocalDate.now().minusDays(14);
            LocalDate periodEnd = LocalDate.now();
            
            int daysWorkedId = attendanceDAO.recordAttendance(
                computation.getEmployee().getEmpId(),
                computation.getDaysWorked(),
                periodStart,
                periodEnd
            );
            
            if (daysWorkedId <= 0) {
                System.out.println("Failed to create attendance record!");
                return;
            }
            
            int periodId = getOrCreatePayrollPeriod();
            
            if (periodId == -1) {
                System.out.println("Failed to get payroll period!");
                return;
            }
            
            PayrollDAO payrollDAO = new PayrollDAO();
            Payroll payroll = new Payroll();
            payroll.setEmpId(computation.getEmployee().getEmpId());
            payroll.setPeriodId(periodId);
            payroll.setDaysWorkedId(daysWorkedId);
            payroll.setGrossPay(computation.getGrossSalary());
            payroll.setTotalDeductions(computation.getTotalDeductions());
            payroll.setNetPay(computation.getNetPay());
            
            boolean payrollSuccess = payrollDAO.generatePayroll(payroll);
            
            if (payrollSuccess) {
                System.out.println("Payroll saved successfully!");
                System.out.printf("Employee: %s\n", computation.getEmployee().getEmpName());
                System.out.printf("Gross Pay: ₱%,.2f\n", computation.getGrossSalary());
                System.out.printf("Net Pay: ₱%,.2f\n", computation.getNetPay());
                System.out.printf("Days Worked ID: %d\n", daysWorkedId);
            } else {
                System.out.println("Failed to save payroll record!");
            }
            
        } catch (Exception e) {
            System.err.println("Error saving to payroll: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // ========== UTILITY METHODS ==========
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }
    
    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Please enter a valid amount: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }
    
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}