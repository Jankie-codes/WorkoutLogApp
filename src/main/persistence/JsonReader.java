package persistence;

import model.User;
import model.Workout;
import model.Exercise;
import model.ExerciseSet;

import java.time.LocalDate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//!!!
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public User read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUser(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private User parseUser(JSONObject jsonObject) {
        User u = new User();
        int bodyWeight = jsonObject.getInt("bodyWeight");
        u.setBodyWeight(bodyWeight);
        addExercises(u, jsonObject);
        addWorkouts(u, jsonObject);
        return u;
    }

    //!!! ADD APPROPRIATE FIELDS
    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addWorkouts(User u, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("workouts");
        for (Object json : jsonArray) {
            JSONObject nextWorkout = (JSONObject) json;
            Workout w = parseWorkout(u, nextWorkout);
            u.addWorkout(w);//possible error when adding
        }
    }

    //!!! ADD APPROPRIATE FIELDS
    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addExercises(User u, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("exercises");
        for (Object json : jsonArray) {
            JSONObject nextExercise = (JSONObject) json;
            addExercise(u, nextExercise);
        }
    }



    //!!!
    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private Workout parseWorkout(User u, JSONObject jsonObject) {
        LocalDate date = LocalDate.parse(jsonObject.getString("date")); //POSSIBLE RUNTIME ERROR
        Workout w = new Workout(date);
        addSets(u, w, jsonObject);
        return w;
    }

    //!!! ADD APPROPRIATE FIELDS
    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addSets(User u, Workout w, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sets");
        for (Object json : jsonArray) {
            JSONObject nextSet = (JSONObject) json;
            addSet(u, w, nextSet);
        }
    }

    //!!!
    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addSet(User u, Workout w, JSONObject jsonObject) {
        String exerciseName = jsonObject.getString("name");
        double minPercentBodyWeightForStrong = jsonObject.getDouble("minPercentBodyWeightForStrong");
        double oneRepMax = jsonObject.getDouble("oneRepMax");

        Exercise exercise = null;
        if (exerciseName.equals("Bench Press")) {
            exercise = u.getExercise("Bench Press");
        } else if (exerciseName.equals("Dead Lift")) {
            exercise = u.getExercise("Dead Lift");
        } else if (exerciseName.equals("Squat")) {
            exercise = u.getExercise("Squat");
        }

        int weight = jsonObject.getInt("weight");
        int reps = jsonObject.getInt("reps");

        ExerciseSet set = new ExerciseSet(exercise, weight, reps);
        w.addSet(set);
    }

    //!!!
    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addExercise(User u, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double minPercentBodyWeightForStrong = jsonObject.getDouble("minPercentBodyWeightForStrong");
        double oneRepMax = jsonObject.getDouble("oneRepMax");

        Exercise exercise = new Exercise(name, minPercentBodyWeightForStrong);
        exercise.setOneRepMax(oneRepMax);
        u.addExercise(exercise);
    }
}
