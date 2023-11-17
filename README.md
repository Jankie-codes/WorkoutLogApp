# My Personal Project: *Workout and Progress Log*

## Basic Application Purpose and Functions

This application will be designed to **track weightlifters' strength and progress over time**. After a workout, users 
will be able to record their completed exercises (e.g. squat, bench press), number of repetitions done, as well as any 
additional notes (e.g. comments on lifting technique) they would like to add. 

Using this data, the application will be able to:
- Show past workout history
- Calculate one-rep maxes* for any given lift
- Determine whether the user is strong for their body weight at any particular lift

(Not an exhaustive list of features)

As previously mentioned, this application is designed for and will mostly be used by **weightlifters** and **those 
trying to improve physical strength in the gym**.

*Note: *one-rep max* means the maximum amount of weight that someone can lift for one repetition, at any particular lift 
(e.g. squat, deadlift, bench press).

## Interests in This Project

Tracking workouts is essential to ensuring progress in the gym and becoming a successful lifter. As a weightlifter for 
one year now, I have been logging all my workouts in a single Apple notes file on my phone. However, as I added
more and more workouts into that file, it became increasingly harder to navigate through. In fact, I already have to 
spend around five seconds just to scroll down to the bottom of the file and begin logging a new workout. 

With this Java application, I aim to make logging workouts and tracking gym progress easy. I (and other lifters) will no
longer have to rely on Apple Notes to do so.

## User Stories

- As a user, I want to be able to log/add an exercise set and specify the type of movement, total weight, 
total repetitions, and date.
- As a user, I want to be able to view a full workout history.
- As a user, I want to be able to view my one-rep maxes for each type of lift.
- As a user, I want to be able to see whether I am strong for my body weight at any particular lift.
- As a user, I want to be able to see my PRs over time for any particular lift.
- As a user, I want to be able to save my previous workout data to file (if I so choose).
- As a user, I want to be able to load my previous workout data from file (if I so choose).

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking the 
"Add set" button on the sidebar on the left, filling in information for the exercise, weight, reps, and date, then
clicking the "Add set" button below all the text fields to process the information and attempt to add a new set to the 
workout log. Red text showing an error message will appear if any of the inputs are invalid. However, if all inputs
are valid, the app will either add the set to an existing workout on the given date, or (if a workout does not exist
yet on that date) create a new workout on the given date and add the given set to it. Keep in mind that the app ensures
that only one workout can exist for each day.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking 
"view PR history" on the sidebar on the left, then filling in the "choose an exercise" text field with one of the 
given exercises listed on the right and pressing enter. Doing this will display a list of all the sets added which were
a new strength record (PR) for the given exercise, along with the date they were performed, the weight lifted, the 
number of reps, and the set's theoretical one rep max.
- You can locate my visual component by running main and opening the app, which will display a splash screen that 
includes a logo, text thanking the user for using the app, and loading text.
- You can save the state of my application by clicking the "save workout log" button on the sidebar on the left.
- You can reload the state of my application by clicking the "load workout log" button on the sidebar on the left.