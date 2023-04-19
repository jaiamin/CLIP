# CLIP: Composite Layering Image Processor

CLIP provides a comprehensive set of features, including the ability to create new collage projects with desired dimensions and max values, add various layers and images, apply filters to layers, load/save projects, save completed composite images in various image formats.

The program utilizes a controller pattern (MVC architecture) to ensure efficient and correct communication between the model and view components.

Overall, the Graphical Collager Program provides a user-friendly interface and robust set of features for creating collages, making it a valuable tool for anyone who needs to quickly and easily create composite images using either the GUI and/or TUI.

## Features

The following features are currently available for the user:
- Create a new project (with .collage extension) with a desired name, height, width, and max value (which ranges from 1-255). This allows for the initialization of a collage project within the program in which a variety of layers and images can be added as desired.
- Load a recent/downloaded project (with .collage extension) into either the GUI and/or text view interface.
- Save the current collage project (with .collage extension) to the filesystem.
- Add various layers to a collage project. Each layer must consist of a unique name that represents it within the project.
- Add images to specific layers given the specific layer name, path of image to be added, and the offset position of the image on the collage project in format (row, col).
- Set the filter of a specific layer (which may or may not consist of images) within the collage project with a valid filter option. The current filter options that are supported by the program include normal, red-component, green-component, blue-component, brighten-value, brighten-intensity, brighten-luma, darken-value, darken-intensity, darken-luma, difference, multiply, and screen. For more information regarding the functionalities of each of these filter options, refer to the USEME file.
- Save the current composite image of the collage project to the filesystem in one of the supported image formats (.ppm, .png, .jpg, .jpeg).
- View the current collage project structure (displays collage project layers and respective images).
- View help menu (displays the program's available commands).
- User-friendly and interactive graphical user interface (GUI) that allows you to create and edit collage projects with ease. 
- Lightweight and flexible text user interface (TUI) that can be accessed via bash scripting.

For more information regarding .collage extensions, refer to the Code Design section below.

Currently, all features mentioned above are available for both user interfaces (GUI and/or TUI).

## Requirements

To run or compile this code, you will need:

- Java 11 or higher JRE
- JUnit 4 for running tests

## USEME

Please refer to the USEME file for instructions on how to use this program.

## Code Design

### IPixel Interface and ColorPixel Class

In this project, we have designed an IPixel interface and implemented a ColorPixel class that implements this interface. The IPixel interface provides a set of methods that allow for the retrieval and setting of values related to a pixel. This includes methods for accessing individual RGBA values of a pixel, as well as getting or setting the pixel's filter.

To ensure that our clients have access to only the necessary methods, we decided not to use the built-in Java Color class. Instead, we created our own ColorPixel class, which is similar to the Color class. The ColorPixel class consists of a red, green, blue, and alpha value, which together represent the color of a pixel and its transparency. Additionally, we have ensured that every IPixel has a filter, which allows us to keep track of whether or not the IPixel's color has been altered by a filter.

To provide clients with easy access to the values of a ColorPixel, we have implemented getters for all values of a ColorPixel, as well as a getter/setter for the filter of a ColorPixel. Additionally, we have taken into account input validation by ensuring that the values of red, green, blue, and alpha fall within the color range of [0, 255]. If any of the color component values are invalid, an IllegalArgumentException is thrown, which is then caught in the controller and handled by displaying and transmitting an error message to the view.

Overall, our IPixel interface and ColorPixel class provide a flexible and robust framework for working with pixel data, and have been designed with the needs of our clients in mind.

### IImage Interface and ColorImage Class

The IImage interface is a crucial part of our image processing application, providing clients with the necessary methods to retrieve or set the value of individual pixels in an image. Our design choices for the ColorImage class were based on the idea that an image should have a height and width to represent its dimensions. Additionally, since the maximum value of a pixel may vary, we included a field to store this value.

To manage the data of the image itself, we decided to use a 2D array of IPixel objects, as it best represented a grid of pixels that would form the image view. Furthermore, we added a field to store the filename of the image, which would be displayed to the user in the view text UI.

When designing the constructors for the ColorImage class, we considered various ways in which an image could be created. The constructors are used throughout the code depending on what values are available when creating an IImage. We also implemented input validation to ensure that the height and width of the image were not less than 1, as an image with no dimensions cannot be created. Moreover, we ensured that the maximum value of the pixel components was within the range [1, 255], as an image with a maximum value of 0 would not display anything.

Apart from the getter and setter methods, we added an updateWithMaxValue() method that allows any user-inputted image to automatically be converted to a maximum value 255-based image. For instance, if a user provides an image that has a maximum value of 50 with all its pixels in range [0, 50], then the method will convert all pixels to a maximum value of 255. This ensures that user inputted images can be easily used with other methods that are maximum value 255-based.

Overall, the ColorImage class and IImage interface provide a flexible and reliable way to represent and manipulate images within our application.

### ImageUtil

In the ImageUtil class, we have implemented various utility methods to handle common tasks involved in converting different file formats to their respective IImage representations. Our program currently supports four popular image formats: PPM, PNG, JPG, and JPEG, and we wanted to provide the functionality to import and export images in all these formats.

To achieve this, we created helper methods in the ImageUtil class that convert each file format to its respective IImage representation. The PPM format is converted to an IImage by reading in the data from the PPM file and constructing a ColorImage object with the extracted data. For the PNG, JPG, and JPEG formats, we utilized the ImageIO library in Java to read in the file data. The resulting ColorPixel objects were then stored in a 2D array, representing the final IImage object.

These helper methods allow us to easily handle importing and exporting different image formats within our program. Additionally, we implemented error handling for cases where the file format is not supported or the file cannot be found, ensuring that the user is provided with helpful error messages in the event of an issue.

### ILayer Interface and ImageLayer Class

The Image Layer feature of our program is an essential aspect that allows users to apply filters and images to layers of the canvas model. The ILayer interface provides methods that allow clients to retrieve and set values, apply filters and images to the layer, and store layer-related information, such as the layer name and the applied filter.

We have designed the ImageLayer class in a way that allows each layer to have the same size as the canvas model, but with fields for height and width for easy access. The image layer is initialized as a 2D array of transparent, white pixels. When a user adds an image to a layer using the addImageToLayer() method, the pixels of the ImageLayer image are updated with the image pixels.

Each layer has a unique layer name and filter that represents the filter applied to all images on that layer. Simple input validation and getter/setter methods ensure that the layer information is accurate and up-to-date. We have also included a previousPixels field that saves the original pixels of the layer when a filter is applied for the first time. This ensures that the image is reset to its original pixels before a new filter is applied, preventing images from overlapping and ensuring that only the most recent filter is applied to the layer.

We have also included an updatePixels() method that ensures that the layer is always updated with the pixels of the new images added to the layer. When a filter is applied, the method checks to see if there is already a filter on the layer. If a filter exists, the method creates a copy of the original pixels. If not, the method resets the image pixels to the original pixels. It then applies the filter to the image and sets the new filter.

Lastly, we made the design choice to crop off any pixel that is not on the pixel grid of the layer/canvas. These pixels are not considered in the final render of the layer/canvas image since they are not viewable by the user due to falling off the grid. This design decision makes the program more efficient by not including unnecessary pixels in the 2D array.

### IFilter and Filter Classes

The Image Editor application features various filters, with the common functionalities implemented in the IFilter interface. The interface provides a method to apply a filter to a layer, which is necessary for all types of filters. Additionally, all the filter classes override the toString() method, providing a string representation of the filter that allows it to be compared to user input for a filter in the controller. For each filter applied to a layer, transparent pixels on an image are skipped since applying a filter would make no difference to a transparent pixel.

#### AbstractBrightness, BrightenFilter, DarkenFilter Classes

The BrightenFilter and DarkenFilter classes are very similar in functionality, except for the way they adjust the brightness of an image. As a result, we created the AbstractBrightness class, which provides the basic functionality of the apply method and allows the extended BrightenFilter and DarkenFilter classes to adjust the brightness of an image. An Enum is used to represent the three unique light types (value, intensity, luma), with switch statements used to determine which type of light should be applied to each type of brightness filter. The factory design pattern is used to effectively apply darkened or brightened filters to an image, indicating which type of AbstractBrightness a specific filter is.

When designing the filters, we ensured that the code was structured to allow for easy addition of new filters in the future. We also tested the filters extensively, ensuring that they produced the intended output while also checking for any potential edge cases that could cause issues. Additionally, the filters were designed to be efficient, with the code optimized to minimize runtime and memory usage.

#### ComponentFilter Class

The ComponentFilter class is an implementation of the IFilter interface, which provides a common method to apply filters to a layer. However, ComponentFilter is different from the BrightenFilter and DarkenFilter classes, so we decided to create a separate class for it.

ComponentFilter is designed to alter the color components of each pixel on an image. To achieve this, we used a similar approach as the brightening filters, by representing the different color components as three unique enums: red, blue, and green. When a user chooses to apply a component filter to a layer, the ComponentFilter class takes the value of the selected color component and sets the green and blue color component values to 0. This effectively isolates the color component of interest and produces a new image where each pixel is represented only by its selected color component value. This process is repeated for each color component that the user selects.

The ComponentFilter class is a useful tool for creating monochrome images or isolating specific colors in an image. For example, if a user applies a red component filter to a layer, only the red parts of the image will be displayed, while the green and blue parts of the image will be removed.

#### NormalFilter Class

The NormalFilter class is an implementation of the IFilter interface and its purpose is to do nothing. In other words, the apply() method of this class does not make any changes to the pixels of a given layer. Instead, this filter is used in conjunction with the applyFilter() method in the ImageLayer class to reset a layer back to its original pixels before applying any subsequent filters. This ensures that only the most recent filter is applied to the layer, and not a cumulative effect of all the filters that have been applied previously. Therefore, NormalFilter plays a critical role in the image processing pipeline as a neutral filter that restores a layer to its original state.

#### DifferenceFilter class

The DifferenceFilter is a unique filter that alters the colors of each pixel based on the background of that pixel. This is in contrast to the other filters which apply a fixed transformation to each pixel. When a DifferenceFilter object is initialized, it takes in all the layers underneath it as a single background image. This allows it to use its apply method to invert the colors of each pixel on the layer depending on the RGB value of the pixel directly underneath it.

For example, if a layer with a DifferenceFilter is on top of a black background layer, applying the filter will not change the colors of the layer. However, if the same layer is on top of a white background layer, the filter will completely invert the colors of each pixel. It's worth noting that any changes to the layers beneath this layer will also be reflected in the color composition of this layer.

Overall, the DifferenceFilter is a powerful tool for creating interesting effects by modifying the colors of a layer based on the colors of the underlying layers.

#### AbstractBlendFilter, MultiplyFilter, ScreenFilter, and RepresentationConverter Classes

The blending filters, namely MultiplyFilter and ScreenFilter, are responsible for changing the colors of a layer while taking into account the background of that layer. The apply() methods of these filters are quite similar and abstracted into an AbstractBlendFilter that alters the colors of the layer based on the subclass that calls it. MultiplyFilter darkens the layer while ScreenFilter brightens it.

These filters make changes to the layer based on each pixel's lightness value and its background pixel's lightness value. To achieve this, the RGB value of each pixel is taken on a 0-1 scale and converted to its HSL components using the RepresentationConverter. The RepresentationConverter class is responsible for converting the RGB values to HSL values and vice versa. Once the HSL value is produced, the lightness value is determined, and a new HSL value is created based on the subclass. Finally, the new HSL value is converted back to RGB values, and the filter is applied as specified.

Overall, these blending filters use a complex process to alter the colors of a layer while taking into account the background of the layer to produce an effective result.

### (Model) ICanvas Interface and CollageModel Class

The ICanvas interface and the CollageModel class together serve as the model for our collage project. The ICanvas interface provides a set of general methods for interacting with the canvas and its layers, while the CollageModel class implements these methods to represent the entire collage.

Each instance of the CollageModel class has its own height and width, which are user-provided and set the size of the canvas and its layers. The CollageModel also has a list of layers, each with a unique name (validated in the addLayerToCanvas() method), and an image that represents the final rendered image of all the layers with their images and filters applied. Additionally, each CollageModel has a project name which represents the name of the collage project.

The updatePixels() method is responsible for determining the final rendered image by updating the pixels of the CollageModel. The CollageModel is initialized as a 2D array of white, opaque pixels, and the updatePixels() method updates the pixels with the image of all the layers. Each layer is updated with the updatePixels() method in the IImage interface, which updates the layer with the images of each IImage added to the layer.

The model also has a method called addImageToLayer, which adds a user-inputted image to a valid layer. If the layer is not found or does not exist, an IllegalArgumentException is thrown. This method is useful for users who want to add new images to the canvas and to specific layers. Overall, the ICanvas interface and the CollageModel class provide a robust model for creating and manipulating collages with multiple layers and images.

### (View) IView Interface and CollageView Class

The IView interface is an essential component of our collage project as it provides necessary methods to display the user interface to both console and GUI users. The text view UI is responsible for displaying the project structure of the collage project to the user. If a project or image name is not provided by the user, it is labeled as "Unknown" in the text view UI. The view also handles errors and input validation by displaying error messages to the user, giving them a detailed explanation of what went wrong and how to correct it.

The CollageView class is constructed with an ICanvas model and an Appendable object, which allows us to efficiently test our transmitted messages to the view. When a user starts the program, the entire list of commands is displayed with descriptions and arguments so the user can create a proper collage project. If the view fails to transmit a message, an IOException is thrown and handled so the user is aware of the failure.

The GUI was designed to be user-friendly and efficient. It is divided into three main parts: the top left section is a scroll pane that displays the collage project canvas, the top right section displays the current project structure, and the bottom section is the user input section. The action buttons, located on the left side, are commands the user is likely to use at the beginning or end of the program, such as the help menu, quit program, new project, save project, and save image buttons. The right side of the bottom section represents the commands themselves. Text fields with input validation are used for numbers and project/layer names to give users the freedom to choose project names, layer names, and project dimensions. A dropdown menu is used for the add-layer and set-filter methods to ensure the user selects only existing layers and valid filter options. File choosing is made easy with the JFileChooser class, which restricts images to only .ppm format files to prevent invalid images from being selected.

Overall, our GUI is user-friendly and efficient, preventing unnecessary errors and invalid commands messages.

### (Controller) 

#### CollageController Interface and CollageControllerImpl Class

The controller is a crucial component of our program, as it serves as the intermediary between the model and view components, facilitating efficient and accurate communication between the two. The CollageController interface is essential to this process, as it provides the start() method which initiates the entire program when called in the Main class. The start method allows the user to input commands until the "quit" command is called, and includes all the available command options. The commands are implemented using a pattern which calls other methods within the CollageControllerImpl class, each of which is responsible for its own command.

In the CollageControllerImpl class, we took in a model, view, and readable object to facilitate user input and to enable the model to transmit collage project progress to the user on the console. For each command, we implemented input validation to ensure that if an invalid argument was provided, the view would render a detailed error message that would inform the user of what went wrong. Additionally, we included the "help" and "project-structure" commands to allow users to view available commands and the current project structure of their collage project. We thoroughly tested each command to ensure that it functioned correctly and that a user could not inadvertently cause an error that was not handled by the controller.

We also made design choices that promoted user-friendliness, such as ensuring that the available commands were clear and concise, and that error messages were detailed and informative. We implemented the ability to load and save collage projects, to add layers and images to a project, and to apply filters to individual layers. We also enabled the user to select existing layers for use in the add-layer and set-filter commands, ensuring that only valid options were available to the user. Additionally, we included input validation for project and layer names, as well as image selection, to ensure that the user could not enter invalid or unsupported inputs.

Overall, our controller implementation was comprehensive and effective, providing a smooth user experience and enabling efficient communication between the model and view components.

#### Features Interface and CollageControllerGUIImpl Class

We recognized the importance of creating a user-friendly graphical user interface (GUI) in addition to the existing text-based user interface (UI) for our collage program. To facilitate communication between the GUI and the underlying model, we designed a Features interface that outlines the methods that the GUI view should implement to interact with the model. The setFeaturesController(IView view) method of this interface enables the controller to work with the given GUI view.

The CollageControllerGUIImpl class was then created to implement the methods defined in the Features interface. It takes in a model and a GUI view and enables the user to perform various commands such as creating a new project, editing the project in different ways, quitting the program, or loading an existing project. Each command method ensures that the changes made in the view are reflected in the model and vice versa.

By providing a separate implementation for the GUI interface, we allowed the user to choose between the text UI or the GUI UI, depending on their preferences. This design choice also improves the user experience by enabling them to interact with the program in a way that is more intuitive and visually appealing.

# Citations

## Image Sources

Link to Image Source: https://people.sc.fsu.edu/~jburkardt/data/ppma/ppma.html

Image Name: pbmlib.ascii.ppm

Terms of Usage: https://people.sc.fsu.edu/~jburkardt/txt/gnu_lgpl.txt

## Code Sources

All code written is owned by (and the work of) Jai Amin.
