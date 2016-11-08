DESIGN.md
=====

### Introduction
The goal of the project is to design and implement a Model-View-Controller structured program that allows a user to type in commands in a simplified Logo language to control a turtle shown on the screen. The MVC design pattern will allow the user to work with the front-end user interface to control the back-end model such that the front-end visualization updates constantly to reflect the logic changing on the back end. The basic commands to control the turtle will be open to the user and an HTML document will show valid inputs for the user, but how these methods are implemented and any intermediate methods used will be encapsulated from the user.


### Design Overview
The overall application is designed using a Model-View-Controller design pattern. This is done to isolate the front-end and back-end components of the application, encapsulating the logic/parsing functionality from the GUI/rendering functionality. The model simply contains information regarding the location and state of the turtle (pen up/down, heading, etc.), while the controller contains the parsing logic which skims through the user input and then changes the model. The view receives updates from the model and updates the user-interface appropriately.


##### Controller
The controller manages data flow between the View subsystem of the application, and the Model subsystem of the application. The Controller is called by the view and then in turn calls methods in the Model which update the Model.  As a result, there was a SLOGOController class called initially from main, which manages the relationship between the SLOGOModel/SLOGOView. The controller is also responsible for parsing user input and generating the expression tree.


##### Model
The Model represents the individual turtles that are displayed in the View. It includes values for the location, heading, whether the pen is up or down, and whether the turtle is showing or not. The View listens to these properties and updates the screen accordingly when these values are updated. The Model also includes methods called by the Controller that update all of the above mentioned properties. 


#####View
The View displays the turtles and their area on the screen. It also allows you to control pen properties, background color, language, and turtle image through ComboBoxes and Buttons. It also displays a command history and user-defined commands that are clickable to run and a variable display. It calls the Controller to run commands that are entered by the user through the single line TextField or the multiline TextArea. The model also calls public methods provided by the view which update graphical elements on the screen.


###How to Add New Features
To add a new command, first, you need to add it to all of the resources files for all of the different languages (e.g. English.properties). Then, you need to make a new class that extends the appropriate type of command (e.g., DisplayCommand, TurtleCommand, etc.). In the method getNumRequiredParameters, you will just return however many parameters your new command needs. In the method evaluate, you will just have to call the methods that are necessary for your command. If your command needs a method that has not been created yet, you will just have to add it to wherever is appropriate. If it has to do with the turtle, you should add it to SLOGOModel. If it has to do with the user interface, you should add it to SLOGOView. 


###Major Design Choices
Model-View-Controller: Probably the most major design choice we made for this project was to set it up as a model-view-controller. The pros for going with this design are that it keeps the front end and the back end separate so either end does not need to know what the other end is doing and it is a good way to keep the code more organized. The cons are that the two ends need to be working at a similar pace so they can test the different features.


### User Interface
* The user will interact with the program by typing their commands into a TextField at the bottom of the screen. They will also be able to select the language, the background color, the image for the turtle, and the pen color through the use of drop-down boxes.
* The user interface will have a screen on which the turtle and pen marks will be displayed that will take up the majority of the left part of the screen. The user will be able to make the turtle move through commands entered in the TextField at the bottom of the screen. At the top of the screen, there will be ComboBoxes for language, background color, turtle image, and pen color, which will be used like a drop down menu. Along the righthand side of the screen, there will be places to view previously entered commands, variables, and user-defined commands. Finally, there will also be a Help button that will bring up an HTML page that serves as a command reference.
* An Alert box will show up whenever the user enters bad input that does not correspond to an already existing command.


### Design Considerations
* The design decision we discussed the most was whether the parsing of the user’s input should take place in the front end or the back end. Along with this came the the decision of what the front end is passing the back end and vice versa. The pros of having the back end do it is that it goes right along with the error checking so it works out well in that sense. The cons are deciding what to pass back to the front end, whether it is a new Command, which is a new class we would create if we went this way, or if it is just the x and y coordinates of the turtle along with whether the pen is up or down, the angle the turtle is facing, and whether the turtle is showing. The pros of having it in the front end are that you already received the String from the user and you can just do it all right there. The cons are then you would have to determine what to send to the back end for error checking, for which we had no clear ideas. We ended up deciding to go with having the parsing take place in the back end and to pass a String from the front end to the back end and pass back all of the above listed information on the turtle from the the back end to the front end.
* Another design decision we spent some significant time on was whether the front end should check to see if a command is a user defined command. Originally, we figured we could just check the input against a Map of some sort. We then realized that this would not make sense because then the front end would have to check every single input to send the corresponding instructions if it was a user defined command. Since the back end is already checking every single input for parsing and error checking, we realized that this was actually the best option.
