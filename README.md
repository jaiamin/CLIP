# CLIP: Composite Layering Image Processor

CLIP provides a comprehensive set of features, including the ability to create new collage projects with desired dimensions and max values, add various layers and images, apply filters to layers, load/save projects, save completed composite images in various image formats.

CLIP utilizes a controller pattern (MVC architecture) to ensure efficient and correct communication between the model and view components.

Overall, CLIP provides a user-friendly interface and robust set of features for creating collages, making it a valuable tool for anyone who needs to quickly and easily create composite images using either the GUI and/or TUI.

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

Currently, all features mentioned above are available for both user interfaces (GUI and/or TUI).

## Requirements

To run or compile this code, you will need:

- Java 11 or higher JRE
- JUnit 4 for running tests

## USEME

Please refer to the USEME file for instructions on how to use this program.

## Licensing
Copyright (c) Jai Amin. All rights reserved.

Licensed under the MIT License.
