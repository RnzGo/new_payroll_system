package payroll_system.dao;

import payroll_system.model.Payroll;
import payroll_system.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollDAO {
    
    // GENERATE PAYROLL FOR AN EMPLOYEE 
    public boolean generatePayroll(Payroll payroll) {
        String sql = "INSERT INTO payroll (emp_id, period_id, days_worked_id, gross_pay, total_deductions, net_pay) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, payroll.getEmpId());
            pstmt.setInt(2, payroll.getPeriodId());
            pstmt.setInt(3, payroll.getDaysWorkedId());
            pstmt.setDouble(4, payroll.getGrossPay());
            pstmt.setDouble(5, payroll.getTotalDeductions());
            pstmt.setDouble(6, payroll.getNetPay());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error generating payroll: " + e.getMessage());
            return false;
        }
    }
    
    // GET PAYROLL BY EMPLOYEE
    public List<Payroll> getPayrollByEmployee(int empId) {
        List<Payroll> payrollList = new ArrayList<>();
        String sql = "SELECT p.*, pp.period_start, pp.period_end, e.emp_name " + 
                     "FROM payroll p " + 
                     "JOIN payroll_period pp ON p.period_id = pp.period_id " + 
                     "JOIN employees e ON p.emp_id = e.emp_id " +
                     "WHERE p.emp_id = ? " + 
                     "ORDER BY pp.period_start DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Payroll payroll = new Payroll();
                payroll.setPayrollId(rs.getInt("payroll_id"));
                payroll.setEmpId(rs.getInt("emp_id"));
                payroll.setPeriodId(rs.getInt("period_id"));
                payroll.setDaysWorkedId(rs.getInt("days_worked_id"));
                payroll.setGrossPay(rs.getDouble("gross_pay"));
                payroll.setTotalDeductions(rs.getDouble("total_deductions"));
                payroll.setNetPay(rs.getDouble("net_pay"));
                payroll.setEmpName(rs.getString("emp_name"));
                payroll.setPeriodStart(rs.getDate("period_start").toLocalDate()); 
                payroll.setPeriodEnd(rs.getDate("period_end").toLocalDate()); 
                
                payrollList.add(payroll);
            }
        } catch (SQLException e) {
            System.err.println("Error getting payroll by employee: " + e.getMessage());
        }       
        return payrollList;
    }
    
    // GET PAYROLL BY PERIOD
    public List<Payroll> getPayrollByPeriod(int periodId) {
        List<Payroll> payrollList = new ArrayList<>();
        String sql = "SELECT p.*, e.emp_name, e.emp_position, pp.period_start, pp.period_end " + 
                     "FROM payroll p " + 
                     "JOIN employees e ON p.emp_id = e.emp_id " +
                     "JOIN payroll_period pp ON p.period_id = pp.period_id " + 
                     "WHERE p.period_id = ? " + 
                     "ORDER BY e.emp_name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, periodId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Payroll payroll = new Payroll();
                payroll.setPayrollId(rs.getInt("payroll_id"));
                payroll.setEmpId(rs.getInt("emp_id"));
                payroll.setPeriodId(rs.getInt("period_id"));
                payroll.setDaysWorkedId(rs.getInt("days_worked_id"));
                payroll.setGrossPay(rs.getDouble("gross_pay"));
                payroll.setTotalDeductions(rs.getDouble("total_deductions"));
                payroll.setNetPay(rs.getDouble("net_pay"));
                payroll.setEmpName(rs.getString("emp_name"));
                payroll.setEmpPosition(rs.getString("emp_position"));
                payroll.setPeriodStart(rs.getDate("period_start").toLocalDate());
                payroll.setPeriodEnd(rs.getDate("period_end").toLocalDate()); 
                
                payrollList.add(payroll);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting payroll by period: " + e.getMessage());
        }
        return payrollList;
    }
    
    // GET ALL PAYROLL RECORDS
    public List<Payroll> getAllPayrollRecords() {
        List<Payroll> payrollList = new ArrayList<>();
        String sql = "SELECT p.*, e.emp_name, e.emp_position, pp.period_start, pp.period_end " + 
                     "FROM payroll p " + 
                     "JOIN employees e ON p.emp_id = e.emp_id " + 
                     "JOIN payroll_period pp ON p.period_id = pp.period_id " + 
                     "ORDER BY pp.period_start DESC, e.emp_name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Payroll payroll = new Payroll();
                payroll.setPayrollId(rs.getInt("payroll_id"));
                payroll.setEmpId(rs.getInt("emp_id"));
                payroll.setPeriodId(rs.getInt("period_id"));
                payroll.setDaysWorkedId(rs.getInt("days_worked_id"));
                payroll.setGrossPay(rs.getDouble("gross_pay"));
                payroll.setTotalDeductions(rs.getDouble("total_deductions"));
                payroll.setNetPay(rs.getDouble("net_pay"));
                payroll.setEmpName(rs.getString("emp_name"));
                payroll.setEmpPosition(rs.getString("emp_position"));
                payroll.setPeriodStart(rs.getDate("period_start").toLocalDate()); 
                payroll.setPeriodEnd(rs.getDate("period_end").toLocalDate());
                
                payrollList.add(payroll);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all payroll records: " + e.getMessage());
        }
        return payrollList;
    }
    
    // CHECK IF PAYROLL EXISTS FOR EMPLOYEE IN PERIOD
    public boolean payrollExists(int empId, int periodId) {
        String sql = "SELECT COUNT(*) FROM payroll WHERE emp_id = ? AND period_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, empId);
            pstmt.setInt(2, periodId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking payroll existence: " + e.getMessage());
        }
        return false;
    }
    
    // DELETE PAYROLL RECORD
    public boolean deletePayroll(int payrollId) {
        String sql = "DELETE FROM payroll WHERE payroll_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, payrollId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting payroll: " + e.getMessage());
            return false;
        }
    }
    
    // VIEW ALL PAYROLL RECORDS WITH DETAILS
    public void viewAllPayrollWithDetails() {
        List<Payroll> payrollList = getAllPayrollRecords();
        
        if (payrollList.isEmpty()) {
            System.out.println("No payroll records found!");
            return;
        }
        
        System.out.println("\n=== ALL PAYROLL RECORDS ===");
        System.out.println("+-----+---------------------+-----------------+----------------+----------------+----------------+");
        System.out.println("| ID  | Employee Name       | Period          | Gross Pay      | Deductions     | Net Pay        |");
        System.out.println("+-----+---------------------+-----------------+----------------+----------------+----------------+");
        
        for (Payroll payroll : payrollList) {
            System.out.printf("| %-3d | %-19s | %-15s | ₱%12.2f | ₱%12.2f | ₱%12.2f |%n",
                payroll.getPayrollId(),
                payroll.getEmpName(),
                payroll.getPeriodStart() + " to " + payroll.getPeriodEnd(),
                payroll.getGrossPay(),
                payroll.getTotalDeductions(),
                payroll.getNetPay());
        }
        
        System.out.println("+-----+---------------------+-----------------+----------------+----------------+----------------+");
    }
    
    // GET PAYROLL BY ID
    public Payroll getPayrollById(int payrollId) {
        String sql = "SELECT p.*, e.emp_name, e.emp_position, pp.period_start, pp.period_end " + 
                     "FROM payroll p " + 
                     "JOIN employees e ON p.emp_id = e.emp_id " + 
                     "JOIN payroll_period pp ON p.period_id = pp.period_id " + 
                     "WHERE p.payroll_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, payrollId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Payroll payroll = new Payroll();
                payroll.setPayrollId(rs.getInt("payroll_id"));
                payroll.setEmpId(rs.getInt("emp_id"));
                payroll.setPeriodId(rs.getInt("period_id"));
                payroll.setDaysWorkedId(rs.getInt("days_worked_id"));
                payroll.setGrossPay(rs.getDouble("gross_pay"));
                payroll.setTotalDeductions(rs.getDouble("total_deductions"));
                payroll.setNetPay(rs.getDouble("net_pay"));
                payroll.setEmpName(rs.getString("emp_name"));
                payroll.setEmpPosition(rs.getString("emp_position"));
                payroll.setPeriodStart(rs.getDate("period_start").toLocalDate()); 
                payroll.setPeriodEnd(rs.getDate("period_end").toLocalDate());
                return payroll;
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting payroll by ID: " + e.getMessage());
        }
        
        return null;
    }
}