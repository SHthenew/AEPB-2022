package com.example.AEPB;

import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Getter
@Data
public class Ticket {
    private final String ticketNo;

    public Ticket() {
        ticketNo = UUID.randomUUID().toString();
    }

    public boolean sameTicket(Ticket ticket) {
        return ticketNo.equals(ticket.getTicketNo());
    }
}
