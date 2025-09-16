package com.innowise.skynet.service.faction.assembler;

import com.innowise.skynet.service.neutral.enums.Part;

import java.util.concurrent.BlockingQueue;

/**
 * Responsible for assembling robots from collected parts.
 * Each robot requires: 1 head, 1 torso, 2 hands, and 2 feet.
 */
public class RobotAssembler {
    /** Number of available head parts */
    private int heads;

    /** Number of available torso parts */
    private int torsos;

    /** Number of available hand parts */
    private int hands;

    /** Number of available feet parts */
    private int feet;

    /** Maximum number of robots that can be built from current parts */
    private int possibleRobots;

    /**
     * Uploads parts from the backpack to the assembler inventory.
     *
     * @param partsBackpack the backpack containing robot parts
     */
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

    /**
     * Checks if at least one robot can be built from current parts.
     *
     * @return true if sufficient parts are available to build at least one robot, false otherwise
     */
    public boolean canBuildAtLeastOneRobot() {
        return possibleRobots > 0;
    }

    /**
     * Builds robots using available parts, returns the number of constructed robots.
     *
     * @return the number of of constructed robots
     */
    public int buildAndReturnRobots() {
        int builtRobots = possibleRobots;

        heads -= possibleRobots;
        torsos -= possibleRobots;
        hands -= possibleRobots * 2;
        feet -= possibleRobots *2;
        possibleRobots = 0;

        return builtRobots;
    }

    /**
     * Resets the assembler's inventory by clearing all part counts and possible robots.
     */
    public void reset() {
        heads = 0;
        torsos = 0;
        hands = 0;
        feet = 0;
        possibleRobots = 0;
    }
}