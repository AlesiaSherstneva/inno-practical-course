package com.innowise.skynet.service.faction.impl;

import com.innowise.skynet.service.faction.Faction;
import com.innowise.skynet.service.neutral.Factory;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * The Wednesday faction - one of the two competing factions in the simulation.
 * Uses LinkedBlockingQueue as a backpack for carrying parts.
 */
public class Wednesday extends Faction {
    public Wednesday(Factory factory) {
        super(factory);
        this.partsBackpack = new LinkedBlockingQueue<>(5);
    }
}