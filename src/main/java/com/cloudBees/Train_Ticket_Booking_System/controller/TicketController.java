package com.cloudBees.Train_Ticket_Booking_System.controller;

import com.cloudBees.Train_Ticket_Booking_System.constants.APP_MESSAGE;
import com.cloudBees.Train_Ticket_Booking_System.dto.TicketDto;
import com.cloudBees.Train_Ticket_Booking_System.enums.Status;
import com.cloudBees.Train_Ticket_Booking_System.exceptions.TicketException;
import com.cloudBees.Train_Ticket_Booking_System.model.CustomResponse;
import com.cloudBees.Train_Ticket_Booking_System.model.Ticket;
import com.cloudBees.Train_Ticket_Booking_System.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/ticket")
@Tag(name = "Ticket Controller", description = "API for managing Ticket (**Dev @Falgun**)")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    /**
     * Books a train ticket.
     *
     * @param ticketDto the details of the ticket to be booked
     * @return a response entity containing the booking status and ticket details
     * @throws TicketException if there are no available seats or if an error occurs during booking
     */
    @Operation(summary = "Book a train ticket", description = "Allows a user to book a train ticket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket successfully booked",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponse.class)))
    })
    @PostMapping("/book")
    public ResponseEntity<CustomResponse> bookTicket(@Valid @RequestBody TicketDto ticketDto) throws TicketException {
        Ticket ticket = ticketService.bookTicket(ticketDto);
        CustomResponse response = new CustomResponse(HttpStatus.OK.value(), Status.SUCCESS.name(), ticket);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Retrieves a receipt to view all ticket details by user email.
     *
     * @param email the email of the user whose ticket details are to be retrieved
     * @return a response entity containing the ticket details or a not found status
     * @throws TicketException if the email does not match any ticket records
     */
    @Operation(summary = "View receipt by user", description = "Retrieves receipt to view all the ticket details by user email.")
    @GetMapping("/receipt/{email}")
    public ResponseEntity<CustomResponse> getTicketByEmail(@PathVariable String email) throws TicketException {
        List<Ticket> tickets = ticketService.getTicketsByEmail(email);
        CustomResponse response = new CustomResponse(tickets != null && !tickets.isEmpty() ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value(),
                tickets != null ? Status.SUCCESS.name() : Status.FAILED.name(), tickets);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Retrieves a list of users seated in a specified section.
     *
     * @param section the section to check for seated users
     * @return a response entity containing the list of users in the specified section
     */
    @Operation(summary = "View Users with seat by Section", description = "Retrieves a list of users seated in a specified section.")
    @GetMapping("/section/{section}")
    public ResponseEntity<CustomResponse> getUsersBySection(@PathVariable String section) throws TicketException {
        List<Map<String, String>> users = ticketService.getUsersBySection(section);
        CustomResponse response = new CustomResponse(HttpStatus.OK.value(), Status.SUCCESS.name(), users);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Removes a specific user from the train.
     *
     * @param email the email of the user to be removed
     * @param seatNumber the seat number to be freed
     * @return a response entity indicating the result of the operation
     * @throws TicketException if the user or seat number does not exist
     */
    @Operation(summary = "Remove user from train", description = "Remove specific user from the train.")
    @DeleteMapping("/remove/{email}")
    public ResponseEntity<CustomResponse> removeUser(@PathVariable String email, @RequestParam String seatNumber) throws TicketException {
        ticketService.removeUser(email, seatNumber);
        CustomResponse response = new CustomResponse(HttpStatus.OK.value(), Status.SUCCESS.name(), APP_MESSAGE.TICKET_DELETED_SUCCESSFULLY);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Modifies a user's train seat.
     *
     * @param email      the email of the user whose seat is to be modified
     * @param currentSeat the current seat to be changed
     * @param newSeat    the new seat to assign to the user
     * @return a response entity containing the updated ticket details or a not found status
     * @throws TicketException if the user or seat does not exist, or if the new seat is not available
     */
    @Operation(summary = "Modify a train seat", description = "Allows a user to modify a train seat.")
    @PutMapping("/modify-seat/{email}")
    public ResponseEntity<CustomResponse> modifyUserSeat(@PathVariable String email, @RequestParam String currentSeat, @RequestParam String newSeat) throws TicketException {
        Ticket updatedTicket = ticketService.modifyUserSeat(email, currentSeat, newSeat);
        CustomResponse response = new CustomResponse(updatedTicket != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value(),
                updatedTicket != null ? Status.SUCCESS.name() : Status.FAILED.name(), updatedTicket);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Checks available train seats.
     *
     * @return a response entity containing a list of available seats
     * @throws TicketException if no seats are available
     */
    @Operation(summary = "Check available train seats", description = "Allows a user to check available train seats.")
    @GetMapping("/available-seats")
    public ResponseEntity<CustomResponse> getAvailableSeats() throws TicketException {
        List<String> availableSeats = ticketService.getAvailableSeats();
        CustomResponse response = new CustomResponse(HttpStatus.OK.value(), Status.SUCCESS.name(), availableSeats);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}
