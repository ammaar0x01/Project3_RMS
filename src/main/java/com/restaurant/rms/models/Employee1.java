package com.restaurant.rms.models;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="Employee1")
public class Employee1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empId;

    @Column(columnDefinition = "TEXT")
    private String empFirstName;
    private String empLastName;
    private Date empStartDate;
    private String role;

    public Employee1(){}
//    public Employee1(int empId, String empFirstName, String empLastName, String empStartDate, String role) {
//        this.empId = empId;
//        this.empFirstName = empFirstName;
//        this.empLastName = empLastName;
//        this.empStartDate = empStartDate;
//        this.role = role;
//    }
//    public Employee1(int empId, String empFirstName, String empLastName, String empStartDate) {
//        this.empId = empId;
//        this.empFirstName = empFirstName;
//        this.empLastName = empLastName;
//        this.empStartDate = empStartDate;
//    }

    // simple
    public Employee1(String empFirstName, String empLastName, Date date, String role) {
        this.empFirstName = empFirstName;
        this.empLastName = empLastName;
        this.empStartDate = date;
        this.role = role;
    }
    // -------------------------------------

    // getters
    public int getEmpId() {
        return empId;
    }

    public String getEmpFirstName() {
        return empFirstName;
    }

    public String getEmpLastName() {
        return empLastName;
    }

    public Date getEmpStartDate() {
        return empStartDate;
    }

    public String getRole(){
        return role;
    }

    @Override
    public String toString() {
        return "Employee1{" +
                "empId=" + empId +
                ", empFirstName='" + empFirstName + '\'' +
                ", empLastName='" + empLastName + '\'' +
                ", empStartDate=" + empStartDate +
                ", role='" + role + '\'' +
                '}';
    }

}
