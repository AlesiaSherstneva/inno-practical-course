package com.innowise.skynet.runner;

import com.innowise.skynet.SimulationRunner;
import com.innowise.skynet.dto.SimulationResultDto;
import com.innowise.skynet.service.faction.impl.Wednesday;
import com.innowise.skynet.service.faction.impl.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
            checkSimulationResults(runner.runSimulation());
        }
    }

    @Test
    void runSeveralSimulationsTest() throws InterruptedException {
        List<SimulationResultDto> simulationResults = runner.runSimulation(5);

        for (SimulationResultDto simulationResult: simulationResults) {
            checkSimulationResults(simulationResult);
        }
    }

    private void checkSimulationResults(SimulationResultDto simulationResult) {
        String expectedSimulationResult;

        if (simulationResult.getWorldArmySize() == simulationResult.getWednesdayArmySize()) {
            expectedSimulationResult = "Draw, no winner";
        } else {
            expectedSimulationResult = String.format("The winner is %s faction",
                    simulationResult.getWorldArmySize() > simulationResult.getWednesdayArmySize()
                    ? World.class.getSimpleName() : Wednesday.class.getSimpleName());
        }

        assertEquals(expectedSimulationResult, simulationResult.getWinner());
    }
}