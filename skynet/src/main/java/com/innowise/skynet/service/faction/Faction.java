package com.innowise.skynet.service.faction;

import com.innowise.skynet.service.faction.assembler.RobotAssembler;
import com.innowise.skynet.service.neutral.Factory;
import com.innowise.skynet.service.neutral.enums.Part;
import com.innowise.skynet.service.neutral.enums.State;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Abstract class which represents a faction that takes parts from a factory and build robots.
 */
public abstract class Faction implements Runnable {
    /** The factory from which parts are collected */
    protected final Factory factory;

    /** Backpack for carrying parts from factory. Max capacity: 5 parts */
    protected BlockingQueue<Part> partsBackpack = new ArrayBlockingQueue<>(5);

    protected RobotAssembler robotAssembler = new RobotAssembler();

    /** The size of built robot army */
    protected int armySize;

    public Faction(Factory factory) {
        this.factory = factory;
    }

    /**
     * The main execution loop for the faction thread.
     * Waits for night, collects parts, and builds robots until simulation ends.
     */
    @Override
    public void run() {
        try {
            while (factory.currentState() != State.FINISHED) {
                synchronized (factory) {
                    while (factory.currentState() == State.DAY) {
                        factory.wait();
                    }
                }

                factory.supplyParts(this);

                unloadBackpack();
                buildRobots();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Takes parts from the factory conveyor and adds them to the backpack.
     * Stops when backpack is full or conveyor is empty.
     *
     * @param conveyor the queue of parts from the factory
     */
    public void takePartsFromFactory(Queue<Part> conveyor) {
        while (partsBackpack.size() < 5 && !conveyor.isEmpty()) {
            partsBackpack.add(conveyor.poll());
        }
    }

    private void unloadBackpack() {
        robotAssembler.uploadWarehouse(partsBackpack);
    }

    private void buildRobots() {
        if (robotAssembler.canBuildAtLeastOneRobot()) {
            armySize += robotAssembler.buildAndReturnRobots();
        }
    }

    /**
     * Returns the current size of the robot army.
     *
     * @return the number of robots built by the faction
     */
    public int getArmySize() {
        return armySize;
    }
}