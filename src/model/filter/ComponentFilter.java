package model.filter;

import model.ColorPixel;
import model.IImage;
import model.IPixel;

/**
 * This is a class that implements the {@code IFilter} interface. It provides an
 * implementation for the component filters that adjust the color component of
 * each pixel in an image.
 */
public class ComponentFilter implements IFilter {
  private final Component component;

  /**
   * This is the constructor of the {@code ComponentFilter} class, which creates a
   * new ComponentFilter object with the specified component type.
   *
   * @param component is the component type to use for the component filter
   * @throws IllegalArgumentException if component is null
   */
  public ComponentFilter(Component component) {
    if (component == null) {
      throw new IllegalArgumentException("invalid input");
    }
    else {
      this.component = component;
    }
  }

  @Override
  public void apply(IImage image, int row, int col) {
    IPixel pixel = image.getPixel(row, col);
    if (pixel.getAlpha() == 0) {
      return;
    }

    IPixel newPixel;
    switch (component) {
      case RED:
        newPixel = new ColorPixel(pixel.getRed(), 0, 0, 255);
        break;
      case GREEN:
        newPixel = new ColorPixel(0, pixel.getGreen(), 0, 255);
        break;
      case BLUE:
        newPixel = new ColorPixel(0, 0, pixel.getBlue(), 255);
        break;
      default:
        throw new IllegalArgumentException();
    }

    newPixel.setFilter(new ComponentFilter(component));
    image.setPixel(row, col, newPixel);
  }

  /**
   * This method overrides the toString() method and returns the String representation
   * of a component type for a component filter.
   *
   * @return the String representation of a component type for a component filter
   */
  @Override
  public String toString() {
    switch (this.component) {
      case RED:
        return "red-component";
      case GREEN:
        return "green-component";
      case BLUE:
        return "blue-component";
      default:
        throw new IllegalArgumentException();
    }
  }
}