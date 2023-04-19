package model;

import java.util.ArrayList;
import java.util.List;

import model.filter.IFilter;
import model.filter.NormalFilter;

/**
 * This class represents the implementation of the {@code ILayer} interface that represents
 * an image layer in an image editor.
 */
public class ImageLayer implements ILayer {
  private final int height;
  private final int width;
  private IImage image;
  private List<IImage> images;
  private final String name;
  private IFilter filter;
  private IPixel[][] previousPixels;

  /**
   * This is the constructor for the {@code ImageLayer} class, which constructs a new
   * {@code ImageLayer}
   * object with the specified height, width, and layer name.
   * Note: This constructor initializes the previousPixels which represents the original
   * pixels of a layer (originally set as transparent white pixels).
   *
   * @param height of the layer/canvas, in pixels
   * @param width  of the layer/canvas, in pixels
   * @param name   of the image layer
   * @throws IllegalArgumentException if height or width are less than 1
   *        or if name is null
   */
  public ImageLayer(int height, int width, String name) throws IllegalArgumentException {
    if (height < 1 || width < 1 || name == null) {
      throw new IllegalArgumentException("invalid input");
    } else {
      this.height = height;
      this.width = width;
      this.image = new ColorImage(height, width, 0, 255);
      this.images = new ArrayList<>();
      this.name = name;
      this.filter = new NormalFilter();
      this.previousPixels = new ColorPixel[height][width];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          this.previousPixels[i][j] = new ColorPixel(255, 255, 255, 0);
        }
      }
    }
  }

  @Override
  public IPixel[][] getPreviousPixels() {
    return this.previousPixels;
  }

  @Override
  public IImage getImage() {
    return this.image;
  }

  @Override
  public List<IImage> getImages() {
    return this.images;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public IFilter getFilter() {
    return this.filter;
  }

  @Override
  public void setFilter(IFilter other) {
    this.filter = other;
  }

  @Override
  public void applyFilter(IFilter filter) {
    if (this.filter.toString().equals("normal")) {
      for (int i = 0; i < this.height; i++) {
        for (int j = 0; j < this.width; j++) {
          previousPixels[i][j] = this.image.getPixel(i, j);
        }
      }
    } else {
      revertToOriginal();
    }

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        filter.apply(this.image, i, j);
      }
    }
    this.filter = filter;
  }

  @Override
  public void revertToOriginal() {
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        this.image.setPixel(i, j, previousPixels[i][j]);
      }
    }
  }

  @Override
  public void addImageToLayer(IImage image, int x, int y) throws IllegalArgumentException {
    if (y < 0 || y >= this.height || x < 0 || x >= this.width) {
      throw new IllegalArgumentException("Image position must be in canvas size grid.");
    }
    this.images.add(image);
    this.updatePixels(image, x, y);
  }

  /**
   * This method is used to update the pixels of the layer based on the current set of images
   * and previous pixels of the layer.
   * Note: The layer crops parts of the image that are not within the gird of the canvas.
   *
   * @param image to be applied to the layer
   * @param x or column position of the image to be applied on the layer
   * @param y or row position of the image to be applied on the layer
   */
  private void updatePixels(IImage image, int x, int y) {
    for (int i = x, ii = 0; i < this.height; i++, ii++) {
      for (int j = y, jj = 0; j < this.width; j++, jj++) {
        try {
          this.image.setPixel(i, j, image.getPixel(ii, jj));
          previousPixels[i][j] = this.image.getPixel(i, j);
        } catch (IndexOutOfBoundsException e) {
          // do nothing (crops parts of the image that are outside of canvas grid)
        }
      }
    }
  }
}
