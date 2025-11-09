
package payroll_system.model;

public class Deductions {
    private int deductionId;
    private String deductionName;
    private double deductionRate;
    
    //Constructor for deductions
    public Deductions(){
    // Empty constructor for database operations
    }
    
    public Deductions(int deductionId, String deductionName, double deductionRate){
        this.deductionId = deductionId;
        this.deductionName = deductionName;
        this.deductionRate = deductionRate;
    }

    public int getDeductionId() {
        return deductionId;
    }

    public void setDeductionId(int deductionId) {
        this.deductionId = deductionId;
    }

    public String getDeductionName() {
        return deductionName;
    }

    public void setDeductionName(String deductionName) {
        this.deductionName = deductionName;
    }

    public double getDeductionRate() {
        return deductionRate;
    }

    public void setDeductionRate(double deductionRate) {
        this.deductionRate = deductionRate;
    }
    
    @Override
    public String toString(){
    
        return "Deduction: [ " +
                "deductionId = " + deductionId + 
                "deductionName = " + deductionName + 
                "deductionRate = " + deductionRate + " ]";
    }
}
