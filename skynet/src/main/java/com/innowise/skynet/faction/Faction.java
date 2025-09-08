package com.innowise.skynet.faction;

import com.innowise.skynet.neutral.Factory;
import com.innowise.skynet.neutral.enums.Part;
import com.innowise.skynet.neutral.enums.State;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class Faction implements Runnable {
    protected final Factory factory;
    protected BlockingQueue<Part> partsBackpack = new ArrayBlockingQueue<>(5);
    protected Map<Part, Integer> partsWarehouse = new HashMap<>();
    protected int armySize;

    public Faction(Factory factory) {
        this.factory = factory;
    }

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

    public int getArmySize() {
        return armySize;
    }
}