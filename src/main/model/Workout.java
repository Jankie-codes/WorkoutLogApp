package model;

import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

//represents a workout done on a particular date, and with a list of sets completed on that day
// from least to most recent
public class Workout implements Writable {
    private LocalDate date;
    private ArrayList<ExerciseSet> sets;

    //EFFECTS: constructs a new workout on the given date, and with no sets (i.e. this.sets is empty)
    public Workout(LocalDate date) {
        this.date = date;
        this.sets = new ArrayList<>();
    }

    //REQUIRES: set must not already be in this.sets
    //MODIFIES: this
    //EFFECTS: adds the given set into this.sets
    public void addSet(ExerciseSet set) {
        sets.add(set);
    }

    public LocalDate getDate() {
        return this.date;
    }

    public ArrayList<ExerciseSet> getSets() {
        return this.sets;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", date.toString());
        json.put("sets", setsToJson());
        return json;
    }

    //EFFECTS: returns this workout's sets (found in this.sets) as a JSON array
    private JSONArray setsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (ExerciseSet s : sets) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}


