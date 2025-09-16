package com.innowise.skynet;

import com.innowise.skynet.dto.SimulationResultDto;
import com.innowise.skynet.service.faction.Faction;
import com.innowise.skynet.service.faction.impl.Wednesday;
import com.innowise.skynet.service.faction.impl.World;
import com.innowise.skynet.service.neutral.Factory;
import com.innowise.skynet.service.neutral.enums.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Main simulation orchestrator that coordinates the robot army competition between factions.
 * This class initializes all components and runs the simulation.
 *
 * <p>The runner manages three main threads:
 * <ul>
 *   <li>Factory thread - produces parts on a daily cycle</li>
 *   <li>World faction thread - collects parts and builds robots</li>
 *   <li>Wednesday faction thread - collects parts and builds robots</li>
 * </ul>
 *
 * After simulation completion, provides methods to retrieve results and determine the winner.
 *
 * @see Factory the central parts production facility
 * @see World the first competing faction
 * @see Wednesday the second competing faction
 * @see Faction base class for both competing factions
 */
public class SimulationRunner {
    /**
     * The factory instance that produces robot parts for both factions.
     * Shared resource between both competing factions.
     */
    private final Factory factory;

    /**
     * The World faction instance competing to build the largest robot army.
     * Represents one of the two opposing forces in the simulation.
     */
    private final Faction world;

    /**
     * The Wednesday faction instance competing to build the largest robot army.
     * Represents the second opposing force in the simulation.
     */
    private final Faction wednesday;

    public SimulationRunner() {
        this.factory = new Factory();
        this.world = new World(factory);
        this.wednesday = new Wednesday(factory);
    }

    /**
     * Executes the complete simulation by starting all threads and waiting for their completion.
     * Starts the factory and both faction threads simultaneously to begin the competition.
     * Blocks until all threads have finished execution (after 100 days).
     *
     * @return SimulationResultDto containing final army sizes and winner information
     */
    public SimulationResultDto runSimulation() throws InterruptedException {
        Thread factoryThread = new Thread(factory);
        Thread worldThread = new Thread(world);
        Thread wednesdayThread = new Thread(wednesday);

        factoryThread.start();
        worldThread.start();
        wednesdayThread.start();

        factoryThread.join();
        worldThread.join();
        wednesdayThread.join();

        return SimulationResultDto.builder()
                .worldArmySize(world.getArmySize())
                .wednesdayArmySize(wednesday.getArmySize())
                .winner(getWinner())
                .build();
    }

    /**
     * Executes multiple simulation runs and collects results for statistical analysis.
     *
     * @param times the number of simulation runs to execute
     * @return list of SimulationResultDto instances containing results from each run
     */
    public List<SimulationResultDto> runSimulation(int times) throws InterruptedException {
        List<SimulationResultDto> simulationResultsList = new ArrayList<>();

        for (int i = 0; i < times; i++) {
            SimulationResultDto simulationResult = runSimulation();
            simulationResultsList.add(simulationResult);
            resetAll();
        }

        return simulationResultsList;
    }

    private String getWinner() {
        if (world.getArmySize() == wednesday.getArmySize()) {
            return "Draw, no winner";
        }

        return String.format("The winner is %s faction", world.getArmySize() > wednesday.getArmySize()
                ? world.getClass().getSimpleName() : wednesday.getClass().getSimpleName());
    }

    private void resetAll() {
        while (factory.currentState() != State.FINISHED) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        factory.reset();
        world.reset();
        wednesday.reset();
    }
}