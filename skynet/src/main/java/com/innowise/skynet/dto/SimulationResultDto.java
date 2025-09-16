package com.innowise.skynet.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SimulationResultDto {
    private int worldArmySize;
    private int wednesdayArmySize;
    private String winner;
}