package model.filter;

import model.ColorPixel;
import model.IImage;
import model.IPixel;

/**
 * This is a class that implements the {@code IFilter} interface. It provides an
 * implementation for the blending filters that adjust the color component of
 * each pixel in an image, this filter inverts the colors based on its background image.
 */
public class DifferenceFilter implements IFilter {
  private IImage prevIm;

  /**
   * This is the constructor of the {@code ComponentFilter} class, which creates a
   * new DifferenceFilter object with the specified component type.
   *
   * @param prevIm is the background image of the current layer
   * @throws IllegalArgumentException if component is null
   */
  public DifferenceFilter(IImage prevIm) {
    if (prevIm == null) {
      throw new IllegalArgumentException();
    } else {
      this.prevIm = prevIm;
    }
  }

  @Override
  public void apply(IImage im, int row, int col) {
    IPixel prevImPixel = this.prevIm.getPixel(row, col);
    int dr = prevImPixel.getRed();
    int dg = prevImPixel.getGreen();
    int db = prevImPixel.getBlue();
    IPixel imagePixel = im.getPixel(row, col);
    int r = imagePixel.getRed();
    int g = imagePixel.getGreen();
    int b = imagePixel.getBlue();
    int a = imagePixel.getAlpha();
    int r1 = Math.abs(r - dr);
    int g1 = Math.abs(g - dg);
    int b1 = Math.abs(b - db);
    IPixel pix = new ColorPixel(r1, g1, b1, a);
    pix.setFilter(this);
    im.setPixel(row, col, pix);
  }

  /**
   * This method overrides the toString() method and returns the String representation
   * of a component type for a difference filter.
   *
   * @return the String representation of a difference filter
   */
  @Override
  public String toString() {
    return "difference";
  }
}
