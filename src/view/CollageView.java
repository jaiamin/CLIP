package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;
import model.IPixel;

/**
 * This class implements the {@code IView} interface, extends {@code JFrame}, and represents
 * a view for a collage project. It is responsible for displaying the text view UI and GUI for
 * the collage project and updates the view when necessary.
 */
public class CollageView extends JFrame implements IView, ActionListener {
  private Features controller;
  private final Appendable object;
  private BufferedImage image;
  private int height;
  private int width;
  private int maxProjectValue;

  // main panels
  private JPanel panel;
  private JPanel imagePanel;

  private JButton loadProjectButton;

  // new project input fields
  private JTextField newProjectNameTF;
  private JTextField newProjectHeightTF;
  private JTextField newProjectWidthTF;
  private JTextField newProjectMaxValueTF;
  private JButton newProjectConfirm;

  // add layer input fields
  private JTextField addLayerNameTF;
  private JButton addLayerConfirm;

  // add image to layer input fields
  private JComboBox<String> addImageToLayerNameCB;
  private JTextField addImageToLayerRowTF;
  private JTextField addImageToLayerColTF;
  private JButton addImageToLayerConfirm;
  private File addImageToLayerImagePath;

  // set filter input fields
  private JComboBox<String> setFilterLayerNameCB;
  private JComboBox<String> setFilterNameCB;
  private JButton setFilterConfirm;

  // project structure label
  private JTextArea projectStructure;

  /**
   * This is the constructor for the {@code CollageView} class, which constructs a new
   * {@code CollageView} object for the {@code CollageControllerImpl} controller.
   *
   * @param object is the output stream for displaying information
   * @throws IllegalArgumentException if parameters are null
   */
  public CollageView(Appendable object) {
    if (object == null) {
      throw new IllegalArgumentException("Invalid arguments.");
    }
    this.object = object;
    this.image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
  }

