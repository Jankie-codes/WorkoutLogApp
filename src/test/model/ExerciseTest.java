package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class ExerciseTest {
    Exercise benchPress;

    @BeforeEach
    public void runBefore() {
        benchPress = new Exercise("Bench Press", 1.0);
    }

    @Test
    public void testConstructor() {
        assertEquals("Bench Press", benchPress.getName());
        assertEquals(1.0, benchPress.getMinPercentBodyWeightForStrong());
        assertEquals(0, benchPress.getOneRepMax());
    }

    @Test
    public void testIsStrong() {
        int bodyWeight = 135;
        benchPress.setOneRepMax(135);
        assertTrue(benchPress.isStrong(bodyWeight));

        benchPress.setOneRepMax(130);
        assertFalse(benchPress.isStrong(bodyWeight));

        benchPress.setOneRepMax(225);
        assertTrue(benchPress.isStrong(bodyWeight));
    }

    @Test
    public void testIsStrongMultipleTimes() {
        int bodyWeight = 135;
        benchPress.setOneRepMax(135);
        assertTrue(benchPress.isStrong(bodyWeight));
        benchPress.setOneRepMax(135);
        assertTrue(benchPress.isStrong(bodyWeight));

        benchPress.setOneRepMax(130);
        assertFalse(benchPress.isStrong(bodyWeight));
        benchPress.setOneRepMax(130);
        assertFalse(benchPress.isStrong(bodyWeight));

        benchPress.setOneRepMax(225);
        assertTrue(benchPress.isStrong(bodyWeight));
        benchPress.setOneRepMax(225);
        assertTrue(benchPress.isStrong(bodyWeight));
    }


}