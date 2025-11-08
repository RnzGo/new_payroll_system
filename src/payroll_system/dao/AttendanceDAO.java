package payroll_system.dao;

import payroll_system.model.Attendance;
import payroll_system.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class AttendanceDAO {
    
    // ADD ATTENDANCE RECORD
    public boolean addAttendance (Attendance attendance){
        String sql = "INSERT INTO attendance (emp_id, days_worked, period_start, period_end) VALUES (?,?,?,?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setInt(1, attendance.getEmpId());
            pstmt.setInt(2, attendance.getDaysWorked());
            pstmt.setDate(3, Date.valueOf(attendance.getPeriodStart()));
            pstmt.setDate(4, Date.valueOf(attendance.getPeriodEnd()));
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e){
            System.err.println("Error Adding Attendance: " + e.getMessage());
            return false;
        }
    }
    
    //GET ALL ATTENDANCE RECORD
    public List <Attendance> getAllAttendance(){
        List<Attendance> attendanceList = new ArrayList<>();
        String sql = "SELECT * FROM attendance ORDER BY period_start DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()){
             
             while (rs.next()){
                 Attendance attendance = mapResultSetToAttendance(rs);
                 attendanceList.add(attendance);
             }
        }catch (SQLException e){
            System.err.println("Error Getting All Attendance: " + e.getMessage());
            e.printStackTrace();
        }
        return attendanceList;
    }
    
    //mapAttendanceSetToAttendance Object
    private Attendance mapResultSetToAttendance(ResultSet rs)throws SQLException{
            Attendance attendance = new Attendance();
            attendance.setDaysWorkedId(rs.getInt("days_worked_id"));
            attendance.setEmpId(rs.getInt("emp_id"));
            attendance.setDaysWorked(rs.getInt("days_worked"));
            attendance.setPeriodStart(rs.getDate("period_start").toLocalDate());
            attendance.setPeriodEnd(rs.getDate("period_end").toLocalDate());
            return attendance;
    }
    
    //GET ATTENDANCE BY EMPLOYEE ID AND PERIOD
    public List<Attendance> getAttendanceByEmployeeIdAndPeriod(int empId, LocalDate periodStart, LocalDate periodEnd){
        List<Attendance> attendanceList = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE emp_id = ? AND period_start = ? AND period_end = ?";
        
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setInt(1, empId);
            pstmt.setDate(2,Date.valueOf(periodStart));
            pstmt.setDate(3,Date.valueOf(periodEnd));
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()){
                Attendance attendance = mapResultSetToAttendance(rs);
                attendanceList.add(attendance);      
            }
        } catch(SQLException e){
            System.err.println("Error Getting Attendance By Employee ID: " + e.getMessage());
            e.printStackTrace();
        }
        return attendanceList;
    }
    
    //GET ALL ATTENDANCE RECORD OF AN EMPLOYEE
    public List<Attendance> getAllAttendanceOfEmployeeById(int empId){
        List<Attendance> attendanceList = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE emp_id = ? ORDER BY period_start DESC";
        
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                Attendance attendance = mapResultSetToAttendance(rs);
                attendanceList.add(attendance);
            }
        } catch (SQLException e){
            System.err.println("Error Getting Attendance All Record of an Employee: " + e.getMessage());
            e.printStackTrace();
        }
        return attendanceList;
    }
}    

