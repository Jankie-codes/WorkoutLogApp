# CPSC 210: My Personal Project: *Workout and Progress Log*

## Note to users (Jan 25, 2024)
Hi there! Thanks for checking out my WorkoutLogApp. 
I made this program as my project for my CPSC 210 course during my studies at UBC. 
I created a text-based user interface using the Java Scanner library, and then a graphical user interface using 
Java Swing.
Please read below to learn about its features!


***

## Basic Application Purpose and Functions
Last updated: January 25, 2024

This application was designed to **track weightlifters' strength and progress over time**. After a workout, users 
are able to record their completed exercises (e.g. squat, bench press), number of repetitions done, as well as any 
additional notes (e.g. comments on lifting technique) they would like to add. 

Using these data, the application is able to:
- Show past workout history
- Calculate one-rep maxes* for any given lift
- Show strength record histories (PR histories) for any given exercise
- Determine whether the user is strong for their body weight at any particular lift

The app can also save and load data.

As previously mentioned, this application is designed for and will mostly be used by **weightlifters** and **those 
trying to improve physical strength in the gym**.

*Note: *one-rep max* means the maximum amount of weight that someone can lift for one repetition, at any particular lift 
(e.g. squat, deadlift, bench press).

## Interests in This Project

Last updated: September 28, 2023

Tracking workouts is essential to ensuring progress in the gym and becoming a successful lifter. As a weightlifter for 
one year now, I have been logging all my workouts in a single Apple notes file on my phone. However, as I added
more and more workouts into that file, it became increasingly harder to navigate through. In fact, I already have to 
spend around five seconds just to scroll down to the bottom of the file and begin logging a new workout. 

With this Java application, I aim to make logging workouts and tracking gym progress easy. I (and other lifters) will no
longer have to rely on Apple Notes to do so.

## User Stories

Last updated: October 27, 2023

- As a user, I want to be able to log/add an exercise set and specify the type of movement, total weight, 
total repetitions, and date.
- As a user, I want to be able to view a full workout history.
- As a user, I want to be able to view my one-rep maxes for each type of lift.
- As a user, I want to be able to see whether I am strong for my body weight at any particular lift.
- As a user, I want to be able to see my PRs over time for any particular lift.
- As a user, I want to be able to save my previous workout data to file (if I so choose).
- As a user, I want to be able to load my previous workout data from file (if I so choose).

# Instructions for Grader

Last updated: November 16, 2023

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

# Phase 4: Task 2

Last updated: January 25, 2024

When the WorkoutLogApp closes, it also prints an event 
log to the console of all actions performed by the user.
An example event log is shown below.


### Representative sample event log:

>Sun Nov 26 16:57:04 PST 2023
>Previously-saved user data successfully obtained from existing file.
>
>Sun Nov 26 16:57:04 PST 2023
>User's bodyweight set to: 135
>
>Sun Nov 26 16:57:04 PST 2023
>Added a new set to a workout on the given date: 2023-10-10
><br>Set specifications:
><br>&nbsp;&nbsp;&nbsp;&nbsp;Exercise: Bench Press
><br>&nbsp;&nbsp;&nbsp;&nbsp;Weight: 135
><br>&nbsp;&nbsp;&nbsp;&nbsp;Reps: 10
>
>Sun Nov 26 16:57:04 PST 2023
>Added a new set to a workout on the given date: 2023-10-10
><br>Set specifications:
><br>&nbsp;&nbsp;&nbsp;&nbsp;Exercise: Bench Press
><br>&nbsp;&nbsp;&nbsp;&nbsp;Weight: 225
><br>&nbsp;&nbsp;&nbsp;&nbsp;Reps: 5
>
>Sun Nov 26 16:57:04 PST 2023
>Added a new workout to the workout log on the given date: 2023-10-10
>
>Sun Nov 26 16:57:04 PST 2023
>Added a new set to a workout on the given date: 2023-11-13
><br>Set specifications:
><br>&nbsp;&nbsp;&nbsp;&nbsp;Exercise: Squat
><br>&nbsp;&nbsp;&nbsp;&nbsp;Weight: 225
><br>&nbsp;&nbsp;&nbsp;&nbsp;Reps: 5
>
>Sun Nov 26 16:57:04 PST 2023
>Added a new workout to the workout log on the given date: 2023-11-13
>
>Sun Nov 26 16:57:22 PST 2023
>Added a new workout to the workout log on the given date: 2023-12-01
>
>Sun Nov 26 16:57:22 PST 2023
>Added a new set to a workout on the given date: 2023-12-01
><br>Set specifications:
><br>&nbsp;&nbsp;&nbsp;&nbsp;Exercise: Bench Press
><br>&nbsp;&nbsp;&nbsp;&nbsp;Weight: 225
><br>&nbsp;&nbsp;&nbsp;&nbsp;Reps: 5
>
>Sun Nov 26 16:57:30 PST 2023
>Added a new set to a workout on the given date: 2023-12-01
><br>Set specifications:
><br>&nbsp;&nbsp;&nbsp;&nbsp;Exercise: Squat
><br>&nbsp;&nbsp;&nbsp;&nbsp;Weight: 315
><br>&nbsp;&nbsp;&nbsp;&nbsp;Reps: 6
>
>Sun Nov 26 16:57:35 PST 2023
>Added a new workout to the workout log on the given date: 2023-12-05
>
>Sun Nov 26 16:57:35 PST 2023
>Added a new set to a workout on the given date: 2023-12-05
><br>Set specifications:
><br>&nbsp;&nbsp;&nbsp;&nbsp;Exercise: Squat
><br>&nbsp;&nbsp;&nbsp;&nbsp;Weight: 315
><br>&nbsp;&nbsp;&nbsp;&nbsp;Reps: 6
>
>Sun Nov 26 16:57:42 PST 2023
>User's bodyweight set to: 145
>
>Sun Nov 26 16:57:48 PST 2023
>Treemap of PR History obtained for the given exercise: Bench Press
>
>Sun Nov 26 16:57:55 PST 2023
>Treemap of PR History obtained for the given exercise: Squat
>
>Sun Nov 26 16:58:00 PST 2023
>Current user data successfully saved and written to file.

