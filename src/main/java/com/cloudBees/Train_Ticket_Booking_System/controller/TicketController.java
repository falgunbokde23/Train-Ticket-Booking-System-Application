package com.cloudBees.Train_Ticket_Booking_System.controller;

import com.cloudBees.Train_Ticket_Booking_System.dto.TicketDto;
import com.cloudBees.Train_Ticket_Booking_System.enums.Status;
import com.cloudBees.Train_Ticket_Booking_System.model.CustomResponse;
import com.cloudBees.Train_Ticket_Booking_System.model.Ticket;
import com.cloudBees.Train_Ticket_Booking_System.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ticket")
public class TicketController {


    @Autowired
    private TicketService ticketService;

    @Operation(summary = "Book a train ticket", description = "Allows a user to book a train ticket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket successfully booked",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponse.class))
    )
    })
    @PostMapping("/book")
    public ResponseEntity<CustomResponse> bookTicket(@Valid @RequestBody TicketDto ticketDto) {
        Ticket ticket = ticketService.bookTicket(ticketDto);
        CustomResponse response = new CustomResponse(HttpStatus.OK.value(), Status.SUCCESS.name(), ticket);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "View receipt by user", description = "Retrieves receipt to view all the ticket details by by user email.")
    @GetMapping("/receipt/{email}")
    public ResponseEntity<CustomResponse> getTicketByEmail(@PathVariable String email) {
        Ticket ticket = ticketService.getTicketByEmail(email);
        CustomResponse response = new CustomResponse(ticket != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value(), ticket != null ? Status.SUCCESS.name() : Status.FAILED.name(), ticket);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "View Users with seat by Section", description = "Retrieves a list of users seated in a specified section.")
    @GetMapping("/section/{section}")
    public ResponseEntity<CustomResponse> getUsersBySection(@PathVariable String section) {
        List<Map<String, String>> users = ticketService.getUsersBySection(section);
        CustomResponse response = new CustomResponse(HttpStatus.OK.value(), Status.SUCCESS.name(), users);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Remove user from train", description = " Remove specific user from the train.")
    @DeleteMapping("/remove/{email}")
    public ResponseEntity<CustomResponse> removeUser(@PathVariable String email) {
        ticketService.removeUser(email);
        CustomResponse response = new CustomResponse(HttpStatus.OK.value(), Status.SUCCESS.name(), null);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Modify a train seat", description = "Allows a user to modify a train seat.")
    @PutMapping("/modify-seat/{email}")
    public ResponseEntity<CustomResponse> modifyUserSeat(@PathVariable String email, @RequestParam String newSeat) {
        Ticket updatedTicket = ticketService.modifyUserSeat(email, newSeat);
        CustomResponse response = new CustomResponse(updatedTicket != null  ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value(),
                updatedTicket != null ? Status.SUCCESS.name() : Status.FAILED.name(), updatedTicket);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Check available train seats", description = "Allows a user to check available train seats.")
    @GetMapping("/available-seats")
    public ResponseEntity<CustomResponse> getAvailableSeats() {
        List<String> availableSeats = ticketService.getAvailableSeats();
        CustomResponse response = new CustomResponse(HttpStatus.OK.value(), Status.SUCCESS.name(), availableSeats);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}
