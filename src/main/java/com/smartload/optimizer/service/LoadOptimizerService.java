package com.smartload.optimizer.service;

import com.smartload.optimizer.model.OptimizationResponse;
import com.smartload.optimizer.model.Order;
import com.smartload.optimizer.model.Truck;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoadOptimizerService {
    public OptimizationResponse optimize(Truck truck, List<Order> orders) {

        int n = orders.size();
        long bestPayout = 0;
        int bestMask = 0;

        for (int mask = 0; mask < (1 << n); mask++) {
            long weight = 0, volume = 0, payout = 0;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    Order o = orders.get(i);

                    weight += o.weightLbs();
                    volume += o.volumeCuft();
                    payout += o.payoutCents();

                    if (weight > truck.maxWeightLbs()
                            || volume > truck.maxVolumeCuft()) {
                        break;
                    }
                }
            }

            if (weight <= truck.maxWeightLbs()
                    && volume <= truck.maxVolumeCuft()
                    && payout > bestPayout) {
                bestPayout = payout;
                bestMask = mask;
            }
        }

        return buildResponse(truck, orders, bestMask, bestPayout);
    }

    private OptimizationResponse buildResponse(
            Truck truck, List<Order> orders, int mask, long payout) {

        List<String> ids = new ArrayList<>();
        long weight = 0, volume = 0;

        for (int i = 0; i < orders.size(); i++) {
            if ((mask & (1 << i)) != 0) {
                Order o = orders.get(i);
                ids.add(o.id());
                weight += o.weightLbs();
                volume += o.volumeCuft();
            }
        }

        return new OptimizationResponse(
                truck.id(),
                ids,
                payout,
                weight,
                volume,
                round(weight * 100.0 / truck.maxWeightLbs()),
                round(volume * 100.0 / truck.maxVolumeCuft())
        );
    }

    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
