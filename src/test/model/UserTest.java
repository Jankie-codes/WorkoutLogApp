package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

public class UserTest {
    User adrian;
    Exercise benchPress;
    Exercise benchPressDuplicateName;
    Exercise deadLift;
    Workout testWorkout1;
    Workout testWorkout2;
    Workout testWorkout3;
    Workout testWorkout4;
    Workout testWorkout5;
    LocalDate date1;
    LocalDate date2;
    LocalDate date3;

    ExerciseSet set1;
    ExerciseSet set2;
    ExerciseSet set3;
    ExerciseSet set4;

    @BeforeEach
    public void runBefore() {
        adrian = new User();
        benchPress = new Exercise("Bench Press", 1.0);
        benchPressDuplicateName = new Exercise("Bench Press", 1.25);
        deadLift = new Exercise("Dead Lift", 2.0);

        date1 = LocalDate.of(2023,9,20);
        date2 = LocalDate.of(2023,9,30);
        date3 = LocalDate.of(2023,8,15);
        testWorkout1 = new Workout(date1);
        testWorkout2 = new Workout(date1);
        testWorkout3 = new Workout(date1);
        testWorkout4 = new Workout(date2);
        testWorkout5 = new Workout(date3);

        set1 = new ExerciseSet(benchPress, 120, 5);
        set2 = new ExerciseSet(benchPress, 135, 7);
        set3 = new ExerciseSet(deadLift, 225, 1);
        set4 = new ExerciseSet(benchPress, 120, 5);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, adrian.getWorkouts().size());
        assertEquals(0, adrian.getExercises().size());
        assertEquals(0, adrian.getBodyWeight());
    }

    @Test
    public void testAddExercise() {
        boolean exerciseAdded1 = adrian.addExercise(benchPress);
        assertEquals(1, adrian.getExercises().size());
        assertEquals(benchPress, adrian.getExercises().get(0));
        assertTrue(exerciseAdded1);
    }

    @Test
    public void testAddExerciseMultipleTimes() {
        boolean exerciseAdded1 = adrian.addExercise(benchPress);
        boolean exerciseAdded2 = adrian.addExercise(deadLift);
        assertEquals(2, adrian.getExercises().size());
        assertEquals(benchPress, adrian.getExercises().get(0));
        assertEquals(deadLift, adrian.getExercises().get(1));
        assertTrue(exerciseAdded1);
        assertTrue(exerciseAdded2);
    }

    @Test
    public void testAddExerciseMultipleTimesSameNameTwice() {
        boolean exerciseAdded1 = adrian.addExercise(benchPress);
        boolean exerciseAdded2 = adrian.addExercise(benchPressDuplicateName);
        assertEquals(1, adrian.getExercises().size());
        assertEquals(benchPress, adrian.getExercises().get(0));
        assertTrue(exerciseAdded1);
        assertFalse(exerciseAdded2);
    }

    @Test
    public void testAddWorkout() {
        adrian.addWorkout(testWorkout1);
        assertEquals(1, adrian.getWorkouts().size());
        assertEquals(testWorkout1, adrian.getWorkouts().get(0));
    }

    @Test
    public void testAddWorkoutMultipleTimesSameDate() {
        adrian.addWorkout(testWorkout1);
        adrian.addWorkout(testWorkout2);
        adrian.addWorkout(testWorkout3);
        assertEquals(3, adrian.getWorkouts().size());
        assertEquals(testWorkout1, adrian.getWorkouts().get(0));
        assertEquals(testWorkout2, adrian.getWorkouts().get(1));
        assertEquals(testWorkout3, adrian.getWorkouts().get(2));
    }

    @Test
    public void testAddWorkoutThreeTimesDifferentDates() {
        adrian.addWorkout(testWorkout1);
        adrian.addWorkout(testWorkout4);
        adrian.addWorkout(testWorkout5);

        assertEquals(3, adrian.getWorkouts().size());
        assertEquals(testWorkout5, adrian.getWorkouts().get(0));
        assertEquals(testWorkout1, adrian.getWorkouts().get(1));
        assertEquals(testWorkout4, adrian.getWorkouts().get(2));
    }

    @Test
    public void testListPRsNoSetsAdded() {
        HashMap<LocalDate, ExerciseSet> personalRecords = adrian.listPRs(benchPress);
        assertEquals(0, personalRecords.size());
    }

    @Test
    public void testListPRsOneSet() {
        testWorkout1.addSet(set1);
        adrian.addWorkout(testWorkout1);

        HashMap<LocalDate, ExerciseSet> personalRecords = adrian.listPRs(benchPress);
        assertEquals(1, personalRecords.size());
        assertEquals(set1, personalRecords.get(date1));
    }

    //adds the stronger set first, then the weaker set
    @Test
    public void testListPRsTwoRecordsOneWorkout() {
        testWorkout1.addSet(set2);
        testWorkout1.addSet(set1);

        adrian.addWorkout(testWorkout1);

        HashMap<LocalDate, ExerciseSet> personalRecords = adrian.listPRs(benchPress);
        assertEquals(1, personalRecords.size());
        assertEquals(set2, personalRecords.get(date1));
    }

    @Test
    public void testListPRsTwoRecordsOnDifferentDays() {
        testWorkout1.addSet(set1);
        testWorkout4.addSet(set2);

        adrian.addWorkout(testWorkout1);
        adrian.addWorkout(testWorkout4);

        HashMap<LocalDate, ExerciseSet> personalRecords = adrian.listPRs(benchPress);
        assertEquals(2, personalRecords.size());
        assertEquals(set1, personalRecords.get(date1));
        assertEquals(set2, personalRecords.get(date2));
    }

    @Test
    public void testListPRsOneRecordTwoWorkouts() {
        testWorkout1.addSet(set2);
        testWorkout4.addSet(set1);

        adrian.addWorkout(testWorkout1);
        adrian.addWorkout(testWorkout4);

        HashMap<LocalDate, ExerciseSet> personalRecords = adrian.listPRs(benchPress);
        assertEquals(1, personalRecords.size());
        assertEquals(set2, personalRecords.get(date1));
    }

    @Test
    public void testListPRsMultipleDifferentExercisesInWorkoutList() {
        testWorkout1.addSet(set1);
        testWorkout4.addSet(set3);

        adrian.addWorkout(testWorkout1);
        adrian.addWorkout(testWorkout4);

        HashMap<LocalDate, ExerciseSet> personalRecords = adrian.listPRs(benchPress);
        assertEquals(1, personalRecords.size());
        assertEquals(set1, personalRecords.get(date1));
    }

    @Test
    public void testListPRsTyingRecords() {
        testWorkout1.addSet(set4);
        testWorkout4.addSet(set1);

        adrian.addWorkout(testWorkout1);
        adrian.addWorkout(testWorkout4);

        HashMap<LocalDate, ExerciseSet> personalRecords = adrian.listPRs(benchPress);
        assertEquals(1, personalRecords.size());
        assertEquals(set4, personalRecords.get(date1));
    }

    @Test
    public void testRoundToBarbellWeight() {
        assertEquals(135, User.roundToBarbellWeight(136.0));
        assertEquals(135, User.roundToBarbellWeight(139.9));
        assertEquals(140, User.roundToBarbellWeight(140.0));
        assertEquals(140, User.roundToBarbellWeight(141.1));
        assertEquals(0, User.roundToBarbellWeight(0.0));
    }
}