  /**
   * This is the constructor for the {@code CollageView} class, which constructs a new
   * {@code CollageView} object for the {@code CollageControllerGUIImpl} controller.
   *
   * @param controller is the asynchronous controller
   * @throws IllegalArgumentException if parameters are null
   */
  public CollageView(Features controller) throws IllegalArgumentException {
    if (controller == null) {
      throw new IllegalArgumentException("Invalid arguments.");
    }
    this.controller = controller;
    this.object = null;
    this.image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

    // set window sizing/restrictions
    int guiHeight = 1000;
    int guiWidth = 1500;
    setSize(guiWidth, guiHeight);
    setResizable(false);
    setTitle("Collage Creator");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // display collage project image
    this.panel = new JPanel();
    this.panel.setPreferredSize(new Dimension((guiWidth / 3) * 2, (guiHeight / 4) * 3));
    this.panel.setLayout(new BorderLayout());
    this.imagePanel = new JPanel();
    JScrollPane scrollPane1 = new JScrollPane(this.panel);
    scrollPane1.setPreferredSize(new Dimension((guiWidth / 3) * 2, (guiHeight / 3) * 2));
    scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    // display button commands
    JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
    buttonsPanel.setPreferredSize(new Dimension(guiWidth, guiHeight / 4));

    // create left button panel with action command buttons
    JPanel leftButtonsPanel = new JPanel(new GridLayout(6, 1));
    leftButtonsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    JLabel leftButtonsLabel = new JLabel("Actions");
    leftButtonsLabel.setFont(new Font("Apple", Font.BOLD, 16));
    JButton helpMenuButton = new JButton("HELP MENU");
    helpMenuButton.addActionListener(e -> {
      try {
        controller.helpMenuCommand();
      } catch (IOException ex) {
        renderGUIMessage("Failed to transmit help menu to the view.", "I/O Error", 0);
      }
    });
    JButton quitButton = new JButton("QUIT");
    quitButton.addActionListener(e -> controller.quitCommand());
    // action commands
    JButton saveImageButton = new JButton("SAVE IMAGE");
    saveImageButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "ppm",
                "png", "jpg", "jpeg");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setMultiSelectionEnabled(false);
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
          String fileToSave = fileChooser.getSelectedFile().getPath();
          try {
            controller.saveImageCommand(fileToSave);
          } catch (IOException ex) {
            renderGUIMessage("Unable to write to: " + fileToSave, "I/O Error", 0);
          }
        }
      }
    });
    JButton saveProjectButton = new JButton("SAVE PROJECT");
    saveProjectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter =
                new FileNameExtensionFilter("Collage files (*.collage)", "collage");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setMultiSelectionEnabled(false);
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
          String fileToSave = fileChooser.getSelectedFile().getPath();
          try {
            controller.saveProjectCommand(fileToSave);
          } catch (IOException ex) {
            renderGUIMessage("Unable to write to: " + fileToSave, "I/O Error", 0);
          }
        }
      }
    });
    this.loadProjectButton = new JButton("LOAD PROJECT");
    this.loadProjectButton.addActionListener(this);
    leftButtonsPanel.add(leftButtonsLabel);
    leftButtonsPanel.add(helpMenuButton);
    leftButtonsPanel.add(quitButton);
    leftButtonsPanel.add(saveImageButton);
    leftButtonsPanel.add(saveProjectButton);
    leftButtonsPanel.add(loadProjectButton);

    // create right buttons panel
    JPanel rightButtonsPanel = new JPanel(new GridLayout(5, 1));
    rightButtonsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    JLabel rightButtonsLabel = new JLabel("Commands");
    rightButtonsLabel.setFont(new Font("Apple", Font.BOLD, 16));

    // new project command inputs
    JPanel newProjectPanel = new JPanel(new GridLayout(1, 6));
    JLabel newProjectLabel = new JLabel("New Project");
    this.newProjectNameTF = new PlaceholderTextField("Name");
    this.newProjectHeightTF = new PlaceholderTextField("Height");
    this.newProjectWidthTF = new PlaceholderTextField("Width");
    this.newProjectMaxValueTF = new PlaceholderTextField("RGB Max Value");
    this.newProjectConfirm = new JButton("Confirm");
    newProjectConfirm.addActionListener(this);
    newProjectPanel.add(newProjectLabel);
    newProjectPanel.add(newProjectNameTF);
    newProjectPanel.add(newProjectHeightTF);
    newProjectPanel.add(newProjectWidthTF);
    newProjectPanel.add(newProjectMaxValueTF);
    newProjectPanel.add(newProjectConfirm);

    // add layer command inputs
    JPanel addLayerPanel = new JPanel(new GridLayout(1, 3));
    JLabel addLayerLabel = new JLabel("Add Layer");
    this.addLayerNameTF = new PlaceholderTextField("Name");
    this.addLayerConfirm = new JButton("Confirm");
    this.addLayerConfirm.addActionListener(this);
    addLayerPanel.add(addLayerLabel);
    addLayerPanel.add(this.addLayerNameTF);
    addLayerPanel.add(this.addLayerConfirm);

    // add image to layer command inputs
    JPanel addImageToLayerPanel = new JPanel(new GridLayout(1, 6));
    JLabel addImageToLayerLabel = new JLabel("Add Image");
    this.addImageToLayerNameCB = new JComboBox<>();
    this.addImageToLayerNameCB.addActionListener(this);
    JButton addImageToLayerImageButton = new JButton("Choose image");
    addImageToLayerImageButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setMultiSelectionEnabled(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "ppm",
                "png", "jpg", "jpeg");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          addImageToLayerImagePath = fileChooser.getSelectedFile();
        }
      }
    });
    this.addImageToLayerRowTF = new PlaceholderTextField("Row");
    this.addImageToLayerColTF = new PlaceholderTextField("Column");
    this.addImageToLayerConfirm = new JButton("Confirm");
    this.addImageToLayerConfirm.addActionListener(this);
    addImageToLayerPanel.add(addImageToLayerLabel);
    addImageToLayerPanel.add(addImageToLayerNameCB);
    addImageToLayerPanel.add(addImageToLayerImageButton);
    addImageToLayerPanel.add(addImageToLayerRowTF);
    addImageToLayerPanel.add(addImageToLayerColTF);
    addImageToLayerPanel.add(addImageToLayerConfirm);

    // set filter command inputs
    JPanel setFilterPanel = new JPanel(new GridLayout(1, 4));
    JLabel setFilterLabel = new JLabel("Set Filter");
    this.setFilterLayerNameCB = new JComboBox<>();
    setFilterLayerNameCB.addActionListener(this);
    String[] filters = {"normal", "red-component", "blue-component", "green-component",
                        "brighten-value", "brighten-intensity", "brighten-luma", "darken-value",
                        "darken-intensity", "darken-luma", "difference", "multiply", "screen"};
    this.setFilterNameCB = new JComboBox<>(filters);
    this.setFilterConfirm = new JButton("Confirm");
    this.setFilterConfirm.addActionListener(this);
    setFilterPanel.add(setFilterLabel);
    setFilterPanel.add(setFilterLayerNameCB);
    setFilterPanel.add(setFilterNameCB);
    setFilterPanel.add(setFilterConfirm);

    // add panels to right buttons panel
    rightButtonsPanel.add(rightButtonsLabel);
    rightButtonsPanel.add(newProjectPanel);
    rightButtonsPanel.add(addLayerPanel);
    rightButtonsPanel.add(addImageToLayerPanel);
    rightButtonsPanel.add(setFilterPanel);

    // add left and right button panels to main button panel
    buttonsPanel.add(leftButtonsPanel);
    buttonsPanel.add(rightButtonsPanel);

    // display current project structure
    JPanel projectStructurePanel = new JPanel();
    projectStructurePanel.setPreferredSize(new Dimension(guiWidth / 3, (guiHeight / 4) * 3));
    JScrollPane scrollPane2 = new JScrollPane(projectStructurePanel);
    scrollPane2.setPreferredSize(new Dimension(guiWidth / 3, (guiHeight / 3) * 2));
    scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.projectStructure = new JTextArea("");
    this.projectStructure.setPreferredSize(new Dimension(guiWidth / 3, (guiHeight / 4) * 3));
    this.projectStructure.disable();
    this.projectStructure.setDisabledTextColor(Color.black);
    projectStructurePanel.add(this.projectStructure);

    // add panels to GUI
    getContentPane().add(scrollPane1, BorderLayout.WEST);
    getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
    getContentPane().add(scrollPane2, BorderLayout.EAST);
    setVisible(true);
  }

  @Override
  public void setFrame(int height, int width, String token, int maxValue, boolean gui) {
    this.height = height;
    this.width = width;
    this.maxProjectValue = maxValue;
    if (gui) {
      this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
      this.imagePanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
          super.paintComponent(g);
          g.drawImage(image, 0, 0, null);
        }
      };
      this.imagePanel.setPreferredSize(new Dimension(width, height));
      this.panel.setPreferredSize(new Dimension(width, height));
      this.panel.add(this.imagePanel, BorderLayout.WEST);
      this.panel.validate();
      this.updateView();
    }
  }

  @Override
  public void updateView() {
    Graphics graphics = image.getGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, width, height);
    graphics.dispose();

    this.controller.getModel().updatePixels();
    double factor = 255.0 / this.maxProjectValue;
    for (int y = 0; y < this.height; y++) {
      for (int x = 0; x < this.width; x++) {
        int argb = 0;
        IPixel pixel = this.controller.getModel().getImage().getPixel(y, x);
        int alpha = (int) Math.round((double) pixel.getAlpha() / factor);
        argb |= ((int) (((double) alpha / this.maxProjectValue) * 255) << 24);

        int red = (int) Math.round((double) pixel.getRed() / factor);
        argb |= ((int) (((double) red / this.maxProjectValue) * 255) << 16);

        int green = (int) Math.round((double) pixel.getGreen() / factor);
        argb |= ((int) (((double) green / this.maxProjectValue) * 255) << 8);

        int blue = (int) Math.round((double) pixel.getBlue() / factor);
        argb |= (int) (((double) blue / this.maxProjectValue) * 255);

        this.image.setRGB(x, y, argb);
      }
    }
    this.imagePanel.repaint();
  }

  @Override
  public void displayProjectStructure() throws IOException {
    try {
      this.object.append(this.controller.getModel().getProjectStructure());
    } catch (IOException e) {
      throw new IOException("Failed to display project structure.");
    }
    catch (NullPointerException eq) {
      throw new IllegalArgumentException("Invalid project conditions.");
    }
  }

  @Override
  public void displayCommands() throws IOException {
    this.object.append(helpMenuCommands());
  }

  @Override
  public String helpMenuCommands() {
    String message = "";
    message += ("Available Commands:\n");
    message += (" - new-project [project-name] [project-height] [project-width] " +
                "[project-max-value] : creates a new collage project\n");
    message += (" - load-project [project-path] : loads a given collage project\n");
    message += (" - save-project : saves the current collage project\n");
    message += (" - add-layer [layer-name] : adds a new, unique layer to current project\n");
    message += (" - add-image-to-layer [layer-name] [image-path] [x-pos] [y-pos] : adds offset " +
                "(x, y) image to given layer \n");
    message += (" - set-filter [layer-name] [filter-option] : sets the filter of the given " +
                "layer as one of\n");
    message += ("    -> normal : does nothing to the image\n");
    message += ("    -> red-component : only uses the red portion of the RGB\n");
    message += ("    -> green-component : only uses the green portion of the RGB\n");
    message += ("    -> blue-component : only uses the blue portion of the RGB\n");
    message += ("    -> brighten-value : adds the brightness value pixel by pixel according to " +
                "value\n");
    message += ("    -> brighten-intensity : adds the brightness intensity pixel by pixel " +
                "according to value\n");
    message += ("    -> brighten-luma : adds the brightness luma pixel by pixel " +
                "according to value\n");
    message += ("    -> darken-value : remove the brightness value pixel by pixel " +
                "according to value\n");
    message += ("    -> darken-intensity : remove the brightness intensity pixel by pixel " +
                "according to value\n");
    message += ("    -> darken-luma : remove the brightness luma pixel by pixel " +
                "according to value\n");
    message += ("    -> difference : invert the colors of the layer based on the " +
                "background layers\n");
    message += ("    -> multiply : darken a layer based on the background images\n");
    message += ("    -> screen : brighten a layer based on the background images\n");
    message += (" - save-image [filename] : saves current project image as PPM, JPG/JPEG, or " +
                "PNG image\n");
    message += (" - quit : quits the project and loses all unsaved work\n");
    message += (" - project-structure : view the current collage project structure\n");
    message += (" - help : view the available commands\n");
    return message;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    try {
      this.object.append(message).append("\n");
    } catch (IOException e) {
      throw new IOException("Failed to transmit the error message.");
    }
  }

  @Override
  public void renderGUIMessage(String message, String title, int type) {
    JOptionPane.showMessageDialog(panel, message, title, type);
  }

  @Override
  public void exit() {
    this.dispose();
  }

  @Override
  public void setTextFieldText(JTextField button, String text) {
    button.setText(text);
  }


  @Override
  public void addComboBoxItem(JComboBox<String> comboBox, String item) {
    comboBox.addItem(item);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    boolean flag = false;
    if (e.getSource() == newProjectConfirm) {
      try {
        String projectName = newProjectNameTF.getText().trim();
        if (projectName.contains(" ")) {
          renderGUIMessage("Project name cannot have spaces.", "Input Error", 0);
          return;
        }
        int projectHeight = Integer.parseInt(newProjectHeightTF.getText().trim());
        int projectWidth = Integer.parseInt(newProjectWidthTF.getText().trim());
        if (projectHeight <= 0 || projectWidth <= 0) {
          renderGUIMessage("Project height and width must be positive integers.", "Input Error", 0);
          return;
        }
        int projectMaxValue = Integer.parseInt(newProjectMaxValueTF.getText().trim());
        if (projectMaxValue < 1 || projectMaxValue > 255) {
          renderGUIMessage("Project max value must be in range [1, 255].", "Input Error", 0);
          return;
        }
        controller.newProjectCommand(projectName, projectHeight, projectWidth, projectMaxValue);

      } catch (NumberFormatException err) {
        this.renderGUIMessage("Project height, width, and max value must be integers",
                "Invalid input", 0);
        return;
      } catch (IOException ex) {
        this.renderGUIMessage("Failed to transmit message to the view.", "I/O Error", 0);
        return;
      }
      flag = true;

    } else if (e.getSource() == addLayerConfirm) {
      String layerName = addLayerNameTF.getText().trim();
      try {
        controller.addLayerCommand(layerName);
      } catch (IOException ex) {
        this.renderGUIMessage("Failed to transmit message to the view.", "I/O Error", 0);
        return;
      }
      flag = true;

    } else if (e.getSource() == addImageToLayerConfirm) {
      String layerName = Objects.requireNonNull(addImageToLayerNameCB.getSelectedItem()).toString();
      String imagePath = addImageToLayerImagePath.getPath();
      int imageRow = Integer.parseInt(addImageToLayerRowTF.getText().trim());
      if (imageRow < 0 || imageRow >= this.height) {
        renderGUIMessage("Image row offset must be in range [0, " + (this.height - 1) + "]",
                "Input Error", 0);
        return;
      }
      int imageCol = Integer.parseInt(addImageToLayerColTF.getText().trim());
      if (imageCol < 0 || imageCol >= this.width) {
        renderGUIMessage("Image column offset must be in range [0, " + (this.width - 1) + "]",
                "Input Error", 0);
        return;
      }
      try {
        controller.addImageToLayerCommand(layerName, imagePath, imageRow, imageCol);
      } catch (IOException ex) {
        this.renderGUIMessage("Failed to transmit message to the view.", "I/O Error", 0);
        return;
      }
      flag = true;

    } else if (e.getSource() == setFilterConfirm) {
      String layerName = Objects.requireNonNull(setFilterLayerNameCB.getSelectedItem()).toString();
      String filterName = Objects.requireNonNull(setFilterNameCB.getSelectedItem()).toString();
      try {
        controller.setFilterCommand(layerName, filterName);
      } catch (IOException ex) {
        this.renderGUIMessage("Failed to transmit message to the view.", "I/O Error", 0);
        return;
      }
      flag = true;

    } else if (e.getSource() == loadProjectButton) {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setAcceptAllFileFilterUsed(false);
      fileChooser.setMultiSelectionEnabled(false);
      FileNameExtensionFilter filter =
              new FileNameExtensionFilter("Collage files (*.collage)", "collage");
      fileChooser.setFileFilter(filter);
      int returnValue = fileChooser.showOpenDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        String loadProjectPath = fileChooser.getSelectedFile().getPath();
        try {
          controller.loadProjectCommand(loadProjectPath);
        } catch (IOException ex) {
          this.renderGUIMessage("Unable to load: " + loadProjectPath, "I/O Error", 0);
          return;
        }
      }
      return;
    }

    this.projectStructure.setText(this.controller.getModel().getProjectStructure());
    if (flag) {
      updateView();
    }
  }

  @Override
  public JTextField getAddLayerNameTF() {
    return addLayerNameTF;
  }

  @Override
  public JComboBox<String> getAddImageToLayerNameCB() {
    return addImageToLayerNameCB;
  }

  @Override
  public JComboBox<String> getSetFilterLayerNameCB() {
    return setFilterLayerNameCB;
  }

  @Override
  public JTextField getAddImageToLayerRowTF() {
    return addImageToLayerRowTF;
  }

  @Override
  public JTextField getAddImageToLayerColTF() {
    return addImageToLayerColTF;
  }

  @Override
  public JTextField getNewProjectNameTF() {
    return newProjectNameTF;
  }

  @Override
  public JTextField getNewProjectHeightTF() {
    return newProjectHeightTF;
  }

  @Override
  public JTextField getNewProjectWidthTF() {
    return newProjectWidthTF;
  }

  @Override
  public JTextField getNewProjectMaxValueTF() {
    return newProjectMaxValueTF;
  }

  @Override
  public void setController(Features controller) {
    if (this.controller == null) {
      this.controller = controller;
    }
  }

  @Override
  public BufferedImage getBuffImage() {
    return this.image;
  }
}