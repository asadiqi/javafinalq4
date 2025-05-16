package com.example.helbhotel.main;

import java.util.ArrayList;
import java.util.List;

public class RoomAssignmentContext {
    private RoomAssignmentStrategy strategy;

    public void setStrategy(RoomAssignmentStrategy strategy) {
        this.strategy = strategy;
    }

    public void suggestRoomsAssigment(Hotel hotel) {
        if (strategy == null)
            throw new IllegalStateException("Strategy not set.");
        strategy.suggestRoomsAssigment(hotel);
    }

    public List<String> getStrategyNames() {
        List<String> strategyList = new ArrayList<>();
        strategyList.add("Random Assignment");
        strategyList.add("Quiet Zone");
        strategyList.add("Stay Purpose");
        strategyList.add("Sequential Assignment");
        return strategyList;
    }
}