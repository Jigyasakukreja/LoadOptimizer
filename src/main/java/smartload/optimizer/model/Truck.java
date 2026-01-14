package com.smartload.optimizer.model;

public record Truck(
        String id,
        long maxWeightLbs,
        long maxVolumeCuft
) {
}
