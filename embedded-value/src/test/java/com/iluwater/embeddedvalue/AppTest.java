package com.iluwater.embeddedvalue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
public class AppTest {
    @Test
    void executeApp(){
        assertDoesNotThrow(()-> App.main(new String[]{}));
    }
}
