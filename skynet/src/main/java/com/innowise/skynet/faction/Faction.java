package com.innowise.skynet.faction;

import com.innowise.skynet.neutral.Factory;
import com.innowise.skynet.neutral.enums.Part;
import com.innowise.skynet.neutral.enums.State;

import java.util.HashMap;
import java.util.Map;
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

    /** Warehouse storing accumulated parts for robot construction */
    protected Map<Part, Integer> partsWarehouse = new HashMap<>();

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
        while (partsBackpack.remainingCapacity() != 0 && !conveyor.isEmpty()) {
            partsBackpack.add(conveyor.poll());
        }
    }

    private void unloadBackpack() {
        for (Part part : partsBackpack) {
            partsWarehouse.merge(part, 1, Integer::sum);
        }
        partsBackpack.clear();
    }

    private void buildRobots() {
        int heads = partsWarehouse.getOrDefault(Part.HEAD, 0);
        int torsos = partsWarehouse.getOrDefault(Part.TORSO, 0);
        int hands = partsWarehouse.getOrDefault(Part.HAND, 0);
        int feet = partsWarehouse.getOrDefault(Part.FEET, 0);

        int possibleRobots = Math.min(Math.min(heads, torsos), Math.min(hands / 2, feet / 2));

        if (possibleRobots > 0) {
            armySize += possibleRobots;
            partsWarehouse.put(Part.HEAD, heads - possibleRobots);
            partsWarehouse.put(Part.TORSO, torsos - possibleRobots);
            partsWarehouse.put(Part.HAND, hands - possibleRobots * 2);
            partsWarehouse.put(Part.FEET, feet - possibleRobots * 2);
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