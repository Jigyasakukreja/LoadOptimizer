package com.smartload.optimizer.model;

import java.time.LocalDate;

public record Order(
        String id,
        long payoutCents,
        long weightLbs,
        long volumeCuft,
        String origin,
        String destination,
        LocalDate pickupDate,
        LocalDate deliveryDate,
        boolean isHazmat
) {
}
