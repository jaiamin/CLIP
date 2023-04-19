package model.filter;

import model.IImage;

/**
 * This is a class that implements the {@code IFilter} interface. It provides an
 * implementation for the normal filter that reverts an image to its original pixels.
 */
public class NormalFilter implements IFilter {
  /**
   * Applies the filter to the specified pixel of the specified image.
   * This implementation does nothing and leaves the image unchanged.
   * Note: The applyFilter method in the ImageLayer class reverts an image
   * to its original pixels everytime it is filtered so {@code NormalFilter}
   * {@code apply} method does not have to do anything.
   *
   * @param image to apply the filter to
   * @param row of the pixel to apply the filter to
   * @param col of the pixel to apply the filter to
   */
  @Override
  public void apply(IImage image, int row, int col) {
    // do nothing
  }

  /**
   * This method overrides the toString() method and returns the String representation
   * for a normal filter.
   *
   * @return the String representation for a normal filter
   */
  @Override
  public String toString() {
    return "normal";
  }
}
