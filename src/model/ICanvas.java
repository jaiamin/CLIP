package model;

import java.io.IOException;
import java.util.ArrayList;

import model.filter.IFilter;

/**
 * This interface represents a canvas used to create an image collage.
 */
public interface ICanvas {
  /**
   * This method is used to add a new, unique layer to the canvas and updates the image
   * of the canvas with the image pixels of the new layer.
   *
   * @param height is the height of the new layer
   * @param width is the width of the new layer
   * @param name is the name of the new layer
   * @throws IllegalArgumentException if a layer with the same name already exists
   */
  void addLayerToCanvas(int height, int width, String name) throws IllegalArgumentException;

  /**
   * This method is used to add an image to a valid, specified layer at the given offset position.
   * Note: An offset of (0, 0) represents the top leftmost position of the canvas.
   *
   * @param layerName is the name of the layer
   * @param image to be added to the layer
   * @param xPos is the x position offset of the image on the layer
   * @param yPos is the y position offset of the image on the layer
   * @throws IllegalArgumentException if the specified layer does not exist
   */
  void addImageToLayer(String layerName, IImage image, int xPos, int yPos)
          throws IllegalArgumentException;

  /**
   * This method is used to update the pixels of the canvas based on the current layers
   * and their respective filters.
   * Note: Any pixel(s) that are not within the grid of the canvas are cropped off and not
   * considered as part of the final canvas image.
   */
  void updatePixels();

  /**
   * Returns the current image of the canvas.
   *
   * @return the current image of the canvas
   */
  IImage getImage();

  /**
   * Returns the current list of layers of the canvas.
   *
   * @return the current list of layers of the canvas
   */
  ArrayList<ILayer> getLayers();

  /**
   * Returns the current project name of the canvas.
   *
   * @return the current project name of the canvas
   */
  String getProjectName();

  /**
   * Returns the layers beneath the layer the filter is set to.
   *
   * @param layerName name of the layer that the filter is applied to
   * @return background Image of the current layer in the project
   */
  IImage getPrevIm(String layerName);

  /**
   * This method is used to get the current project structure which consists of the current
   * layers and images that form the created/loaded collage project.
   *
   * @return the current project structure of the collage project
   */
  String getProjectStructure();

  /**
   * This method is used to get the {@code IFilter} type for a given valid String
   * representation of a filter option type. If an invalid filter option
   * is provided by a user, the method throws a RuntimeException.
   *
   * @param filterStr is the String representation of a filter
   * @param layerName is the given layer name used for the HSL filters
   * @return the appropriate filter option for the given String representation
   * @throws RuntimeException if invalid filter name is given
   */
  IFilter getFilterFromString(String filterStr, String layerName) throws RuntimeException;

  /**
   * This method is used to get the ARGB value of a specific coordinate on the current collage.
   *
   * @param x height coordinate of the pixel on the collage
   * @param y width coordinate of the pixel on the collage
   * @param maxProjectValue is the max value of color representations in the project
   * @param factor is the value 255 scaled by the maxProjectValue
   * @return value of the ARGB representation of the coordinate on the collage
   */
  int getARGB(int x, int y, int maxProjectValue, double factor);

  /**
   * This method outlines a way of returning an object of the model.
   * @return an initialized object of type ICanvas
   */
  ICanvas setModel();

  /**
   * This method outlines a way of returning an object of the model when
   * given the height, width, and project name for it.
   *
   * @param height is the height of the model to be created
   * @param width is the width of the model to be created
   * @param name is the project name of the model to be created
   * @return an initialized object of type ICanvas
   */
  ICanvas setModel(int height, int width, String name);

  /**
   * This method outlines a way of returning an object of the model when
   * given the height, width, and project name for it.
   *
   * @param height is the height of the model to be created
   * @param width is the width of the model to be created
   * @param pixels is the grid given to initiate the model object with
   * @param layers is the set of layers for the given model
   * @return an initialized object of type ICanvas
   */
  ICanvas setModel(int height, int width, IPixel[][] pixels, ArrayList<ILayer> layers);

  /**
   * This method adds a filter to the project using a given filter and a layer name.
   *
   * @param filterOption is the filter to be applied
   * @param layerName is the name of the layer that the filter should be applied to
   */
  void addFilter(IFilter filterOption, String layerName);

  /**
   * This method returns an image based on a given filename.
   *
   * @param imagePath is the name of the file that contains the image to be returned
   * @return a new object of type IImage
   * @throws IOException if the file cannot be read
   */
  IImage createImage(String imagePath) throws IOException;

  /**
   * This method returns an image compiled based on the given grid of
   * pixels and the max value for their color scale.
   *
   * @param pixels is the grid that the image is constructed from
   * @param maxVal is the maximum value for the RGB components of the image
   * @return a new object of type IImage
   */
  IImage createImage(IPixel[][] pixels, int maxVal);

  /**
   * This method creates and returns a single pixel based on the given
   * RGBA values and the filter that edits it.
   *
   * @param red is the red component value of the pixel
   * @param green is the green component value of the pixel
   * @param blue is the blue component value of the pixel
   * @param alpha is the alpha value of the new pixel
   * @param filter is the filter that is placed on the pixel and indicates how it should be edited
   * @return a new object of type IPixel
   */
  IPixel createPixel(int red, int green, int blue, int alpha, IFilter filter);
}
