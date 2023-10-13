package model;

//represents a type of exercise movement with a name,
// a minimum percentage of body weight which must be lifted for 1 rep for a lifter to be
// considered strong at this movement, and
// the maximum amount of weight which the user should be able to lift for one rep; a one-rep-max.
public class Exercise {
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


    public String getName() {
        return this.name;
    }

    public double getMinPercentBodyWeightForStrong() {
        return this.minPercentBodyWeightForStrong;
    }

    public double getOneRepMax() {
        return this.oneRepMax;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMinPercentBodyWeightForStrong(double minPercentBodyWeightForStrong) {
        this.minPercentBodyWeightForStrong = minPercentBodyWeightForStrong;
    }

    public void setOneRepMax(double oneRepMax) {
        this.oneRepMax = oneRepMax;
    }


}
