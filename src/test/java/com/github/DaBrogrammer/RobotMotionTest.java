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

    
    // REQUIREMENT R1
    @Test
    public void testInitializeSystem_Floor() {
        RobotMotion.initializeSystem(3);
        int[][] expectedFloor = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        Assertions.assertArrayEquals(expectedFloor, RobotMotion.getFloor());
        
        RobotMotion.initializeSystem(10);
        int[][] expectedFloorBigger = {{0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0}};
        Assertions.assertArrayEquals(expectedFloorBigger, RobotMotion.getFloor());
        
    }

    
    // REQUIREMENT R2
    @Test
    public void testInitializeSystem_Robot() {
    	RobotMotion.initializeSystem(3);
        Assertions.assertEquals(0, RobotMotion.getPosX());
        Assertions.assertEquals(0, RobotMotion.getPosY());

        Assertions.assertFalse(RobotMotion.isPenDown());

        Assertions.assertEquals(RobotMotion.Direction.NORTH, RobotMotion.getDirection());
    }
    
    
    // REQUIREMENT R3
    @Test
    public void testSetPenUp() {
    	RobotMotion.initializeSystem(3);
    	Assertions.assertFalse(RobotMotion.isPenDown());
    	RobotMotion.setPen(false);
    	Assertions.assertFalse(RobotMotion.isPenDown());
    }
    
    
    // REQUIREMENT R4
    @Test
    public void testNoDrawing_PenUP() {
        RobotMotion.initializeSystem(3);

        RobotMotion.setPen(false); //pen is up, NO DRAWING
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
    
    
    // REQUIREMENT R5
    @Test
    public void testSetPenDown() {
    	RobotMotion.initializeSystem(3);
    	RobotMotion.setPen(true);
    	Assertions.assertTrue(RobotMotion.isPenDown());
    }

    
    // REQUIRMENT R6
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

    
    // REQUIREMENT R7
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
    

    // REQUIREMENT R8
    @Test
    public void testMove() {
        RobotMotion.initializeSystem(3);
        RobotMotion.setPen(true); // must set pen down in order to draw, not mandatory in order to move
        RobotMotion.move(2);
        Assertions.assertEquals(0, RobotMotion.getPosX());
        Assertions.assertEquals(2, RobotMotion.getPosY());
        RobotMotion.turnRight();
        RobotMotion.move(1);
        Assertions.assertEquals(1, RobotMotion.getPosX());
        Assertions.assertEquals(2, RobotMotion.getPosY());
        
        RobotMotion.initializeSystem(10);
        RobotMotion.setPen(false);
        RobotMotion.move(6);
        Assertions.assertEquals(0, RobotMotion.getPosX());
        Assertions.assertEquals(6, RobotMotion.getPosY());
        RobotMotion.turnRight();
        RobotMotion.move(2);
        Assertions.assertEquals(2, RobotMotion.getPosX());
        Assertions.assertEquals(6, RobotMotion.getPosY());
        RobotMotion.turnRight();
        RobotMotion.move(3);
        RobotMotion.move(1);
        Assertions.assertEquals(2, RobotMotion.getPosX());
        Assertions.assertEquals(2, RobotMotion.getPosY());
    }
    
    
    // REQUIREMENT R9
    @Test
    public void testPrintFloor() {
   
        RobotMotion.initializeSystem(3);
        RobotMotion.setPen(true); // pen is down, drawing is expected
        RobotMotion.move(2);
        RobotMotion.turnRight();
        RobotMotion.move(1);
        RobotMotion.printFloor();

        String expectedOutput2 = "  +------+" + System.lineSeparator() +
                "2 |* *   |" + System.lineSeparator() +
                "1 |*     |" + System.lineSeparator() +
                "0 |*     |" + System.lineSeparator() +
                "  +------+" + System.lineSeparator() +
                "   0 1 2 " + System.lineSeparator();
        
        Assertions.assertEquals(expectedOutput2, outputStream.toString());
    }

    
    // REQUIREMENT R10
    @Test
    public void testPrintCurrentPosition() {
        RobotMotion.initializeSystem(3);

        RobotMotion.printCurrentPosition();
        Assertions.assertEquals("Position: 0, 0 - Pen: up - Facing: north\n", outputStream.toString());

        outputStream.reset();
        RobotMotion.setPen(false); // pen up, no drawing
        RobotMotion.turnRight();
        RobotMotion.move(2);
        RobotMotion.printCurrentPosition();
        Assertions.assertEquals("Position: 2, 0 - Pen: up - Facing: east\n", outputStream.toString());

        outputStream.reset();
        RobotMotion.initializeSystem(3);
        RobotMotion.setPen(true); // must set pen down in order to draw
        RobotMotion.turnRight();
        RobotMotion.move(1);
        RobotMotion.printCurrentPosition();
        Assertions.assertEquals("Position: 1, 0 - Pen: down - Facing: east\n", outputStream.toString());
    }


}
