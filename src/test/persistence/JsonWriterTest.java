package persistence;

import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//Code influenced by the JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriterTest extends JsonTest {
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
    void testWriterInvalidFile() {
        try {
            User u = new User();
            u.setBodyWeight(135);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterNoWorkoutsAdded() {
        try {
            User u = new User();
            u.setBodyWeight(135);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyUser.json");
            writer.open();
            writer.write(u);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyUser.json");
            u = reader.read();
            assertEquals(135, u.getBodyWeight());
            assertEquals(0, u.getWorkouts().size());
            assertEquals(0, u.getExercises().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralUser() {
        try {
            User u = new User();
            u.setBodyWeight(135);
            u.addExercise(benchPress);
            u.addExercise(deadLift);

            testWorkout1.addSet(set1);
            testWorkout2.addSet(set2);
            testWorkout2.addSet(set3);
            u.addWorkout(testWorkout1);
            u.addWorkout(testWorkout2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUser.json");
            writer.open();
            writer.write(u);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralUser.json");
            u = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }
}
