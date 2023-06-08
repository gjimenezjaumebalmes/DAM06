package Application;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class Departments {
    private int deptNo;

    private String deptName;

    private String location;

    private List<Employees> employees;

    // Constructors, getters and setters

    public Departments() {
        employees = new ArrayList<Employees>();
    }

    public Departments(String deptName, String location) {
        this.deptName = deptName;
        this.location = location;
        employees = new ArrayList<Employees>();
    }

    // Getters and setters

    public int getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(int deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Employees> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employees> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employees employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }
}
