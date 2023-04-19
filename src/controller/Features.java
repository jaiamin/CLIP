package controller;

import java.io.IOException;

import model.ICanvas;
import view.IView;

/**
 * Represents the asynchronous controller for the {@code CollageView}. This interface
 * contains the methods that are necessary to represent the various commands for the
 * GUI and TUI Collager programs.
 */
public interface Features {
  /**
   * This method is used to update the view of the asynchronous controller.
   *
   * @param view is the new view for the asynchronous controller
   */
  void setFeaturesController(IView view);

  /**
   * This method is used to create a new project with the given name and given height, width,
   * and max value dimensions. The height and width must be a positive integer and the
   * max value of the project must be an integer in the range [1, 255].
   *
   * @param name of the collage project
   * @param height of the collage project
   * @param width of the collage project
   * @param maxValue of the collage project
   * @throws IOException if an I/O error occurs when rendering a message to the view
   */
  void newProjectCommand(String name, int height, int width, int maxValue) throws IOException;

  /**
   * This method is used to add a new layer with the given name to the top of the whole project.
   * This layer always has a fully transparent white image and the Normal filter on by default.
   * To add a new layer to the collage project, the layer name must be unique.
   *
   * @param name is a unique layer name to add to the project
   * @throws IOException if an I/O error occurs when rendering a message to the view
   */
  void addLayerCommand(String name) throws IOException;

  /**
   * This method is used to place an image on a specific layer such that the top left
   * corner of the image is at (row, col). The layer name provided must be a valid layer
   * within the collage project. Images added to the collage project must be one of the
   * support image formats: PPM, JPG/JPEG, and/or PNG.
   *
   * @param layerName is a valid layer of the collage project
   * @param imagePath is the path of an image to be added to the collage project
   * @param row is a valid row to place an image on the collage project
   * @param col is a valid column to place an image on the collage project
   * @throws IOException if an I/O error occurs when rendering a message to the view
   */
  void addImageToLayerCommand(String layerName, String imagePath, int row, int col)
          throws IOException;

  /**
   * This method is used to set the filter of the given layer. The given layer must be a valid
   * layer within the collage project. The filter options/name that are program currently supports
   * include the following:
   * normal, red-component, green-component, blue-component brighten-value, brighten-intensity,
   * brighten-luma, darken-value, darken-intensity, darken-luma, difference, multiply, screen.
   *
   * @param layerName is a valid layer of the collage project
   * @param filterName is a valid filter to be applied to a layer of the collage project
   * @throws IOException if an I/O error occurs when rendering a message to the view
   */
  void setFilterCommand(String layerName, String filterName) throws IOException;

  /**
   * This method is used to save the result of applying all filters on the image. In other
   * words, it saves the final rendered/composite image of the collage project. The exported
   * filename must be one of the following supported image formats: PPM, JPG/JPEG, and/or PNG.
   *
   * @param filename is the path at which to save the final rendered image of the collage project
   * @throws IOException if an I/O error occurs when saving the image
   */
  void saveImageCommand(String filename) throws IOException;

  /**
   * This method is used to save the project as one file.
   * The exported project filename must end with .collage extension.
   * The collage project format is:
   * C1
   * width height
   * max-value
   * layer-name filter-name
   * LAYER-CONTENT-FORMAT
   * ...
   * layer-name filter-name
   * LAYER-CONTENT-FORMAT
   *
   * @param filename is the path at which to save the collage project
   * @throws IOException if an I/O error occurs when saving a collage project
   */
  void saveProjectCommand(String filename) throws IOException;

  /**
   * This method is used to load a project into the program. All layers, filters, and images
   * that exist within the provided collage project will be imported into the program workspace.
   * The imported project filename must end with .collage extension.
   *
   * @param filename is the path of the project to be loaded to the view.
   * @throws IOException if an I/O error occurs when loading a collage project
   */
  void loadProjectCommand(String filename) throws IOException;

  /**
   * This method is used to quit the view and Collager program.
   */
  void quitCommand();

  /**
   * This method is used to display the help menu which shows all the available
   * commands for the collage project program.
   *
   * @throws IOException if an I/O error occurs when rendering a message to the view
   */
  void helpMenuCommand() throws IOException;

  /**
   * This method returns the current collage project model.
   *
   * @return the current collage project model
   */
  ICanvas getModel();
}
