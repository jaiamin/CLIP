package model;

import java.io.IOException;
import java.util.ArrayList;

import model.filter.BrightenFilter;
import model.filter.Component;
import model.filter.ComponentFilter;
import model.filter.DarkenFilter;
import model.filter.DifferenceFilter;
import model.filter.IFilter;
import model.filter.LightType;
import model.filter.MultiplyFilter;
import model.filter.NormalFilter;
import model.filter.ScreenFilter;

/**
 * This class that represents a collage model, which is a canvas with multiple layers
 * that can contain images with various filters applied to them.
 */
public class CollageModel implements ICanvas {
  private final int height;
  private final int width;
  private final IImage image;
  private final ArrayList<ILayer> layers;
  private String projectName;

  /**
   * This is the constructor for the {@code CollageModel} class, which creates a new
   * {@code CollageModel} with a default size of 1x1 and an empty layer list.
   */
  public CollageModel() {
    this.height = 1;
    this.width = 1;
    this.image = new ColorImage(height, width, 1, 255);
    this.layers = new ArrayList<>();
  }

  /**
   * This is the constructor for the {@code CollageModel} class, which creates a new
   * {@code CollageModel} with the specified height, width, pixels, and layers.
   *
   * @param height of the canvas
   * @param width  of the canvas
   * @param pixels to initialize the main image with
   * @param layers to initialize the canvas with
   * @throws IllegalArgumentException if height or width are less than 1
   *                                  or if inputs are null
   */
  public CollageModel(int height, int width, IPixel[][] pixels, ArrayList<ILayer> layers)
          throws IllegalArgumentException {
    if (height < 1 || width < 1 || pixels == null || layers == null) {
      throw new IllegalArgumentException("invalid input");
    }
    this.height = height;
    this.width = width;
    this.image = new ColorImage(pixels);
    this.layers = layers;
  }

  /**
   * This is the constructor for the {@code CollageModel} class, which creates a new
   * {@code CollageModel} with the specified height, width, and project name.
   *
   * @param height of the canvas
   * @param width  of the canvas
   * @param name   of the project
   * @throws IllegalArgumentException if height or width are less than 1
   *                                  or if name is null
   */
  public CollageModel(int height, int width, String name) {
    if (height < 1 || width < 1 || name == null) {
      throw new IllegalArgumentException("invalid input");
    }
    this.height = height;
    this.width = width;
    this.image = new ColorImage(height, width, 255, 255);
    this.layers = new ArrayList<>();
    this.projectName = name;
  }

  @Override
  public void addLayerToCanvas(int height, int width, String name) throws IllegalArgumentException {
    for (ILayer l : layers) {
      if (l.getName().equals(name)) {
        throw new IllegalArgumentException("Cannot have same layer name twice.");
      }
    }
    this.layers.add(new ImageLayer(height, width, name));
    this.updatePixels();
  }

  @Override
  public void addImageToLayer(String layerName, IImage image, int xPos, int yPos)
          throws IllegalArgumentException {
    boolean layerFound = false;
    int layerInd = -1;
    for (int i = 0; i < this.layers.size(); i++) {
      if (layers.get(i).getName().equals(layerName)) {
        layers.get(i).addImageToLayer(image, xPos, yPos);
        layerFound = true;
        layerInd = i + 1;
        break;
      }
    }
    if (!layerFound) {
      throw new IllegalArgumentException("Layer '" + layerName + "' does not exist.");
    }
    this.updatePixels();
    if (layerInd != -1) {
      for (int j = layerInd; j < this.layers.size(); j++) {
        if (layers.get(j).getFilter().toString().equals("difference")) {
          layers.get(j).applyFilter(new DifferenceFilter(
                  this.getPrevIm(this.layers.get(j).getName())));
        }
        if (layers.get(j).getFilter().toString().equals("multiply")) {
          layers.get(j).applyFilter(new MultiplyFilter(
                  this.getPrevIm(this.layers.get(j).getName())));
        }
        if (layers.get(j).getFilter().toString().equals("screen")) {
          layers.get(j).applyFilter(new ScreenFilter(
                  this.getPrevIm(this.layers.get(j).getName())));
        }
      }
    }
  }

  @Override
  public void updatePixels() {
    for (ILayer layer : layers) {
      for (int i = 0; i < this.height; i++) {
        for (int j = 0; j < this.width; j++) {
          try {
            IPixel pixel = layer.getImage().getPixel(i, j);
            if (pixel.getAlpha() != 0) {
              this.image.setPixel(i, j, pixel);
              IFilter layerFilter = layer.getFilter();
              if (!layerFilter.toString().equals(pixel.getFilter().toString())) {
                layerFilter.apply(this.image, i, j);
                IPixel newPixel = this.image.getPixel(i, j);
                this.image.setPixel(i, j, newPixel);
              }
            }
          } catch (IndexOutOfBoundsException e) {
            // do nothing
          }
        }
      }
    }
  }

  @Override
  public IImage getImage() {
    return this.image;
  }

  @Override
  public ArrayList<ILayer> getLayers() {
    return this.layers;
  }

  @Override
  public String getProjectName() {
    return this.projectName;
  }

