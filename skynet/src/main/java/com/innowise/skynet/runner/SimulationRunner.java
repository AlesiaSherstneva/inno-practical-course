package com.innowise.skynet.runner;

import com.innowise.skynet.faction.Faction;
import com.innowise.skynet.faction.Wednesday;
import com.innowise.skynet.faction.World;
import com.innowise.skynet.neutral.Factory;

public class SimulationRunner {
    private final Factory factory;
    private final Faction world;
    private final Faction wednesday;

    public SimulationRunner() {
        this.factory = new Factory();
        this.world = new World(factory);
        this.wednesday = new Wednesday(factory);
    }

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

    public int getWorldArmySize() {
        return world.getArmySize();
    }

    public int getWednesdayArmySize() {
        return wednesday.getArmySize();
    }

    public String getWinner() {
        if (getWorldArmySize() == getWednesdayArmySize()) {
            return "Draw, no winner";
        }

        return String.format("The winner is %s faction", getWorldArmySize() > getWednesdayArmySize()
                ? world.getClass().getSimpleName() : wednesday.getClass().getSimpleName());
    }
}