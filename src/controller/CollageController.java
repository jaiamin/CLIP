package controller;

import java.io.IOException;

/**
 * This interface represents a controller for a Collage Creator program.
 * It provides a method to start the program.
 */
public interface CollageController {
  /**
   * Displays the welcome message and options to create or load a new collage project.
   * Accepts user input and performs corresponding acton depending on user's choice of command.
   * The current available commands include 'new-project', 'add-layer', 'add-image-to-layer',
   * 'set-filter', 'save-image', 'quit', 'project-structure', and 'help'.
   *
   * @throws IOException if there is an I/O error reading in the file path for loading a project
   *        or rendering a message to the view
   */
  void start() throws IOException;
}

