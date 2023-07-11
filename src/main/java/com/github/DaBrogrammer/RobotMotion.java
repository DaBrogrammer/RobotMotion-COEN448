package com.github.DaBrogrammer;

import java.util.Arrays;
import java.util.Scanner;

public class RobotMotion {
    private static int[][] floor;
    private static int posX;
    private static int posY;
    private static boolean penDown;
    private static Direction direction;

    private enum Direction {
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
                    case 'i':
                        int n = Integer.parseInt(commandArgs);
                        initializeSystem(n);
                        break;
                    case 'c':
                        printCurrentPosition();
                        break;
                    case 'd':
                        penDown = true;
                        break;
                    case 'u':
                        penDown = false;
                        break;
                    case 'r':
                        turnRight();
                        break;
                    case 'l':
                        turnLeft();
                        break;
                    case 'm':
                        int spaces = Integer.parseInt(commandArgs);
                        move(spaces);
                        break;
                    case 'p':
                        printFloor();
                        break;
                    case 'q':
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid command!");
                        break;
                }
            }
        }
    }

    private static void initializeSystem(int n) {
        floor = new int[n][n];
        for (int[] row : floor) {
            Arrays.fill(row, 0);
        }
        posX = 0;
        posY = 0;
        penDown = false;
        direction = Direction.NORTH;
    }

    private static void printCurrentPosition() {
        System.out.printf("Position: %d, %d - Pen: %s - Facing: %s\n",
                posX, posY, penDown ? "down" : "up", direction.toString().toLowerCase());
    }

    private static void turnRight() {
        switch (direction) {
            case NORTH:
                direction = Direction.EAST;
                break;
            case EAST:
                direction = Direction.SOUTH;
                break;
            case SOUTH:
                direction = Direction.WEST;
                break;
            case WEST:
                direction = Direction.NORTH;
                break;
        }
    }

    private static void turnLeft() {
        switch (direction) {
            case NORTH:
                direction = Direction.WEST;
                break;
            case EAST:
                direction = Direction.NORTH;
                break;
            case SOUTH:
                direction = Direction.EAST;
                break;
            case WEST:
                direction = Direction.SOUTH;
                break;
        }
    }

    private static void move(int spaces) {
        switch (direction) {
            case NORTH:
                for (int i = 0; i < spaces; i++) {
                    if (posY < floor.length - 1) {
                        posY++;
                        if (penDown) {
                            floor[posX][posY] = 1;
                        }
                    }
                }
                break;
            case EAST:
                for (int i = 0; i < spaces; i++) {
                    if (posX < floor.length - 1) {
                        posX++;
                        if (penDown) {
                            floor[posX][posY] = 1;
                        }
                    }
                }
                break;
            case SOUTH:
                for (int i = 0; i < spaces; i++) {
                    if (posY > 0) {
                        posY--;
                        if (penDown) {
                            floor[posX][posY] = 1;
                        }
                    }
                }
                break;
            case WEST:
                for (int i = 0; i < spaces; i++) {
                    if (posX > 0) {
                        posX--;
                        if (penDown) {
                            floor[posX][posY] = 1;
                        }
                    }
                }
                break;
        }
    }

    private static void printFloor() {
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
                if (i == 0 && j == 0) {
                    System.out.print("* "); // Print the robot's position
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