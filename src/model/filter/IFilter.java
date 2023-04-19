package model.filter;

import model.IImage;

/**
 * This interface represents a type of filter that can be applied to an {@code IImage}.
 * It provides a method to apply a specific filter to an image at a specified (row, col).
 */
public interface IFilter {
  /**
   * This method applies the brightness filter to the pixel at the specified row and column
   * in the specified image.
   * Note: Does not apply brightness filter to transparent pixels.
   *
   * @param image to apply the filter to
   * @param row of the pixel to apply the filter to
   * @param col of the pixel to apply the filter to
   */
  void apply(IImage image, int row, int col);
}
