package model.filter;

import java.util.Arrays;
import java.util.Collections;

/**
 * This class extends the functionality of the {@code AbstractBrightness} abstract class.
 * It provides an implementation for the brightness filters that increase the brightness
 * of each pixel in an image.
 */
public class BrightenFilter extends AbstractBrightness {
  LightType type;

  /**
   * This is the constructor of the {@code BrightenFilter} class, which creates a
   * new {@code BrightenFilter} object with the specified light type.
   *
   * @param type the light type to use for the brightness filter
   * @throws IllegalArgumentException if type is null.
   */
  public BrightenFilter(LightType type) {
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
   * the component with the maximum integer value.
   * @param r is the red component of the color
   * @param g is the green component of the color
   * @param b is the blue component of the color
   * @return the maximum integer component of the given values
   */
  @Override
  protected int getValue(int r, int g, int b) {
    return Collections.max(Arrays.asList(r, g, b));
  }

  /**
   * This method takes in integer values of an RGB component and returns the value of
   * their sum as an integer.
   * @param r is the red component of the color
   * @param g is the green component of the color
   * @param b is the blue component of the color
   * @return the average of the three values
   */
  @Override
  protected int getIntensity(int r, int g, int b) {
    return (r + g + b) / 3;
  }

  /**
   * This method takes in integer values of an RGB component and returns the value of
   * the luma.
   * @param r is the red component of the color
   * @param g is the green component of the color
   * @param b is the blue component of the color
   * @return the luma value based on the three inputs as an integer
   */
  @Override
  protected int getLuma(int r, int g, int b) {
    double lumaValue = (0.2126 * r) + (0.7152 * g) + (0.0722 * b);
    return (int) lumaValue;
  }

  /**
   * This method creates a new BrightenFilter object with the given type.
   * @param type is the light type of the {@code AbstractBrightness}
   * @return a new BrightenFilter with the given LightType
   */
  @Override
  protected AbstractBrightness factory(LightType type) {
    return new BrightenFilter(type);
  }

  /**
   * This method overrides the toString() method and returns the String representation
   * of a light type for a brightened filter.
   *
   * @return the String representation of a light type for a brightened filter
   */
  @Override
  public String toString() {
    switch (this.type) {
      case VALUE:
        return "brighten-value";
      case INTENSITY:
        return "brighten-intensity";
      case LUMA:
        return "brighten-luma";
      default:
        throw new IllegalArgumentException();
    }
  }
}
