package com.cloudBees.Train_Ticket_Booking_System.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ticket extends BaseModel {
    private String id;
    private String from;
    private String to;
    private double price;
    private User user;
    private String seat;
    private String section;

    public Ticket(String id, String from, String to, double price, User user, String seat, String section) {
        super(); // Call BaseModel constructor to set createdAt and updatedAt
        this.id = id;
        this.from = from;
        this.to = to;
        this.price = price;
        this.user = user;
        this.seat = seat;
        this.section = section;
    }

    public void setSeat(String seat) {
        this.seat = seat;
        updateTimestamp(); // Update the updatedAt timestamp when seat is modified
    }
}
