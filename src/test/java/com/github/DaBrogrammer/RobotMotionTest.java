package com.github.DaBrogrammer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RobotMotionTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testInitializeSystem() {
        RobotMotion.initializeSystem(3);

        int[][] expectedFloor = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        Assertions.assertArrayEquals(expectedFloor, RobotMotion.getFloor());

        Assertions.assertEquals(0, RobotMotion.getPosX());
        Assertions.assertEquals(0, RobotMotion.getPosY());

        Assertions.assertFalse(RobotMotion.isPenDown());

        Assertions.assertEquals(RobotMotion.Direction.NORTH, RobotMotion.getDirection());
    }

    @Test
    public void testTurnRight() {
        RobotMotion.initializeSystem(3);

        RobotMotion.turnRight();
        Assertions.assertEquals(RobotMotion.Direction.EAST, RobotMotion.getDirection());

        RobotMotion.turnRight();
        Assertions.assertEquals(RobotMotion.Direction.SOUTH, RobotMotion.getDirection());

        RobotMotion.turnRight();
        Assertions.assertEquals(RobotMotion.Direction.WEST, RobotMotion.getDirection());

        RobotMotion.turnRight();
        Assertions.assertEquals(RobotMotion.Direction.NORTH, RobotMotion.getDirection());
    }

    @Test
    public void testTurnLeft() {
        RobotMotion.initializeSystem(3);

        RobotMotion.turnLeft();
        Assertions.assertEquals(RobotMotion.Direction.WEST, RobotMotion.getDirection());

        RobotMotion.turnLeft();
        Assertions.assertEquals(RobotMotion.Direction.SOUTH, RobotMotion.getDirection());

        RobotMotion.turnLeft();
        Assertions.assertEquals(RobotMotion.Direction.EAST, RobotMotion.getDirection());

        RobotMotion.turnLeft();
        Assertions.assertEquals(RobotMotion.Direction.NORTH, RobotMotion.getDirection());
    }

    @Test
    public void testMove() {
        RobotMotion.initializeSystem(3);

        RobotMotion.move(2);
        Assertions.assertEquals(0, RobotMotion.getPosX());
        Assertions.assertEquals(2, RobotMotion.getPosY());
        Assertions.assertFalse(RobotMotion.isPenDown());

        RobotMotion.turnRight();
        RobotMotion.move(1);
        Assertions.assertEquals(1, RobotMotion.getPosX());
        Assertions.assertEquals(2, RobotMotion.getPosY());
        Assertions.assertFalse(RobotMotion.isPenDown());
    }

    @Test
    public void testPrintCurrentPosition() {
        RobotMotion.initializeSystem(3);

        RobotMotion.printCurrentPosition();
        Assertions.assertEquals("Position: 0, 0 - Pen: up - Facing: north\n", outputStream.toString());
        
        outputStream.reset();
        RobotMotion.turnRight();
        RobotMotion.move(2);
        RobotMotion.printCurrentPosition();
        Assertions.assertEquals("Position: 2, 0 - Pen: up - Facing: east\n", outputStream.toString());
        
        outputStream.reset();
        RobotMotion.turnLeft();
        RobotMotion.move(1);
        RobotMotion.printCurrentPosition();
        Assertions.assertEquals("Position: 2, 1 - Pen: up - Facing: north\n", outputStream.toString());
    }

    @Test
    public void testPrintFloor() {
        RobotMotion.initializeSystem(3);

        RobotMotion.move(2);
        RobotMotion.turnRight();
        RobotMotion.move(1);

        outputStream.reset();
        RobotMotion.printFloor();

        String expectedOutput = "  +------+" + System.lineSeparator() +
                "2 |      |" + System.lineSeparator() +
                "1 |      |" + System.lineSeparator() +
                "0 |      |" + System.lineSeparator() +
                "  +------+" + System.lineSeparator() +
                "   0 1 2 " + System.lineSeparator();

        Assertions.assertEquals(expectedOutput, outputStream.toString());
    }

}