NOTE:
Events are logged when:
- A new workout is added to the user's list of workouts
- A new set is added to a workout on a specific date
- The user's bodyweight value is changed
- The user's PR history for a given exercise is obtained
- User data is saved and written to file
- User data is loaded from file

### **IMPORTANT FOR GRADER: Certain events are NOT logged. Here is why:**

Events are **not** logged when the user attempts to view workout history, view 1-rep-maxes, or view relative strength. 
This is because those three user actions involve classes within the GUI package accessing various model fields and 
information using getter methods. Students are not allowed to log events within the UI package, and logging an 
event every time a getter method is called would clutter the event log, so I decided to not log these three user actions
at all. 

TA Trevor Glennon approved this decision and said that **I can still earn full marks** in the following Piazza post: 
https://piazza.com/class/lly08e6z2hzwn/post/1488_f1

# Phase 4: Task 3

Last updated: November 28, 2023

If I had more time to refactor my project (i.e. the WorkoutLogApp), I would have used the Singleton design pattern for 
my User class. A major problem with my GUI code was that it has one User instance which contains all the 
user's data such as his/her bodyweight, workouts, sets, etc., but rather than using the Singleton pattern to 
easily access this single instance globally, each one of
my classes in the GUI package has its own User field pointing to a common memory location (i.e. the single user).
This design still works effectively whenever the User's fields are modified, but things get problematic whenever 
the program attempts to load data (due to reconstructing a new User).

The process for loading pre-existing user data works by constructing a new User, filling in its appropriate fields
based on an existing file, and then modifying each GUI class's user field to point to this newly-constructed User. This
process is incredibly prone to bugs, as every time data is loaded, the program needs to modify every User field within each
of the classes in the GUI package, and me forgetting to change even a single User field 
(as I add more GUI classes and modify the code) would break the entire program. 
Using the Singleton pattern would ensure that the single instance of User could
easily be referred to globally, within any class, and that the program would no longer need a User field within each GUI class 
pointing to the same instance (as each class could just call a getInstance method statically whenever user data is needed). 
The app would also no longer need to modify every single User field one by one when loading data.

### **IMPORTANT FOR GRADER: Including 3rd party classes in my UML Class Diagram**

My UML Class Diagram includes the 3rd party classes JPanel and JButton. This is done to accurately illustrate the WorkoutLogAppGUI
and Sidebar classes which each have a set of actual type JPanel and JButton respectively, while the sets' elements are of a more specific
actual type (i.e. their actual type is a subclass such as AddSetPanel, LambdaButton, etc). Adding the 3rd party classes
JPanel and JButton to my class diagram keeps this illustration clear and correct, and not including these two classes would only make
my UML Class Diagram more vague and less accurate.

TA Trevor Glennon approved my request to add JPanel and JButton to my UML Class diagram, in the following Piazza Post:
https://piazza.com/class/lly08e6z2hzwn/post/1505