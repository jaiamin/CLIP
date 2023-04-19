package controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.ColorPixel;
import model.ICanvas;
import model.IImage;
import model.ILayer;
import model.IPixel;
import model.filter.IFilter;
import view.IView;

/**
 * Implementation of {@code CollageController} interface,
 * which controls the interaction between the collage model and the TextViewUI (TUI) view.
 */
public class CollageControllerImpl implements CollageController, Features {
  private ICanvas model;
  private final IView view;
  private final Scanner object;
  private int height;
  private int width;
  private boolean projectCreated;
  private boolean endGame;
  private boolean ignoreOtherCommands;
  private int projectMaxValue;

  /**
   * This is the constructor for the {@code CollageControllerImpl} class. If any of these arguments
   * are null, an IllegalArgumentException is thrown.
   *
   * @param model  an {@code ICanvas} object representing the image canvas
   * @param view   an {@code IView} object representing the user interface
   * @param object a Readable object representing the input source of the program
   * @throws IllegalArgumentException if either the model, view, or obejct provided are null
   */
  public CollageControllerImpl(ICanvas model, IView view, Readable object)
          throws IllegalArgumentException {
    if (model == null || view == null || object == null) {
      throw new IllegalArgumentException("CollageControllerImpl inputs must not be null.");
    }
    this.model = model;
    this.view = view;
    this.view.setController(this);
    this.object = new Scanner(object);
    this.projectCreated = false;
    this.endGame = false;
    this.ignoreOtherCommands = false;
  }

  @Override
  public void start() throws IOException {
    view.renderMessage("Welcome to Image Manipulator and Editor!\n");
    view.displayCommands();
    view.renderMessage("\nStart by creating or loading a collage project below.\n");

    while (!this.endGame) {
      String command = this.object.next();
      boolean invalidClaim = false;

      switch (command) {
        // quits the program
        case "quit":
          this.quitCommand();
          return;

        // view available commands
        case "help":
          this.helpMenuCommand();
          break;

        // view current project structure
        case "project-structure":
          if (!this.projectCreated) {
            view.renderMessage("Error: Project has not been created/loaded.");
            break;
          }
          if (this.model.getLayers().size() == 0) {
            view.renderMessage("Current project is empty.");
            break;
          }
          break;

        // loads collage project from filesystem and updates model
        case "load-project":
          if (this.projectCreated) {
            view.renderMessage("Error: Project has already been created/loaded.");
            ignoreOtherCommands = true;
            break;
          }
          String path = object.next();
          String[] words = path.split("\\.");
          if (!Objects.equals(words[words.length - 1], "collage")) {
            view.renderMessage("Error: Collage projects must be saved with '.collage' extension.");
            ignoreOtherCommands = true;
          }
          try {
            this.loadProjectCommand(path);
          } catch (IOException e) {
            view.renderMessage("Error: Unable to read: " + path);
            ignoreOtherCommands = true;
            break;
          }
          break;

        // creates a new project canvas with the given name, height, width, and max value
        case "new-project":
          if (this.projectCreated) {
            view.renderMessage("Error: Project has already been created/loaded.");
            ignoreOtherCommands = true;
            break;
          }
          String projectName = object.next();
          try {
            this.height = getValidSize();
          } catch (IllegalArgumentException e) {
            view.renderMessage(e.getMessage());
            ignoreOtherCommands = true;
            break;
          }
          try {
            this.width = getValidSize();
          } catch (IllegalArgumentException e) {
            view.renderMessage(e.getMessage());
            ignoreOtherCommands = true;
            break;
          }
          try {
            this.projectMaxValue = Integer.parseInt(object.next());
          } catch (IllegalArgumentException e) {
            view.renderMessage("Error: Project max value must be an integer in range (0, 255].");
            this.ignoreOtherCommands = true;
            break;
          }
          if (this.projectMaxValue > 255 || this.projectMaxValue < 1) {
            view.renderMessage("Error: Cannot create project due to invalid project max value");
            this.ignoreOtherCommands = true;
            break;
          }
          this.newProjectCommand(projectName, this.height, this.width, this.projectMaxValue);
          break;

        // saves current project as [canvas-name].collage
        case "save-project":
          if (!this.projectCreated) {
            view.renderMessage("Error: Project has not been created or loaded yet.");
            ignoreOtherCommands = true;
            break;
          }
          if (this.model.getLayers().size() == 0) {
            view.renderMessage("Error: Project must have at least one layer before being saved.");
            ignoreOtherCommands = true;
            break;
          }
          String savePath = this.model.getProjectName() + ".collage";
          this.saveProjectCommand(savePath);
          break;

        // adds a layer to the project canvas with a unique name
        case "add-layer":
          if (!this.projectCreated) {
            view.renderMessage("Error: Project has not been created or loaded yet.");
            ignoreOtherCommands = true;
            break;
          }
          String layerAddName = object.next();
          this.addLayerCommand(layerAddName);
          break;

        // adds an image to a specified layer of the project canvas with an offset of (x, y)
        case "add-image-to-layer":
          if (!this.projectCreated) {
            view.renderMessage("Error: Project has not been created or loaded yet.");
            ignoreOtherCommands = true;
            break;
          }
          String layerName = object.next();
          String imagePath = object.next();
          try {
            int xPos = getValidOffset(this.width);
            int yPos = getValidOffset(this.height);
            this.addImageToLayerCommand(layerName, imagePath, xPos, yPos);
          } catch (FileNotFoundException | IllegalArgumentException e) {
            view.renderMessage(e.getMessage());
            ignoreOtherCommands = true;
            break;
          } catch (IOException e) {
            view.renderMessage("Error: Failed to read the image from the given path.");
            ignoreOtherCommands = true;
            break;
          } catch (ArrayIndexOutOfBoundsException e) {
            ignoreOtherCommands = true;
            break;
          }
          break;

        // sets filter of a given layer with the given filter option/type
        case "set-filter":
          if (!this.projectCreated) {
            view.renderMessage("Error: Project has not been created or loaded yet.");
            ignoreOtherCommands = true;
            break;
          }
          String layerSetName = object.next();
          String filterOption = object.next();
          this.setFilterCommand(layerSetName, filterOption);
          break;

        case "save-image":
          if (!this.projectCreated) {
            view.renderMessage("Error: Project has not been created or loaded yet.");
            ignoreOtherCommands = true;
            break;
          }
          String saveImagePath = object.next();
          this.saveImageCommand(saveImagePath);
          break;

        default:
          if (!ignoreOtherCommands) {
            view.renderMessage("Error: Invalid command. Try again.");
          }
          invalidClaim = true;
          break;
      }

      if (!invalidClaim) {
        this.model.updatePixels();
        view.displayProjectStructure();
      }
    }
  }

