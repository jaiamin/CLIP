package model.filter;

import java.util.ArrayList;

import model.IImage;
import model.RepresentationConverter;

/**
 * Represents a blending filter that brightens a layer based on the lightness of the
 * image underneath it.
 */
public class ScreenFilter extends AbstractBlendFilters {

  /**
   * This is the constructor of the {@code ScreenFilter} class, which creates a
   * new ScreenFilter object with the given background image.
   * @param prevIm is the cumulated background image of a layer the filter is being applied to
   */
  public ScreenFilter(IImage prevIm) {
    super(prevIm);
  }

  /**
   * This method returns the RGB values as a list of integers that represent the red, green, and
   * blue values for a pixel on a layer that is being altered with this filter. The new
   * RGB values are based on the given list of the hsl components of the pixel being edited
   * as well as the given list of the hsl components of the pixel in the same coordinate but on
   * the background image of the layer. This edit is done to brighten the layer based on the
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
    double l = (1 - ((1 - hsl.get(2)) * (1 - dhsl.get(2))));
    return RepresentationConverter.convertHSLtoRGB(h, s, l);
  }

  /**
   * This method overrides the toString() method and returns the String representation
   * of a component type for a screen filter.
   *
   * @return the String representation of a screen filter
   */
  public String toString() {
    return "screen";
  }
}


