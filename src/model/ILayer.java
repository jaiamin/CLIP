package model;

import java.util.List;
import model.filter.IFilter;


/**
 * This interface represents a layer and defines the common operations for a layer.
 */
public interface ILayer {
  /**
   * Returns the pixels of the image before any filters were applied.
   *
   * @return the pixels of the image before any filters were applied
   */
  IPixel[][] getPreviousPixels();

  /**
   * Returns the image that represents this layer.
   *
   * @return the image that represents this layer
   */
  IImage getImage();

  /**
   * Returns a list of images that have been added to this layer.
   *
   * @return a list of images that have been added to this layer
   */
  List<IImage> getImages();

  /**
   * Returns the name of this image layer.
   *
   * @return the name of this image layer
   */
  String getName();

  /**
   * Returns the filter applied to this image layer.
   *
   * @return the filter applied to this image layer
   */
  IFilter getFilter();

  /**
   * Sets the filter applied to this image layer to the specified filter.
   *
   * @param other the filter to be applied to this image layer
   */
  void setFilter(IFilter other);

  /**
   * Applies the specified filter to this image layer.
   * First, applyFilter method checks to see if there is already on the layer. If so,
   * it creates a copy of the original pixels. If not, then it resets the image pixels to the
   * original pixels. Then, it applies the filter to the image and sets the new filter.
   *
   * @param filter the filter to be applied to this image layer
   */
  void applyFilter(IFilter filter);

  /**
   * Reverts the image pixels to the original pixels before any filters were applied.
   */
  void revertToOriginal();

  /**
   * Adds the specified image to this layer at the specified position.
   *
   * @param image the image to be added to this layer
   * @param x the x-coordinate (column) of the position at which to add the image
   * @param y the y-coordinate (row) of the position at which to add the image
   * @throws IllegalArgumentException if the image position is outside the bounds of the canvas
   */
  void addImageToLayer(IImage image, int x, int y) throws IllegalArgumentException;
}
