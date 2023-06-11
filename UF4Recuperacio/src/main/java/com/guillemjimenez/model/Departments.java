package com.guillemjimenez.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "departments")

public class Departments {

    @Id
    @Column(name = "deptno")
    private int deptNo;

    @Column(name = "deptname")
    private String deptName;

    @Column(name = "location")
    private String location;


    public Departments() {
    }

    public Departments(int deptNo, String deptName, String location) {
        this.deptNo = deptNo;
        this.deptName = deptName;
        this.location = location;
    }

    public int getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(int deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptname) {
        this.deptName = deptname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}