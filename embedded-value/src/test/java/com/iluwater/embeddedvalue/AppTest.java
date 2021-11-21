package com.iluwater.embeddedvalue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
public class AppTest {

    /**
     * Asserts that there are no exception while running the app itself.
     */
    @Test
    void executeApp(){
        assertDoesNotThrow(()-> App.main(new String[]{}));
    }
}
