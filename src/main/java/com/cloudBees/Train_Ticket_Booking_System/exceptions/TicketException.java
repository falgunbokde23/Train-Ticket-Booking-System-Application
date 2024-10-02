package com.cloudBees.Train_Ticket_Booking_System.exceptions;


public class TicketException extends Exception{
    private String msg;

    public TicketException(String msg) {
        super(msg);
        this.msg = msg;
    }
    public String getMessage() {
        return msg;
    }

}
