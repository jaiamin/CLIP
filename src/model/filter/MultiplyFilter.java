package model.filter;

import java.util.ArrayList;

import model.IImage;
import model.RepresentationConverter;

/**
 * Represents a blending filter that darkens a layer based on the lightness of the
 * image underneath it.
 */
public class MultiplyFilter extends AbstractBlendFilters {

  /**
   * This is the constructor of the {@code MultiplyFilter} class, which creates a
   * new MultiplyFilter object with the given background image.
   * @param prevIm is the cumulated background image of a layer the filter is being applied to
   */
  public MultiplyFilter(IImage prevIm) {
    super(prevIm);
  }

  /**
   * This method returns the RGB values as a list of integers that represent the red, green, and
   * blue values for a pixel on a layer that is being altered with this filter. The new
   * RGB values are based on the given list of the hsl components of the pixel being edited
   * as well as the given list of the hsl components of the pixel in the same coordinate but on
   * the background image of the layer. This edit is done to darken the layer based on the
   * lightness value of both given lists.
   * @param hsl is the hsl component listed as double values corresponding to
   *            the hue, saturation, and light values of a pixel.
   * @param dhsl is the hsl component listed as double values representing the
   *             hue, saturation, and light values of another pixel.
   * @return a list of integers that respectively represent the new red, green, and blue values
   * for a pixel that the filter is applied to
   */
  protected ArrayList<Integer> getValues(ArrayList<Double> hsl, ArrayList<Double> dhsl) {
    double h = hsl.get(0);
    double s = hsl.get(1);
    double l = (hsl.get(2) * dhsl.get(2));
    return RepresentationConverter.convertHSLtoRGB(h, s, l);
  }

  /**
   * This method overrides the toString() method and returns the String representation
   * of a component type for a multiply filter.
   *
   * @return the String representation of a multiply filter
   */
  public String toString() {
    return "multiply";
  }
}


