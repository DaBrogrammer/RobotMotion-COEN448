package com.github.DaBrogrammer;

import java.util.Arrays;
import java.util.Scanner;

public class RobotMotion {
    private static int[][] floor;
    private static int posX;
    private static int posY;
    private static boolean penDown;
    private static Direction direction;

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

    public static Direction getDirection() {
        return direction;
    }

    enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine();

            if (command.length() > 0) {
                char commandChar = Character.toLowerCase(command.charAt(0));
                String commandArgs = command.length() > 1 ? command.substring(2) : "";

                switch (commandChar) {
                    case 'i' -> {
                        int n = Integer.parseInt(commandArgs);
                        initializeSystem(n);
                    }
                    case 'c' -> printCurrentPosition();
                    case 'd' -> penDown = true;
                    case 'u' -> penDown = false;
                    case 'r' -> turnRight();
                    case 'l' -> turnLeft();
                    case 'm' -> {
                        int spaces = Integer.parseInt(commandArgs);
                        move(spaces);
                    }
                    case 'p' -> printFloor();
                    case 'q' -> running = false;
                    default -> System.out.println("Invalid command!");
                }
            }
        }
    }

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

    static void printCurrentPosition() {
        System.out.printf("Position: %d, %d - Pen: %s - Facing: %s\n",
                posX, posY, penDown ? "down" : "up", direction.toString().toLowerCase());
    }

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

        if (penDown) {
            floor[posX][posY] = 1; // Mark the initial position with "*" if pen is down
        }

        switch (direction) {
            case NORTH -> {
                for (int i = 0; i < spaces; i++) {
                    if (posY < floor.length - 1) {
                        posY++;
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