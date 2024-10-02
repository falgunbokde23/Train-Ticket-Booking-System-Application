package com.cloudBees.Train_Ticket_Booking_System.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TicketDto {
    @NotBlank(message = "From location cannot be empty")
    private String from;

    @NotBlank(message = "To location cannot be empty")
    private String to;

    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be empty")
    private String email;
}
