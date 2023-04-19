package model;

/**
 * This interface represents an image and defines the common operations for an image.
 */
public interface IImage {
  /**
   * This method is used to update the color values of each pixel in an image so that the maximum
   * pixel value is 255 (ensures that each pixel is 255-based).
   * Note: If the current maximum pixel is already 255, this method does nothing.
   */
  void updateWithMaxValue();

  /**
   * Returns the height of the image, in pixels.
   *
   * @return the height of the image, in pixels
   */
  int getHeight();

  /**
   * Returns the width of the image, in pixels.
   *
   * @return the width of the image, in pixels
   */
  int getWidth();

  /**
   * Returns the 2D array of {@code IPixel} objects that represent the pixels of an image.
   *
   * @return the 2D array of {@code IPixel} objects that represent the pixels of an image
   */
  IPixel[][] getPixels();

  /**
   * Returns the maximum value of each color component of the pixels.
   *
   * @return the maximum value of each color component of the pixels
   */
  int getMaxValue();

  /**
   * Retrieves the {@code IPixel} that is positioned at the specified (row, col)
   * in the image pixels.
   *
   * @param row of a pixel in the image
   * @param col of a pixel in the image
   * @return the {@code IPixel} that is positioned at the specified (row, col) in the image pixels
   */
  IPixel getPixel(int row, int col);

  /**
   * Sets the {@code IPixel} that is positioned at the specified (row, col) in the image pixels
   * to the specified {@code IPixel}.
   *
   * @param row of a pixel in the image
   * @param col of a pixel in the image
   * @param pixel to be set at (row, col) in the image pixels
   */
  void setPixel(int row, int col, IPixel pixel);

  /**
   * Returns the filename of an image.
   * Note: If no filename exists (null), then a RuntimeException is thrown.
   *
   * @return the filename of an image
   * @throws RuntimeException if image filename is null
   */
  String getFilename() throws RuntimeException;
}