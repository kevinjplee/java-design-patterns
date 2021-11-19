    package com.iluwater.embeddedvalue;

public class Inventory {
    private static int egg;
    private static int milk;

    /**
     * Initializes the inventory private variables.
     */
    public Inventory()
    {
        setEgg(1);
        setMilk(1);
    }

    /**
     * Getter for Egg.
     * @return
     */
    public int getEgg() {
        return egg;
    }

    /**
     * Setting for Egg.
     * @param amount
     */
    public void setEgg(int amount){
        if(amount >= 0){
            egg = amount;
        }
    }

    /**
     * Getter for Milk.
     * @return
     */
    public int getMilk() {  
        return milk;
    }

    /**
     * Setter for Milk.
     * @param amount
     */
    public void setMilk(int amount){
        if(amount >= 0){
            milk = amount;
        }
    }

    
}
