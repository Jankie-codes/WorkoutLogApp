package model;

import org.json.JSONObject;
import persistence.Writable;

//represents an exercise set done during a workout with an exercise, total weight lifted, and total reps done.
public class ExerciseSet implements Writable {
    Exercise exercise;
    int weight;
    int reps;

    //REQUIRES: weight > 0, reps > 0
    //EFFECTS: constructs an ExerciseSet with the given exercise, weight, and # of reps
    public ExerciseSet(Exercise exercise, int weight, int reps) {
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
    }

    //EFFECTS: returns the theoretical one rep max for this set using the Matt Brzycki equation:
    // (weight / ( 1.0278 - 0.0278 * reps ))
    public double theoreticalOneRepMax() {
        return (((double) this.weight) / (1.0278 - 0.0278 * this.reps));
    }

    //REQUIRES: bodyWeight > 0
    //EFFECTS: returns the theoretical one rep max for this set as a percentage of the given body weight
    public double theoreticalOneRepMaxInPercentBodyWeight(int bodyWeight) {
        return (this.theoreticalOneRepMax() / bodyWeight);
    }

    public Exercise getExercise() {
        return this.exercise;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getReps() {
        return this.reps;
    }

    //commented out the methods below because they are not ever used in the program
    // and code coverage expects tests for them.

    //public void setExercise(Exercise exercise) {
    //    this.exercise = exercise;
    //}

    //public void setWeight(int weight) {
    //    this.weight = weight;
    //}

    //public void setReps(int reps) {
    //    this.reps = reps;
    //}

    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", exercise.getName());
        json.put("minPercentBodyWeightForStrong", exercise.getMinPercentBodyWeightForStrong());
        json.put("oneRepMax", exercise.getOneRepMax());

        json.put("weight", weight);
        json.put("reps", reps);
        return json;
    }
}






