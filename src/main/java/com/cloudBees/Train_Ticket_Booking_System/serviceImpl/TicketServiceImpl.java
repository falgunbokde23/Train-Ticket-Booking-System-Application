package com.cloudBees.Train_Ticket_Booking_System.serviceImpl;

import com.cloudBees.Train_Ticket_Booking_System.constants.APP_MESSAGE;
import com.cloudBees.Train_Ticket_Booking_System.dto.TicketDto;
import com.cloudBees.Train_Ticket_Booking_System.exceptions.TicketException;
import com.cloudBees.Train_Ticket_Booking_System.model.Ticket;
import com.cloudBees.Train_Ticket_Booking_System.model.User;
import com.cloudBees.Train_Ticket_Booking_System.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketServiceImpl implements TicketService {

    private Map<String, List<Ticket>> tickets = new HashMap<>();
    private Queue<String> sectionA = new LinkedList<>(List.of("A1", "A2", "A3"));
    private Queue<String> sectionB = new LinkedList<>(List.of("B1", "B2", "B3"));

    @Override
    public Ticket bookTicket(TicketDto ticketDto) throws TicketException {
        String seat;
        String section;

        if (!sectionA.isEmpty()) {
            seat = sectionA.poll();
            section = "A"; // Assign section A
        } else if(!sectionB.isEmpty()){
            seat = sectionB.poll();
            section = "B"; // Assign section B
        } else {
            throw new TicketException(APP_MESSAGE.SEAT_IS_NOT_AVAILABLE);
        }
        User user = new User(UUID.randomUUID().toString(), ticketDto.getFirstName(), ticketDto.getLastName(), ticketDto.getEmail());
        Ticket ticket = new Ticket(UUID.randomUUID().toString(), ticketDto.getFrom(), ticketDto.getTo(), 20.0, user, seat, section); // Include section
        List<Ticket> ticketList = tickets.getOrDefault(ticketDto.getEmail(), new ArrayList<>());
        // Add the new ticket to the list
        ticketList.add(ticket);
        tickets.put(ticketDto.getEmail(), ticketList);
        return ticket;
    }

    @Override
    public List<Ticket> getTicketsByEmail(String email) throws TicketException {
        List<Ticket> ticketList = tickets.getOrDefault(email, new ArrayList<>()); // Return an empty list if no tickets found
        if(ticketList != null && !ticketList.isEmpty()){
            return ticketList;
        }
        throw new TicketException(APP_MESSAGE.TICKET_NOT_FOUND);
    }

    @Override
    public List<Map<String, String>> getUsersBySection(String section) throws TicketException {
        List<Map<String, String>> usersInSection = new ArrayList<>();
        for (List<Ticket> ticketList : tickets.values()) {
            for (Ticket ticket : ticketList) {
                if (ticket.getSection().equalsIgnoreCase(section)) {
                    Map<String, String> userSeatInfo = new HashMap<>();
                    userSeatInfo.put("firstName", ticket.getUser().getFirstName());
                    userSeatInfo.put("lastName", ticket.getUser().getLastName());
                    userSeatInfo.put("email", ticket.getUser().getEmail());
                    userSeatInfo.put("seat", ticket.getSeat());
                    usersInSection.add(userSeatInfo);
                }
            }
        }
        if(usersInSection.isEmpty()){
            throw new TicketException(APP_MESSAGE.TICKET_NOT_FOUND_WITH_GIVEN_SECTION);
        }
        return usersInSection;
    }

    @Override
    public void removeUser(String email, String seatBooked) throws TicketException {
        List<Ticket> userTickets = tickets.get(email); // Fetch the user's tickets
        if (userTickets != null) {
            // Find and remove the ticket for the specified seat
            Ticket ticketToRemove = userTickets.stream()
                    .filter(ticket -> ticket.getSeat().equals(seatBooked))
                    .findFirst()
                    .orElse(null);

            if (ticketToRemove != null) {
                userTickets.remove(ticketToRemove); // Remove the ticket from the user's list

                // Return the seat to the respective section
                returnSeatToSection(ticketToRemove.getSeat());
            } else {
                throw new TicketException(APP_MESSAGE.TICKET_NOT_FOUND_WITH_USER_SEAT_NO);
            }

        }
    }

    @Override
    public List<String> getAvailableSeats() throws TicketException {
        List<String> availableSeats = new ArrayList<>();
        availableSeats.addAll(sectionA); // Add available seats from Section A
        availableSeats.addAll(sectionB); // Add available seats from Section B
        // Check if no seats are available
        if (availableSeats.isEmpty()) {
            throw new TicketException(APP_MESSAGE.SEAT_IS_NOT_AVAILABLE);// Throw exception if no seats are available
        }

        return availableSeats;
    }

    @Override
    public Ticket modifyUserSeat(String email, String currentSeat, String newSeat) throws TicketException {
        List<Ticket> userTickets = tickets.get(email); // Fetch the user's tickets

        if (userTickets != null) {
            // Find the ticket for the specified current seat
            Ticket ticketToModify = userTickets.stream()
                    .filter(ticket -> ticket.getSeat().equals(currentSeat))
                    .findFirst()
                    .orElse(null);

            if (ticketToModify != null) {
                // Check if the new seat is available and not already assigned to this user
                if ((sectionA.contains(newSeat) || sectionB.contains(newSeat)) && !newSeat.equals(ticketToModify.getSeat())) {
                    // Return the old seat back to the available section
                    returnSeatToSection(ticketToModify.getSeat());

                    // Update the seat in the existing ticket
                    ticketToModify.setSeat(newSeat);

                    // Remove the new seat from availability
                    if (sectionA.contains(newSeat)) {
                        sectionA.remove(newSeat);
                    } else {
                        sectionB.remove(newSeat);
                    }

                    return ticketToModify; // Return the modified ticket
                }
            }
        }
        throw new TicketException(APP_MESSAGE.SEAT_IS_NOT_AVAILABLE); // Return null if the seat is not available or user not found
    }

    private void returnSeatToSection(String seat) {
        if (seat.startsWith("A")) {
            sectionA.offer(seat); // Return to Section A
        } else  {
            sectionB.offer(seat); // Return to Section B
        }
    }



}
