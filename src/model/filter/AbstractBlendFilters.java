package model.filter;

import java.util.ArrayList;

import model.ColorPixel;
import model.IImage;
import model.IPixel;
import model.RepresentationConverter;

/**
 * This abstract class represents filters that use the lightness value
 * of the composite image underneath when editing the current layer. The way that
 * the filter alters the layer depends on the given image that is the cumulated background
 * image of the that layer.
 */
public abstract class AbstractBlendFilters implements IFilter {
  private final IImage prevIm;

  /**
   * This constructor initializes the blending filters for the {@code AbstractBlendFilters}
   * class. The current supported blending filters include multiply and screen.
   *
   * @param prevIm represents the image of the images underneath the current layer
   */
  public AbstractBlendFilters(IImage prevIm) {
    if (prevIm == null) {
      throw new IllegalArgumentException("invalid inputs");
    }
    else {
      this.prevIm = prevIm;
    }
  }

  /**
   * This method applies a {@code AbstractBlendFilters} filter onto a specific layer image.
   * It uses the RGB components of the prevIm as well as the given image to alter the
   * given image in the specified manner based on the values of the prevIm at the given
   * coordinates.
   *
   * @param im to apply the filter to
   * @param row of the pixel to apply the filter to
   * @param col of the pixel to apply the filter to
   */
  public void apply(IImage im, int row, int col) {
    double dr = this.prevIm.getPixel(row, col).getRed() / 255.0;
    double dg = this.prevIm.getPixel(row, col).getGreen() / 255.0;
    double db = this.prevIm.getPixel(row, col).getBlue() / 255.0;
    ArrayList<Double> dhsl = RepresentationConverter.convertRGBtoHSL(dr, dg, db);
    double r = im.getPixel(row, col).getRed() / 255.0;
    double g = im.getPixel(row, col).getGreen() / 255.0;
    double b = im.getPixel(row, col).getBlue() / 255.0;
    ArrayList<Double> hsl = RepresentationConverter.convertRGBtoHSL(r, g, b);
    int a = im.getPixel(row, col).getAlpha();
    ArrayList<Integer> rgb = this.getValues(hsl, dhsl);
    int r1 = rgb.get(0);
    int g1 = rgb.get(1);
    int b1 = rgb.get(2);
    IPixel pix = new ColorPixel(r1, g1, b1, a);
    pix.setFilter(this);
    im.setPixel(row, col, pix);
  }

  /**
   * This method returns the RGB component values once converted from an HSL representation.
   *
   * @param hsl is the hsl component listed as double values corresponding to
   *            the hue, saturation, and light values of a pixel.
   * @param dhsl is the hsl component listed as double values representing the
   *             hue, saturation, and light values of another pixel.
   * @return a list of RGB component values listed as red, green, and blue
   * components in integer form
   */
  protected abstract ArrayList<Integer> getValues(ArrayList<Double> hsl, ArrayList<Double> dhsl);

  /**
   * Represents the String representation of an {@code AbstractBlendFilters} filter.
   *
   * @return the string representation of this filter.
   */
  public abstract String toString();
}
