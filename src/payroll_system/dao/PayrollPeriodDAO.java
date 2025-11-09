package payroll_system.dao;

import payroll_system.util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PayrollPeriodDAO {
    
    // CREATE NEW PAYROLL PERIOD
    public boolean createPayrollPeriod(LocalDate periodStart, LocalDate periodEnd) {
        String sql = "INSERT INTO payroll_period (period_start, period_end, date_issued) VALUES (?, ?, CURRENT_DATE)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, java.sql.Date.valueOf(periodStart));
            pstmt.setDate(2, java.sql.Date.valueOf(periodEnd));
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating payroll period: " + e.getMessage());
            return false;
        }
    }
    
    // GET ALL PAYROLL PERIODS
    public List<String[]> getAllPayrollPeriods() {
        List<String[]> periods = new ArrayList<>();
        String sql = "SELECT period_id, period_start, period_end, date_issued FROM payroll_period ORDER BY period_start DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String[] period = new String[4];
                period[0] = String.valueOf(rs.getInt("period_id"));
                period[1] = rs.getDate("period_start").toLocalDate().toString();
                period[2] = rs.getDate("period_end").toLocalDate().toString();
                period[3] = rs.getDate("date_issued").toString();
                periods.add(period);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting payroll periods: " + e.getMessage());
        }
        
        return periods;
    }
    
    // GET OR CREATE PAYROLL PERIOD
    public int getOrCreatePayrollPeriod(LocalDate periodStart, LocalDate periodEnd) {
        // First, try to find existing period
        String findSql = "SELECT period_id FROM payroll_period WHERE period_start = ? AND period_end = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(findSql)) {
            
            pstmt.setDate(1, java.sql.Date.valueOf(periodStart));
            pstmt.setDate(2, java.sql.Date.valueOf(periodEnd));
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("period_id");
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding payroll period: " + e.getMessage());
        }
        
        // If no existing period found, create a new one
        String insertSql = "INSERT INTO payroll_period (period_start, period_end, date_issued) VALUES (?, ?, CURRENT_DATE)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setDate(1, java.sql.Date.valueOf(periodStart));
            pstmt.setDate(2, java.sql.Date.valueOf(periodEnd));
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error creating payroll period: " + e.getMessage());
        }
        
        return -1;
    }
    
    // GET LATEST PAYROLL PERIOD
    public int getLatestPayrollPeriod() {
        String sql = "SELECT period_id FROM payroll_period ORDER BY period_id DESC LIMIT 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("period_id");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting latest payroll period: " + e.getMessage());
        }
        
        return -1;
    }
    
    // GET PERIOD BY ID
    public String[] getPeriodById(int periodId) {
        String sql = "SELECT period_id, period_start, period_end, date_issued FROM payroll_period WHERE period_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, periodId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String[] period = new String[4];
                period[0] = String.valueOf(rs.getInt("period_id"));
                period[1] = rs.getDate("period_start").toLocalDate().toString();
                period[2] = rs.getDate("period_end").toLocalDate().toString();
                period[3] = rs.getDate("date_issued").toString();
                return period;
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting period by ID: " + e.getMessage());
        }
        
        return null;
    }
}