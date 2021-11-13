package com.iluwater.embeddedvalue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class InventoryTest {
    Inventory inventory;

    @BeforeEach
    void initialize(){
        inventory  = new Inventory();
    }

    @Test
    void getAndSetEgg(){
        final int newEgg = 5;
        inventory.setEgg(5);
        assertEquals(newEgg, inventory.getEgg());
    }

    @Test
    void getAndSetMilk(){
        final int newMilk = 6;
        inventory.setMilk(6);
        assertEquals(newMilk, inventory.getMilk());
    }
}
