package com.github.DaBrogrammer;

import java.util.Arrays;
import java.util.Scanner;

public class RobotMotion {
    private static int[][] floor;
    private static int posX;
    private static int posY;
    private static boolean penDown;
    private static Direction direction;
    private static boolean initialized;

    public static Object[] getFloor() {
        return floor;
    }

    public static int getPosX() {
        return posX;
    }

    public static int getPosY() {
        return posY;
    }

    public static boolean isPenDown() {
        return penDown;
    }
    
    public static void setPen(boolean setting) {
    	penDown = setting;
    }

    public static Direction getDirection() {
        return direction;
    }

    enum Direction {
        NORTH, EAST, SOUTH, WEST
    }


    public static void main(String[] args) {
        RobotMotion.run();
    }

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // main loop
        while (running) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine();
            // remove any spaces in the command
            command = command.replaceAll("\\s", "");

            // check if the command is not empty
            if (command.length() > 0) {
                // get the first character of the command
                char commandChar = Character.toLowerCase(command.charAt(0));
                // get the value of the command arguments
                String commandArgs = command.length() > 1 ? command.substring(1) : "";

                // check the command character and call the appropriate method
                switch (commandChar) {
                    case 'i' -> {
                        try{
                            int n = Integer.parseInt(commandArgs);
                            initializeSystem(n);
                            initialized = true;
                        }
                        catch(NumberFormatException e){
                            System.out.println("Invalid arguments for 'i' command! Please enter an integer with the command.");
                        }
                    }
                    case 'c' -> {
                        if(initialized && command.length() == 1){
                            printCurrentPosition();
                        }
                        else{
                            System.out.println("Invalid command!");
                        }
                    }
                    case 'd' -> {
                        if(initialized && command.length() == 1){
                            setPen(true);
                        }
                        else{
                            System.out.println("Invalid command!");
                        }
                    }
                    case 'u' -> {
                        if(initialized && command.length() == 1){
                            setPen(false);
                        }
                        else{
                            System.out.println("Invalid command!");
                        }
                    }
                    case 'r' -> {
                        if(initialized && command.length() == 1){
                            turnRight();
                        }
                        else{
                            System.out.println("Invalid command!");
                        }
                    }
                    case 'l' -> {
                        if(initialized && command.length() == 1){
                            turnLeft();
                        }
                        else{
                            System.out.println("Invalid command!");
                        }
                    }
                    case 'm' -> {
                        try{
                            if(initialized){
                                int spaces = Integer.parseInt(commandArgs);
                                move(spaces);
                            }
                            else{
                                System.out.println("Invalid command!");
                            }
                        }
                        catch(NumberFormatException e){
                            System.out.println("Invalid arguments for 'm' command! Please enter an integer with the command.");
                        }
                    }
                    case 'p' -> {
                        if(initialized && command.length() == 1) {
                            printFloor();
                        }
                        else{
                            System.out.println("Invalid command!");
                        }
                    }
                    //case 'q' -> running = false;
                    case 'q' -> {
                        if(command.length() == 1) {
                            running = false;
                        }
                        else{
                            System.out.println("Invalid command!");
                        }
                    }
                    default -> System.out.println("Invalid command!");
                }
            }
        }
    }

    // starting from the bottom left corner and set the pen up
    static void initializeSystem(int n) {
        floor = new int[n][n];
        for (int[] row : floor) {
            Arrays.fill(row, 0);
        }
        posX = 0;
        posY = 0;
        penDown = false;
        direction = Direction.NORTH;
    }


    // print the current position of the robot, the pen state and the direction
    static void printCurrentPosition() {
        System.out.printf("Position: %d, %d - Pen: %s - Facing: %s\n",
                posX, posY, penDown ? "down" : "up", direction.toString().toLowerCase());
    }

    // direction of the robot is changed based on the current direction
    static void turnRight() {
        switch (direction) {
            case NORTH -> direction = Direction.EAST;
            case EAST -> direction = Direction.SOUTH;
            case SOUTH -> direction = Direction.WEST;
            case WEST -> direction = Direction.NORTH;
        }
    }

    static void turnLeft() {
        switch (direction) {
            case NORTH -> direction = Direction.WEST;
            case EAST -> direction = Direction.NORTH;
            case SOUTH -> direction = Direction.EAST;
            case WEST -> direction = Direction.SOUTH;
        }
    }

    static void move(int spaces) {
        // mark the initial position with "*" if pen is down
        if (penDown) {
            floor[posX][posY] = 1; // Mark the initial position with "*" if pen is down
        }

        // move the robot based on the direction
        switch (direction) {
            case NORTH -> {
                // check if the robot is not going out of bounds
                for (int i = 0; i < spaces; i++) {
                    // move the robot one space up
                    if (posY < floor.length - 1) {
                        // increment the y position
                        posY++;
                        // mark the position with "*" if pen is down
                        if (penDown) {
                            floor[posX][posY] = 1;
                        }
                    }
                }
            }
            case EAST -> {
                for (int i = 0; i < spaces; i++) {
                    if (posX < floor.length - 1) {
                        posX++;
                        if (penDown) {
                            floor[posX][posY] = 1;
                        }
                    }
                }
            }
            case SOUTH -> {
                for (int i = 0; i < spaces; i++) {
                    if (posY > 0) {
                        posY--;
                        if (penDown) {
                            floor[posX][posY] = 1;
                        }
                    }
                }
            }
            case WEST -> {
                for (int i = 0; i < spaces; i++) {
                    if (posX > 0) {
                        posX--;
                        if (penDown) {
                            floor[posX][posY] = 1;
                        }
                    }
                }
            }
        }
    }

    
    // print the floor with the robot's path
    static void printFloor() {
        int size = floor.length;

        // Print the top border
        System.out.print("  +");
        for (int i = 0; i < size; i++) {
            System.out.print("--");
        }
        System.out.println("+");

        // Print the rows
        for (int j = size - 1; j >= 0; j--) {
            System.out.print(j + " |"); // Print the row number

            // Print the columns
            for (int i = 0; i < size; i++) {
                if (i == posX && j == posY) {
                    System.out.print(penDown ? "* " : "  "); // Check pen state before printing
                } else {
                    System.out.print(floor[i][j] == 1 ? "* " : "  ");
                }
            }
            System.out.println("|");
        }

        // Print the bottom border
        System.out.print("  +");
        for (int i = 0; i < size; i++) {
            System.out.print("--");
        }
        System.out.println("+");

        // Print the column numbers
        System.out.print("   ");
        for (int i = 0; i < size; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}