package com.restaurant.rms.models;

public class Employee {
    private String empFirstName;
    private String empLastName;
    private String empStartDate;
    private int empId;

    private Employee(EmployeeBuilder builder) {
        this.empFirstName = builder.empFirstName;
        this.empLastName = builder.empLastName;
        this.empStartDate = builder.empStartDate;
        this.empId = builder.empId;
    }

    public String getEmpFirstName() {
        return empFirstName;
    }

    public String getEmpLastName() {
        return empLastName;
    }

    public String getEmpStartDate() {
        return empStartDate;
    }

    public int getEmpId() {
        return empId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empFirstName='" + empFirstName + '\'' +
                ", empLastName='" + empLastName + '\'' +
                ", empStartDate='" + empStartDate + '\'' +
                ", empId=" + empId +
                '}';
    }

    // ----------------------------------------

    public static class EmployeeBuilder{
        private String empFirstName;
        private String empLastName;
        private String empStartDate;
        private int empId;

        public EmployeeBuilder(){

        }

        // ----------------------------------------

        public EmployeeBuilder setEmpFirstName(String empFirstName) {
            this.empFirstName = empFirstName;
            return this;
        }

        public EmployeeBuilder setEmpLastName(String empLastName) {
            this.empLastName = empLastName;
            return this;
        }

        public EmployeeBuilder setEmpStartDate(String empStartDate) {
            this.empStartDate = empStartDate;
            return this;
        }

        public EmployeeBuilder setEmpId(int empId) {
            this.empId = empId;
            return this;
        }

        public Employee build(){
            return new Employee(this);
        }
    }
}
