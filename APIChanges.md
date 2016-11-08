## API Changes


### Changes Made

#### Major Changes
* Switched from two front end and two back end API Interfaces to a Model, View Controller design with public interfaces and internal APIs for each
* This entailed switching to three public interfaces (SLOGOModel, SLOGOView, and SLOGOController) with each having their own internal and external API’s. For the most part, the Controller can be thought of as part of the back end, but it functions more as an Integration API so we treat it as a separate interface to be able to work with the View and Model.

#### Minor changes
* View external: Allowing controller to display variables/user commands in view, getters for the maximum x and y values, setters for pen color, background color, and image
* View internal: Added methods for adding Lines, Rectangles, TextAreas, and ScrollableVBoxes to the view
* Controller external: added methods that may need to be called for the second sprint including: getVarMap(), getInstrMap(), modelID, tell(), toggleActive(), ask, getModels(), generateSettingsFile(), loadSettingsFile() 


#### Are Changes for the Better?
Absolutely. The four original interfaces were constructed before we understood the MVC structure, and since implementing MVC into our API, the strucutre of what is public and to whom has improved dramatically.
