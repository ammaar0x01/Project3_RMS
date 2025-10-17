/*
 * Developer: Ammaar
 * Started: 22.04.25
 * Updated: 05.06.25
*/

package com.restaurant.rms.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name="FoodOrder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime orderTime;
    private double estimatedWaitTime;
    // calculate this in the back-end, instead of a user entering a value?
    private double totalPrice;
    private String status;
    // private String orderDetails;
    // private ArrayList<String> orderDetails;

    public Order(){}
    public Order(LocalDateTime orderTime, double estimatedWaitTime, double totalPrice, String status) {
        this.orderTime = orderTime;
        this.estimatedWaitTime = estimatedWaitTime;
        this.totalPrice = totalPrice;
        this.status = status;
    }
    // ----------------------------------

    // Getters
    public int getId() {
        return id;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public double getEstimatedWaitTime() {
        return estimatedWaitTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public void setEstimatedWaitTime(double estimatedWaitTime) {
        this.estimatedWaitTime = estimatedWaitTime;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderTime=" + orderTime +
                ", estimatedWaitTime=" + estimatedWaitTime +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                '}';
    }
}
