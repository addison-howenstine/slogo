## API Changes


#### Changes Made
* View external: Allowing controller to display variables/user commands in view, getters for the maximum x and y values, setters for pen color, background color, and image
* View internal: Added methods for adding Lines, Rectangles, TextAreas, and ScrollableVBoxes to the view
* Controller internal:  getVarMap(), getInstrMap() accessible from Instructions




#### Changes Major or Minor?
* Major change: switching to MVC from back-end/front-end distinction
	* This entailed switching to three public interfaces (SLOGOModel, SLOGOView, and SLOGOController) with each having their own internal and external API’s. For the most part, the Controller can be thought of as part of the back end, but it functions more as an Integration API so we treat it as such as a separate interface to be able to work with the View and Model.
* Minor changes: adding public methods for accessing resources (variable map, instruction map, maxX/Y values, pen color, background color, image)




#### Are Changes for the Better?
Absolutely. The four original interfaces were constructed before we understood the MVC structure, and since implementing MVC into our API, the strucutre of what is public and to whom has improved dramatically. https://www.youtube.com/watch?v=2fR4JotwwWo


#### Potential Upcoming Changes
* We are planning on giving the APIs for the View more appropriate names.
* We will add more appropriate getters and setters when necessary.
* Making the public methods in the individual Instruction classes protected and therefore not part of the API.
* Controller external: allow view to modify user variables
