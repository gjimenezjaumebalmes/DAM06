package com.example.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_no")
    private int empNo;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "hire_date")
    private Date hireDate;

    @Column(name = "role")
    private String role;

    @Column(name = "salary")
    private double salary;

    @ManyToOne
    @JoinColumn(name = "deptno")
    private Departments department;

    // Constructors, getters and setters

    public Employees() {
    }

    public Employees(String lastName, String firstName, Date birthDate, String gender, Date hireDate, String role, double salary, Departments department) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.hireDate = hireDate;
        this.role = role;
        this.salary = salary;
        this.department = department;
    }

    public Employees(String firstName, String lastName, String birthDate, String gender, String hireDate, String role, float salary, int deptno) {
    }

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

    public int getDeptNo() {
        return department != null ? Integer.parseInt(String.valueOf(department.getDeptNo())) : 0;
    }

    public List<Employees> getEmployees() {
        return department != null ? department.getEmployees() : new ArrayList<>();
    }
}