//package com.restaurant.rms.models;
//
//import jakarta.persistence.*;
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "EmployeeSalary")
//public class EmployeeSalary {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int employeePaymentId;
//
//    @Column(nullable = false)
//    private double employeePaymentAmount;
//
//    @Column(nullable = false)
//    private String employeePaymentMethod;
//
//    @Column
//    private LocalDate lastPayment;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "employee_id")
//    private Employee employee;
//
//    public EmployeeSalary() {}
//
//    public EmployeeSalary(double employeePaymentAmount, String employeePaymentMethod, LocalDate lastPayment, Employee employee) {
//        this.employeePaymentAmount = employeePaymentAmount;
//        this.employeePaymentMethod = employeePaymentMethod;
//        this.lastPayment = lastPayment;
//        this.employee = employee;
//    }
//
//    // Getters & Setters
//    public int getEmployeePaymentId() {
//        return employeePaymentId;
//    }
//    public void setEmployeePaymentId(int employeePaymentId) {
//        this.employeePaymentId = employeePaymentId;
//    }
//
//    public double getEmployeePaymentAmount() {
//        return employeePaymentAmount;
//    }
//    public void setEmployeePaymentAmount(double employeePaymentAmount) {
//        this.employeePaymentAmount = employeePaymentAmount;
//    }
//
//    public String getEmployeePaymentMethod() {
//        return employeePaymentMethod;
//    }
//    public void setEmployeePaymentMethod(String employeePaymentMethod) {
//        this.employeePaymentMethod = employeePaymentMethod;
//    }
//
//    public LocalDate getLastPayment() {
//        return lastPayment;
//    }
//    public void setLastPayment(LocalDate lastPayment) {
//        this.lastPayment = lastPayment;
//    }
//
//    public Employee getEmployee() {
//        return employee;
//    }
//    public void setEmployee(Employee employee) {
//        this.employee = employee;
//    }
//
//    @Override
//    public String toString() {
//        return "EmployeeSalary{" +
//                "employeePaymentId=" + employeePaymentId +
//                ", employeePaymentAmount=" + employeePaymentAmount +
//                ", employeePaymentMethod='" + employeePaymentMethod + '\'' +
//                ", lastPayment=" + lastPayment +
//                ", employee=" + (employee != null ? employee.getId() : null) +
//                '}';
//    }
//}

package com.restaurant.rms.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
// Matches existing naming convention observed in the project:
@Table(name = "EmployeeSalary")
public class EmployeeSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeePaymentId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "employee_payment_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal employeePaymentAmount;

    @NotBlank
    @Column(name = "employee_payment_method", nullable = false, length = 40)
    private String employeePaymentMethod;

    @Column(name = "last_payment")
    private LocalDate lastPayment;

    public EmployeeSalary() {}

    public EmployeeSalary(Employee employee, BigDecimal amount, String method, LocalDate lastPayment) {
        this.employee = employee;
        this.employeePaymentAmount = amount;
        this.employeePaymentMethod = method;
        this.lastPayment = lastPayment;
    }

    public Integer getEmployeePaymentId() { return employeePaymentId; }
    public void setEmployeePaymentId(Integer employeePaymentId) { this.employeePaymentId = employeePaymentId; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public BigDecimal getEmployeePaymentAmount() { return employeePaymentAmount; }
    public void setEmployeePaymentAmount(BigDecimal employeePaymentAmount) { this.employeePaymentAmount = employeePaymentAmount; }

    public String getEmployeePaymentMethod() { return employeePaymentMethod; }
    public void setEmployeePaymentMethod(String employeePaymentMethod) { this.employeePaymentMethod = employeePaymentMethod; }

    public LocalDate getLastPayment() { return lastPayment; }
    public void setLastPayment(LocalDate lastPayment) { this.lastPayment = lastPayment; }

    @Override
    public String toString() {
        return "EmployeeSalary{" +
                "employeePaymentId=" + employeePaymentId +
                ", employeeId=" + (employee != null ? employee.getId() : null) +
                ", employeePaymentAmount=" + employeePaymentAmount +
                ", employeePaymentMethod='" + employeePaymentMethod + '\'' +
                ", lastPayment=" + lastPayment +
                '}';
    }
}

