//package com.restaurant.rms.models;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "users")
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer userId;
//
//    @Column(nullable = false, unique = true)
//    private String username;
//
//    @Column(nullable = false)
//    private String password;
//
//    @Column(nullable = false)
//    private String role;
//
//    @ManyToOne
//    @JoinColumn(name = "employee_id", nullable = false)
//    private Employee employee;
//
//    public User() {}
//
//    public User(String username, String password, String role, Employee employee) {
//        this.username = username;
//        this.password = password;
//        this.role = role;
//        this.employee = employee;
//    }
//
//    // Getters and setters
//
//    public Integer getUserId() { return userId; }
//    public void setUserId(Integer userId) { this.userId = userId; }
//    public String getUsername() { return username; }
//    public void setUsername(String username) { this.username = username; }
//    public String getPassword() { return password; }
//    public void setPassword(String password) { this.password = password; }
//    public String getRole() { return role; }
//    public void setRole(String role) { this.role = role; }
//    public Employee getEmployee() { return employee; }
//    public void setEmployee(Employee employee) { this.employee = employee; }
//}

package com.restaurant.rms.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_users_username", columnNames = {"username"}),
                @UniqueConstraint(name = "uk_users_employee", columnNames = {"employee_id"})
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotBlank
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String username;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String password;

    @NotBlank
    @Size(max = 40)
    @Column(nullable = false, length = 40)
    private String role;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public User() {}

    public User(String username, String password, String role, Employee employee) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.employee = employee;
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
}
