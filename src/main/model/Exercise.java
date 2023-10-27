package model;

import org.json.JSONObject;
import persistence.Writable;

//represents a type of exercise movement with a name,
// a minimum percentage of body weight which must be lifted for 1 rep for a lifter to be
// considered strong at this movement, and
// the maximum amount of weight which the user should be able to lift for one rep; a one-rep-max.
public class Exercise implements Writable {
    String name;
    double minPercentBodyWeightForStrong;
    double oneRepMax;

    //REQUIRES: minPercent > 0
    //EFFECTS: creates an Exercise with the given name and minimum percentage. By default, the user's one-rep-max for
    // this exercise is set to 0 lbs.
    public Exercise(String name, Double minPercent) {
        this.name = name;
        this.minPercentBodyWeightForStrong = minPercent;
        this.oneRepMax = 0;
    }

    //REQUIRES: bodyWeight > 0
    //EFFECTS: returns true if the user can lift, for at least one rep, the minimum percentage of body weight
    // required to be considered strong at this exercise
    public boolean isStrong(int bodyWeight) {
        return ((((double) this.oneRepMax) / bodyWeight) >= this.minPercentBodyWeightForStrong);
    }

    //REQUIRES: bodyWeight > 0
    //EFFECTS: returns this exercise's one rep max as a percentage of the given body weight
    public double oneRepMaxInPercentBodyWeight(int bodyWeight) {
        return (this.oneRepMax / bodyWeight);
    }

    //MODIFIES: this
    //EFFECTS: if the given ExerciseSet has a greater theoretical one rep max than the current one rep max,
    // then update this.oneRepMax to the given ExerciseSet's theoretical max
    public void setNewPRIfNecessary(ExerciseSet set) {
        if (set.theoreticalOneRepMax() > this.oneRepMax) {
            this.oneRepMax = set.theoreticalOneRepMax();
        }
    }

    public String getName() {
        return this.name;
    }

    public double getMinPercentBodyWeightForStrong() {
        return this.minPercentBodyWeightForStrong;
    }

    public double getOneRepMax() {
        return this.oneRepMax;
    }

    public void setOneRepMax(double oneRepMax) {
        this.oneRepMax = oneRepMax;
    }

    //commented out the methods below because they are not ever used in the program
    // and code coverage expects tests for them.

    //public void setName(String name) {
    //    this.name = name;
    //}

    //public void setMinPercentBodyWeightForStrong(double minPercentBodyWeightForStrong) {
    //    this.minPercentBodyWeightForStrong = minPercentBodyWeightForStrong;
    //}

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("minPercentBodyWeightForStrong", minPercentBodyWeightForStrong);
        json.put("oneRepMax", oneRepMax);
        return json;
    }
}
