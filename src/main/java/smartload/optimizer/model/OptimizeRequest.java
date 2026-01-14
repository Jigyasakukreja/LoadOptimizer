package com.smartload.optimizer.model;

import java.util.List;

public record OptimizeRequest(
        Truck truck,
        List<Order> orders
) {
}