  private int getValidSize() {
    try {
      int input = Integer.parseInt(this.object.next());
      if (input < 1) {
        throw new IllegalArgumentException("Error: Project size must be greater than " +
                                           "or equal to 1.");
      }
      return input;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Error: Size must be an integer.");
    }
  }

  private int getValidOffset(int size) {
    try {
      int input = Integer.parseInt(this.object.next());
      if (input < 0 || input >= size) {
        throw new IllegalArgumentException("Error: Offset position must be within range of " +
                                           "collage grid.");
      }
      return input;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Error: (x, y) offset must be integers.");
    }
  }

  @Override
  public void setFeaturesController(IView view) {
    // do nothing
  }

  @Override
  public void newProjectCommand(String name, int height, int width, int maxValue)
          throws IOException {
    this.model = this.model.setModel(height, width, name);
    view.setFrame(height, width, name, maxValue, false);
    this.projectCreated = true;
    this.height = height;
    this.width = width;
    this.projectMaxValue = maxValue;
    view.renderMessage("'" + this.model.getProjectName() + "' collage project created " +
                       "successfully!");
  }

  @Override
  public void addLayerCommand(String name) throws IOException {
    try {
      this.model.addLayerToCanvas(this.height, this.width, name);
      view.renderMessage("'" + name + "' layer added.");
    } catch (IllegalArgumentException e) {
      view.renderMessage("Error: Layer '" + name + "' already exists.");
      ignoreOtherCommands = true;
    }
  }

  @Override
  public void addImageToLayerCommand(String layerName, String imagePath, int row, int col)
          throws IOException {
    IImage image = this.model.createImage(imagePath);
    image.updateWithMaxValue();
    this.model.addImageToLayer(layerName, image, row, col);
    view.renderMessage("Image added successfully to layer '" + layerName
                       + "' at position (" + row + ", " + col + ").");
  }

