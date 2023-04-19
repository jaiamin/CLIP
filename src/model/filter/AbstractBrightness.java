package model.filter;

import model.ColorPixel;
import model.IImage;
import model.IPixel;

/**
 * This is an abstract class that implements the {@code IFilter} interface. It provides a common
 * implementation for the brightness filters that adjust the brightness of each pixel in an image.
 */
public abstract class AbstractBrightness implements IFilter {
  private final LightType type;

  /**
   * This is the constructor of the {@code AbstractBrightness} class, which creates a
   * new AbstractBrightness object with the specified light type.
   *
   * @param type the light type to use for the brightness filter
   */
  public AbstractBrightness(LightType type) {
    this.type = type;
  }

  @Override
  public void apply(IImage image, int row, int col) {
    IPixel pixel = image.getPixel(row, col);
    if (pixel.getAlpha() == 0) {
      return;
    }
    IPixel newPixel = getNewColor(pixel);
    newPixel.setFilter(factory(type));
    image.setPixel(row, col, newPixel);
  }

  /**
   * This method is used to retrieve a new pixel with the brightness adjusted based
   * on the type of brightness filter.
   *
   * @param pixel the original pixel to adjust
   * @return the new pixel with the adjusted brightness
   */
  private IPixel getNewColor(IPixel pixel) {
    int b;
    switch (type) {
      case VALUE:
        b = getValue(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
        break;
      case INTENSITY:
        b = getIntensity(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
        break;
      case LUMA:
        b = getLuma(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
        break;
      default:
        throw new IllegalArgumentException();
    }
    int newRed = pixel.getRed() + b;
    int newGreen = pixel.getGreen() + b;
    int newBlue = pixel.getBlue() + b;
    int a = pixel.getAlpha();
    return new ColorPixel(getInt(newRed), getInt(newGreen), getInt(newBlue), a);
  }

  /**
   * This method is used to clip the specified value to the range [0, 255].
   *
   * @param value of the clip
   * @return the clipped value
   */
  private int getInt(int value) {
    if (value <= 0) {
      return 0;
    } else if (value >= 255) {
      return 255;
    }
    return value;
  }

  /**
   * This abstract method is used to calculate the brightness adjustment value for the specified
   * RGB color based on the Value formula.
   *
   * @param r is the red component of the color
   * @param g is the green component of the color
   * @param b is the blue component of the color
   * @return the brightness adjustment value
   */
  abstract protected int getValue(int r, int g, int b);

  /**
   * This abstract method is used to calculate the brightness adjustment intensity for the specified
   * RGB color based on the Intensity formula.
   *
   * @param r is the red component of the color
   * @param g is the green component of the color
   * @param b is the blue component of the color
   * @return the brightness adjustment intensity
   */
  abstract protected int getIntensity(int r, int g, int b);

  /**
   * This abstract method is used to calculate the brightness adjustment luma for the specified
   * RGB color based on the Luma formula.
   *
   * @param r is the red component of the color
   * @param g is the green component of the color
   * @param b is the blue component of the color
   * @return the brightness adjustment luma
   */
  abstract protected int getLuma(int r, int g, int b);

  /**
   * This method follows the factory design pattern and returns the appropriate
   * type of {@code AbstractBrightness}.
   *
   * @param type is the light type of the {@code AbstractBrightness}
   * @return a type of {@code AbstractBrightness}
   */
  abstract protected AbstractBrightness factory(LightType type);
}