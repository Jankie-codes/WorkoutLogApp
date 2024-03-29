package ui.tui;

import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.time.LocalDate;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

import persistence.JsonReader;
import persistence.JsonWriter;

//Workout log application, in the form of a Text-based User Interface
public class WorkoutLogAppTUI {
    private static final String JSON_STORE = "./data/user.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    static final String UNITS = "lbs";

    Scanner input;
    int menuState = 0;
    User user;
    Exercise benchPress;
    Exercise squat;
    Exercise deadLift;
    Exercise exerciseChosen;
    int weightSelected;
    int repsSelected;
    LocalDate dateSelected;
    ExerciseSet setToBeMade;
    Workout workoutToBeMade;
    DecimalFormat decimalFormat;
    DateTimeFormatter dateTimeFormat;
    final String[] prompts = {
            "Welcome to myFitnessTracker!\n\nPlease enter body weight: \n",
            "Select from:\n"
                    + "\t[A] -> add a set\n"
                    + "\t[P] -> view current PRs for each exercise\n"
                    + "\t[H] -> view PR history for a given exercise\n"
                    + "\t[V] -> view full workout history\n"
                    + "\t[R] -> view relative strength for each exercise\n"
                    + "\t[S] -> save workout log to file\n"
                    + "\t[L] -> load workout log from file",

            "Choose an exercise:\n"
                    + "\t[B] -> Bench Press\n"
                    + "\t[S] -> Squat\n"
                    + "\t[D] -> Deadlift\n",

            "NOT USED Viewing PRs for each exercise:\n",

            "Choose an exercise:\n"
                    + "\t[B] -> Bench Press\n"
                    + "\t[S] -> Squat\n"
                    + "\t[D] -> Deadlift\n",

            "",

            "",

            "Enter weight (must be greater than 0):\n",

            "Enter reps (must be greater than 0):\n",

            "Choose date (mm-dd-yyyy):\n"
    };

    //EFFECTS: runs the teller application
    public WorkoutLogAppTUI() {
        runFitnessTrackerApp();
    }

    //MODIFIES: this
    //EFFECTS: initializes 3 new exercises: squat, benchPress, and deadLift, and initializes a new user with these
    // 3 exercises in his/her list of exercises. Also initializes a new decimal format to use when rounding weight
    // values, and initializes the scanner.

    //Initializes new jsonWriter and jsonReader in case the user wants to save or load data.
    public void init() {
        user = new User();
        benchPress = new Exercise("Bench Press", 1.0);
        deadLift = new Exercise("Dead Lift", 2.0);
        squat = new Exercise("Squat", 1.75);
        user.addExercise(benchPress);
        user.addExercise(deadLift);
        user.addExercise(squat);
        decimalFormat = new DecimalFormat("#.###");
        input = new Scanner(System.in);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    public void runFitnessTrackerApp() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayPrompt();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nQuitting program.");
        System.out.println("--------------------\nEVENT LOG:\n");
        for (model.Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
            System.out.println("");
        }
    }

    //EFFECTS: displays a prompt to the user, depending on menuState
    public void displayPrompt() {
        System.out.println(prompts[menuState]);
    }

