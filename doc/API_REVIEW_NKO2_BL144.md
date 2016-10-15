API Peer Review
===============

### Group Members

* Noah Over, nko2
* Ben Linde, bl144

### Part 1
1. We kept the external APIs short so it is more flexible and easier to create a new view based off the models API and vice versa.
2. Our APIs keep all the parsing in the back end, which was what we discussed the most when designing our project.
3. The only exceptions we could think of taking place in the front end is an error with the language selection if a language that does not have a resource file is selected. We will catch exceptions from the back end and display them in the form of Alerts, however.
4. We think good means that your API is flexible, simple, accomplishes what is necessary, and does not have many external methods and we believe that our APIs accomplish all of those goals.

### Part 2

1. Here are the use cases:
	a. The user types "bk 20", sees the turtle move backwards twenty spots, and the command is added to history.
	b. The user selects a different language from the ComboBox, all of the text on the screen is updated, and they can now type the commands in the new language.
	c. The user types bad input, it is discovered in the back end, an exception is thrown to the front end where it is caught, and an Alert box is shown allowing the user to realize their mistake.
	d. The user selects a new color for the pen from a ComboBox and from now on the pen rights in that color or at least until they change it again.
	e. The user clicks on an old command and the turtle carries out said command.
2. I think regular expressions will be useful for my group's design because it will help parse the commands from the TextField in the user interface.
3. I am most excited about displaying the turtle and the pen marks in the window.
4. I am most worried about making user-defined commands and previous commands clickable to execute, as well as the variables clickable to edit.