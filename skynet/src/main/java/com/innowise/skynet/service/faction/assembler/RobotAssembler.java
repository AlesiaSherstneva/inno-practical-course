package com.innowise.skynet.service.faction.assembler;

import com.innowise.skynet.service.neutral.enums.Part;

import java.util.concurrent.BlockingQueue;

public class RobotAssembler {
    private int heads;
    private int torsos;
    private int hands;
    private int feet;
    private int possibleRobots;

    public void uploadWarehouse(BlockingQueue<Part> partsBackpack) {
        while (!partsBackpack.isEmpty()) {
            switch (partsBackpack.poll()) {
                case HEAD -> heads++;
                case TORSO -> torsos++;
                case HAND -> hands++;
                case FEET -> feet++;
            }
        }

        possibleRobots = Math.min(Math.min(heads, torsos), Math.min(hands / 2, feet / 2));
    }

    public boolean canBuildAtLeastOneRobot() {
        return possibleRobots > 0;
    }

    public int buildAndReturnRobots() {
        int builtRobots = possibleRobots;

        heads -= possibleRobots;
        torsos -= possibleRobots;
        hands -= possibleRobots * 2;
        feet -= possibleRobots *2;
        possibleRobots = 0;

        return builtRobots;
    }

    public void reset() {
        heads = 0;
        torsos = 0;
        hands = 0;
        feet = 0;
        possibleRobots = 0;
    }
}