package controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import model.ICanvas;
import model.IImage;
import model.ILayer;
import model.IPixel;


import model.filter.IFilter;
import view.IView;

/**
 * Implementation of {@code CollageController} interface,
 * which controls the interaction between the collage model and the GUI view.
 */
public class CollageControllerGUIImpl implements Features {
  private ICanvas model;
  private IView view;
  private int height;
  private int width;
  private int projectMaxValue;
  private boolean projectStarted;

  /**
   * This is the constructor for the {@code CollageControllerGUIImpl} class.
   * If the model is null, an IllegalArgumentException is thrown.
   *
   * @param model an {@code ICanvas} object representing the image canvas
   * @throws IllegalArgumentException if the model provided is null
   */
  public CollageControllerGUIImpl(ICanvas model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("CollageControllerGUIImpl inputs must not be null.");
    }
    this.model = model;
    this.view = null;
    this.projectStarted = false;
  }

  @Override
  public void setFeaturesController(IView view) {
    this.view = view;
  }

  @Override
  public void newProjectCommand(String name, int height, int width, int maxValue) {
    if (projectStarted) {
      view.renderGUIMessage("Collage project has already been created/loaded", "Command Error", 0);
      return;
    }

    this.height = height;
    this.width = width;
    this.projectMaxValue = maxValue;
    this.model = this.model.setModel(height, width, name);
    view.setFrame(height, width, name, maxValue, true);

    view.setTextFieldText(view.getNewProjectNameTF(), "");
    view.setTextFieldText(view.getNewProjectHeightTF(), "");
    view.setTextFieldText(view.getNewProjectWidthTF(), "");
    view.setTextFieldText(view.getNewProjectMaxValueTF(), "");
    projectStarted = true;
  }

  @Override
  public void addLayerCommand(String name) {
    if (!projectStarted) {
      view.renderGUIMessage("Create a new or load a collage project first.", "Command Error", 0);
      return;
    }
    for (ILayer layer : this.getModel().getLayers()) {
      if (layer.getName().equals(name)) {
        view.renderGUIMessage("Layer '" + name + "' already exists.", "Input Error", 0);
        return;
      }
    }
    this.model.addLayerToCanvas(height, width, name);
    view.setTextFieldText(view.getAddLayerNameTF(), "");
    view.addComboBoxItem(view.getAddImageToLayerNameCB(), name);
    view.addComboBoxItem(view.getSetFilterLayerNameCB(), name);
  }

  @Override
  public void addImageToLayerCommand(String layerName, String imagePath, int row, int col) {
    if (!projectStarted) {
      view.renderGUIMessage("Create a new or load a collage project first.", "Command Error", 0);
      return;
    }
    if (this.model.getLayers().size() == 0) {
      view.renderGUIMessage("No layers exist on current collage project.", "Command Error", 0);
      return;
    }
    IImage newImage;
    try {
      newImage = this.model.createImage(imagePath);
    } catch (IOException e) {
      view.renderGUIMessage("Failed to transmit.", "Command Error", 0);
      return;
    } catch (IllegalArgumentException e) {
      view.renderGUIMessage("Invalid image format. Only PNG, JPG/JPEG, and PPM formats " +
                            "supported", "Command Error", 0);
      return;
    }
    newImage.updateWithMaxValue();
    this.model.addImageToLayer(layerName, newImage, row, col);
    view.setTextFieldText(view.getAddImageToLayerRowTF(), "");
    view.setTextFieldText(view.getAddImageToLayerColTF(), "");
  }

  @Override
  public void setFilterCommand(String layerName, String filterName) throws IOException {
    if (!projectStarted) {
      view.renderGUIMessage("Create a new or load a collage project first.", "Command Error", 0);
      return;
    }
    IFilter filterOption;
    try {
      filterOption = this.getModel().getFilterFromString(Objects.requireNonNull(filterName),
              layerName);
    } catch (RuntimeException ex) {
      view.renderMessage(ex.getMessage());
      filterOption = getModel().getFilterFromString("normal", null);
    }
    this.model.addFilter(filterOption, layerName);
  }

  @Override
  public void saveImageCommand(String filename) {
    String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    try {
      if (ext.equals("ppm")) {
        FileWriter writer;
        writer = new FileWriter(filename);
        writer.write("P3\n" + width + " " + height + "\n" + projectMaxValue + "\n");
        double factor = 255.0 / projectMaxValue;
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            IPixel pixel = this.model.getImage().getPixel(i, j);
            int red = (int) Math.round((double) pixel.getRed() / factor);
            int green = (int) Math.round((double) pixel.getGreen() / factor);
            int blue = (int) Math.round((double) pixel.getBlue() / factor);
            writer.write(red + " " + green + " " + blue + " ");
          }
          writer.write("\n");
        }
        writer.close();
      } else {
        BufferedImage img = view.getBuffImage();
        File file = new File(filename);
        ImageIO.write(img, filename.substring(filename.lastIndexOf(".") + 1), file);
      }
    } catch (IOException e) {
      view.renderGUIMessage("Unable to save image.", "Command Error", 0);
    }
  }

  @Override
  public void saveProjectCommand(String filename) throws IOException {
    if (!projectStarted) {
      view.renderGUIMessage("Create a new or load a collage project first.", "Command Error", 0);
      return;
    }
    try {
      FileWriter writer = new FileWriter(filename);
      writer.flush();
      writer.write(this.getModel().getProjectName() + "\n" + this.width + " " + this.height + "\n"
                   + this.projectMaxValue + "\n");
      double factor = 255.0 / this.projectMaxValue;
      for (ILayer layer : this.getModel().getLayers()) {
        writer.write(layer.getName() + " " + layer.getFilter().toString() + "\n");
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            IPixel pixel = layer.getPreviousPixels()[i][j];
            int red = (int) Math.round((double) pixel.getRed() / factor);
            int green = (int) Math.round((double) pixel.getGreen() / factor);
            int blue = (int) Math.round((double) pixel.getBlue() / factor);
            int alpha = (int) Math.round((double) pixel.getAlpha() / factor);
            writer.write(red + " " + green + " " + blue + " " + alpha + "\n");
          }
        }
      }
      writer.close();
    } catch (IOException e) {
      throw new IOException("Error: Unable to write to: " + filename);
    }
  }

  @Override
  public void loadProjectCommand(String filename) throws IOException {
    if (projectStarted) {
      view.renderGUIMessage("Collage project has already been created/loaded", "Command Error", 0);
      return;
    }
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String name1 = reader.readLine();
    String[] size = reader.readLine().split("\\s+");
    int width = Integer.parseInt(size[0]);
    int height = Integer.parseInt(size[1]);
    this.model = this.model.setModel(height, width, name1);
    this.projectMaxValue = Integer.parseInt(reader.readLine());
    if (width < 0 || height < 0) {
      view.renderGUIMessage("Cannot load project due to invalid size", "Load Error", 0);
    } else if (this.projectMaxValue > 255 || this.projectMaxValue < 1) {
      view.renderGUIMessage("Cannot load project due to invalid project max value",
              "Load Error", 0);
    } else {
      view.setFrame(height, width, name1, this.projectMaxValue, true);
      this.height = height;
      this.width = width;
      IPixel[][] pixels1 = new IPixel[height][width];
      String line = reader.readLine();
      while (line != null) {
        String[] names = line.split("\\s+");
        IFilter filter;
        try {
          filter = this.getModel().getFilterFromString(names[1], names[0]);
        } catch (RuntimeException e) {
          view.renderMessage(e.getMessage());
          filter = this.getModel().getFilterFromString("normal", null);
        }
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            line = reader.readLine();
            String[] colors = line.split("\\s+");
            int red = Integer.parseInt(colors[0]);
            int green = Integer.parseInt(colors[1]);
            int blue = Integer.parseInt(colors[2]);
            int alpha = Integer.parseInt(colors[3]);
            IPixel pixel = this.model.createPixel(red, green, blue, alpha, filter);
            pixels1[i][j] = pixel;
          }
        }
        this.model.addLayerToCanvas(height, width, names[0]);
        ILayer layer = this.model.getLayers().get(this.model.getLayers().size() - 1);
        IImage newImage = this.model.createImage(pixels1, this.projectMaxValue);
        newImage.updateWithMaxValue();
        layer.addImageToLayer(newImage, 0, 0);
        layer.applyFilter(filter);
        this.model.updatePixels();
        view.addComboBoxItem(view.getAddImageToLayerNameCB(), layer.getName());
        view.addComboBoxItem(view.getSetFilterLayerNameCB(), layer.getName());
        line = reader.readLine();
      }
      reader.close();
      view.updateView();
      projectStarted = true;
    }
  }

  @Override
  public void quitCommand() {
    view.exit();
  }

  @Override
  public void helpMenuCommand() {
    view.renderGUIMessage(view.helpMenuCommands(), "Help Menu", 1);
  }

  @Override
  public ICanvas getModel() {
    return this.model;
  }
}