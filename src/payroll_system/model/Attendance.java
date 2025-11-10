package payroll_system.model;
import java.time.LocalDate;



public class Attendance {
    private int daysWorkedId;
    private int empId;
    private double daysWorked;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    
    //Empty Constructor for Database Operation
    public Attendance(){}
    
    public Attendance(int empId, double daysWorked, LocalDate periodStart, LocalDate periodEnd){
        this.empId = empId;
        this.daysWorked = daysWorked;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }
    
    //Getters and Setters
    public int getDaysWorkedId() {
        return daysWorkedId;
    }

    public void setDaysWorkedId(int daysWorkedId) {
        this.daysWorkedId = daysWorkedId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public double getDaysWorked() {
        return daysWorked;
    }

    public void setDaysWorked(double daysWorked) {
        this.daysWorked = daysWorked;
    }

    public LocalDate getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDate periodStart) {
        this.periodStart = periodStart;
    }

    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
    }
    
    
    @Override
    public String toString(){
        return "Attendance [ " + "Employee Id = " + empId +
                ", Days Worked = " + daysWorked + ", Period Start = " + periodStart + ", Period End = " + periodEnd;
    }
}