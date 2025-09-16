package com.innowise.skynet.service.neutral;

import com.innowise.skynet.service.faction.Faction;
import com.innowise.skynet.service.neutral.enums.Part;
import com.innowise.skynet.service.neutral.enums.State;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * The central factory class that produces robot parts and coordinates with factions.
 * Implements Runnable to operate in its own thread, managing the production cycle
 * and synchronizing access to parts between competing factions.
 */
public class Factory implements Runnable {
    /**
     * Conveyor belt storing produced parts
     */
    private final Deque<Part> conveyor = new ArrayDeque<>(10);

    /**
     * Semaphore used to synchronize factions' threads visiting Factory at nighttime
     */
    private final Semaphore nightSemaphore = new Semaphore(0);

    /**
     * Synchronization barrier for fair faction access to conveyor
     */
    private final CyclicBarrier conveyorAccessBarrier = new CyclicBarrier(2);

    /**
     * Current operational state of the factory (DAY, NIGHT, or FINISHED)
     */
    private volatile State currentState = State.DAY;

    /**
     * Counter tracking the current day of factory working
     */
    private int currentDay;

    /**
     * Total number of days the factory will produce robot parts
     */
    private static final int TOTAL_DAYS = 100;

    /**
     * Generator used to select the type of robot parts and the number of parts to produce randomly
     */
    private static final Random RANDOM = new Random();

    /**
     * Main execution method for the factory thread.
     * Manages the daily production cycle: produces parts during the day,
     * transitions to night for faction collection, and repeats for TOTAL_DAYS.
     * Ensures proper synchronization and state transitions.
     */
    public void run() {
        try {
            while (currentState != State.FINISHED && currentDay < TOTAL_DAYS) {
                currentDay++;
                conveyor.clear();
                produceParts();

                synchronized (this) {
                    currentState = State.NIGHT;
                    notifyAll();
                }

                if (!nightSemaphore.tryAcquire(2, 5, TimeUnit.SECONDS)) {
                    nightSemaphore.drainPermits();
                }

                synchronized (this) {
                    currentState = State.DAY;
                }
            }

            synchronized (this) {
                currentState = State.FINISHED;
                notifyAll();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void produceParts() {
        int detailsNumber = RANDOM.nextInt(10) + 1;
        for (int i = 0; i < detailsNumber; i++) {
            conveyor.offer(Part.values()[RANDOM.nextInt(Part.values().length)]);
        }
    }

    /**
     * Allows a faction to collect parts from the conveyor during nighttime.
     * Releases a semaphore permit to track the collection and provides synchronized
     * access to the conveyor belt to prevent race conditions.
     *
     * @param faction the faction attempting to collect parts
     */
    public void supplyParts(Faction faction) {
        try {
            conveyorAccessBarrier.await(5, TimeUnit.SECONDS);

            synchronized (conveyor) {
                faction.takePartsFromFactory(conveyor);
            }

            nightSemaphore.release();
        } catch (InterruptedException | BrokenBarrierException | TimeoutException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Returns the current operational state of the factory.
     *
     * @return the current State (DAY, NIGHT, or FINISHED)
     */
    public State currentState() {
        return currentState;
    }

    public void reset() {
        conveyor.clear();
        currentState = State.DAY;
        currentDay = 0;
        nightSemaphore.drainPermits();
        conveyorAccessBarrier.reset();
    }
}