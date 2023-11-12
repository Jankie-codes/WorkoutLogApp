package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.TreeMap;

import model.exceptions.*;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

//represents a weightlifter (i.e. the user), with a body weight, list of exercises done, and list of workouts
// completed in his/her life from least to most recent.
public class User implements Writable {
    ArrayList<Workout> workouts;
    ArrayList<Exercise> exercises;
    int bodyWeight;

    //EFFECTS: constructs a new User with no workouts (i.e. this.workouts is empty), no exercises/movements done before
    // (i.e. this.exercises is empty), and a body weight of 0 lbs
    public User() {
        this.workouts = new ArrayList<>();
        this.exercises = new ArrayList<>();
        this.bodyWeight = 0;
    }

    //REQUIRES: exercise must not already be in this.exercises
    //MODIFIES: this
    //EFFECTS: adds the given exercise into this.exercises and returns true ONLY IF no Exercise in this.exercises
    // has the same name already. If an Exercise in this.exercises already has the same name as
    // the exercise to be added, then add nothing and return false
    public boolean addExercise(Exercise exercise) {
        for (Exercise exerciseInList : this.exercises) {
            if (exerciseInList.getName().equals(exercise.getName())) {
                return false;
            }
        }
        this.exercises.add(exercise);
        return true;
    }

    //REQUIRES: workoutToAdd must not already be in this.workouts
    //MODIFIES: this
    //EFFECTS: adds the given workoutToAdd to this.workouts at the right index, making sure that
    // this.workouts is in order from least to most recent workout.
    // If adding two workouts which are on the same date, add both of them and leaves the most recently added
    // one as the newest.
    public void addWorkout(Workout workoutToAdd) {
        for (Workout workout : this.workouts) {
            if (workout.getDate().compareTo(workoutToAdd.getDate()) > 0) {
                this.workouts.add(this.workouts.indexOf(workout), workoutToAdd);
                return;
            }
        }
        if (!(this.workouts.contains(workoutToAdd))) {
            this.workouts.add(workoutToAdd);
        }
    }

    //REQUIRES: exercise must be in this.exercises
    //EFFECTS: returns a treemap containing the all the user's sets which are personal weightlifting records
    // (PRs) for a given exercise. Each value is an exerciseSet, and each key is the date when the set was performed.
    //If multiple personal records occur on the same day, only include the strongest set (i.e. with a higher
    // theoreticalOneRepMax).
    //Tying records are not added.
    //LocalDate keys are organized in chronological order.
    public TreeMap<LocalDate, ExerciseSet> listPRs(Exercise exercise) {
        double oneRepMaxSoFar = 0;
        TreeMap<LocalDate, ExerciseSet> personalRecords = new TreeMap<>();

        for (int i = 0; i <= (this.workouts.size() - 1); i++) {
            for (ExerciseSet set : this.workouts.get(i).getSets()) {
                if ((set.getExercise() == exercise) && (set.theoreticalOneRepMax() > oneRepMaxSoFar)) {
                    personalRecords.put(this.workouts.get(i).getDate(), set);
                    oneRepMaxSoFar = set.theoreticalOneRepMax();
                }
            }
        }
        return personalRecords;
    }

    //REQUIRES: numberToBeRounded >= 0
    //EFFECTS: returns the greatest multiple of 5 which is still less than numberToBeRounded.
    // in the context of this program, this method returns a weight which can be loaded up on a barbell (with 2.5lb
    // plates being the most precise plate available).
    public static int roundToBarbellWeight(double numberToBeRounded) {
        return ((((int) numberToBeRounded) / 5) * 5);
    }

    public ArrayList<Workout> getWorkouts() {
        return this.workouts;
    }

    public ArrayList<Exercise> getExercises() {
        return this.exercises;
    }

    public int getBodyWeight() {
        return this.bodyWeight;
    }

    public void setBodyWeight(int bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    //REQUIRES: multiple workouts on the same date do not exist in this.workouts.
    //EFFECTS: if a workout with the given date exists in this.workouts, then return that workout.
    // Otherwise, return null
    public Workout getWorkoutOnDate(LocalDate date) {
        for (Workout workout : this.workouts) {
            if (workout.getDate().compareTo(date) == 0) {
                return workout;
            }
        }
        return null;
    }

    //EFFECTS: returns true if a workout with the given date exists in this.workouts. Otherwise, return false
    public Boolean workoutExistsOnDate(LocalDate date) {
        for (Workout workout : this.workouts) {
            if (workout.getDate().compareTo(date) == 0) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: if an Exercise with the given exerciseName exists in this.exercises, returns it
    // otherwise, returns null
    public Exercise getExercise(String exerciseName) throws ExerciseNotFoundException {
        for (Exercise exercise : this.exercises) {
            if (exercise.getName().equals(exerciseName)) {
                return exercise;
            }
        }
        throw new ExerciseNotFoundException();
    }

    //EFFECTS: returns a String of all exercise names in this.exercises, each one separated by a new line.
    // Exercise names are printed in order of this.exercises ArrayList.
    public String getExerciseNamesString() {
        String listOfNames = "";
        for (Exercise exercise : this.getExercises()) {
            listOfNames += exercise.getName();
            listOfNames += "\n";
        }
        listOfNames = listOfNames.trim();
        return listOfNames;
    }

    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("workouts", workoutsToJson());
        json.put("exercises", exercisesToJson());
        json.put("bodyWeight", bodyWeight);
        return json;
    }

    // EFFECTS: returns this user's workouts (listed in this.workouts) as a JSON array
    private JSONArray workoutsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Workout w : workouts) {
            jsonArray.put(w.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns this user's exercises (listed in this.exercises) as a JSON array
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise e : exercises) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}