  @Override
  public void setFilterCommand(String layerName, String filterName) throws IOException {
    if (!projectCreated) {
      view.renderMessage("Create a new or load a collage project first.");
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
    try {
      this.model.addFilter(filterOption, layerName);
    } catch (RuntimeException e) {
      view.renderMessage(e.getMessage());
    }
  }

  @Override
  public void saveImageCommand(String filename) throws IOException {
    String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    ArrayList<String> imgFormats = new ArrayList<>(Arrays.asList("ppm", "png", "jpg", "jpeg"));
    if (!imgFormats.contains(ext)) {
      view.renderMessage("Error: Collage images must be saved as PPM, PNG, and/or JPG/JPEG.");
      ignoreOtherCommands = true;
    } else {
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
          BufferedImage img = new BufferedImage(this.width, this.height,
                  BufferedImage.TYPE_INT_RGB);
          this.model.updatePixels();
          double factor = 255.0 / this.projectMaxValue;
          for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
              int argb = 0;
              IPixel pixel = this.model.getImage().getPixel(y, x);
              int alpha = (int) Math.round((double) pixel.getAlpha() / factor);
              argb |= ((int) (((double) alpha / this.projectMaxValue) * 255) << 24);

              int red = (int) Math.round((double) pixel.getRed() / factor);
              argb |= ((int) (((double) red / this.projectMaxValue) * 255) << 16);

              int green = (int) Math.round((double) pixel.getGreen() / factor);
              argb |= ((int) (((double) green / this.projectMaxValue) * 255) << 8);

              int blue = (int) Math.round((double) pixel.getBlue() / factor);
              argb |= (int) (((double) blue / this.projectMaxValue) * 255);

              img.setRGB(x, y, argb);
            }
          }
          File file = new File(filename);
          ImageIO.write(img, filename.substring(filename.lastIndexOf(".") + 1), file);
        }
      } catch (IOException e) {
        view.renderGUIMessage("Unable to save image.", "Command Error", 0);
      }
    }
  }

  @Override
  public void saveProjectCommand(String filename) throws IOException {
    try {
      FileWriter writer = new FileWriter(filename);
      writer.flush();
      writer.write(this.model.getProjectName() + "\n" + this.width + " " +
                   this.height + "\n" + this.projectMaxValue + "\n");
      double factor = 255.0 / this.projectMaxValue;
      for (ILayer layer : this.model.getLayers()) {
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
      view.renderMessage("Error: Unable to write to: " + filename);
      ignoreOtherCommands = true;
    }
  }

  @Override
  public void loadProjectCommand(String filename) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String name1 = reader.readLine();
    String[] size = reader.readLine().split("\\s+");
    int width = Integer.parseInt(size[0]);
    int height = Integer.parseInt(size[1]);
    this.projectMaxValue = Integer.parseInt(reader.readLine());
    if (width < 0 || height < 0) {
      view.renderMessage("Error: Cannot load project due to invalid size");
      this.ignoreOtherCommands = true;
    } else if (this.projectMaxValue > 255 || this.projectMaxValue < 1) {
      view.renderMessage("Error: Cannot load project due to invalid project max value");
      this.ignoreOtherCommands = true;
    } else {
      this.model = this.model.setModel(height, width, name1);
      IPixel[][] pixels1 = new IPixel[height][width];
      ArrayList<ILayer> layers = new ArrayList<>();
      String line = reader.readLine();
      while (line != null) {
        String[] names = line.split("\\s+");
        IFilter filter;
        try {
          filter = this.model.getFilterFromString(names[1], names[0]);
        } catch (RuntimeException e) {
          view.renderMessage(e.getMessage());
          filter = this.getModel().getFilterFromString("normal", null);
        }
        line = reader.readLine();
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            String[] colors = line.split("\\s+");
            int red = Integer.parseInt(colors[0]);
            int green = Integer.parseInt(colors[1]);
            int blue = Integer.parseInt(colors[2]);
            int alpha = Integer.parseInt(colors[3]);
            IPixel pixel = this.model.createPixel(red, green, blue, alpha, filter);
            pixels1[i][j] = pixel;
            line = reader.readLine();
          }
        }
        this.model.addLayerToCanvas(height, width, names[0]);
        ILayer layer = this.model.getLayers().get(this.model.getLayers().size() - 1);
        IImage newImage = this.model.createImage(pixels1, this.projectMaxValue);
        newImage.updateWithMaxValue();
        layer.addImageToLayer(newImage, 0, 0);
        layers.add(layer);
        layer.applyFilter(filter);
      }
      reader.close();

      this.height = height;
      this.width = width;
      this.model = this.model.setModel(height, width, pixels1, layers);
      view.setFrame(height, width, name1, this.projectMaxValue, false);
      this.projectCreated = true;
      view.renderMessage("'" + name1 + "' collage project loaded successfully!");
    }
  }

  @Override
  public void quitCommand() {
    this.endGame = true;
    if (this.projectCreated) {
      this.view.exit();
    }
  }

  @Override
  public void helpMenuCommand() throws IOException {
    view.displayCommands();
  }

  @Override
  public ICanvas getModel() {
    return this.model;
  }
}