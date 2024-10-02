package com.cloudBees.Train_Ticket_Booking_System.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseModel {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BaseModel() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }
}
