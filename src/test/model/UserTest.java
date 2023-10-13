package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

public class UserTest {
    User testUser;
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
        testUser = new User();
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
        assertEquals(0, testUser.getWorkouts().size());
        assertEquals(0, testUser.getExercises().size());
        assertEquals(0, testUser.getBodyWeight());
    }

    @Test
    public void testAddExercise() {
        boolean exerciseAdded1 = testUser.addExercise(benchPress);
        assertEquals(1, testUser.getExercises().size());
        assertEquals(benchPress, testUser.getExercises().get(0));
        assertTrue(exerciseAdded1);
    }

    @Test
    public void testAddExerciseMultipleTimes() {
        boolean exerciseAdded1 = testUser.addExercise(benchPress);
        boolean exerciseAdded2 = testUser.addExercise(deadLift);
        assertEquals(2, testUser.getExercises().size());
        assertEquals(benchPress, testUser.getExercises().get(0));
        assertEquals(deadLift, testUser.getExercises().get(1));
        assertTrue(exerciseAdded1);
        assertTrue(exerciseAdded2);
    }

    @Test
    public void testAddExerciseMultipleTimesSameNameTwice() {
        boolean exerciseAdded1 = testUser.addExercise(benchPress);
        boolean exerciseAdded2 = testUser.addExercise(benchPressDuplicateName);
        assertEquals(1, testUser.getExercises().size());
        assertEquals(benchPress, testUser.getExercises().get(0));
        assertTrue(exerciseAdded1);
        assertFalse(exerciseAdded2);
    }

    @Test
    public void testAddWorkout() {
        testUser.addWorkout(testWorkout1);
        assertEquals(1, testUser.getWorkouts().size());
        assertEquals(testWorkout1, testUser.getWorkouts().get(0));
    }

    @Test
    public void testAddWorkoutMultipleTimesSameDate() {
        testUser.addWorkout(testWorkout1);
        testUser.addWorkout(testWorkout2);
        testUser.addWorkout(testWorkout3);
        assertEquals(3, testUser.getWorkouts().size());
        assertEquals(testWorkout1, testUser.getWorkouts().get(0));
        assertEquals(testWorkout2, testUser.getWorkouts().get(1));
        assertEquals(testWorkout3, testUser.getWorkouts().get(2));
    }

    @Test
    public void testAddWorkoutThreeTimesDifferentDates() {
        testUser.addWorkout(testWorkout1);
        testUser.addWorkout(testWorkout4);
        testUser.addWorkout(testWorkout5);

        assertEquals(3, testUser.getWorkouts().size());
        assertEquals(testWorkout5, testUser.getWorkouts().get(0));
        assertEquals(testWorkout1, testUser.getWorkouts().get(1));
        assertEquals(testWorkout4, testUser.getWorkouts().get(2));
    }

    @Test
    public void testListPRsNoSetsAdded() {
        HashMap<LocalDate, ExerciseSet> personalRecords = testUser.listPRs(benchPress);
        assertEquals(0, personalRecords.size());
    }

    @Test
    public void testListPRsOneSet() {
        testWorkout1.addSet(set1);
        testUser.addWorkout(testWorkout1);

        HashMap<LocalDate, ExerciseSet> personalRecords = testUser.listPRs(benchPress);
        assertEquals(1, personalRecords.size());
        assertEquals(set1, personalRecords.get(date1));
    }

    //adds the stronger set first, then the weaker set
    @Test
    public void testListPRsTwoRecordsOneWorkout() {
        testWorkout1.addSet(set2);
        testWorkout1.addSet(set1);

        testUser.addWorkout(testWorkout1);

        HashMap<LocalDate, ExerciseSet> personalRecords = testUser.listPRs(benchPress);
        assertEquals(1, personalRecords.size());
        assertEquals(set2, personalRecords.get(date1));
    }

    @Test
    public void testListPRsTwoRecordsOnDifferentDays() {
        testWorkout1.addSet(set1);
        testWorkout4.addSet(set2);

        testUser.addWorkout(testWorkout1);
        testUser.addWorkout(testWorkout4);

        HashMap<LocalDate, ExerciseSet> personalRecords = testUser.listPRs(benchPress);
        assertEquals(2, personalRecords.size());
        assertEquals(set1, personalRecords.get(date1));
        assertEquals(set2, personalRecords.get(date2));
    }

    @Test
    public void testListPRsOneRecordTwoWorkouts() {
        testWorkout1.addSet(set2);
        testWorkout4.addSet(set1);

        testUser.addWorkout(testWorkout1);
        testUser.addWorkout(testWorkout4);

        HashMap<LocalDate, ExerciseSet> personalRecords = testUser.listPRs(benchPress);
        assertEquals(1, personalRecords.size());
        assertEquals(set2, personalRecords.get(date1));
    }

    @Test
    public void testListPRsMultipleDifferentExercisesInWorkoutList() {
        testWorkout1.addSet(set1);
        testWorkout4.addSet(set3);

        testUser.addWorkout(testWorkout1);
        testUser.addWorkout(testWorkout4);

        HashMap<LocalDate, ExerciseSet> personalRecords = testUser.listPRs(benchPress);
        assertEquals(1, personalRecords.size());
        assertEquals(set1, personalRecords.get(date1));
    }

    @Test
    public void testListPRsTyingRecords() {
        testWorkout1.addSet(set4);
        testWorkout4.addSet(set1);

        testUser.addWorkout(testWorkout1);
        testUser.addWorkout(testWorkout4);

        HashMap<LocalDate, ExerciseSet> personalRecords = testUser.listPRs(benchPress);
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

    @Test
    public void testGetWorkoutOnDate() {
        assertNull(testUser.getWorkoutOnDate(date1));
        testUser.addWorkout(testWorkout1);
        testUser.addWorkout(testWorkout4);
        assertEquals(testWorkout1, testUser.getWorkoutOnDate(date1));
        assertEquals(testWorkout4, testUser.getWorkoutOnDate(date2));
    }

    @Test
    public void testWorkoutExistsOnDate() {
        testUser.addWorkout(testWorkout1);
        testUser.addWorkout(testWorkout4);

        assertTrue(testUser.workoutExistsOnDate(date1));
        assertTrue(testUser.workoutExistsOnDate(date2));
        assertFalse(testUser.workoutExistsOnDate(date3));
        assertFalse(testUser.workoutExistsOnDate(LocalDate.of(2023,01,01)));
        assertFalse(testUser.workoutExistsOnDate(LocalDate.of(2022,04,20)));
    }
}
