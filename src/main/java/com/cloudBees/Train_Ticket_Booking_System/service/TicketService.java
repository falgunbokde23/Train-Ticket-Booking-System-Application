package com.cloudBees.Train_Ticket_Booking_System.service;

import com.cloudBees.Train_Ticket_Booking_System.dto.TicketDto;
import com.cloudBees.Train_Ticket_Booking_System.model.Ticket;

import java.util.List;
import java.util.Map;

public interface TicketService {

    Ticket bookTicket(TicketDto ticketDto);

    Ticket getTicketByEmail(String email);

    List<Map<String, String>> getUsersBySection(String section);

    void removeUser(String email);

    List<String> getAvailableSeats();

    Ticket modifyUserSeat(String email, String newSeat);
}
