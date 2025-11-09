package payroll_system.dao;

import payroll_system.model.Attendance;
import payroll_system.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class AttendanceDAO {
    
    // ADD ATTENDANCE RECORD
    public boolean addAttendance(Attendance attendance) {
        String sql = "INSERT INTO attendance (emp_id, days_worked, period_start, period_end) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, attendance.getEmpId());
            pstmt.setDouble(2, attendance.getDaysWorked());
            pstmt.setDate(3, java.sql.Date.valueOf(attendance.getPeriodStart()));
            pstmt.setDate(4, java.sql.Date.valueOf(attendance.getPeriodEnd()));
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    attendance.setDaysWorkedId(generatedId);
                    return true;
                }
            }
            return false;
            
        } catch (SQLException e) {
            System.err.println("Error adding attendance: " + e.getMessage());
            return false;
        }
    }
    
    // GET ALL ATTENDANCE RECORDS
    public List<Attendance> getAllAttendance() {
        List<Attendance> attendanceList = new ArrayList<>();
        String sql = "SELECT * FROM attendance ORDER BY period_start DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Attendance attendance = mapResultSetToAttendance(rs);
                attendanceList.add(attendance);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all attendance: " + e.getMessage());
        }
        return attendanceList;
    }
    
    // MAP RESULTSET TO ATTENDANCE OBJECT
    private Attendance mapResultSetToAttendance(ResultSet rs) throws SQLException {
        Attendance attendance = new Attendance();
        attendance.setDaysWorkedId(rs.getInt("days_worked_id"));
        attendance.setEmpId(rs.getInt("emp_id"));
        attendance.setDaysWorked(rs.getDouble("days_worked"));
        attendance.setPeriodStart(rs.getDate("period_start").toLocalDate());
        attendance.setPeriodEnd(rs.getDate("period_end").toLocalDate());
        return attendance;
    }
    
    // GET ATTENDANCE BY EMPLOYEE ID AND PERIOD
    public List<Attendance> getAttendanceByEmployeeIdAndPeriod(int empId, LocalDate periodStart, LocalDate periodEnd) {
        List<Attendance> attendanceList = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE emp_id = ? AND period_start = ? AND period_end = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, empId);
            pstmt.setDate(2, Date.valueOf(periodStart));
            pstmt.setDate(3, Date.valueOf(periodEnd));
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Attendance attendance = mapResultSetToAttendance(rs);
                attendanceList.add(attendance);
            }
        } catch (SQLException e) {
            System.err.println("Error getting attendance by employee and period: " + e.getMessage());
        }
        return attendanceList;
    }
    
    // GET ALL ATTENDANCE RECORDS OF AN EMPLOYEE
    public List<Attendance> getAllAttendanceOfEmployeeById(int empId) {
        List<Attendance> attendanceList = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE emp_id = ? ORDER BY period_start DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Attendance attendance = mapResultSetToAttendance(rs);
                attendanceList.add(attendance);
            }
        } catch (SQLException e) {
            System.err.println("Error getting employee attendance records: " + e.getMessage());
        }
        return attendanceList;
    }
    
    // DELETE ATTENDANCE RECORD
    public boolean deleteAttendance(int daysWorkedId) {
        String sql = "DELETE FROM attendance WHERE days_worked_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, daysWorkedId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            if (e.getMessage().contains("foreign key constraint") || e.getErrorCode() == 1451) {
                System.out.println("Cannot delete: This record is used in payroll. Delete related payroll first.");
            } else {
                System.err.println("Error deleting attendance: " + e.getMessage());
            }
            return false;
        }
    }
    
    // RECORD ATTENDANCE
    public int recordAttendance(int empId, double daysWorked, LocalDate periodStart, LocalDate periodEnd) {
        String sql = "INSERT INTO attendance (emp_id, days_worked, period_start, period_end) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, empId);
            pstmt.setDouble(2, daysWorked);
            pstmt.setDate(3, java.sql.Date.valueOf(periodStart));
            pstmt.setDate(4, java.sql.Date.valueOf(periodEnd));
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
            return -1;
            
        } catch (SQLException e) {
            System.err.println("Error recording attendance: " + e.getMessage());
            return -1;
        }
    }
}