  @Override
  public IImage getPrevIm(String layerName) {
    IImage im = new ColorImage(this.height, this.width, 255, this.image.getMaxValue());
    for (int i = 0; layers.size() > 0 && i < layers.size()
            && !layers.get(i).getName().equals(layerName); i++) {
      for (int j = 0; j < this.height; j++) {
        for (int k = 0; k < this.width; k++) {
          IPixel pixel = layers.get(i).getImage().getPixel(j, k);
          if (pixel.getAlpha() != 0) {
            im.setPixel(j, k, pixel);
            IFilter layerFilter = layers.get(i).getFilter();
            if (!layerFilter.toString().equals(pixel.getFilter().toString())) {
              layerFilter.apply(im, j, k);
              IPixel newPixel = im.getPixel(j, k);
              im.setPixel(j, k, newPixel);
            }
          }
        }
      }
    }
    return im;
  }

  @Override
  public String getProjectStructure() {
    String projectStructure = "";
    if (this.getLayers().size() > 0) {
      projectStructure += ("Current Project Structure:\n");
    }
    for (int i = 0; i < this.getLayers().size(); i++) {
      ILayer layer = this.getLayers().get(i);
      projectStructure += ("Layer #" + (i + 1) + ": " + layer.getName()
              + " (" + layer.getFilter() + ")" + "\n");
      for (int j = 0; j < layer.getImages().size(); j++) {
        String imgName;
        try {
          imgName = layer.getImages().get(j).getFilename();
        } catch (RuntimeException e) {
          imgName = "Unknown";
        }
        projectStructure += (" - Image #" + (j + 1) + ": " + imgName + "\n");
      }
    }
    return projectStructure;
  }

  @Override
  public IFilter getFilterFromString(String filterStr, String layerName) throws RuntimeException {
    switch (filterStr) {
      case "normal":
        return new NormalFilter();
      case "red-component":
        return new ComponentFilter(Component.RED);
      case "green-component":
        return new ComponentFilter(Component.GREEN);
      case "blue-component":
        return new ComponentFilter(Component.BLUE);
      case "brighten-value":
        return new BrightenFilter(LightType.VALUE);
      case "brighten-intensity":
        return new BrightenFilter(LightType.INTENSITY);
      case "brighten-luma":
        return new BrightenFilter(LightType.LUMA);
      case "darken-value":
        return new DarkenFilter(LightType.VALUE);
      case "darken-intensity":
        return new DarkenFilter(LightType.INTENSITY);
      case "darken-luma":
        return new DarkenFilter(LightType.LUMA);
      case "difference":
        return new DifferenceFilter(this.getPrevIm(layerName));
      case "multiply":
        return new MultiplyFilter(this.getPrevIm(layerName));
      case "screen":
        return new ScreenFilter(this.getPrevIm(layerName));
      default:
        throw new RuntimeException("Error: " + filterStr + " is not a valid filter option.");
    }
  }

  @Override
  public int getARGB(int x, int y, int maxProjectValue, double factor) {
    int argb = 0;
    IPixel pixel = this.getImage().getPixel(y, x);
    int alpha = (int) Math.round((double) pixel.getAlpha() / factor);
    argb |= ((int) (((double) alpha / maxProjectValue) * 255) << 24);

    int red = (int) Math.round((double) pixel.getRed() / factor);
    argb |= ((int) (((double) red / maxProjectValue) * 255) << 16);

    int green = (int) Math.round((double) pixel.getGreen() / factor);
    argb |= ((int) (((double) green / maxProjectValue) * 255) << 8);

    int blue = (int) Math.round((double) pixel.getBlue() / factor);
    argb |= (int) (((double) blue / maxProjectValue) * 255);
    return argb;
  }

  @Override
  public ICanvas setModel() {
    return new CollageModel();
  }

  @Override
  public ICanvas setModel(int height, int width, String name) {
    return new CollageModel(height, width, name);
  }

  @Override
  public ICanvas setModel(int height, int width, IPixel[][] pixels, ArrayList<ILayer> layers) {
    return new CollageModel(height, width, pixels, layers);
  }

  @Override
  public void addFilter(IFilter filterOption, String layerName) {
    int layerInd = -1;
    for (int i = 0; i < this.getLayers().size(); i++) {
      if (this.getLayers().get(i).getName().equals(layerName)) {
        this.getLayers().get(i).applyFilter(filterOption);
        layerInd = i + 1;
        break;
      }
    }
    this.updatePixels();
    if (layerInd != -1) {
      for (int j = layerInd; j < this.getLayers().size(); j++) {
        ILayer layer = this.getLayers().get(j);
        String filter = layer.getFilter().toString();
        if (filter.equals("difference")) {
          layer.applyFilter(new DifferenceFilter(this.getPrevIm(layer.getName())));
        }
        if (filter.equals("multiply")) {
          layer.applyFilter(new MultiplyFilter(this.getPrevIm(layer.getName())));
        }
        if (filter.equals("screen")) {
          layer.applyFilter(new ScreenFilter(this.getPrevIm(layer.getName())));
        }
      }
    }
    else {
      throw new RuntimeException("Error: Layer '" + layerName + "' does not exist.");
    }
  }

  @Override
  public IImage createImage(String imagePath) throws IOException {
    return ImageUtil.readImage(imagePath);
  }

  @Override
  public IImage createImage(IPixel[][] pixels, int maxVal) {
    return new ColorImage(pixels, maxVal);
  }

  @Override
  public IPixel createPixel(int red, int green, int blue, int alpha, IFilter filter) {
    return new ColorPixel(red, green, blue, alpha, filter);
  }
}