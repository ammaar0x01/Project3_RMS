package com.restaurant.rms.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "EmployeeSalary")
public class EmployeeSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeePaymentId;

    @Column(nullable = false)
    private double employeePaymentAmount;

    @Column(nullable = false)
    private String employeePaymentMethod;

    @Column
    private LocalDate lastPayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public EmployeeSalary() {}

    public EmployeeSalary(double employeePaymentAmount, String employeePaymentMethod, LocalDate lastPayment, Employee employee) {
        this.employeePaymentAmount = employeePaymentAmount;
        this.employeePaymentMethod = employeePaymentMethod;
        this.lastPayment = lastPayment;
        this.employee = employee;
    }

    // Getters & Setters
    public int getEmployeePaymentId() {
        return employeePaymentId;
    }
    public void setEmployeePaymentId(int employeePaymentId) {
        this.employeePaymentId = employeePaymentId;
    }

    public double getEmployeePaymentAmount() {
        return employeePaymentAmount;
    }
    public void setEmployeePaymentAmount(double employeePaymentAmount) {
        this.employeePaymentAmount = employeePaymentAmount;
    }

    public String getEmployeePaymentMethod() {
        return employeePaymentMethod;
    }
    public void setEmployeePaymentMethod(String employeePaymentMethod) {
        this.employeePaymentMethod = employeePaymentMethod;
    }

    public LocalDate getLastPayment() {
        return lastPayment;
    }
    public void setLastPayment(LocalDate lastPayment) {
        this.lastPayment = lastPayment;
    }

    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "EmployeeSalary{" +
                "employeePaymentId=" + employeePaymentId +
                ", employeePaymentAmount=" + employeePaymentAmount +
                ", employeePaymentMethod='" + employeePaymentMethod + '\'' +
                ", lastPayment=" + lastPayment +
                ", employee=" + (employee != null ? employee.getId() : null) +
                '}';
    }
}
