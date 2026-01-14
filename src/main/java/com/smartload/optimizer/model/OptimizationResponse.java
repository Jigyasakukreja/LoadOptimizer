package com.smartload.optimizer.model;

import java.util.List;

public record OptimizationResponse(
        String truckId,
        List<String> selectedOrderIds,
        long totalPayoutCents,
        long totalWeightLbs,
        long totalVolumeCuft,
        double utilizationWeightPercent,
        double utilizationVolumePercent
) {
    public static OptimizationResponse empty(String truckId) {
        return new OptimizationResponse(truckId, List.of(), 0, 0, 0, 0, 0);
    }
}
