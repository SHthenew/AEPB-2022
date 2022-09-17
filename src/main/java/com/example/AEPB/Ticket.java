package com.example.AEPB;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Ticket {
    private final String ticketNo;

    public Ticket() {
        ticketNo = UUID.randomUUID().toString();
    }
}
