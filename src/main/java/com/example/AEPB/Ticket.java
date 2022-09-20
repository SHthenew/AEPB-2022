package com.example.AEPB;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Getter
@Data
@Builder
public class Ticket {
    private final String ticketNo;

    public Ticket() {
        ticketNo = UUID.randomUUID().toString();
    }

    public boolean sameTicket(Ticket ticket) {
        return ticketNo.equals(ticket.getTicketNo());
    }
}
