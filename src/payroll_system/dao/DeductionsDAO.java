
package payroll_system.dao;
import payroll_system.model.Deductions;
import payroll_system.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeductionsDAO {
    
    // GET ALL DEDUCTIONS
    public List<Deductions> getAllDeductions() {
        List<Deductions> deductionsList = new ArrayList<>();
        String sql = "SELECT * FROM deductions ORDER BY deduction_id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Deductions deduction = new Deductions();
                deduction.setDeductionId(rs.getInt("deduction_id"));
                deduction.setDeductionName(rs.getString("deduction_name"));
                deduction.setDeductionRate(rs.getDouble("deduction_rate"));
                
                deductionsList.add(deduction);
            }
            
        } catch (SQLException e) {
            System.err.println("ERROR GETTING ALL DEDUCTIONS: " + e.getMessage());
            e.printStackTrace();
        }
        
        return deductionsList;
    }
    
    // GET DEDUCTION BY ID
    public Deductions getDeductionById(int deductionId) {
        String sql = "SELECT * FROM deductions WHERE deduction_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, deductionId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Deductions deduction = new Deductions();
                deduction.setDeductionId(rs.getInt("deduction_id"));
                deduction.setDeductionName(rs.getString("deduction_name"));
                deduction.setDeductionRate(rs.getDouble("deduction_rate"));
                return deduction;
            }
            
        } catch (SQLException e) {
            System.err.println("ERROR GETTING DEDUCTION BY ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    // ADD NEW DEDUCTION
    public boolean addDeduction(Deductions deduction) {
        String sql = "INSERT INTO deductions (deduction_name, deduction_rate) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, deduction.getDeductionName());
            pstmt.setDouble(2, deduction.getDeductionRate());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("ERROR ADDING DEDUCTION: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // UPDATE DEDUCTION
    public boolean updateDeduction(Deductions deduction) {
        String sql = "UPDATE deductions SET deduction_name = ?, deduction_rate = ? WHERE deduction_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, deduction.getDeductionName());
            pstmt.setDouble(2, deduction.getDeductionRate());
            pstmt.setInt(3, deduction.getDeductionId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("ERROR UPDATING DEDUCTION: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // DELETE DEDUCTION
    public boolean deleteDeduction(int deductionId) {
        String sql = "DELETE FROM deductions WHERE deduction_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, deductionId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("ERROR DELETING DEDUCTION: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // GET DEDUCTION BY NAME
    public Deductions getDeductionByName(String deductionName) {
        String sql = "SELECT * FROM deductions WHERE deduction_name = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, deductionName);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Deductions deduction = new Deductions();
                deduction.setDeductionId(rs.getInt("deduction_id"));
                deduction.setDeductionName(rs.getString("deduction_name"));
                deduction.setDeductionRate(rs.getDouble("deduction_rate"));
                return deduction;
            }
            
        } catch (SQLException e) {
            System.err.println("ERROR GETTING DEDUCTION BY NAME: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
}
