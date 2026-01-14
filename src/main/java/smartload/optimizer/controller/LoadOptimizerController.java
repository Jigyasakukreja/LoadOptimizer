package com.smartload.optimizer.controller;

import com.smartload.optimizer.exception.InvalidRequestException;
import com.smartload.optimizer.model.OptimizationResponse;
import com.smartload.optimizer.model.OptimizeRequest;
import com.smartload.optimizer.service.LoadOptimizerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/load-optimizer")
public class LoadOptimizerController {
    private final LoadOptimizerService service;

    public LoadOptimizerController(LoadOptimizerService service) {
        this.service = service;
    }

    @PostMapping("/optimize")
    public ResponseEntity<OptimizationResponse> optimize(
            @RequestBody OptimizeRequest request) {

        if (request.truck() == null) {
            throw new InvalidRequestException("Truck details must be provided");
        }

        if (request.truck().maxWeightLbs() <= 0
                || request.truck().maxVolumeCuft() <= 0) {
            throw new InvalidRequestException("Truck capacity must be positive");
        }

        if (request.orders() == null) {
            throw new InvalidRequestException("Orders list must not be null");
        }

        return ResponseEntity.ok(
                service.optimize(request.truck(), request.orders()));
    }
}
