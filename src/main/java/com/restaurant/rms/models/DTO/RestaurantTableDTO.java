package com.restaurant.rms.models.DTO;

import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

public class RestaurantTableDTO {

    @NotEmpty(message = "Table number cannot be empty")
    private int tableNumber;

    @NotEmpty(message = "Capacity cannot be empty")
    private int capacity;

    @NotEmpty(message = "Status cannot be empty")
    private String status;

    @NotEmpty(message = "Last cleaned date cannot be empty")
    private Date lastCleaned;
    // ----------------------------------

    // getters and setters
    public @NotEmpty(message = "Table number cannot be empty") int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(@NotEmpty(message = "Table number cannot be empty") int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public @NotEmpty(message = "Capacity cannot be empty") int getCapacity() {
        return capacity;
    }

    public void setCapacity(@NotEmpty(message = "Capacity cannot be empty") int capacity) {
        this.capacity = capacity;
    }

    public @NotEmpty(message = "Status cannot be empty") String getStatus() {
        return status;
    }

    public void setStatus(@NotEmpty(message = "Status cannot be empty") String status) {
        this.status = status;
    }

    public @NotEmpty(message = "Last cleaned date cannot be empty") Date getLastCleaned() {
        return lastCleaned;
    }

//    public void setLastCleaned(@NotEmpty(message = "Last cleaned date cannot be empty") Date lastCleaned) {
//        this.lastCleaned = lastCleaned;
//    }


}
