package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExerciseSetTest {
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
        assertEquals(benchPress, set1.getExercise());
        assertEquals(benchPress, set2.getExercise());
        assertEquals(benchPress, set3.getExercise());

        assertEquals(5, set1.getReps());
        assertEquals(2, set2.getReps());
        assertEquals(1, set3.getReps());

        assertEquals(120, set1.getWeight());
        assertEquals(135, set2.getWeight());
        assertEquals(145, set3.getWeight());
    }

    @Test
    public void testTheoreticalOneRepMaxBerger() {
        assertEquals(133.0270611, set1.theoreticalOneRepMaxBerger(), 0.0000001);
        assertEquals(138.5235, set2.theoreticalOneRepMaxBerger(), 0.0000001);
        assertEquals(145, set3.theoreticalOneRepMaxBerger(), 0.0000001);
    }

    @Test
    public void testTheoreticalOneRepMaxBergerMultipleTimes() {
        assertEquals(133.0270611, set1.theoreticalOneRepMaxBerger(), 0.0000001);
        assertEquals(133.0270611, set1.theoreticalOneRepMaxBerger(), 0.0000001);
        assertEquals(133.0270611, set1.theoreticalOneRepMaxBerger(), 0.0000001);
    }

    @Test
    public void testTheoreticalOneRepMaxInPercentBodyWeightBrzycki() {
        int bodyWeight1 = 135;
        int bodyWeight2 = 145;

        assertEquals(0.9174280075, set1.theoreticalOneRepMaxInPercentBodyWeight(bodyWeight2), 0.0000001);
        assertEquals(1.0, set3.theoreticalOneRepMaxInPercentBodyWeight(bodyWeight2), 0.0000001);
        assertEquals(1.0261, set2.theoreticalOneRepMaxInPercentBodyWeight(bodyWeight1), 0.0000001);
    }

    @Test
    public void testTheoreticalOneRepMaxInPercentBodyWeightMultipleTimesBrzycki() {
        int bodyWeight2 = 145;

        assertEquals(0.9174280075, set1.theoreticalOneRepMaxInPercentBodyWeight(bodyWeight2), 0.0000001);
        assertEquals(0.9174280075, set1.theoreticalOneRepMaxInPercentBodyWeight(bodyWeight2), 0.0000001);
    }

    //The following two tests assume that the Brzycki equation is used in theoreticalOneRepMaxInPercentBodyWeight method
    /*
    @Test
    public void testTheoreticalOneRepMaxInPercentBodyWeightBrzycki() {
        int bodyWeight1 = 135;
        int bodyWeight2 = 145;

        assertEquals(0.9311275955, set1.theoreticalOneRepMaxInPercentBodyWeight(bodyWeight2), 0.0000001);
        assertEquals(1.0, set3.theoreticalOneRepMaxInPercentBodyWeight(bodyWeight2), 0.0000001);
        assertEquals(1.028594939, set2.theoreticalOneRepMaxInPercentBodyWeight(bodyWeight1), 0.0000001);
    }

    @Test
    public void testTheoreticalOneRepMaxInPercentBodyWeightMultipleTimesBrzycki() {
        int bodyWeight2 = 145;

        assertEquals(0.9311275959, set1.theoreticalOneRepMaxInPercentBodyWeight(bodyWeight2), 0.0000001);
        assertEquals(0.9311275959, set1.theoreticalOneRepMaxInPercentBodyWeight(bodyWeight2), 0.0000001);
    }
    */

    @Test
    public void testTheoreticalOneRepMaxBrzycki() {
        assertEquals(135.0135014, set1.theoreticalOneRepMaxBrzycki(), 0.0000001);
        assertEquals(138.8603168, set2.theoreticalOneRepMaxBrzycki(), 0.0000001);
        assertEquals(145, set3.theoreticalOneRepMaxBrzycki(), 0.0000001);
    }

    @Test
    public void testTheoreticalOneRepMaxBrzyckiMultipleTimes() {
        assertEquals(135.0135014, set1.theoreticalOneRepMaxBrzycki(), 0.0000001);
        assertEquals(135.0135014, set1.theoreticalOneRepMaxBrzycki(), 0.0000001);
        assertEquals(135.0135014, set1.theoreticalOneRepMaxBrzycki(), 0.0000001);
    }
}
