package ca.ubc.cs.cpsc210.microwave.tests;

import ca.ubc.cs.cpsc210.microwave.model.IMicrowaveOvenStateMachine;
import ca.ubc.cs.cpsc210.microwave.model.MicrowaveOvenState;
import ca.ubc.cs.cpsc210.microwave.model.MicrowaveOvenStateMachine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

// unit tests for MicrowaveOvenStateMachine
public class MicrowaveOvenStateMachineTest {
    private IMicrowaveOvenStateMachine testOven;

    @Before
    public void runBefore() {
        testOven = new MicrowaveOvenStateMachine();
    }

    @Test
    public void testXXXXX() {

    }

    @Test
    public void testSetTimeFromAllStates() {
        assertEquals(MicrowaveOvenState.IDLE, testOven.getCurrentState());
        assertEquals(MicrowaveOvenState.PROGRAMMED, testOven.setTime(10, 0));
        assertNull(testOven.setTime(1,30));
        assertEquals(MicrowaveOvenState.PROGRAMMED, testOven.setPowerLevel(100));
        assertNull(testOven.setTime(0,0));
        assertEquals(MicrowaveOvenState.COOKING, testOven.start());
        assertNull(testOven.setTime(1,1));
        assertEquals(MicrowaveOvenState.PAUSED,testOven.pause());
        assertNull(testOven.setTime(10,10));
        assertEquals(MicrowaveOvenState.COOKING,testOven.resume());
        assertNull(testOven.setTime(10,10));
        testOven.pause();
        testOven.cancel();
        assertEquals(MicrowaveOvenState.PROGRAMMED,testOven.setTime(1,1));
    }

    @Test
    public void testSetPowerFromAllStates() {
        assertEquals(MicrowaveOvenState.IDLE, testOven.getCurrentState());
        assertNull(testOven.setPowerLevel(10));
        testOven.setTime(1,1);
        assertEquals(MicrowaveOvenState.PROGRAMMED, testOven.setPowerLevel(50));
        assertEquals(MicrowaveOvenState.COOKING, testOven.start());
        assertNull(testOven.setPowerLevel(50));
        assertEquals(MicrowaveOvenState.PAUSED,testOven.pause());
        assertNull(testOven.setPowerLevel(15));
        assertEquals(MicrowaveOvenState.COOKING,testOven.resume());
        assertNull(testOven.setPowerLevel(18));
        testOven.pause();
        testOven.cancel();
        assertNull(testOven.setPowerLevel(25));
    }

    @Test
    public void testStartFromAllStates() {
        assertNull(testOven.start());
        testOven.setTime(1,0);
        testOven.setPowerLevel(50);
        assertEquals(MicrowaveOvenState.PROGRAMMED, testOven.getCurrentState());
        assertEquals(MicrowaveOvenState.COOKING, testOven.start());
        assertNull(testOven.start());
        assertEquals(MicrowaveOvenState.PAUSED, testOven.pause());
        assertNull(testOven.start());
        assertEquals(MicrowaveOvenState.COOKING, testOven.resume());
        testOven.pause();
        testOven.cancel();
        assertNull(testOven.start());
    }

    @Test
    public void testCancelFromAllStates() {
        assertNull(testOven.cancel());
        testOven.setTime(1,0);
        testOven.setPowerLevel(50);
        assertEquals(MicrowaveOvenState.IDLE,testOven.cancel());
        testOven.setTime(1,0);
        testOven.setPowerLevel(50);
        assertEquals(MicrowaveOvenState.COOKING,testOven.start());
        assertNull(testOven.cancel());
        assertEquals(MicrowaveOvenState.PAUSED,testOven.pause());
        assertEquals(MicrowaveOvenState.IDLE, testOven.cancel());
        assertNull(testOven.cancel());
    }

    @Test
    public void testPauseFromAllStates() {
        assertNull(testOven.pause());
        assertEquals(MicrowaveOvenState.PROGRAMMED,testOven.setTime(1,0));
        assertNull(testOven.pause());
        assertEquals(MicrowaveOvenState.PROGRAMMED,testOven.setPowerLevel(100));
        assertNull(testOven.pause());
        assertEquals(MicrowaveOvenState.COOKING,testOven.start());
        assertEquals(MicrowaveOvenState.PAUSED,testOven.pause());
        assertNull(testOven.pause());
        assertEquals(MicrowaveOvenState.IDLE,testOven.cancel());
        assertNull(testOven.pause());
    }

    @Test
    public void testResumeFromAllStates() {
        assertNull(testOven.resume());
        assertEquals(MicrowaveOvenState.PROGRAMMED,testOven.setTime(1,0));
        assertNull(testOven.resume());
        assertEquals(MicrowaveOvenState.PROGRAMMED,testOven.setPowerLevel(100));
        assertNull(testOven.resume());
        assertEquals(MicrowaveOvenState.COOKING,testOven.start());
        assertNull(testOven.resume());
        assertEquals(MicrowaveOvenState.PAUSED,testOven.pause());
        assertEquals(MicrowaveOvenState.COOKING,testOven.resume());
        testOven.pause();
        testOven.cancel();
        assertNull(testOven.resume());
    }



}