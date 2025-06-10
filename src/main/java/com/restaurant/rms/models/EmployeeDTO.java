package com.restaurant.rms.models;

import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

public class EmployeeDTO {

    @NotEmpty(message="Field cannot be empty")
    private String empFirstName;

    @NotEmpty(message="Field cannot be empty")
    private String empLastName;

    private Date empStartDate;

    @NotEmpty(message="Field cannot be empty")
    private String role;
    // --------------------------------

    // setters and getters
    public @NotEmpty(message = "Field cannot be empty") String getEmpFirstName() {
        return empFirstName;
    }

    public void setEmpFirstName(@NotEmpty(message = "Field cannot be empty") String empFirstName) {
        this.empFirstName = empFirstName;
    }

    public @NotEmpty(message = "Field cannot be empty") String getEmpLastName() {
        return empLastName;
    }

    public void setEmpLastName(@NotEmpty(message = "Field cannot be empty") String empLastName) {
        this.empLastName = empLastName;
    }

    public Date getEmpStartDate() {
        return empStartDate;
    }

    public @NotEmpty(message = "Field cannot be empty") String getRole() {
        return role;
    }

    public void setRole(@NotEmpty(message = "Field cannot be empty") String role) {
        this.role = role;
    }
}
