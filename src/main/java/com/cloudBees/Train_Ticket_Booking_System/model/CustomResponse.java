package com.cloudBees.Train_Ticket_Booking_System.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse implements Serializable {
    private Integer code;
    private String message;
    private Object response;
}
