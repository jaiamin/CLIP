package view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import controller.Features;

/**
 * This interface represents the view of a collage project.
 */
public interface IView {
  /**
   * This method is used to set the dimensions of the view, create a new
   * {@code CollageModel} object
   * with the given height, width, and token, and initialize
   * the main JFrame, image, and JPanel objects.
   *
   * @param height   of the view
   * @param width    of the view
   * @param token    for the project
   * @param maxValue is the maximum pixel value for the project
   * @param gui      represents if functionality for a GUI is required
   */
  void setFrame(int height, int width, String token, int maxValue, boolean gui);

  /**
   * This method is used to update the view by updating the image and displaying the correct
   * project structure.
   */
  void updateView();

  /**
   * This method is used to update the view and display the current project structure which formats
   * and displays the current set of layers and images on the collage project.
   *
   * @throws IOException if there is an error displaying the project structure to the view
   */
  void displayProjectStructure() throws IOException;

  /**
   * This method is used to display the help menu for users which provides a set of commands
   * that are valid and accepted by the controller.
   *
   * @throws IOException if there is an error displaying the help menu/list of commands to the view
   */
  void displayCommands() throws IOException;

  /**
   * This method is used to render and transmit a message to the view.
   *
   * @param message is the String message to be displayed to the view
   * @throws IOException if there is an error displaying and transmitting a message to the view
   */
  void renderMessage(String message) throws IOException;

  /**
   * This method is used to dispose/exit the GUI view.
   */
  void exit();

  /**
   * This method is used to display a JOptionPane to the GUI with the provided message, title,
   * and type of JOptionPane.
   *
   * @param message to display to GUI
   * @param title   of the JOptionPane
   * @param type    of JOptionPane
   */
  void renderGUIMessage(String message, String title, int type);

  /**
   * This method is used to set/change the text of a specific JTextField.
   *
   * @param button is a JButton
   * @param text   is the new text to set for a given JButton
   */
  void setTextFieldText(JTextField button, String text);

  /**
   * This method is used to add a combo box item to a given combo box.
   *
   * @param comboBox is a JComboBox
   * @param item     to be added to a JComboBox
   */
  void addComboBoxItem(JComboBox<String> comboBox, String item);

  /**
   * This method is used to get the add layer name text field.
   *
   * @return the add layer name text field
   */
  JTextField getAddLayerNameTF();

  /**
   * This method is used to get the selection of the add image to layer name combo box.
   *
   * @return the selection of the add image to layer name combo box
   */
  JComboBox<String> getAddImageToLayerNameCB();

  /**
   * This method is used to get the selection of the set filter name combo box.
   *
   * @return the selection of the set filter name combo box
   */
  JComboBox<String> getSetFilterLayerNameCB();

  /**
   * This method is used to get the add image to layer row text field.
   *
   * @return the add image to layer row text field
   */
  JTextField getAddImageToLayerRowTF();

  /**
   * This method is used to get the add image to layer column text field.
   *
   * @return the add image to layer column text field
   */
  JTextField getAddImageToLayerColTF();


  /**
   * This method is used to get the new project name text field.
   *
   * @return the new project name text field
   */
  JTextField getNewProjectNameTF();

  /**
   * This method is used to get the new project height text field.
   *
   * @return the new project height text field
   */
  JTextField getNewProjectHeightTF();

  /**
   * This method is used to get the new project width text field.
   *
   * @return the new project width text field
   */
  JTextField getNewProjectWidthTF();

  /**
   * This method is used to get the new project max value text field.
   *
   * @return the new project max value text field
   */
  JTextField getNewProjectMaxValueTF();

  /**
   * This method is used to display the help menu which provides a list of the
   * available commands for the program.
   *
   * @return the help menu which displays the available commands
   */
  String helpMenuCommands();

  /**
   * Initializes the Controller parameter of the view.
   * @param controller is the controller that is used to initialize the parameter
   */
  void setController(Features controller);

  /**
   * Returns the current rendered buffered image of the collage project.
   *
   * @return the current rendered buffered image
   */
  BufferedImage getBuffImage();
}