package com.example.AEPB;

import lombok.*;

import java.util.UUID;

@Getter
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Ticket {
    private final String ticketNo;
    private final String parkingLotName;

    public Ticket(String parkingLotName) {
        ticketNo = UUID.randomUUID().toString();
        this.parkingLotName = parkingLotName;
    }

}
