package model;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorkoutTest {
    LocalDate date1;
    Workout testWorkout;
    Exercise benchPress;
    ExerciseSet set1;
    ExerciseSet set2;
    ExerciseSet set3;

    @BeforeEach
    public void runBefore() {
        date1 = LocalDate.of(2023,9,20);
        testWorkout = new Workout(date1);
        benchPress = new Exercise("Bench Press", 1.0);
        set1 = new ExerciseSet(benchPress, 120, 5);
        set2 = new ExerciseSet(benchPress, 120, 5);
        set3 = new ExerciseSet(benchPress, 135, 1);
    }


    @Test
    public void testConstructor() {
        assertEquals(date1, testWorkout.getDate());
        assertEquals(0, testWorkout.getSets().size());
    }

    @Test
    public void testAddSet() {
        testWorkout.addSet(set1);
        assertEquals(1, testWorkout.getSets().size());
        assertEquals(set1, testWorkout.getSets().get(0));
    }

    @Test
    public void testAddSetMultipleTimes() {
        testWorkout.addSet(set1);
        testWorkout.addSet(set3);

        assertEquals(2, testWorkout.getSets().size());
        assertEquals(set1, testWorkout.getSets().get(0));
        assertEquals(set3, testWorkout.getSets().get(1));
    }

    @Test
    public void testAddSetMultipleTimesSameFields() {
        testWorkout.addSet(set1);
        testWorkout.addSet(set2);
        assertEquals(2, testWorkout.getSets().size());
        assertEquals(set1, testWorkout.getSets().get(0));
        assertEquals(set2, testWorkout.getSets().get(1));
    }
}
