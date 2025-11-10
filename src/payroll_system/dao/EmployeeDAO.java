package payroll_system.dao;

import payroll_system.model.Employee;
import payroll_system.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
  
public class EmployeeDAO {
    
    //ADD AN EMPLOYEE
    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (emp_name, emp_position, rate_per_day, emp_address, contact_number, emp_status) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employee.getEmpName());
            pstmt.setString(2, employee.getEmpPosition());
            pstmt.setDouble(3, employee.getRatePerDay());
            pstmt.setString(4, employee.getEmpAddress());
            pstmt.setString(5, employee.getContactNumber());
            pstmt.setString(6, "Active");
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //VIEW EMPLOYEES
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE emp_status = 'Active'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setEmpId(rs.getInt("emp_id"));
                emp.setEmpName(rs.getString("emp_name"));
                emp.setEmpPosition(rs.getString("emp_position"));
                emp.setRatePerDay(rs.getDouble("rate_per_day"));
                emp.setEmpAddress(rs.getString("emp_address"));
                emp.setContactNumber(rs.getString("contact_number"));
                emp.setEmpStatus(rs.getString("emp_status"));
                
                employees.add(emp);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    
    
    //UPDATE AN EMPLOYEE'S INFORMATION
    public boolean updateEmployee(Employee employee){
        
        String sql = "UPDATE employees SET emp_name=?, emp_position=?, rate_per_day=? ," + 
                "emp_address=?, contact_number=?, emp_status=? WHERE emp_id=?";
        
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, employee.getEmpName());
            pstmt.setString(2, employee.getEmpPosition());
            pstmt.setDouble(3, employee.getRatePerDay());
            pstmt.setString(4, employee.getEmpAddress());
            pstmt.setString(5, employee.getContactNumber());
            pstmt.setString(6, employee.getEmpStatus());
            pstmt.setInt(7, employee.getEmpId());
            
            int rows = pstmt.executeUpdate();
            return rows > 0;
            
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    // ARCHIVING AN EMPLOYEE
    public boolean archiveEmployee(int empId){
        String sql = "UPDATE employees SET emp_status = 'Inactive' WHERE emp_id = ?";
        
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setInt(1,empId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        }catch(SQLException e){
            System.out.println("Error: Archiving Cancelled...");
            e.printStackTrace();
            return false;
        }
    }
    
    //GET ACTIVE EMPLOYEES
    public List<Employee> getActiveEmployees(){
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE emp_status = 'Active'";
        
        try(Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
        
            while(rs.next()){
                Employee emp = new Employee();
                emp.setEmpId(rs.getInt("emp_id"));
                emp.setEmpName(rs.getString("emp_name"));
                emp.setEmpPosition(rs.getString("emp_position"));
                emp.setRatePerDay(rs.getDouble("rate_per_day"));
                emp.setEmpAddress(rs.getString("emp_address"));
                emp.setContactNumber(rs.getString("contact_number"));
                emp.setEmpStatus(rs.getString("emp_status"));
                
                employees.add(emp);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return employees;
    }
    
    // GET INACTIVE EMPLOYEES
    public List<Employee> getInactiveEmployee(){
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE emp_status = 'Inactive'";
        
        try(Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
        
            while(rs.next()){
                Employee emp = new Employee();
                emp.setEmpId(rs.getInt("emp_id"));
                emp.setEmpName(rs.getString("emp_name"));
                emp.setEmpPosition(rs.getString("emp_position"));
                emp.setRatePerDay(rs.getDouble("rate_per_day"));
                emp.setEmpAddress(rs.getString("emp_address"));
                emp.setContactNumber(rs.getString("contact_number"));
                emp.setEmpStatus(rs.getString("emp_status"));
                
                employees.add(emp);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return employees;
    }
    
    //GET ARCHIVED EMPLOYEES
    public List<Employee> getArchivedEmployees(){
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE emp_status = 'Inactive' ORDER BY emp_id";
        
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()){
        
            while(rs.next()){
                Employee emp = new Employee();
                emp.setEmpId(rs.getInt("emp_id"));
                emp.setEmpName(rs.getString("emp_name"));
                emp.setEmpPosition(rs.getString("emp_position"));
                emp.setRatePerDay(rs.getDouble("rate_per_day"));
                emp.setEmpAddress(rs.getString("emp_address"));
                emp.setContactNumber(rs.getString("contact_number"));
                emp.setEmpStatus(rs.getString("emp_status"));
                employees.add(emp);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return employees;
    }
    
    //RESTORE ARCHIVED EMPLOYEES
    public boolean restoreEmployee(int empId){
        String sql = "UPDATE employees SET emp_status = 'Active' WHERE emp_id =?";
        
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setInt(1, empId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e){
            System.out.println("Error Restoring Employee...");
            e.printStackTrace();
            return false;
        }
    }
    
    //GET EMPLOYEE BY ID
    public Employee getEmployeeById(int empId){
        String sql = "SELECT * FROM employees WHERE emp_id = ?";
        
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()){
                Employee emp = new Employee();
                emp.setEmpId(rs.getInt("emp_id"));
                emp.setEmpName(rs.getString("emp_name"));
                emp.setEmpPosition(rs.getString("emp_position"));
                emp.setRatePerDay(rs.getDouble("rate_per_day"));
                emp.setEmpAddress(rs.getString("emp_address"));
                emp.setContactNumber(rs.getString("contact_number"));
                emp.setEmpStatus(rs.getString("emp_status"));
                
                return emp;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
        
    

