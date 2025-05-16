package com.example.helbhotel.main;

import java.util.ArrayList;
import java.util.List;

public class RoomAssignmentContext {
    private RoomAssignmentStrategy strategy;
    private final String ERROR_STRATEGY_NOT_SET = "Strategy not set.";
    private final String STRATEGY_RANDOM = "Random Assignment";
    private final String STRATEGY_QUIET_ZONE = "Quiet Zone";
    private final String STRATEGY_STAY_PURPOSE = "Stay Purpose";
    private final String STRATEGY_SEQUENTIAL = "Sequential Assignment";
    public void setStrategy(RoomAssignmentStrategy strategy) {
        this.strategy = strategy;
    }

    public void suggestRoomsAssigment(Hotel hotel) {
        if (strategy == null)
            throw new IllegalStateException(ERROR_STRATEGY_NOT_SET);
        strategy.suggestRoomsAssigment(hotel);
    }

    public List<String> getStrategyNames() {
        List<String> strategyList = new ArrayList<>();
        strategyList.add(STRATEGY_RANDOM);
        strategyList.add(STRATEGY_QUIET_ZONE);
        strategyList.add(STRATEGY_STAY_PURPOSE);
        strategyList.add(STRATEGY_SEQUENTIAL);
        return strategyList;
    }
}