    //EFFECTS: processes user command at any point of the application
    @SuppressWarnings("methodlength")
    public void processCommand(String command) {
        switch (menuState) {
            case 0:
                processCommand0(command);
                break;
            case 1:
                processCommand1(command);
                break;
            case 2:
                processCommand2(command);
                break;
            case 7:
                processCommand7(command);
                break;
            case 8:
                processCommand8(command);
                break;
            case 9:
                processCommand9(command);
                break;
            case 4:
                processCommand4(command);
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: processes user command when choosing bodyweight. Processes the user's bodyweight input
    //modifies menuState accordingly
    public void processCommand0(String command) {
        user.setBodyWeight(Integer.parseInt(command));
        menuState = 1;
    }

    //MODIFIES: this
    //EFFECTS: processes user command when at main menu (choosing an application feature)
    //modifies menuState depending on user's input
    @SuppressWarnings("methodlength")
    public void processCommand1(String command) {
        switch (command) {
            case "a":
                menuState = 2;
                break;
            case "p":
                printCurrentPRs();
                menuState = 1;
                break;
            case "h":
                menuState = 4;
                break;
            case "v":
                printFullWorkoutHistory();
                menuState = 1;
                break;
            case "r":
                listRelativeStrength();
                menuState = 1;
                break;
            case "s":
                saveUser();
                break;
            case "l":
                loadUser();
                break;
            default:
                System.out.println("Error: pressed wrong key");
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: processes user command when trying to add a set and choosing an exercise. Collects user's chosen exercise
    //modifies menuState depending on user's input
    public void processCommand2(String command) {
        switch (command) {
            case "b":
                exerciseChosen = user.getExercise("Bench Press");
                menuState = 7;
                break;
            case "s":
                exerciseChosen = user.getExercise("Squat");
                menuState = 7;
                break;
            case "d":
                exerciseChosen = user.getExercise("Dead Lift");
                menuState = 7;
                break;
            default:
                System.out.println("Error: pressed wrong key");
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: processes user command when trying to add a set and choosing a weight value. Collects user's weight value
    //modifies menuState depending on user's input
    public void processCommand7(String command) {
        if (Integer.parseInt(command) >= 0) {
            weightSelected = Integer.parseInt(command);
            System.out.println("Weight successfully selected.");
            menuState = 8;
        } else {
            System.out.println("Error: incorrect weight chosen please try again");
        }
    }

    //MODIFIES: this
    //EFFECTS: processes user command when trying to add a set and choosing # of reps. Collects user's # of reps
    //modifies menuState depending on user's input
    public void processCommand8(String command) {
        if (Integer.parseInt(command) >= 0) {
            repsSelected = Integer.parseInt(command);
            System.out.println("Reps successfully selected.");
            menuState = 9;
        } else {
            System.out.println("Error: incorrect reps chosen please try again");
        }
    }

    //MODIFIES: this
    //EFFECTS: processes user command when trying to add a set and choosing a date. Collects user's date, and
    // attempts to add the set to an existing workout (or make a new workout with the set if no workout exists
    // on that date yet)
    //modifies menuState to revert back to main menu
    public void processCommand9(String command) {
        dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        dateSelected = LocalDate.from(LocalDate.parse(command, dateTimeFormat));
        setToBeMade = new ExerciseSet(exerciseChosen, weightSelected, repsSelected);
        if (!(user.workoutExistsOnDate(dateSelected))) {
            workoutToBeMade = new Workout(dateSelected);
            user.addWorkout(workoutToBeMade);
            workoutToBeMade.addSet(setToBeMade);
            exerciseChosen.setNewPRIfNecessary(setToBeMade);
            System.out.println("Date chosen successfully. Made a new workout and added the set successfully.");
        } else {
            user.getWorkoutOnDate(dateSelected).addSet(setToBeMade);
            exerciseChosen.setNewPRIfNecessary(setToBeMade);
            System.out.println("Date chosen successfully. "
                    + "Workout already exists on that day, and set added to that workout.");
        }
        menuState = 1;
    }

    //EFFECTS: prints PRs for each exercise: squat, bench press, and deadlift
    public void printCurrentPRs() {
        System.out.println("Viewing PRs for each exercise:\n");
        for (Exercise exercise : user.getExercises()) {
            System.out.println((exercise.getName()) + ": " + exercise.getOneRepMax() + " " + UNITS);
        }
        System.out.println("\n");
    }

    //MODIFIES: this
    //EFFECTS: processes user command when trying to view PR history and choosing an exercise.
    // Collects user's exercise input
    // modifies menuState depending on user's input
    public void processCommand4(String command) {
        switch (command) {
            case "b":
                exerciseChosen = user.getExercise("Bench Press"); //CHANGE THIS TOO!
                printPRHistory(exerciseChosen);
                menuState = 1;
                break;
            case "s":
                exerciseChosen = user.getExercise("Squat");
                printPRHistory(exerciseChosen);
                menuState = 1;
                break;
            case "d":
                exerciseChosen = user.getExercise("Dead Lift");
                printPRHistory(exerciseChosen);
                menuState = 1;
                break;
            default:
                System.out.println("Error: pressed wrong key");
                break;
        }
    }

    //EFFECTS: prints the user's PR history for a given exercise
    //PR History is displayed chronologically
    public void printPRHistory(Exercise exercise) {
        TreeMap<LocalDate, ExerciseSet> personalRecordHistory = user.listPRs(exercise);
        System.out.println(exercise.getName() + " PR History:\n");
        personalRecordHistory.forEach((k,v) -> System.out.println("Date: " + k + " PR: "
                + User.roundToBarbellWeight(v.theoreticalOneRepMaxBerger())
                + " " + UNITS + " 1RM"
                + " (" + v.getWeight() + " " + UNITS + " " + v.getReps() + " reps)" + "\n"));
    }

    //EFFECTS: print the user's full workout history.
    public void printFullWorkoutHistory() {
        System.out.println("Viewing full workout history:\n");
        for (Workout workout : user.getWorkouts()) {
            System.out.println(workout.getDate());
            for (ExerciseSet set : workout.getSets()) {
                System.out.println("\t" + set.getExercise().getName() + ": " + set.getWeight() + " " + UNITS + " "
                        + set.getReps()
                        + " reps");
            }
        }
        System.out.println("-WORKOUT HISTORY END-\n");
    }

    //EFFECTS: list user's relative strength at each exercise, and states
    // whether they are strong at any particular lift
    public void listRelativeStrength() {
        System.out.println("Viewing relative strength for each exercise:\n");
        for (Exercise exercise : user.getExercises()) {
            if (exercise.isStrong(user.getBodyWeight())) {
                System.out.println((exercise.getName()) + ": "
                        + decimalFormat.format(exercise.oneRepMaxInPercentBodyWeight(user.getBodyWeight()))
                        + "x bodyweight"
                        + " STRONG!");

            } else {
                System.out.println((exercise.getName()) + ": "
                        + decimalFormat.format(exercise.oneRepMaxInPercentBodyWeight(user.getBodyWeight()))
                        + "x bodyweight");
            }
        }
        System.out.println("\n");
    }

    //Code influenced by the JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: saves all User data to file
    private void saveUser() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
            System.out.println("Saved most recent workout log data to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //Code influenced by the JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: loads User from file
    private void loadUser() {
        try {
            user = jsonReader.read();
            System.out.println("Loaded pre-existing workout log data from" + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}