package com.innowise.skynet.runner;

import com.innowise.skynet.faction.Faction;
import com.innowise.skynet.faction.Wednesday;
import com.innowise.skynet.faction.World;
import com.innowise.skynet.neutral.Factory;

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
     */
    public void runSimulation() throws InterruptedException {
        Thread factoryThread = new Thread(factory);
        Thread worldThread = new Thread(world);
        Thread wednesdayThread = new Thread(wednesday);

        factoryThread.start();
        worldThread.start();
        wednesdayThread.start();

        factoryThread.join();
        worldThread.join();
        wednesdayThread.join();
    }

    /**
     * Returns the final army size of the World faction.
     * Should be called after simulation completion.
     *
     * @return the number of robots built by the World faction
     */
    public int getWorldArmySize() {
        return world.getArmySize();
    }

    /**
     * Returns the final army size of the Wednesday faction.
     * Should be called after simulation completion.
     *
     * @return the number of robots built by the Wednesday faction
     */
    public int getWednesdayArmySize() {
        return wednesday.getArmySize();
    }

    /**
     * Returns the winner of the robot army competition.
     *
     * @return a string indicating the winning faction or a draw result
     */
    public String getWinner() {
        if (getWorldArmySize() == getWednesdayArmySize()) {
            return "Draw, no winner";
        }

        return String.format("The winner is %s faction", getWorldArmySize() > getWednesdayArmySize()
                ? world.getClass().getSimpleName() : wednesday.getClass().getSimpleName());
    }
}