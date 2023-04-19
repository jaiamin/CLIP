package model.filter;

import java.util.Arrays;
import java.util.Collections;

/**
 * This class extends the functionality of the {@code AbstractBrightness} abstract class.
 * It provides an implementation for the brightness filters that darken the brightness
 * of each pixel in an image.
 */
public class DarkenFilter extends AbstractBrightness {
  LightType type;

  /**
   * This is the constructor of the {@code DarkenFilter} class, which creates a
   * new {@code DarkenFilter} object with the specified light type.
   *
   * @param type the light type to use for the brightness darkened filter
   * @throws IllegalArgumentException if type is null
   */
  public DarkenFilter(LightType type) {
    super(type);
    if (type == null) {
      throw new IllegalArgumentException("invalid input");
    }
    else {
      this.type = type;
    }
  }

  /**
   * This method takes in integer values of an RGB component and returns the value of
   * the component with the maximum integer value as a negative integer.
   * @param r is the red component of the color
   * @param g is the green component of the color
   * @param b is the blue component of the color
   * @return the maximum integer component of the given values as a negative integer
   */
  @Override
  protected int getValue(int r, int g, int b) {
    return -Collections.max(Arrays.asList(r, g, b));
  }

  /**
   * This method takes in integer values of an RGB component and returns the negative value of
   * their sum as an integer.
   * @param r is the red component of the color
   * @param g is the green component of the color
   * @param b is the blue component of the color
   * @return the negative average of the three values
   */
  @Override
  protected int getIntensity(int r, int g, int b) {
    return -((r + g + b) / 3);
  }

  /**
   * This method takes in integer values of an RGB component and returns the negative value of
   * the luma.
   * @param r is the red component of the color
   * @param g is the green component of the color
   * @param b is the blue component of the color
   * @return the negative luma value based on the three inputs as an integer
   */
  @Override
  protected int getLuma(int r, int g, int b) {
    double lumaValue = (0.2126 * r) + (0.7152 * g) + (0.0722 * b);
    return (int) -lumaValue;
  }

  /**
   * This method creates a new DarkenFilter object with the given type.
   * @param type is the light type of the {@code AbstractBrightness}
   * @return a new DarkenFilter with the given LightType
   */
  @Override
  protected AbstractBrightness factory(LightType type) {
    return new DarkenFilter(type);
  }

  /**
   * This method overrides the toString() method and returns the String representation
   * of a light type for a darkened filter.
   *
   * @return the String representation of a light type for a darkened filter
   */
  @Override
  public String toString() {
    switch (this.type) {
      case VALUE:
        return "darken-value";
      case INTENSITY:
        return "darken-intensity";
      case LUMA:
        return "darken-luma";
      default:
        throw new IllegalArgumentException();
    }
  }
}
