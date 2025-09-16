package com.innowise.skynet.service.faction.impl;

import com.innowise.skynet.service.faction.Faction;
import com.innowise.skynet.service.neutral.Factory;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * The World faction - one of the two competing factions in the simulation.
 * Uses ArrayBlockingQueue as a backpack for carrying part.
 */
public class World extends Faction {
    public World(Factory factory) {
        super(factory);
        this.partsBackpack = new ArrayBlockingQueue<>(5);
    }
}