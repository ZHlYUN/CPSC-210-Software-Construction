package ca.ubc.cs.cpsc210.machine.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the payment unit in a vending machine.
 */
public class PaymentUnit {
    public int numLoonies;   // number of loonies banked in machine for making change
    public int numQuarters;  // number of quarters banked in machine for making change
    public int numDimes;     // number of dimes banked in machine for making change
    public int numNickels;   // number of nickels banked in machine for making change
    public List<Coin> currentTransaction;   // list of coins inserted into machine during current transaction

    private int bankedCoinValue; // changing value of bankedCoins
    private int insertedCoinValue; // changing value of insertedCoins
    public List<Coin> returnedCoins; // changing value of returned Coins
    public int ChangeValue;

    // EFFECTS: constructs a payment unit with no banked coins and no coins inserted into the machine
    // as part of a payment
    public PaymentUnit() {
        numLoonies = 0;
        numQuarters = 0;
        numDimes = 0;
        numNickels = 0;
        currentTransaction = new LinkedList<Coin>();
        returnedCoins = new LinkedList<Coin>();
    }

    // MODIFIES: this
    // EFFECTS: clears all the coins banked in the unit
    public void clearCoinsBanked() {
        numLoonies = 0;
        numQuarters = 0;
        numDimes = 0;
        numNickels = 0;
    }

    // REQUIRES: number > 0
    // MODIFIES: this
    // EFFECTS: adds number coins of type c to the banked coins in the unit
    public void addCoinsToBanked(Coin c, int number) {
        if (c == Coin.DIME) {numDimes += number;} else {
            if (c == Coin.LOONIE) {numLoonies += number;} else {
                if (c == Coin.QUARTER) {numQuarters += number;} else {
                    if (c == Coin.NICKEL) {numNickels += number;}
                }
            }
        }
    }

    public int getNumberOfCoinsBankedOfType(Coin c) {
        switch (c) {
            case DIME:
                return numDimes;
            case LOONIE:
                return numLoonies;
            case QUARTER:
                return numQuarters;
            default:
                return numNickels;
        }
    }


    // EFFECTS: returns number of coins banked of the given type
    /*public int getNumberOfCoinsBankedOfType(Coin c) {
        if (c == Coin.DIME) {return numDimes;} else {
            if (c == Coin.LOONIE) {return numLoonies;} else {
                if (c == Coin.QUARTER) {return numQuarters;} else {
                    if (c == Coin.NICKEL) {return numNickels;} else {
                        return 0;
                    }
                }
            }
        }
    }*/

    // EFFECTS: returns the total value of all coins banked in the unit
    public int getValueOfCoinsBanked() {
        bankedCoinValue = (numLoonies * Coin.LOONIE.getValue())+ (numNickels * Coin.NICKEL.getValue())
        + (numDimes * Coin.DIME.getValue()) +  (numQuarters * Coin.QUARTER.getValue());
        return bankedCoinValue;
    }

    // MODIFIES: this
    // EFFECTS: adds coin c to the unit as a part of a transaction
    public void insertCoin(Coin c) {
        currentTransaction.add(c);
    }

    // EFFECTS: returns value of coins inserted for current transaction
    public int getValueOfCoinsInserted() {
        insertedCoinValue = 0;
        for (int i = 0; i < currentTransaction.size(); i++) {
            if (currentTransaction.get(i)==Coin.QUARTER){
                insertedCoinValue+= 25;
            }
            if (currentTransaction.get(i)==Coin.DIME){
                insertedCoinValue+= 10;
            }
            if (currentTransaction.get(i)==Coin.LOONIE){
                insertedCoinValue+= 100;
            }
            if (currentTransaction.get(i)==Coin.NICKEL){
                insertedCoinValue+= 5;
            }
        }
        return insertedCoinValue;
    }

    // MODIFIES: this
    // EFFECTS: coins inserted for current transaction are cleared; list of coins
    // inserted for current transaction is returned in the order in which they were inserted.
    public List<Coin> cancelTransaction() {
        for (int i=0; i< currentTransaction.size(); i++) {
            returnedCoins.add(this.currentTransaction.get(i));
        }
        this.currentTransaction.clear();
        return returnedCoins;
    }

    // REQUIRES: cost <= total value of coins inserted as part of current transaction
    // MODIFIES: this
    // EFFECTS: adds coins inserted to coins banked in unit and returns list of coins that will be provided as change.
    // Coins of largest possible value are used when determining the change.  Change in full is not guaranteed -
    // will provide only as many coins as are banked in the machine, without going over the amount due.
    public List<Coin> makePurchase(int cost) {
        ChangeValue = getValueOfCoinsInserted() - cost;
        if (cost <= getValueOfCoinsInserted()) {
            transferCoins();}
        else {
            ChangeValue = 0;}
        while (ChangeValue>=100 && numLoonies>=1){
            returnedCoins.add(Coin.LOONIE);
            ChangeValue = ChangeValue - Coin.LOONIE.getValue();
            numLoonies-=1;
        }
        while (ChangeValue>=25 && numQuarters>=1){
            returnedCoins.add(Coin.QUARTER);
            ChangeValue = ChangeValue - Coin.QUARTER.getValue();
            numQuarters-=1;
        }
        while (ChangeValue>=10 && numDimes>=1){
            returnedCoins.add(Coin.DIME);
            ChangeValue = ChangeValue - Coin.DIME.getValue();
            numDimes-=1;
        }
        while (ChangeValue>=5 && numNickels>=1){
            returnedCoins.add(Coin.NICKEL);
            ChangeValue = ChangeValue - Coin.NICKEL.getValue();
            numNickels-=1;
        }
        return returnedCoins;
    }

    // MODIFIES: this
    // EFFECTS: adds coins from currentTransaction to the banked coins, and clears currentTransaction
    public void transferCoins(){
        for(int i=0; i < currentTransaction.size(); i++){
            addCoinsToBanked(currentTransaction.get(i),1);
        }
        currentTransaction.clear();
    }
}
