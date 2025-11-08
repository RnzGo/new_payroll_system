package payroll_system.service;

import payroll_system.model.Attendance;
import payroll_system.dao.AttendanceDAO;
import payroll_system.dao.EmployeeDAO;
import payroll_system.model.Employee;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class PayrollService {
    
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private Scanner scanner = new Scanner (System.in);
    
    //METHOD TO START THE PROGRAM
    public void start(){
        
        //Switch Case for choices
        while(true){
            
            showMainMenu();
            int choice = getIntInput("\nCHOOSE AN OPTION: ");
            
            switch(choice){
                case 1 -> addEmployee();
                case 2 -> viewAllEmployees();
                case 3 -> updateEmployee();
                case 4 -> archiveEmployee();
                case 5 -> viewArchivedEmployees();
                case 6 -> {
                    System.out.println("Exiting Program...");
                    return;
                }
                default -> System.out.println("Invalid option");
                
            }
        }
    }
    
    //METHOD FOR MENU
    private void showMainMenu(){
        System.out.println("\n ==PAYROLL SYSTEM==");
        System.out.println("1.) Add Employee");
        System.out.println("2.) View All Employees");
        System.out.println("3.) Update an Employee's Informtaion");
        System.out.println("4.) Archive an Employee");
        System.out.println("5.) View Archive Employees");
        System.out.println("6.) Exit");
        
    }
    
// ================================== EMPLOYEE MANAGEMENT =================================
    //METHOD FOR ADD EMPLOYEE
    private void addEmployee(){
        System.out.println("\n ----Add New Employee---- ");
        
        // Get the new Employee's name
        System.out.print("FULL NAME : ");
        String name = scanner.nextLine();
        
        //Get the new Employee's position
        System.out.print("POSITION : ");
        String position = scanner.nextLine();
        
        //Get the new Employee's rate per day
        double rate = getDoubleInput("RATE PER DAY: ");
        
        //Get the new Employee's Residence Address
        System.out.print("ADDRESS: ");
        String address = scanner.nextLine();
        
        //Get the contact number of the Employee
        System.out.print("CONTACT NUMBER: ");
        String contact = scanner.nextLine();
        
        //Create an employee object
        Employee employee = new Employee();
        
        // Use the setter method to fill the object's attribute
        employee.setEmpName(name);
        employee.setEmpPosition(position);
        employee.setRatePerDay(rate);
        employee.setEmpAddress(address);
        employee.setContactNumber(contact);
        employee.setEmpStatus("Active");
        
        boolean success = employeeDAO.addEmployee(employee);
        System.out.println(success? "Employee successfully added!":"Failed to add employee!");
        
    }
    
    //METHOD FOR VIEW EMPLOYEES
    private void viewAllEmployees(){
        System.out.println("\n----VIEW ALL EMPLOYEES----");
        List<Employee> employees = employeeDAO.getAllEmployees();
        
        if (employees.isEmpty()){
            System.out.println("No Employees Found!");
            return;
        }
        
        System.out.println("\nID || NAME || POSITION || RATE PER DAY || STATUS");
        System.out.println("------------------------------------------------");
        
        //Iterates through the list of employees
        for (Employee emp : employees){
            System.out.printf("\n%d | %s | %s | P%.2f | %s\n",
                    emp.getEmpId(),
                    emp.getEmpName(),
                    emp.getEmpPosition(),
                    emp.getRatePerDay(),
                    emp.getEmpStatus());
        }
    }
    
    //Returning a value to the caller (Choice)
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
    
    // Returning a value to the caller (Rate Per Day)
    private double getDoubleInput(String prompt){
        System.out.print(prompt);
        while (!scanner.hasNextDouble()){
            System.out.println("Please Enter A Valid Amount: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }
    
    private void updateEmployee(){
        
        System.out.println("\n ---UPDATE AN EMPLOYEE'S INFORMATION---");
        
        //SHOW ALL EMPLOYEES FOR SEARCHING PURPOSE
        viewAllEmployees();
        
        //ENTERING THE ID OF THE EMPLOYEE
        int empId = getIntInput("\n ENTER EMPLOYEE'S ID: ");
        
        //GET THE EMPLOYEE's CURRENT DATA
        Employee employee = employeeDAO.getEmployeeById(empId);
        
        // ERROR IF NOT FOUND
        if (employee == null){
            System.out.println("Error: Employee with ID number " + empId +" Not Found!");
            return;
        }
        //ERROR IF ALREADY ARCHIVED
        else if ("Inactive".equals(employee.getEmpStatus())){
            System.out.println("Error: This Employee Is Already Archived!");
            return;
        }
        
        System.out.println("\n Current Employee Details: ");
        System.out.println("Name: " + employee.getEmpName());
        System.out.println("Position: " + employee.getEmpPosition());
        System.out.println("Rate Per Day: " + employee.getRatePerDay());
        System.out.println("Address: " + employee.getEmpAddress());
        System.out.println("Contact: " + employee.getContactNumber());
        
        System.out.println("\n ENTER NEW DETAILS (PRESS ENTER TO KEEP CURRENT VALUE)");
        
        /*
        //UPDATE STATUS
        System.out.println("UPDATE STATUS: " + employee.getEmpStatus());
        String newStatus = scanner.nextLine();
        if(!newStatus.trim().isEmpty()){
            employee.setEmpStatus(newStatus);
        }
        */
        
        //UPDATE NAME
        System.out.print("New Name [ " + employee.getEmpName() + " ]: ");
        String newName = scanner.nextLine();
        if(!newName.trim().isEmpty()){
            employee.setEmpName(newName);
        }
        
        //UPDATE POSITION
        System.out.print("New Position [ " + employee.getEmpPosition() + " ]: ");
        String newPosition = scanner.nextLine();
        if(!newPosition.trim().isEmpty()){
            employee.setEmpPosition(newPosition);
        }
        
        //RATE PER DAY
        System.out.print("New Rate Per Day [P " + employee.getRatePerDay() + " ]: ");
        String newRate = scanner.nextLine();
        if (!newRate.trim().isEmpty()){
            try{
                double rate = Double.parseDouble(newRate);
                employee.setRatePerDay(rate);
            } catch(NumberFormatException e){
                System.out.println("Invalid rate format. Keeping the current Rate...");
            }
        }
        
        //ADDRESS
        System.out.print("New Address [ " + employee.getEmpAddress() + " ]: ");
        String address = scanner.nextLine();
        if (!address.trim().isEmpty()){
            employee.setEmpAddress(address);
        }
        
        //CONTACT
        System.out.print("New Contact Number [ " + employee.getContactNumber() + " ]");
        String contact = scanner.nextLine();
        if(!contact.trim().isEmpty()){
            employee.setContactNumber(contact);
        }
        
        //CONFIRMATION ON UPDATES
        System.out.println("\n\n UPDATED EMPLOYEE INFOMATION: ");
        System.out.println("Name: " + employee.getEmpName());
        System.out.println("Position: " + employee.getEmpPosition());
        System.out.println("Rate Per Day: P " + employee.getRatePerDay());
        System.out.println("Address: " + employee.getEmpAddress());
        System.out.println("Contact Number: " + employee.getContactNumber());
        
        
        System.out.print("Confirm update? (y/n): ");
        String confirm = scanner.nextLine();
        
        // CONFIRMATION TO UPDATE EMPLOYEE'S INFORMATION
        if(confirm.equalsIgnoreCase("y")){
            boolean success = employeeDAO.updateEmployee(employee);
            if(success){
            System.out.println("\nEmployee's Information Succesfully Updated!");
            }
        } else{
            System.out.println("\nFailed to update Employee! Update Cancelled");
        } 
    }
    
    //================================== ARCHIVE EMPLOYEES ================================
    private void archiveEmployee(){
        System.out.println("=== ARCHIVING EMPLOYEE ===");
        
        //SHOW LIST OF ACTIVE EMPLOYEES
        List<Employee> activeEmployee = employeeDAO.getActiveEmployees();
        
        if(activeEmployee.isEmpty()){
            System.out.println("No Active Employee Found!");
            return;
        }
        
        System.out.println("\n ALL ACTIVE EMPLOYEES ");
        System.out.println("ID \tname \t\t Position");
        for (Employee emp : activeEmployee){
            System.out.printf("%d\t%-15s\t%-10s%n",
                                emp.getEmpId(),
                                emp.getEmpName(),
                                emp.getEmpPosition());
        }
        
        System.out.print("\n\nENTER EMPLOYEE ID TO ARCHIVE: ");
        int empId = scanner.nextInt();
        scanner.nextLine();//To clear buffer
        
        // VERIFY IF EMPLOYEE EXIST AND CURRENTLY ACTIVE
        Employee employee = employeeDAO.getEmployeeById(empId);
        if (employee == null){
            System.out.println("EMPLOYEE NOT FOUND!");
            return;
        }
        
        if("Inactive".equals(employee.getEmpStatus())){
            System.out.println("EMPLOYEE IS ALREADY ARCHIVED! ");
        }
        
        //SHOW DETAILS FOR BETTER CONFIRMATION BEFORE ARCHIVING
        System.out.println("\n\n=== EMPLOYEE TO ARCHIVE ===");
        System.out.println("\nID: " + employee.getEmpId());
        System.out.println("NAME: " + employee.getEmpName());
        System.out.println("RATE PER DAY: " + employee.getRatePerDay());
        
        System.out.println("ARE YOU SURE YOU WANT TO ARCHIVE THIS EMPLOYEE?(y/n): ");
        String confirm = scanner.nextLine();
        
        if(confirm.equalsIgnoreCase("y")){
            boolean success = employeeDAO.archiveEmployee(empId);
        } else{
            System.out.println("FAILED TO ARCHIVE EMPLOYEE... ARCHIVING CANCELLED!");
        }
    }
    
    //VIEW ARCHIVED EMPLOYEES
    private void viewArchivedEmployees(){
        System.out.println("\n==== ARCHIVED EMPLOYEES ====");
        
        List<Employee> archivedEmployees = employeeDAO.getArchivedEmployees();
        
        if(archivedEmployees.isEmpty()){
            System.out.println("NO ARCHIVED EMPLOYEES FOUND! ");
            return;
    }
        
        System.out.println("\n ARCHIVED EMPLOYEES ");
        System.out.println("\nID \t NAME \t\t\t POSITION \t RATE PER DAY \t\t STATUS");
        
        for (Employee emp : archivedEmployees){
            System.out.printf("\n %d \t %-15s \t %-10s \t P %,-10.2f \t %-8s%n",
            emp.getEmpId(),
            emp.getEmpName(),
            emp.getEmpPosition(),
            emp.getRatePerDay(),
            emp.getEmpStatus());
}
    }
    
    //================================== ATTENDANCE =========================================
    private void recordAttendance(){
        System.out.println("\n ==== RECORD ATTENDANCE ==== ");
        
        //SHOW ACTIVE EMPLOYEES
        List<Employee> activeEmployees = employeeDAO.getActiveEmployees();
        
        if(activeEmployees.isEmpty()){
            System.out.println("No Active Employee Found!");
            return;
        }
        
        System.out.println("\n === ACTIVE EMPLOYEES ===");
        System.out.println(" ID \t ||  NAME \t ||  POSITION");
        
        for (Employee emp : activeEmployees){
            System.out.printf("\n%d \t %-15s \t %-10s%n",
            emp.getEmpId(),
            emp.getEmpName(),
            emp.getEmpPosition());
        }
        //EMPLOYEE ID FOR RECORD
        System.out.print("ENTER EMPLOYEE ID: ");
        int empId = scanner.nextInt();
        scanner.nextLine();
        
        //VERIFY IF EMPLOYEE EXIST
        Employee employee = employeeDAO.getEmployeeById(empId);
        if (employee == null || "Inactive".equals(employee.getEmpStatus())){
            System.out.println("Employee Not Found Or Inactive!");
            return;
        }
        
        //RECORD THE ATTENDANCE BY PERIOD
        System.out.print("ENTER DAYS WORKED: ");
        int daysWorked = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("ENTER PERIOD START (YYYY-MM-DD): ");
        String startStr = scanner.nextLine();
        LocalDate periodStart = LocalDate.parse(startStr);
        
        
        System.out.print("ENTER PERIOD END (YYYY-MM-DD): ");
        String endStr = scanner.nextLine();
        LocalDate periodEnd = LocalDate.parse(endStr);
        
        //CREATE ATTENDANCE RECORD
        Attendance attendance = new Attendance(empId,daysWorked,periodStart,periodEnd);
        
        //CONFIRMATION OF RECORD
        System.out.println("\n ARE YOU SURE TO RECORD THIS ATTENDANCE OF  " + employee.getEmpName() + " || " + employee.getEmpPosition());
        System.out.println("\nSTARTING PERIOD: " + attendance.getPeriodStart());
        System.out.println("ENDING PERIOD: " + attendance.getPeriodEnd());
        System.out.println("DAYS WORKED: " + attendance.getDaysWorked());
        
        System.out.print("CONFIRM RECORDING? (y/n): ");
        String confirm = scanner.nextLine();
        
        //CONFIRMATION 
        if(confirm.equalsIgnoreCase("y")){
            AttendanceDAO attendanceDAO = new AttendanceDAO();
            boolean success = attendanceDAO.addAttendance(attendance);
            
            if (success){
                System.out.println("\n Employee's Attendance Recorded Successfully");
            }else{
                System.out.println("\n Failed To Record Employee's Attendance!");
            }
        } else {
            System.out.println("\n RECORDING CANCELLED!");
        }
    }
    //VIEW EMPLOYEE ATTENDANCE
    private void viewEmployeeAttendance(){
            System.out.println("\n ==== VIEW EMPLOYEE ATTENDANCE ==== ");
            
            System.out.print("ENTER EMPLOYEE ID: ");
            int empId = scanner.nextInt();
            scanner.nextLine();
            
            //VERIFY IF EMPLOYEE EXISTS
            Employee employee = employeeDAO.getEmployeeById(empId);
            if (employee == null){
                System.out.println("NO EMPLOYEE FOUND!");
                return;
            }
            // IF EMPLOYEE EXIST
            System.out.println("\n EMPLOYEE NAME: " + employee.getEmpName() + "\t POSITION: " + employee.getEmpPosition());
            
            List <Attendance> attendanceRecord = new AttendanceDAO().getAllAttendanceOfEmployeeById(empId);
            
            if (attendanceRecord.isEmpty()){
                System.out.println("\n No Record Exist!");
                return;
            }
            
            System.out.println("\n ATTENDANCE RECORD OF " + employee.getEmpName() + " || " + employee.getEmpPosition());
            
            for (Attendance att : attendanceRecord){
                System.out.println("\n[ PERIOD: " + att.getPeriodStart() + " to " + att.getPeriodEnd());
                System.out.println("DAYS WORKED: " + att.getDaysWorked() + " ]");
        }
    }
}
