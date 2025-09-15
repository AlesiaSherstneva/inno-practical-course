package com.innowise.skynet.runner;

import com.innowise.skynet.SimulationRunner;
import com.innowise.skynet.service.faction.Wednesday;
import com.innowise.skynet.service.faction.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimulationRunnerTest {
    private SimulationRunner runner;

    @BeforeEach
    void setUp() {
        runner = new SimulationRunner();
    }

    @Test
    void runSimulationTest() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            runner.runSimulation();

            int worldArmy = runner.getWorldArmySize();
            int wednesdayArmy = runner.getWednesdayArmySize();

            String simulationResult = runner.getWinner();

            String expectedSimulationResult;
            if (worldArmy == wednesdayArmy) {
                expectedSimulationResult = "Draw, no winner";
            } else {
                expectedSimulationResult = String.format("The winner is %s faction", worldArmy > wednesdayArmy
                        ? World.class.getSimpleName() : Wednesday.class.getSimpleName());
            }

            assertEquals(expectedSimulationResult, simulationResult);
        }
    }
}