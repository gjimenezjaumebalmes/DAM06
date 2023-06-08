package Application;

import java.util.Date;


public class Employees {

    private int empNo;


    private String lastName;


    private String firstName;


    private Date birthDate;


    private String gender;


    private Date hireDate;


    private String role;


    private double salary;


    private Departments department;

    // Constructors, getters and setters

    public Employees(String firstName, String lastName, String birthDate, String gender, String hireDate, String role, float salary) {
    }

    public Employees(String lastName, String firstName, Date birthDate, String gender, Date hireDate, String role, double salary) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.hireDate = hireDate;
        this.role = role;
        this.salary = salary;
    }

    // Getters and setters

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Departments getDepartment() {
        return department;
    }

    public void setDepartment(Departments department) {
        this.department = department;
    }
}
