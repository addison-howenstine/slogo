README.md
===
by Philip Foo, Harshil Garg, Addison Howenstine, and Noah Over

#### Time spent
Date started: Friday, October 14
Date finished: Monday, October 31
##### Estimated hours worked:
* Phil: 45-50
* Harshil: 
* Addison: 80
* Noah: 70

#### Role in Project
* Phil: (back end) Controller, Parser, Turtle movement/wrapping, Load/Save, serialization, user instructions, user variables
* Harshil: (front end) command history window in Playground, multiline input, HTML help document
* Addison: (back end) Controller, Parser, Instructions, Observer/Observable, Model, front end / back end integration
* Noah: (front end) View, Playground, View helper classes, buttons / combo boxes, front end / back end integration

#### Resources used
* [SLOGO instructions](http://www.cs.duke.edu/courses/compsci308/fall16/assign/03_slogo/index.php)
* Professor Duvall's parser code from lecture as outline for our SLOGOParser
* Professor Duvall’s animation code from example_animation.
* Javadocs for a lot of JavaFx features including VBox, Line, PathTransition, RotateTransition, Animation, etc.

#### Running project
Run `SLOGOMain.java`.

#### Files used for testing
Running `SLOGOMain.java` and inputting commands in the command prompt is sufficient for testing for this project.

#### Necessary Data/Resources
* Image files located in `turtle_images`
* Language .properties files located in `src/resources`
* Saved settings .ser files located in `lib` 

#### Using program
Program can be run from `SLOGOMain.java`. From here commands can be run using the command line at the bottom of the window or by opening the "multiline" pane from the top menu bar.

#### Known bugs/crashes
* Recursion does not work
* Errors are handled rather than allowed to crash the program, the program will stop and display the error in a model at the first error it encounters.

#### Assignment impressions
This assignment was really cool and extremely helpful in allowing us to think about design patterns, specifically MVC. It was difficult working with a larger group, but the front end / back end division worked well. It would have been useful to see more example commands earlier. [These helpful command inputs](http://www.cs.duke.edu/courses/compsci308/fall16/assign/03_slogo/examples/) were released later but would have been helpful earlier. It also would have been helpful to go over MVC in class before the API Interfaces were due and to go over reflection and the idea of using an expression tree for Instructions before the first weekend. We spent a lot of time the first weekend trying to figure out how to structure this, and then it was covered in class the very next Tuesday which was frustrating to consider how many hours it took to figure out on our own.