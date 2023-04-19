# Decoupling 
## Required files for decoupling: 

### Controller package:

- Features: This interface defines the protocol for communication between the user and the model/view via the controller. It is essential for enforcing a decoupled architecture, as it provides a clear separation of responsibilities between the different components of the system.

- CollageController: This interface outlines the initialization process of the program and is implemented by CollageControllerImpl. It allows for a consistent start-up procedure across different implementations of the controller.

- CollageControllerGUIImpl: This class provides a GUI implementation of the controller, following the protocol outlined in Features. It handles the GUI aspects of user interactions with the program, such as displaying and modifying the project.

- CollageControllerImpl: This class provides a TextUI implementation of the controller, also following the protocol outlined in Features. It allows for a command-line interface for users who prefer a text-based interaction with the program.

### View package:

- IView: This interface defines the protocol for displaying the program to the user, whether through a GUI or a text-based interface. By separating this responsibility from the controller and model, it enables different implementations of the view without affecting the other components.

### Model package:

- ICanvas: This interface defines the protocol for the model, which handles the implementation of changes received from the user via the controller, and communicates these changes back to the controller for display on the view. By providing a clear framework for how the model should behave, it enables different implementations of the model without affecting the other components.

- IFilter (in the Filter package): This interface is required for the ICanvas interface to compile, as the method getFilterFromString(String filterStr, String layerName) returns an object of type IFilter. By defining the filter interface in a separate package, it allows for easy integration of new filter types into the system without affecting the model or other components.

- IImage: This interface is required for the ICanvas interface to compile, as it provides the framework for adding, getting and modifying images in the model. By separating this responsibility from the controller and view, it enables different implementations of images without affecting the other components.

- ILayer: This interface is required for the ICanvas interface to compile, as it provides the framework for adding, getting and modifying layers in the model. By separating this responsibility from the controller and view, it enables different implementations of layers without affecting the other components.

- IPixel: This interface is required for the view to compile, as it provides a framework for handling pixel objects in the methods outlined in the protocols of IImage and ILayer. By separating this responsibility from the controller and model, it enables different implementations of pixels without affecting the other components.
