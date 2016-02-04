package ca.ubc.cs.cpsc210.servicecard.tests;

import ca.ubc.cs.cpsc210.servicecard.model.FoodServicesCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

// Unit tests for FoodServiceCard class
public class FoodServicesCardTest {
    public static final int INITIAL_BALANCE = 10000;
    private FoodServicesCard testCard;

    @Before
    public void setUp() throws Exception {
        testCard = new FoodServicesCard(INITIAL_BALANCE);
    }

    @Test
    public void testMakeNoPurchase() {
        assertEquals(10000, testCard.getBalance());
    }

    @Test
    public void testReload() {
        testCard.reload(10000);
        assertEquals(20000, testCard.getBalance());
        assertEquals(0, testCard.getRewardPoints());
    }

    @Test
    public void testMakeSmallPurchase() {
        testCard.makePurchase(1000);
        assertEquals(9000, testCard.getBalance());
        assertEquals(1000, testCard.getRewardPoints());
    }

    @Test
    public void testMakeRewardPurchase() {
        testCard.makePurchase(2000);
        assertEquals(8010, testCard.getBalance());
        assertEquals(0, testCard.getRewardPoints());
    }

    @Test
    public void testMakeBigRewardPurchase() {
        testCard.makePurchase(2900);
        assertEquals(7110, testCard.getBalance());
        assertEquals(900, testCard.getRewardPoints());
    }

    @Test
    public void testExcessPurchase() {
        assertFalse(testCard.makePurchase(100000));
    }

    @Test
    public void testManyPurchases() {
        testCard.reload(10000);
        assertTrue(testCard.makePurchase(5000));
        assertTrue(testCard.makePurchase(15000));
        assertFalse(testCard.makePurchase(500000));

    }

    @Test
    public void testMorePurchases () {
        testCard.reload(10000);
        assertEquals(20000, testCard.getBalance());
        testCard.makePurchase(5000);
        assertEquals(15020, testCard.getBalance());
        assertEquals(1000, testCard.getRewardPoints());
        testCard.makePurchase(15000);
        assertEquals(100, testCard.getBalance());
        assertEquals(0, testCard.getRewardPoints());
    }
}