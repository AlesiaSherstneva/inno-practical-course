# java-internship-tasks

This repository contains the completed tasks for the Java internship program.

## task 1: custom LinkedList

**Description:** Implementation of a custom doubly LinkedList with standard operations.

**Features:**
- add/remove elements at beginning, end or specific position
- get element by index or position (first/last)
- get size of a list
- full unit test coverage

## task 2: sales and customer analysis

**Description:** Analysis of orders using Java Stream API to generate business metrics.

**Features:**
- find unique customer cities
- calculate total income from completed orders
- identity most popular product
- compute average check for delivered orders
- find customers with more than five orders
- full unit test coverage

## task 3: robot army competition simulation

**Description:** Multi-threaded simulation of a 100-days competitions between two factions building robot
armies.

**Features:**
- **Factory thread:** produces up to 10 random robot parts daily
- **Faction threads:** World and Wednesday factions collection parts nightly
- **Part limits:** each faction can collect up to 5 parts per night
- **World faction:** uses ArrayBlockingQueue as a robot parts backpack
- **Wednesday faction:** uses LinkedBlockingQueue as a robot parts backpack
- **Robot assembler:** build robots from collected parts (1 head, 1 torso, 2 hands, 2 feet)
- **Thread synchronization:** full coordination between all entities
- **Competition logic:** after 100-days simulation the winner faction determines based on most 
completed robots