package com.cloudBees.Train_Ticket_Booking_System.serviceImpl;

import com.cloudBees.Train_Ticket_Booking_System.dto.TicketDto;
import com.cloudBees.Train_Ticket_Booking_System.model.Ticket;
import com.cloudBees.Train_Ticket_Booking_System.model.User;
import com.cloudBees.Train_Ticket_Booking_System.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketServiceImpl implements TicketService {

    private Map<String, Ticket> tickets = new HashMap<>();
    private Queue<String> sectionA = new LinkedList<>(List.of("A1", "A2", "A3"));
    private Queue<String> sectionB = new LinkedList<>(List.of("B1", "B2", "B3"));

    @Override
    public Ticket bookTicket(TicketDto ticketDto) {
        String seat;
        String section;

        if (!sectionA.isEmpty()) {
            seat = sectionA.poll();
            section = "A"; // Assign section A
        } else {
            seat = sectionB.poll();
            section = "B"; // Assign section B
        }
        User user = new User(UUID.randomUUID().toString(), ticketDto.getFirstName(), ticketDto.getLastName(), ticketDto.getEmail());
        Ticket ticket = new Ticket(UUID.randomUUID().toString(), ticketDto.getFrom(), ticketDto.getTo(), 20.0, user, seat, section); // Include section
        tickets.put(ticketDto.getEmail(), ticket); // Store ticket in the map using email as the key
        return ticket;
    }

    @Override
    public Ticket getTicketByEmail(String email) {
        return tickets.get(email);
    }

    @Override
    public List<Map<String, String>> getUsersBySection(String section) {
        List<Map<String, String>> usersInSection = new ArrayList<>();
        for (Ticket ticket : tickets.values()) {
            if (ticket.getSeat().startsWith(section)) {
                Map<String, String> userSeatInfo = new HashMap<>();
                userSeatInfo.put("firstName", ticket.getUser().getFirstName());
                userSeatInfo.put("lastName", ticket.getUser().getLastName());
                userSeatInfo.put("email", ticket.getUser().getEmail());
                userSeatInfo.put("seat", ticket.getSeat());
                usersInSection.add(userSeatInfo);
            }
        }
        return usersInSection;
    }

    @Override
    public void removeUser(String email) {
        Ticket ticket = tickets.remove(email);
        if (ticket != null) {
            // Return the seat to the respective section
            String seat = ticket.getSeat();
            if (seat.startsWith("A")) {
                sectionA.offer(seat);
            } else {
                sectionB.offer(seat);
            }
        }
    }

    @Override
    public List<String> getAvailableSeats() {
        List<String> availableSeats = new ArrayList<>();
        availableSeats.addAll(sectionA); // Add available seats from Section A
        availableSeats.addAll(sectionB); // Add available seats from Section B
        return availableSeats; // Return list of available seats
    }

    @Override
    public Ticket modifyUserSeat(String email, String newSeat) {
        Ticket ticket = tickets.get(email); // Fetch the existing ticket

        if (ticket != null) {
            // Check if the new seat is available and not already assigned to this user
            if ((sectionA.contains(newSeat) || sectionB.contains(newSeat)) && !newSeat.equals(ticket.getSeat())) {

                // Return the old seat back to the available section
                if (ticket.getSeat().startsWith("A")) {
                    sectionA.offer(ticket.getSeat()); // Return old seat to Section A
                    ticket.setSection("A");
                } else {
                    sectionB.offer(ticket.getSeat()); // Return old seat to Section B
                    ticket.setSection("B");
                }

                // Update the seat in the existing ticket
                ticket.setSeat(newSeat);

                // Remove the new seat from availability
                if (sectionA.contains(newSeat)) {
                    sectionA.remove(newSeat);
                } else {
                    sectionB.remove(newSeat);
                }

                return ticket; // Return the modified ticket
            }
        }
        return null; // Return null if the seat is not available or user not found
    }



}
