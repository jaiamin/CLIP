package model;

import model.filter.IFilter;

/**
 * This interface represents a pixel in an image and provides the methods to get and
 * set the color information and filter applied to the pixel.
 */
public interface IPixel {
  /**
   * Returns the red value of the pixel.
   *
   * @return the red value of the pixel
   */
  int getRed();

  /**
   * Returns the green value of the pixel.
   *
   * @return the green value of the pixel
   */
  int getGreen();

  /**
   * Returns the blue value of the pixel.
   *
   * @return the blue value of the pixel
   */
  int getBlue();

  /**
   * Returns the alpha value of the pixel.
   *
   * @return the alpha value of the pixel
   */
  int getAlpha();

  /**
   * Returns the filter to be applied to the pixel.
   *
   * @return the filter to be applied to the pixel
   */
  IFilter getFilter();

  /**
   * Sets the filter to be applied to the pixel.
   *
   * @param other filter to be applied to the pixel
   */
  void setFilter(IFilter other);
}