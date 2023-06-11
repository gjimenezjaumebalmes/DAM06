package com.guillemjimenez.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employees")
public class Employees {

    @Id
    @Column(name = "emp_no")
    private int emp_no;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "hire_date")
    private Date hireDate;

    @Column(name = "role")
    private String role;

    @Column(name = "salary")
    private double salary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deptno", nullable = false)
    private Departments departments;

    public Employees() {
    }

    public Employees(int emp_no, String firstName, String lastName, String gender, Date birth_date, Date hireDate, String role, double salary, Departments departments) {
        this.emp_no = emp_no;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birth_date;
        this.hireDate = hireDate;
        this.role = role;
        this.salary = salary;
        this.departments = departments;
    }

    public int getEmpNo() {
        return emp_no;
    }

    public void setEmpNo(int empNo) {
        this.emp_no = empNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birth_date) {
        this.birthDate = birth_date;
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
        return departments;
    }

    public void setDepartment(Departments departments) {
        this.departments = departments;
    }
}