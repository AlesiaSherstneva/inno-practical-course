package com.innowise.skynet.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Data Transfer Object representing the results of a simulation run.
 */
@Getter
@Builder
public class SimulationResultDto {
    /** The final size of the World faction robot army */
    private int worldArmySize;

    /** The final size of the Wednesday faction robot army */
    private int wednesdayArmySize;

    /** The winner of the simulation based on army size comparison */
    private String winner;
}