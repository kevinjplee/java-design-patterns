package com.iluwater.embeddedvalue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class InventoryTest {
    Inventory inventory;

    /**
     * Initializes the materials for the later tests
     */
    @BeforeEach
    void initialize(){
        inventory  = new Inventory();
    }

    /**
     * Check getter and setter for the egg
     */
    @Test
    void getAndSetEgg(){
        final int newEgg = 5;
        inventory.setEgg(5);
        assertEquals(newEgg, inventory.getEgg());
    }

    /**
     * Check getter and setter for the milk
     */
    @Test
    void getAndSetMilk(){
        final int newMilk = 6;
        inventory.setMilk(6);
        assertEquals(newMilk, inventory.getMilk());
    }
}
