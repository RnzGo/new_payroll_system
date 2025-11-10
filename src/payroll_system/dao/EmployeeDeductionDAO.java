package payroll_system.dao;

import payroll_system.model.EmployeeDeduction;
import payroll_system.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDeductionDAO {
    
    // ADD EMPLOYEE DEDUCTION
    public boolean addEmployeeDeduction(EmployeeDeduction empDeduction) {
        String sql = "INSERT INTO emp_deduction (deduction_id, emp_id, deduction_amount) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, empDeduction.getDeductionId());
            pstmt.setInt(2, empDeduction.getEmpId());
            pstmt.setDouble(3, empDeduction.getDeductionAmount());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("ERROR ADDING EMPLOYEE DEDUCTION: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // GET ALL DEDUCTIONS FOR AN EMPLOYEE
    public List<EmployeeDeduction> getDeductionsByEmployee(int empId) {
        List<EmployeeDeduction> deductions = new ArrayList<>();
        String sql = "SELECT ed.*, d.deduction_name, e.emp_name " +
                     "FROM emp_deduction ed " +
                     "JOIN deductions d ON ed.deduction_id = d.deduction_id " +
                     "JOIN employees e ON ed.emp_id = e.emp_id " +
                     "WHERE ed.emp_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                EmployeeDeduction empDeduction = new EmployeeDeduction();
                empDeduction.setEmpDeductionId(rs.getInt("emp_deduction_id"));
                empDeduction.setDeductionId(rs.getInt("deduction_id"));
                empDeduction.setEmpId(rs.getInt("emp_id"));
                empDeduction.setDeductionAmount(rs.getDouble("deduction_amount"));
                empDeduction.setDeductionName(rs.getString("deduction_name"));
                empDeduction.setEmpName(rs.getString("emp_name"));
                
                deductions.add(empDeduction);
            }
            
        } catch (SQLException e) {
            System.err.println("ERROR GETTING EMPLOYEE DEDUCTIONS: " + e.getMessage());
            e.printStackTrace();
        }
        
        return deductions;
    }
    
    // GET EMPLOYEE DEDUCTION BY ID
    public EmployeeDeduction getEmployeeDeductionById(int empDeductionId) {
        String sql = "SELECT ed.*, d.deduction_name, e.emp_name " +
                     "FROM emp_deduction ed " +
                     "JOIN deductions d ON ed.deduction_id = d.deduction_id " +
                     "JOIN employees e ON ed.emp_id = e.emp_id " +
                     "WHERE ed.emp_deduction_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, empDeductionId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                EmployeeDeduction empDeduction = new EmployeeDeduction();
                empDeduction.setEmpDeductionId(rs.getInt("emp_deduction_id"));
                empDeduction.setDeductionId(rs.getInt("deduction_id"));
                empDeduction.setEmpId(rs.getInt("emp_id"));
                empDeduction.setDeductionAmount(rs.getDouble("deduction_amount"));
                empDeduction.setDeductionName(rs.getString("deduction_name"));
                empDeduction.setEmpName(rs.getString("emp_name"));
                
                return empDeduction;
            }
            
        } catch (SQLException e) {
            System.err.println("ERROR GETTING EMPLOYEE DEDUCTION BY ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    // UPDATE EMPLOYEE DEDUCTION
    public boolean updateEmployeeDeduction(EmployeeDeduction empDeduction) {
        String sql = "UPDATE emp_deduction SET deduction_id = ?, emp_id = ?, deduction_amount = ? WHERE emp_deduction_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, empDeduction.getDeductionId());
            pstmt.setInt(2, empDeduction.getEmpId());
            pstmt.setDouble(3, empDeduction.getDeductionAmount());
            pstmt.setInt(4, empDeduction.getEmpDeductionId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("ERROR UPDATING EMPLOYEE DEDUCTION: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // DELETE EMPLOYEE DEDUCTION
    public boolean deleteEmployeeDeduction(int empDeductionId) {
        String sql = "DELETE FROM emp_deduction WHERE emp_deduction_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, empDeductionId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("ERROR DELETING EMPLOYEE DEDUCTION: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // GET TOTAL DEDUCTIONS FOR EMPLOYEE (for payroll calculation)
    public double getTotalEmployeeDeductions(int empId) {
        String sql = "SELECT SUM(deduction_amount) as total_deductions FROM emp_deduction WHERE emp_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("total_deductions");
            }
            
        } catch (SQLException e) {
            System.err.println("ERROR GETTING TOTAL EMPLOYEE DEDUCTIONS: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0.0;
    }
    
    // CHECK IF EMPLOYEE ALREADY HAS A SPECIFIC DEDUCTION TYPE
    public boolean employeeHasDeductionType(int empId, int deductionId) {
        String sql = "SELECT COUNT(*) FROM emp_deduction WHERE emp_id = ? AND deduction_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, empId);
            pstmt.setInt(2, deductionId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("ERROR CHECKING EMPLOYEE DEDUCTION: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}
