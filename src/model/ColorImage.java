package model;

/**
 * This class implements the {@code IImage} interface and represents a color image with pixels
 * that have red, green, blue, and alpha (RGBA) pixels.
 */
public class ColorImage implements IImage {
  private final int height;
  private final int width;
  private final int maxValue;
  private final IPixel[][] pixels;
  private String filename;

  /**
   * This is the constructor for the {@code ColorImage} class, which creates a new
   * {@code ColorImage}
   * object with the specified height, width, maximum value of the color components, 2D array of
   * {@code IPixel} objects that represent the pixels, and the filename.
   *
   * @param height of the image
   * @param width of the image
   * @param maxValue is the maximumValue of each color component of the pixels
   * @param pixels is the 2D array of {@code IPixel} objects that represent the pixels of the image
   * @param filename of the image file
   * @throws IllegalArgumentException if the height, width, or maximum value is less than 1 or
   *        greater than 255 or if parameters are null
   */
  public ColorImage(int height, int width, int maxValue, IPixel[][] pixels, String filename)
          throws IllegalArgumentException {
    if (height < 1 || width < 1 || maxValue < 1
            || maxValue > 255 || pixels == null || filename == null) {
      throw new IllegalArgumentException("invalid inputs");
    }
    else {
      this.height = height;
      this.width = width;
      this.maxValue = maxValue;
      this.pixels = pixels;
      this.filename = filename;
    }
  }

  /**
   * This is the constructor for the {@code ColorImage} class, which creates a new
   * {@code ColorImage}
   * with the specified 2D array of {@code IPixel} objects that represent the pixels of an image.
   *
   * @param pixels is the 2D array of {@code IPixel} objects that represent the pixels of an image
   * @throws IllegalArgumentException if pixels is null
   */
  public ColorImage(IPixel[][] pixels) {
    if (pixels == null) {
      throw new IllegalArgumentException("invalid input");
    } else {
      this.height = pixels.length;
      this.width = pixels[0].length;
      this.maxValue = 255;
      this.pixels = pixels;
    }
  }

  /**
   * This is the constructor for the {@code ColorImage} class, which creates a new
   * {@code ColorImage}
   * with the specified 2D array of {@code IPixel} objects that represent the pixels of an image
   * and the maximum value of the color components of the pixels.
   *
   * @param pixels is the 2D array of {@code IPixel} objects that represent the pixels of an image
   * @param maxValue is the maximum value of each color component of the pixels
   * @throws IllegalArgumentException if the maximum value is less than 1 or greater than 255
   *        or if pixels is null
   */
  public ColorImage(IPixel[][] pixels, int maxValue) throws IllegalArgumentException {
    if (maxValue < 1 || maxValue > 255 || pixels == null) {
      throw new IllegalArgumentException("invalid input");
    }
    else {
      this.height = pixels.length;
      this.width = pixels[0].length;
      this.maxValue = maxValue;
      this.pixels = pixels;
    }
  }

  /**
   * This is the constructor for the {@code ColorImage} class, which creates a new
   * {@code ColorImage}
   * with the specified height and width of the image, alpha value of the pixels,
   * and the maximum
   * value of the color components of the pixels.
   *
   * @param height of the image
   * @param width of the image
   * @param alpha value of the pixels
   * @param maxValue is the maximum value of each color component of the pixels
   * @throws IllegalArgumentException if the height and width are less than 1, alpha value is not
   *        in range [0, 255], or max value is not in range [1, 255]
   */
  public ColorImage(int height, int width, int alpha, int maxValue)
          throws IllegalArgumentException {
    if (height < 1 || width < 1 || alpha < 0 || alpha > 255 || maxValue < 1 || maxValue > 255) {
      throw new IllegalArgumentException("invalid input");
    }
    this.height = height;
    this.width = width;
    this.maxValue = maxValue;
    this.pixels = new ColorPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        IPixel newPixel = new ColorPixel(maxValue, maxValue, maxValue, alpha);
        this.pixels[i][j] = newPixel;
      }
    }
  }

  @Override
  public void updateWithMaxValue() {
    if (this.getMaxValue() == 255) {
      return;
    }
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        IPixel pixel = this.getPixel(i, j);
        double factor = 255.0 / this.getMaxValue();
        int red = (int) Math.round((double) pixel.getRed() * factor);
        int green = (int) Math.round((double) pixel.getGreen() * factor);
        int blue = (int) Math.round((double) pixel.getBlue() * factor);
        IPixel newPixel = new ColorPixel(red, green, blue, 255);
        this.setPixel(i, j, newPixel);
      }
    }
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public IPixel[][] getPixels() {
    return this.pixels;
  }

  @Override
  public int getMaxValue() {
    return this.maxValue;
  }

  @Override
  public IPixel getPixel(int row, int col) {
    return this.pixels[row][col];
  }

  @Override
  public void setPixel(int row, int col, IPixel pixel) {
    this.pixels[row][col] = pixel;
  }

  @Override
  public String getFilename() throws RuntimeException {
    if (this.filename == null) {
      throw new RuntimeException("Unknown filename");
    }
    return this.filename;
  }
}