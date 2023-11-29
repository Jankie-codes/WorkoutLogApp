package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class ExerciseTest {
    Exercise benchPress;

    ExerciseSet set1;
    ExerciseSet set2;
    ExerciseSet set3;

    @BeforeEach
    public void runBefore() {
        benchPress = new Exercise("Bench Press", 1.0);
        set1 = new ExerciseSet(benchPress, 120, 5);
        set2 = new ExerciseSet(benchPress, 135, 2);
        set3 = new ExerciseSet(benchPress, 145, 1);
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

    @Test
    public void testTheoreticalOneRepMax() {
        benchPress.setOneRepMax(135);
        double percentage1 = benchPress.oneRepMaxInPercentBodyWeight(135);
        assertEquals(1.0, percentage1);

        double percentage2 = benchPress.oneRepMaxInPercentBodyWeight(100);
        assertEquals(1.35, percentage2);

        double percentage3 = benchPress.oneRepMaxInPercentBodyWeight(270);
        assertEquals(0.5, percentage3);
    }

    @Test
    public void testSetNewPRIfNecessary() {
        double max1 = set1.theoreticalOneRepMaxBerger();
        double max2 = set2.theoreticalOneRepMaxBerger();
        double max3 = set3.theoreticalOneRepMaxBerger();
        benchPress.setOneRepMax(max2);
        benchPress.setNewPRIfNecessary(set1);
        assertEquals(max2, benchPress.getOneRepMax());

        benchPress.setNewPRIfNecessary(set2);
        assertEquals(max2, benchPress.getOneRepMax());

        benchPress.setNewPRIfNecessary(set3);
        assertEquals(max3, benchPress.getOneRepMax());
    }
}