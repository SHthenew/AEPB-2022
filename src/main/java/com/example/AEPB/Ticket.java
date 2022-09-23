package com.example.AEPB;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Ticket {
    private final String ticketNo;

    public Ticket() {
        ticketNo = UUID.randomUUID().toString();
    }

}
