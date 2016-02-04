package ca.ubc.cs.cpsc210.machine.test;

import ca.ubc.cs.cpsc210.machine.model.Coin;
import ca.ubc.cs.cpsc210.machine.model.PaymentUnit;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PaymentUnitTest {
    private PaymentUnit paymentUnit;

    @Before
    public void setUp() {
        paymentUnit = new PaymentUnit();
    }

    @Test
    public void testXXXXXX() {

    }

    @Test
    public void testEmptyPU() {
        assertEquals(0, paymentUnit.getNumberOfCoinsBankedOfType(Coin.DIME));
        assertEquals(0, paymentUnit.getNumberOfCoinsBankedOfType(Coin.NICKEL));
        assertEquals(0, paymentUnit.getNumberOfCoinsBankedOfType(Coin.LOONIE));
        assertEquals(0, paymentUnit.getNumberOfCoinsBankedOfType(Coin.QUARTER));
        assertEquals(0, paymentUnit.getValueOfCoinsBanked());
    }

    @Test
    public void testClearCoinsBanked() {
        paymentUnit.clearCoinsBanked();
        assertEquals(0, paymentUnit.getValueOfCoinsBanked());
    }

    @Test
    public void testAddMultipleCoins() {
        paymentUnit.addCoinsToBanked(Coin.QUARTER, 2);
        paymentUnit.addCoinsToBanked(Coin.DIME, 2);
        paymentUnit.addCoinsToBanked(Coin.LOONIE, 1);
        paymentUnit.addCoinsToBanked(Coin.NICKEL, 3);
        assertEquals(2, paymentUnit.getNumberOfCoinsBankedOfType(Coin.QUARTER));
        assertEquals(2, paymentUnit.getNumberOfCoinsBankedOfType(Coin.DIME));
        assertEquals(1, paymentUnit.getNumberOfCoinsBankedOfType(Coin.LOONIE));
        assertEquals(3, paymentUnit.getNumberOfCoinsBankedOfType(Coin.NICKEL));
        assertEquals(185, paymentUnit.getValueOfCoinsBanked());
    }

    @Test
    public void testInsertMultipleCoin() {
        paymentUnit.insertCoin(Coin.DIME);
        assertEquals(10, paymentUnit.getValueOfCoinsInserted());
        paymentUnit.insertCoin(Coin.LOONIE);
        assertEquals(110, paymentUnit.getValueOfCoinsInserted());
        paymentUnit.insertCoin(Coin.NICKEL);
        assertEquals(115, paymentUnit.getValueOfCoinsInserted());
        assertTrue(paymentUnit.currentTransaction.get(0)==Coin.DIME);
        assertTrue(paymentUnit.currentTransaction.get(1)==Coin.LOONIE);
        assertEquals(0, paymentUnit.getValueOfCoinsBanked());
        paymentUnit.addCoinsToBanked(Coin.QUARTER,2);
        assertEquals(50, paymentUnit.getValueOfCoinsBanked());
    }

    @Test
    public void testCancelTransaction() {
        paymentUnit.insertCoin(Coin.DIME);
        paymentUnit.insertCoin(Coin.NICKEL);
        paymentUnit.cancelTransaction();
        assertTrue(paymentUnit.returnedCoins.get(0) == Coin.DIME);
        assertFalse(paymentUnit.returnedCoins.get(1) == Coin.LOONIE);
        assertTrue(paymentUnit.currentTransaction.size()==0);
        assertEquals(0, paymentUnit.getValueOfCoinsInserted());
    }

    @Test
    public void testCoinTransfer() {
        paymentUnit.addCoinsToBanked(Coin.DIME,1);
        paymentUnit.insertCoin(Coin.NICKEL);
        paymentUnit.transferCoins();
        assertTrue(paymentUnit.currentTransaction.size()==0);
        assertEquals(15, paymentUnit.getValueOfCoinsBanked());
        assertEquals(1, paymentUnit.getNumberOfCoinsBankedOfType(Coin.NICKEL));
    }

    @Test
    public void testSingleExactPurchase() {
        paymentUnit.insertCoin(Coin.LOONIE);
        paymentUnit.makePurchase(100);
        assertTrue(paymentUnit.returnedCoins.size()==0);
        assertTrue(paymentUnit.getValueOfCoinsBanked()==100);
        assertTrue(paymentUnit.currentTransaction.size()==0);
        assertTrue(paymentUnit.getValueOfCoinsInserted()==0);
    }


    @Test
    public void testSingleNonExactPurchase() {
        paymentUnit.insertCoin(Coin.LOONIE);
        paymentUnit.makePurchase(80);
        // Can return two dimes, but nothing as 0 banked coins
        assertTrue(paymentUnit.returnedCoins.size()==0);
    }


    @Test
    public void testSingleNonExactPurchaseBanked() {
        paymentUnit.addCoinsToBanked(Coin.DIME,1);
        paymentUnit.addCoinsToBanked(Coin.NICKEL,1);
        paymentUnit.insertCoin(Coin.LOONIE);
        paymentUnit.makePurchase(80);
        assertEquals(5, paymentUnit.ChangeValue);
        // Can return two dimes, but return only 1 dime and 1 nickel
        assertTrue(paymentUnit.returnedCoins.get(0)==Coin.DIME);
        assertTrue(paymentUnit.returnedCoins.size()==2);
        assertTrue(paymentUnit.returnedCoins.get(1)==Coin.NICKEL);
    }

    @Test
    public void testExcessPurchase() {
        paymentUnit.addCoinsToBanked(Coin.LOONIE,50);
        paymentUnit.insertCoin(Coin.LOONIE);
        paymentUnit.makePurchase(500);
        assertEquals(0,paymentUnit.returnedCoins.size());
        assertEquals(5000,paymentUnit.getValueOfCoinsBanked());
        assertEquals(100, paymentUnit.getValueOfCoinsInserted());
        assertEquals(0,paymentUnit.ChangeValue);
        paymentUnit.insertCoin(Coin.LOONIE);
        assertEquals(200, paymentUnit.getValueOfCoinsInserted());
        paymentUnit.makePurchase(150);
        assertEquals(50,paymentUnit.ChangeValue);
        assertEquals(0,paymentUnit.returnedCoins.size());
        assertEquals(52,paymentUnit.numLoonies);
    }

    @Test
    public void testFourQuarterChange(){
        paymentUnit.addCoinsToBanked(Coin.QUARTER,4);
        for(int i=0; i<=7;i++){
        paymentUnit.insertCoin(Coin.QUARTER);}
        assertEquals(200, paymentUnit.getValueOfCoinsInserted());
        paymentUnit.makePurchase(100);
        assertEquals(8,paymentUnit.getNumberOfCoinsBankedOfType(Coin.QUARTER));
        assertEquals(4,paymentUnit.returnedCoins.size());
    }

    @Test
    public void testTwoDimesAndNickelChange(){
        paymentUnit.addCoinsToBanked(Coin.DIME,3);
        paymentUnit.addCoinsToBanked(Coin.NICKEL,4);
        paymentUnit.insertCoin(Coin.LOONIE);
        paymentUnit.makePurchase(75);
        assertEquals(3,paymentUnit.returnedCoins.size());
        assertEquals(Coin.DIME,paymentUnit.returnedCoins.get(1));
        assertEquals(Coin.NICKEL,paymentUnit.returnedCoins.get(2));
        assertEquals(0, paymentUnit.ChangeValue);
    }

    @Test
    public void testOneDimeThreeNickelChange() {
        paymentUnit.addCoinsToBanked(Coin.DIME,1);
        paymentUnit.addCoinsToBanked(Coin.NICKEL,3);
        assertEquals(3,paymentUnit.getNumberOfCoinsBankedOfType(Coin.NICKEL));
        paymentUnit.insertCoin(Coin.LOONIE);
        paymentUnit.makePurchase(75);
        assertEquals(4,paymentUnit.returnedCoins.size());
        assertEquals(Coin.NICKEL,paymentUnit.returnedCoins.get(1));
    }

    @Test
    public void makeLargePurchase(){
        paymentUnit.addCoinsToBanked(Coin.LOONIE,5);
        for(int i=0; i<5;i++){paymentUnit.insertCoin(Coin.LOONIE);}
        assertEquals(500, paymentUnit.getValueOfCoinsInserted());
        paymentUnit.makePurchase(200);
        assertEquals(3,paymentUnit.returnedCoins.size());
        assertEquals(7, paymentUnit.getNumberOfCoinsBankedOfType(Coin.LOONIE));
    }


}
