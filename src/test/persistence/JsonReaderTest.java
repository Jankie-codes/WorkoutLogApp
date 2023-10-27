package persistence;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

public class JsonReaderTest extends JsonTest {
    Exercise benchPress;
    Exercise deadLift;
    Workout testWorkout1;
    Workout testWorkout2;
    LocalDate date1;
    LocalDate date2;

    ExerciseSet set1;
    ExerciseSet set2;
    ExerciseSet set3;

    @BeforeEach
    public void runBefore() {
        benchPress = new Exercise("Bench Press", 1.0);
        deadLift = new Exercise("Dead Lift", 2.0);

        date1 = LocalDate.of(2023,9,20);
        date2 = LocalDate.of(2023,9,30);
        testWorkout1 = new Workout(date1);
        testWorkout2 = new Workout(date2);

        set1 = new ExerciseSet(benchPress, 120, 5);
        set2 = new ExerciseSet(benchPress, 135, 7);
        set3 = new ExerciseSet(deadLift, 225, 1);
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            User u = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyUser() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyUser.json");
        try {
            User u = reader.read();
            assertEquals(135, u.getBodyWeight());
            assertEquals(0, u.getWorkouts().size());
            assertEquals(0, u.getExercises().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralUser() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralUser.json");
        try {
            User u = reader.read();
            assertEquals(135, u.getBodyWeight());

            assertEquals(2, u.getExercises().size());
            checkExercise(benchPress.getName(),
                    benchPress.getMinPercentBodyWeightForStrong(),
                    benchPress.getOneRepMax(), u.getExercises().get(0));
            checkExercise(deadLift.getName(),
                    deadLift.getMinPercentBodyWeightForStrong(),
                    deadLift.getOneRepMax(), u.getExercises().get(1));

            assertEquals(2, u.getWorkouts().size());

            List<ExerciseSet> setsExpected = new ArrayList<>();
            setsExpected.add(set1);
            checkWorkout(date1, setsExpected, u.getWorkouts().get(0));

            setsExpected = new ArrayList<>();
            setsExpected.add(set2);
            setsExpected.add(set3);
            checkWorkout(date2, setsExpected, u.getWorkouts().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
