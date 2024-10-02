package com.cloudBees.Train_Ticket_Booking_System.service;

import com.cloudBees.Train_Ticket_Booking_System.dto.TicketDto;
import com.cloudBees.Train_Ticket_Booking_System.exceptions.TicketException;
import com.cloudBees.Train_Ticket_Booking_System.model.Ticket;

import java.util.List;
import java.util.Map;

public interface TicketService {

    Ticket bookTicket(TicketDto ticketDto) throws TicketException;

    List<Ticket> getTicketsByEmail(String email) throws TicketException;

    List<Map<String, String>> getUsersBySection(String section) throws TicketException;

    void removeUser(String email, String seatBooked) throws TicketException;

    List<String> getAvailableSeats() throws TicketException;

    Ticket modifyUserSeat(String email, String currentSeat, String newSeat) throws TicketException;
}
