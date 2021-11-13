    package com.iluwater.embeddedvalue;

public class Inventory {
    private static int egg;
    private static int milk;

    public Inventory()
    {
        setEgg(1);
        setMilk(1);
    }

    public int getEgg() {
        return egg;
    }

    public void setEgg(int amount){
        if(amount >= 0){
            egg = amount;
        }
    }

    public int getMilk() {  
        return milk;
    }

    public void setMilk(int amount){
        if(amount >= 0){
            milk = amount;
        }
    }

    
}
