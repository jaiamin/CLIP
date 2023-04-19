# USEME

This file contains a list of available commands that can be used in both the GUI and TUI.

## Help
#### GUI
Click on the ‘HELP MENU’ button to access a list of available commands for the collage project program.

#### TUI
Command: ```$ help```

###

## Quit
#### GUI
Click on the ‘QUIT’ button to exit the program and lose any unsaved progress.

#### TUI
Command: ```$ quit```

## Save Image
#### GUI
Click on the ‘SAVE IMAGE’ button to save the current collage as a single image file in PPM, JPG/JPEG, or PNG format.

#### TUI
Command: ```$ save-image [image-path]```

Note: Images must be saved/exported with one of the suported image formats: PPM, JPG/JPEG, and/or PNG.

## Save Project
#### GUI
Click on the ‘SAVE PROJECT’ button to save the current state of your collage project.

#### TUI
Command: ```$ save-project [project-path]```

Note: Projects must be saved/exported with .collage extension.

## Load Project
#### GUI
Click on the ‘LOAD PROJECT’ button to continue working on a previously saved collage project. 

#### TUI
Command: ```$ load-project [project-path]```

Note: You can only load a project that has been saved as a .collage file format.

## New Project
#### GUI
To create a new project, follow these steps:

- Enter the name of your project in the ‘Name’ box located to the right of “New Project”.
- Specify the height and width of the collage grid in the ‘Height’ and ‘Width’ boxes as positive integer values.
- Set the maximum RGB value for the colors in your project as a positive integer value less than 256 in the ‘RGB Max Value’ box.
- Click on the ‘Confirm’ button to create your new project.

#### TUI
Command: ```$ new-project [name] [height] [width] [max-value]```

## Add Layer
#### GUI
To add a new layer to your collage project, follow these steps:

- Enter a unique name for your layer in the ‘Name’ box located to the right of “Add Layer”.
- Click on the ‘Confirm’ button to add the layer to your project.

#### TUI
Command: ```$ add-layer [layer-name]```

Note: Only unique layer names will be added to the collage project.

## Add Image
#### GUI
To add an image to a layer in your collage project, follow these steps:

- Use the drop-down menu to select the layer you want to add the image to.
- Click on the ‘Choose Image’ button to select the image you want to add. Your image must be in PPM, PNG, or JPG/JPEG format.
- Specify the distance from the top and left of the collage where you want the image to be placed using the ‘Row’ and ‘Column’ boxes as positive integer values.
- Click on the ‘Confirm’ button to add the image to your selected layer.

## Set Filter
#### GUI
To apply a filter to a layer in your collage project, follow these steps:

- Use the drop-down menu to select the layer you want to edit.
- Use the other drop-down menu in the same row to select from the available filter options.
- Click on the ‘Confirm’ button to apply the selected filter to your selected layer.

#### TUI
Command: ```$ set-filter [layer-name] [filter-option]```

Note: Layer name must be a valid layer within the collage project and the filter option should be one of the following below.

### Available Filter Options

#### normal
Reverts the image to its original colored pixels.

#### red-component 
Filters only the red portion of the RGB of the image will be used.

#### green-component
Filters only the green portion of the RGB of the image will be used.

#### blue-component
Filters only the blue portion of the RGB of the image will be used.

#### brighten-value
Increases the value of the image according to the value formula (max value of the 3 color components)

#### brighten-intensity
increase the intensity of the image according to the intensity formula (average value of the 3 color components).

#### brighten-luma
increase the luma of the image according to the luma formula (```(0.2126 * r) + (0.7152 * g) + (0.0722 * b)```).

#### darken-value
Decreases the value of the image according to the value formula (max value of the 3 color components).

#### darken-intensity
Decreases the intensity of the image according to the intensity formula (average value of the 3 color components).

#### darken-luma
Decreases the luma of the image according to the luma formula (```(0.2126 * r) + (0.7152 * g) + (0.0722 * b)```).

#### difference
Inverts the colors of the layer based on the background layers.

#### multiply
Darkens the layer based on the background images.

#### screen
Brightens the layer based on the background images.
