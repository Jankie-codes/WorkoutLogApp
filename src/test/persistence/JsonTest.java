package persistence;

import model.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Code influenced by the JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {
    protected void checkExercise(String name, double minPercent, double oneRepMax, Exercise exercise) {
        assertEquals(name, exercise.getName());
        assertEquals(minPercent, exercise.getMinPercentBodyWeightForStrong());
        assertEquals(oneRepMax, exercise.getOneRepMax());
    }

    protected void checkWorkout(LocalDate date, List<ExerciseSet> sets, Workout workout) {
        assertEquals(0, date.compareTo(workout.getDate()));
        checkSets(sets, workout);
    }

    protected void checkSets(List<ExerciseSet> sets, Workout workout) {
        for (int i = 0; i < sets.size(); i++) {
            checkSet(sets.get(i), workout.getSets().get(i));
        }
    }

    protected void checkSet(ExerciseSet setExpected, ExerciseSet setActual) {
        assertEquals(setExpected.getReps(), setActual.getReps());
        assertEquals(setExpected.getWeight(), setActual.getWeight());
        Exercise exerciseExpected = setExpected.getExercise();
        checkExercise(exerciseExpected.getName(), exerciseExpected.getMinPercentBodyWeightForStrong(),
                exerciseExpected.getOneRepMax(), setActual.getExercise());
    }
}
