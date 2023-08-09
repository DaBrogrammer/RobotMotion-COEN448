package com.github.DaBrogrammer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.ByteArrayInputStream;
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
        // test case 1: smaller floor
    	RobotMotion.initializeSystem(3);
        int[][] expectedFloor = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        Assertions.assertArrayEquals(expectedFloor, RobotMotion.getFloor());
        
        // test case 2: larger floor
        RobotMotion.initializeSystem(10);
        int[][] expectedFloorBigger = {{0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0}};
        Assertions.assertArrayEquals(expectedFloorBigger, RobotMotion.getFloor());
        
    }

    
    // REQUIREMENT R2
    @Test
    public void testInitializeSystem_Robot() {
    	// assert that the robot is in position [0, 0], facing north, and with pen up after system initialization
    	RobotMotion.initializeSystem(3);
        Assertions.assertEquals(0, RobotMotion.getPosX());
        Assertions.assertEquals(0, RobotMotion.getPosY());

        Assertions.assertFalse(RobotMotion.isPenDown());

        Assertions.assertEquals(RobotMotion.Direction.NORTH, RobotMotion.getDirection());
    }
    
    
    // REQUIREMENT R3
    @Test
    public void testSetPenUp() {
    	// assert that robot has the pen up after initialization + when the setPen(boolean) function is called with "false" as input
    	RobotMotion.initializeSystem(3);
    	Assertions.assertFalse(RobotMotion.isPenDown());
    	RobotMotion.setPen(false);
    	Assertions.assertFalse(RobotMotion.isPenDown());
    }
    
    
    // REQUIREMENT R4
    @Test
    public void testNoDrawing_PenUP() {
        // test that the robot will not draw on the floor as long as the pen is up
    	RobotMotion.initializeSystem(3);

        RobotMotion.setPen(false); // pen is up, NO DRAWING
        RobotMotion.move(2);
        RobotMotion.turnRight();
        RobotMotion.move(1);

        outputStream.reset();
        RobotMotion.printFloor();
        
        // robot is moving across the floor, however the pen is set to up (false), therefore we should not expect
        // any drawing to be done on the floor, therefore the floor must appear blank
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
    	Assertions.assertFalse(RobotMotion.isPenDown()); // pen is up by default after initialization
    	RobotMotion.setPen(true); // set the pen down (true) using the function setPen(boolean)
    	Assertions.assertTrue(RobotMotion.isPenDown()); // assert that the pen is now down after the function is called with "true" as input
    }

    
    // REQUIRMENT R6
    @Test
    public void testTurnRight() {
        RobotMotion.initializeSystem(3);

        // from NORTH, turning right makes the robot go EAST
        RobotMotion.turnRight();
        Assertions.assertEquals(RobotMotion.Direction.EAST, RobotMotion.getDirection());

        // from EAST, turning right makes the robot go SOUTH
        RobotMotion.turnRight();
        Assertions.assertEquals(RobotMotion.Direction.SOUTH, RobotMotion.getDirection());

        // from SOUTH, turning right makes the robot go WEST
        RobotMotion.turnRight();
        Assertions.assertEquals(RobotMotion.Direction.WEST, RobotMotion.getDirection());

        // from WEST, turning right makes the robot go NORTH
        RobotMotion.turnRight();
        Assertions.assertEquals(RobotMotion.Direction.NORTH, RobotMotion.getDirection());
    }

    
    // REQUIREMENT R7
    @Test
    public void testTurnLeft() {
        RobotMotion.initializeSystem(3);

        // from NORTH, turning left makes the robot go WEST
        RobotMotion.turnLeft();
        Assertions.assertEquals(RobotMotion.Direction.WEST, RobotMotion.getDirection());

        // from WEST, turning left makes the robot go SOUTH
        RobotMotion.turnLeft();
        Assertions.assertEquals(RobotMotion.Direction.SOUTH, RobotMotion.getDirection());

        // from SOUTH, turning left makes the robot go EAST
        RobotMotion.turnLeft();
        Assertions.assertEquals(RobotMotion.Direction.EAST, RobotMotion.getDirection());

        // from EAST, turning left makes the robot go NORTH
        RobotMotion.turnLeft();
        Assertions.assertEquals(RobotMotion.Direction.NORTH, RobotMotion.getDirection());
    }
    

    // REQUIREMENT R8
    @Test
    public void testMove() {
        // test case 1: smaller system, smaller movements
    	RobotMotion.initializeSystem(3);
        RobotMotion.setPen(true); // must set pen down in order to draw, not mandatory in order to move
        RobotMotion.move(2);
        Assertions.assertEquals(0, RobotMotion.getPosX());
        Assertions.assertEquals(2, RobotMotion.getPosY());
        RobotMotion.turnRight();
        RobotMotion.move(1);
        Assertions.assertEquals(1, RobotMotion.getPosX());
        Assertions.assertEquals(2, RobotMotion.getPosY());
        
        // test case 2: larger system, larger movements
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
        
        // test case 3: to improve coverage
        RobotMotion.initializeSystem(5);
        RobotMotion.setPen(true);
        RobotMotion.turnRight();
        RobotMotion.move(3);
        RobotMotion.turnLeft();
        RobotMotion.move(2);
        RobotMotion.turnLeft();
        RobotMotion.move(1);
        Assertions.assertEquals(2, RobotMotion.getPosX());
        Assertions.assertEquals(2, RobotMotion.getPosY());
        
        RobotMotion.turnLeft();
        RobotMotion.move(1);
        Assertions.assertEquals(2, RobotMotion.getPosX());
        Assertions.assertEquals(1, RobotMotion.getPosY());
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
        // test case 1: print current position after initialization
        RobotMotion.printCurrentPosition();
        Assertions.assertEquals("Position: 0, 0 - Pen: up - Facing: north\n", outputStream.toString());

        // test case 2: same system, movement is performed, but pen is up
        outputStream.reset();
        RobotMotion.setPen(false);
        RobotMotion.turnRight();
        RobotMotion.move(2);
        RobotMotion.printCurrentPosition();
        Assertions.assertEquals("Position: 2, 0 - Pen: up - Facing: east\n", outputStream.toString());

        // test case 3: new system, movement is performed, with pen down
        outputStream.reset();
        RobotMotion.initializeSystem(3);
        RobotMotion.setPen(true);
        RobotMotion.turnRight();
        RobotMotion.move(1);
        RobotMotion.printCurrentPosition();
        Assertions.assertEquals("Position: 1, 0 - Pen: down - Facing: east\n", outputStream.toString());
        
        // test case 4: larger system, larger movements performed, with pen down
        outputStream.reset();
        RobotMotion.initializeSystem(10);
        RobotMotion.setPen(true);
        RobotMotion.move(4);
        RobotMotion.turnRight();
        RobotMotion.move(7);
        RobotMotion.turnRight();
        RobotMotion.printCurrentPosition();
        Assertions.assertEquals("Position: 7, 4 - Pen: down - Facing: south\n", outputStream.toString());
    }

    
    // REQUIREMENT R11
    @Test
    public void testEndProgramCommand() {
        // Redirect System.in to provide the "q" command
        ByteArrayInputStream inputStream = new ByteArrayInputStream("q\n".getBytes());
        System.setIn(inputStream);

        // Execute the run method
        RobotMotion.run();

        // The program should terminate after the "q" command
        Assertions.assertTrue(true);
    }

    
    // REQUIREMENT R12
    @Test
    public void  testInvalidCommand() {
        // test case 1: invalid command
        ByteArrayInputStream inputStream = new ByteArrayInputStream("a\nq\n".getBytes());
        System.setIn(inputStream);
        RobotMotion.run();
        Assertions.assertEquals("Enter command: Invalid command!" +System.lineSeparator()+ "Enter command: ", outputStream.toString());
    }


    // TEST CASES ADDED BASED ON QA REMARKS
    @Test
    public void testInitializeNegative() {
        RobotMotion.initializeSystem(-3);
        Assertions.assertEquals("Invalid floor size. Please enter an integer larger than 0", outputStream.toString().trim());

        outputStream.reset();
        RobotMotion.initializeSystem(0);
        Assertions.assertEquals("Invalid floor size. Please enter an integer larger than 0", outputStream.toString().trim());
    }

    @Test
    public void testMoveNegative() {
        RobotMotion.initializeSystem(5);
        RobotMotion.move(-3);
        Assertions.assertEquals("Invalid. Must move by a positive integer.", outputStream.toString().trim());
    }

    @Test
    public void testMoveOutOfBounds() {
        RobotMotion.initializeSystem(3);
        RobotMotion.setPen(true);
        RobotMotion.move(5);
        Assertions.assertEquals("Movement is going out of bounds in the NORTH direction. \n"+ "Robot has stopped at floor limit 2", outputStream.toString().trim());

        outputStream.reset();
        RobotMotion.initializeSystem(3);
        RobotMotion.setPen(true);
        RobotMotion.move(2);
        RobotMotion.turnRight();
        RobotMotion.move(5);
        Assertions.assertEquals("Movement is going out of bounds in the EAST direction. \n"+ "Robot has stopped at floor limit 2", outputStream.toString().trim());

        outputStream.reset();
        RobotMotion.initializeSystem(3);
        RobotMotion.setPen(true);
        RobotMotion.turnRight();
        RobotMotion.turnRight();
        RobotMotion.move(5);
        Assertions.assertEquals("Movement is going out of bounds in the SOUTH direction. \n"+ "Robot has stopped at floor limit 0", outputStream.toString().trim());

        outputStream.reset();
        RobotMotion.initializeSystem(3);
        RobotMotion.setPen(true);
        RobotMotion.turnRight();
        RobotMotion.turnRight();
        RobotMotion.turnRight();
        RobotMotion.move(5);
        Assertions.assertEquals("Movement is going out of bounds in the WEST direction. \n"+ "Robot has stopped at floor limit 0", outputStream.toString().trim());
    }



}
