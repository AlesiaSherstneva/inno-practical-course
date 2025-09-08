package com.innowise.skynet.neutral;

import com.innowise.skynet.faction.Faction;
import com.innowise.skynet.neutral.enums.Part;
import com.innowise.skynet.neutral.enums.State;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Factory implements Runnable {
    private final Deque<Part> conveyor = new ArrayDeque<>(10);
    private final Semaphore nightSemaphore = new Semaphore(0);
    private volatile State currentState = State.DAY;
    private int currentDay;

    private static final int TOTAL_DAYS = 100;
    private static final Random RANDOM = new Random();

    public void run() {
        try {
            while (currentState != State.FINISHED && currentDay < TOTAL_DAYS) {
                currentDay++;
                produceParts();

                synchronized (this) {
                    currentState = State.NIGHT;
                    notifyAll();
                }

                nightSemaphore.acquire(2);

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

    public void produceParts() {
        int detailsNumber = RANDOM.nextInt(10) + 1;
        for (int i = 0; i < detailsNumber; i++) {
            conveyor.offer(Part.values()[RANDOM.nextInt(Part.values().length)]);
        }
    }

    public void supplyParts(Faction faction) throws InterruptedException {
        nightSemaphore.release();
        synchronized (conveyor) {
            faction.takePartsFromFactory(conveyor);
        }
    }

    public State currentState() {
        return currentState;
    }
}