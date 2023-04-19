import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import controller.CollageController;
import controller.CollageControllerGUIImpl;
import controller.CollageControllerImpl;
import controller.Features;
import model.CollageModel;
import view.CollageView;

/**
 * Represents the main function to run the collage project program.
 */
public class Main {
  /**
   * The main method is the entry point for the collage project program.
   * The three valid command-line arguments:
   * java -jar Program.jar -file path-of-script-file
   * - when invoked in this manner the program should open the script file,
   *   execute it and then shut down.
   * java -jar Program.jar -text
   * - when invoked in this manner the program should open in an interactive text mode,
   *   allowing the user to type the script and execute it one line at a time.
   * java -jar Program.jar
   * - when invoked in this manner the program should open the graphical user interface.
   *   This is what will happen if you simply double-click on the jar file.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      // launches the GUI mode if no command-line arguments are inputted
      guiHelper();
    }
    else if (args.length == 1 && args[0].equals("-text")) {
      // launches the interactive Text View UI mode if -text is specified
      textViewHelper(new InputStreamReader(System.in));
    }
    else if (args.length == 2 && args[0].equals("-file")) {
      // opens and executes a valid script file
      File scriptFile = new File(args[1]);
      if (!scriptFile.exists() || !scriptFile.isFile()) {
        System.out.println("Invalid script file: " + scriptFile);
      }
      textViewHelper(new FileReader(scriptFile));
    }
    else {
      System.out.println("Invalid command-line arguments for collage project program.");
    }
  }

  /**
   * Starts the collage project program for the {@code CollageControllerGUIImpl} controller.
   * Handles the GUI collage project view.
   */
  private static void guiHelper() {
    CollageModel model = new CollageModel();
    Features controller = new CollageControllerGUIImpl(model);
    CollageView view = new CollageView(controller);
    controller.setFeaturesController(view);
  }

  /**
   * Starts the collage project program for the {@code CollageControllerImpl} controller.
   * Handles the Text View UI collage project view.
   *
   * @param in is the readable object
   * @throws IOException if any I/O error occurs when transmitting messages
   */
  private static void textViewHelper(Readable in) throws IOException {
    CollageModel model = new CollageModel();
    CollageView view = new CollageView(System.out);
    CollageController controller = new CollageControllerImpl(model, view, in);
    controller.start();
  }
}
