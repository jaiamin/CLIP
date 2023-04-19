package model;

import model.filter.IFilter;
import model.filter.NormalFilter;

/**
 * This class implements the {@code IPixel} interface and represents a colored pixel
 * in an image with color information (RGBA).
 */
public class ColorPixel implements IPixel {
  private int red;
  private int green;
  private int blue;
  private int alpha;
  private IFilter filter;

  /**
   * This is the constructor for the {@code ColorPixel} class, which constructs a {@code ColorPixel}
   * object with the specified red, green, blue, and alpha values.
   * Note: The filter is default set to {@code NormalFilter}.
   *
   * @param red   value of the pixel, between 0 and 255
   * @param green value of the pixel, between 0 and 255
   * @param blue  value of the pixel, between 0 and 255
   * @param alpha value of the pixel, between 0 and 255
   * @throws IllegalArgumentException if any of the input values are out of the range [0, 255]
   */
  public ColorPixel(int red, int green, int blue, int alpha) throws IllegalArgumentException {
    if (red < 0 || red > 255 || green < 0 || green > 255
        || blue < 0 || blue > 255 || alpha < 0 || alpha > 255) {
      throw new IllegalArgumentException("invalid input");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
    this.filter = new NormalFilter();

  }

  /**
   * This is the constructor for the {@code ColorPixel} class, which constructs a {@code ColorPixel}
   * object with the specified red, green, blue, alpha, and {@code IFilter} values.
   *
   * @param red    value of the pixel, between 0 and 255
   * @param green  value of the pixel, between 0 and 255
   * @param blue   value of the pixel, between 0 and 255
   * @param alpha  value of the pixel, between 0 and 255
   * @param filter to be applied to the pixel
   * @throws IllegalArgumentException if any of the input values are out of the range [0, 255]
   *        or if filter is null
   */
  public ColorPixel(int red, int green, int blue, int alpha, IFilter filter) {
    if (red < 0 || red > 255 || green < 0 || green > 255
            || blue < 0 || blue > 255 || alpha < 0 || alpha > 255 || filter == null) {
      throw new IllegalArgumentException("invalid input");
    } else {
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.alpha = alpha;
      this.filter = filter;
    }
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public int getAlpha() {
    return this.alpha;
  }

  @Override
  public IFilter getFilter() {
    return this.filter;
  }

  @Override
  public void setFilter(IFilter other) {
    this.filter = other;
  }
}