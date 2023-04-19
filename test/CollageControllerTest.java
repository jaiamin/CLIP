import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

import controller.CollageController;
import controller.CollageControllerImpl;
import controller.Features;
import model.CollageModel;
import model.ICanvas;
import model.IImage;
import model.ILayer;
import model.IPixel;
import model.filter.IFilter;
import view.CollageView;
import view.IView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the functions and behavior of the CollageController class.
 */
public class CollageControllerTest {

  String initialText = "Welcome to Image Manipulator and Editor!\n" +
          "\nAvailable Commands:\n" +
          " - new-project [project-name] [project-height] [project-width] " +
          "[project-max-value] : creates a new collage project\n" +
          " - load-project [project-path] : loads a given collage project\n" +
          " - save-project : saves the current collage project\n" +
          " - add-layer [layer-name] : adds a new, unique layer to current project\n" +
          " - add-image-to-layer [layer-name] [image-path] [x-pos] [y-pos] : " +
          "adds offset" +
          " (x, y) image to given layer \n" +
          " - set-filter [layer-name] [filter-option] : sets the filter of the " +
          "given layer " +
          "as one of\n" +
          "    -> normal : does nothing to the image\n" +
          "    -> red-component : only uses the red portion of the RGB\n" +
          "    -> green-component : only uses the green portion of the RGB\n" +
          "    -> blue-component : only uses the blue portion of the RGB\n" +
          "    -> brighten-value : adds the brightness value pixel by pixel " +
          "according to value\n" +
          "    -> brighten-intensity : adds the brightness intensity pixel by pixel " +
          "according to value\n" +
          "    -> brighten-luma : adds the brightness luma pixel by pixel " +
          "according to value\n" +
          "    -> darken-value : remove the brightness value pixel by pixel " +
          "according to value\n" +
          "    -> darken-intensity : remove the brightness intensity pixel " +
          "by pixel according to" +
          " value\n" +
          "    -> darken-luma : remove the brightness luma pixel by pixel " +
          "according to value\n" +
          "    -> difference : invert the colors of the layer based on the " +
          "background layers\n" +
          "    -> multiply : darken a layer based on the background images\n" +
          "    -> screen : brighten a layer based on the background images\n" +
          " - save-image [filename] : saves current project image as PPM, JPG/JPEG, or PNG image\n" +
          " - quit : quits the project and loses all unsaved work\n" +
          " - project-structure : view the current collage project structure\n" +
          " - help : view the available commands\n" +
          "\nStart by creating or loading a collage project below.\n\n";

  /**
   * Creates a mock Canvas Model.
   */
  public static class TestingModel implements ICanvas {
    final StringBuilder log;

    public TestingModel(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void addLayerToCanvas(int height, int width, String name) throws IllegalArgumentException {

    }

    @Override
    public void addImageToLayer(String layerName, IImage image, int xPos, int yPos)
            throws IllegalArgumentException {
      // mock
    }

    @Override
    public void updatePixels() {
      // mock
    }

    @Override
    public IImage getImage() {
      return null;
    }

    @Override
    public ArrayList<ILayer> getLayers() {
      return new ArrayList<ILayer>();
    }

    @Override
    public String getProjectName() {
      return " ";
    }

    @Override
    public IImage getPrevIm(String layerName) {
      return null;
    }

    @Override
    public String getProjectStructure() {
      return "";
    }

    @Override
    public IFilter getFilterFromString(String filterStr, String layerName) throws RuntimeException {
      return null;
    }

    @Override
    public int getARGB(int x, int y, int maxProjectValue, double factor) {
      return 0;
    }

    @Override
    public ICanvas setModel() {
      return null;
    }

    @Override
    public ICanvas setModel(int height, int width, String name) {
      return null;
    }

    @Override
    public ICanvas setModel(int height, int width, IPixel[][] pixels, ArrayList<ILayer> layers) {
      return new TestingModel(log);
    }

    @Override
    public void addFilter(IFilter filterOption, String layerName) {

    }

    @Override
    public IImage createImage(String imagePath) throws IOException {
      return null;
    }

    @Override
    public IImage createImage(IPixel[][] pixels, int maxVal) {
      return null;
    }

    @Override
    public IPixel createPixel(int red, int green, int blue, int alpha, IFilter filter) {
      return null;
    }
  }

  class AppendableIOExMock implements Appendable {

    @Override
    public Appendable append(CharSequence csq) throws IOException {
      throw new IOException();
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
      throw new IOException();
    }

    @Override
    public Appendable append(char c) throws IOException {
      throw new IOException();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor1() {
    Reader in = new StringReader("quit");
    StringBuilder out = new StringBuilder();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(null, iv1, in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2() {
    Reader in = new StringReader("quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    CollageController cc1 = new CollageControllerImpl(ic1, null, in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor3() {
    Reader in = null;
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor4() {
    Reader in = new StringReader("quit");
    StringBuilder out = new StringBuilder();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(null, iv1, in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor5() {
    Reader in = new StringReader("quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerImpl(ic1, null, in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor6() {
    Reader in = null;
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
  }

  @Test
  public void testInputs() throws IOException {
    Reader in = new StringReader("quit");
    StringBuilder dontCareOutput = new StringBuilder();
    StringBuilder log = new StringBuilder();
    ICanvas ic1 = new TestingModel(log);
    IView iv1 = new CollageView(dontCareOutput);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.start();
    assertEquals("", log.toString());
  }

  @Test
  public void testStart1() throws IOException {
    Reader in = new StringReader("quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic2 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic2, iv1, in);
    cc1.start();
    assertEquals(initialText, out.toString());
  }

  @Test
  public void testStart2() throws IOException {
    Reader in = new StringReader("new-project p1 200 200 five new-project p1 200 200 300 " +
            "new-project p1 200 200 0 new-project p1 200 200 255 " +
            "add-layer l1 " +
            "add-image-to-layer l1 res/example1.ppm 20 50 " +
            "add-layer l2 set-filter l1 red-component quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.start();
    assertEquals(initialText +
            "Error: Project max value must be an integer in range (0, 255].\n" +
            "Error: Cannot create project due to invalid project max value\n" +
            "Error: Cannot create project due to invalid project max value\n" +
            "'p1' collage project created successfully!\n" +
            "'l1' layer added.\n" + "Current Project Structure:\n" + "Layer #1: l1 " +
            "(normal)\n" +
            "Image added successfully to layer 'l1' at position (20, 50).\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: res/example1.ppm\n" + "'l2' layer added.\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: res/example1.ppm\n" + "Layer #2: l2 (normal)\n" +
            "Current Project Structure:\n" +
            "Layer #1: l1 (red-component)\n" + " - Image #1: res/example1.ppm\n" +
            "Layer #2: l2 (normal)\n", out.toString());
  }

  @Test
  public void testStart3() throws IOException {
    Reader in = new StringReader("new-project proj 500 300 255 add-layer layer1 save-project " +
            "add-layer layer2 add-image-to-layer layer1 res/example1.ppm " +
            "20 50 " +
            "set-filter layer1 brighten-luma add-image-to-layer layer2 " +
            "res/example2.ppm 0 30 " +
            "help set-filter layer2 darken-value save-image image1.ppm " +
            "add-layer layer3 save-project quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic2 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic2, iv1, in);
    cc1.start();
    assertEquals(initialText +
            "'proj' collage project created successfully!\n" + "'layer1' layer added.\n" +
            "Current Project Structure:\n" + "Layer #1: layer1 (normal)\n" +
            "Current Project Structure:\n" +
            "Layer #1: layer1 (normal)\n" + "'layer2' layer added.\n" +
            "Current Project Structure:\n" +
            "Layer #1: layer1 (normal)\n" + "Layer #2: layer2 (normal)\n" +
            "Image added successfully to layer 'layer1' at position (20, 50).\n" +
            "Current Project Structure:\n" +
            "Layer #1: layer1 (normal)\n" + " - Image #1: res/example1.ppm\n" +
            "Layer #2: layer2 (normal)\n" + "Current Project Structure:\n" +
            "Layer #1: layer1 (brighten-luma)\n" + " - Image #1: res/example1.ppm\n" +
            "Layer #2: layer2 (normal)\n" +
            "Image added successfully to layer 'layer2' at position (0, 30).\n" +
            "Current Project Structure:\n" + "Layer #1: layer1 (brighten-luma)\n" +
            " - Image #1: res/example1.ppm\n" + "Layer #2: layer2 (normal)\n" +
            " - Image #1: res/example2.ppm\n" +
            "Available Commands:\n" +
            " - new-project [project-name] [project-height] [project-width] " +
            "[project-max-value] " +
            ": creates a new collage project\n" +
            " - load-project [project-path] : loads a given collage project\n" +
            " - save-project : saves the current collage project\n" +
            " - add-layer [layer-name] : adds a new, unique layer to current project\n" +
            " - add-image-to-layer [layer-name] [image-path] [x-pos] [y-pos] : " +
            "adds offset (x, y)" +
            " image to given layer \n" +
            " - set-filter [layer-name] [filter-option] : sets the filter of the given" +
            " layer as one of\n" +
            "    -> normal : does nothing to the image\n" +
            "    -> red-component : only uses the red portion of the RGB\n" +
            "    -> green-component : only uses the green portion of the RGB\n" +
            "    -> blue-component : only uses the blue portion of the RGB\n" +
            "    -> brighten-value : adds the brightness value pixel by pixel according" +
            " to value\n" +
            "    -> brighten-intensity : adds the brightness intensity pixel by pixel " +
            "according to value\n" +
            "    -> brighten-luma : adds the brightness luma pixel by pixel according " +
            "to value\n" +
            "    -> darken-value : remove the brightness value pixel by pixel " +
            "according to value\n" +
            "    -> darken-intensity : remove the brightness intensity pixel by pixel " +
            "according to value\n" +
            "    -> darken-luma : remove the brightness luma pixel by pixel according " +
            "to value\n" +
            "    -> difference : invert the colors of the layer based on the " +
            "background layers\n" +
            "    -> multiply : darken a layer based on the background images\n" +
            "    -> screen : brighten a layer based on the background images\n" +
            " - save-image [filename] : saves current project image as PPM, JPG/JPEG, or PNG image\n" +
            " - quit : quits the project and loses all unsaved work\n" +
            " - project-structure : view the current collage project structure\n" +
            " - help : view the available commands\n" + "Current Project Structure:\n" +
            "Layer #1: layer1 (brighten-luma)\n" +
            " - Image #1: res/example1.ppm\n" + "Layer #2: layer2 (normal)\n" +
            " - Image #1: res/example2.ppm\n" +
            "Current Project Structure:\n" + "Layer #1: layer1 (brighten-luma)\n" +
            " - Image #1: res/example1.ppm\n" +
            "Layer #2: layer2 (darken-value)\n" + " - Image #1: res/example2.ppm\n" +
            "Current Project Structure:\n" +
            "Layer #1: layer1 (brighten-luma)\n" + " - Image #1: res/example1.ppm\n" +
            "Layer #2: layer2 (darken-value)\n" +
            " - Image #1: res/example2.ppm\n" + "'layer3' layer added.\n" +
            "Current Project Structure:\n" +
            "Layer #1: layer1 (brighten-luma)\n" + " - Image #1: res/example1.ppm\n" +
            "Layer #2: layer2 (darken-value)\n" +
            " - Image #1: res/example2.ppm\n" + "Layer #3: layer3 (normal)\n" +
            "Current Project Structure:\n" +
            "Layer #1: layer1 (brighten-luma)\n" + " - Image #1: res/example1.ppm\n" +
            "Layer #2: layer2 (darken-value)\n" +
            " - Image #1: res/example2.ppm\n" + "Layer #3: layer3 (normal)\n", out.toString());
  }

  @Test
  public void testNewProject() throws IOException {
    Reader in = new StringReader("new-project p1 300 300 255 new-project p2 quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.start();
    assertEquals(initialText +
            "'p1' collage project created successfully!\n" +
            "Error: Project has already been created/loaded.\n", out.toString());
  }

  @Test
  public void testNewProject2() throws IOException {
    Reader in = new StringReader("new-project p1 twenty 20 255 new-project " +
            "p1 20 twenty 255 new-project p1 20 20 255 quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.start();
    assertEquals(initialText +
            "Error: Size must be an integer.\n" + "Error: Size must be an integer.\n" +
            "'p1' collage project created successfully!\n", out.toString());
  }

  @Test
  public void testNewProjectCommand() throws IOException {
    StringReader in = new StringReader(" ");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.newProjectCommand("p1", 20, 20, 255);
    assertEquals("'p1' collage project created successfully!\n", out.toString());
    assertEquals(20, cc1.getModel().getImage().getHeight());
    assertEquals(20, cc1.getModel().getImage().getWidth());
  }

  @Test
  public void testAddLayer() throws IOException {
    Reader in = new StringReader("add-layer l1 new-project p1 300 300 255 add-layer " +
            "l1 add-layer l1 add-layer l2 set-filter l2 red-component quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    assertEquals(0, ic1.getLayers().size());
    cc1.start();
    assertEquals(initialText +
            "Error: Project has not been created or loaded yet.\n" + "'p1' collage project " +
            "created successfully!\n" +
            "'l1' layer added.\n" + "Current Project Structure:\n" + "Layer #1: " +
            "l1 (normal)\n" +
            "Error: Layer 'l1' already exists.\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            "'l2' layer added.\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + "Layer #2: l2 (normal)\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            "Layer #2: l2 (red-component)\n", out.toString());
  }

  @Test
  public void testAddLayerCommand1() throws IOException {
    Reader in = new StringReader(" ");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
    assertEquals(0, ic1.getLayers().size());
    cc1.newProjectCommand("p1", 30, 30, 255);
    cc1.addLayerCommand("l1");
    assertEquals(
            "'p1' collage project created successfully!\n" +
                    "'l1' layer added.\n", out.toString());
    assertEquals(1, cc1.getModel().getLayers().size());
  }

  @Test
  public void testAddLayerCommand2() throws IOException {
    Reader in = new StringReader(" ");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
    assertEquals(0, ic1.getLayers().size());
    cc1.newProjectCommand("p1", 30, 30, 255);
    cc1.addLayerCommand("l1");
    cc1.addLayerCommand("l1");
    assertEquals(
            "'p1' collage project created successfully!\n" +
                    "'l1' layer added.\n" +
                    "Error: Layer 'l1' already exists.\n", out.toString());
    assertEquals(1, cc1.getModel().getLayers().size());
  }

  @Test
  public void testAddImageToLayer1() throws IOException {
    Reader in = new StringReader("add-image-to-layer l1 new-project p1 300 300 255 " +
            "add-image-to-layer l1 res/example1.ppm 0 0 add-layer " +
            "l1 add-image-to-layer l1" +
            " res/example1.ppm 0 0 " +
            "add-layer l2 add-image-to-layer l2 im1 30 30 " +
            "add-image-to-layer l2 " +
            "res/example2.ppm 0 20 " +
            "add-image-to-layer l2 res/example1.ppm 250 250 " +
            "add-image-to-layer l2 " +
            "res/example1.ppm 400 50 " +
            "add-image-to-layer res/example1.ppm l2 400 -30 quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.start();
    assertEquals(initialText +
            "Error: Project has not been created or loaded yet.\n" +
            "'p1' collage project created successfully!\n" + "Layer 'l1' does not exist.\n" +
            "'l1' layer added.\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + "Image added successfully to layer 'l1' at position " +
            "(0, 0).\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + " - Image #1: res/example1.ppm\n" +
            "'l2' layer added.\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + " - Image #1: res/example1.ppm\n" +
            "Layer #2: l2 (normal)\n" + "Invalid image format. Only PPM, JPG/JPEG," +
            " and PNG are supported.\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: res/example1.ppm\n" + "Layer #2: l2 (normal)\n" +
            "Image added successfully to layer 'l2' at position (0, 20).\n"
            + "Current Project " +
            "Structure:\n" +
            "Layer #1: l1 (normal)\n" + " - Image #1: res/example1.ppm\n" +
            "Layer #2: l2 (normal)\n" + " - Image #1: res/example2.ppm\n" +
            "Image added successfully to layer 'l2' at position (250, 250).\n" +
            "Current Project " + "Structure:\n" +
            "Layer #1: l1 (normal)\n" + " - Image #1: res/example1.ppm\n" + "Layer #2: " +
            "l2 (normal)\n" +
            " - Image #1: res/example2.ppm\n" + " - Image #2: res/example1.ppm\n" +
            "Error: Offset position must be within range of collage grid.\n" +
            "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + " - Image #1: res/example1.ppm\n" +
            "Layer #2: l2 (normal)\n" + " - Image #1: res/example2.ppm\n"
            + " - Image #2: res/example1.ppm\n" +
            "Error: Offset position must be within range of collage grid.\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n"
            + " - Image #1: res/example1.ppm\n" +
            "Layer #2: l2 (normal)\n" + " - Image #1: res/example2.ppm\n"
            + " - Image #2: res/example1.ppm\n", out.toString());
  }

  @Test
  public void testAddImageToLayer2() throws IOException {
    Reader in = new StringReader("add-image-to-layer l1 new-project p1 300 300 255 " +
            "add-image-to-layer l1 res/example-jpeg.jpeg 0 0 add-layer " +
            "l1 add-image-to-layer l1" +
            " res/img.png 0 0 " +
            "add-layer l2 add-image-to-layer l2 im1 30 30 " +
            "add-image-to-layer l2 " +
            "res/example-jpg.jpg 0 20 " +
            "add-image-to-layer l2 res/example-jpeg.jpeg 250 250 " +
            "add-image-to-layer l2 " +
            "res/example1.ppm 400 50 " +
            "add-image-to-layer res/img.png l2 400 -30 quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.start();
    assertEquals(initialText +
            "Error: Project has not been created or loaded yet.\n" +
            "'p1' collage project created successfully!\n" + "Layer 'l1' does not exist.\n" +
            "'l1' layer added.\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + "Image added successfully to layer 'l1' at position " +
            "(0, 0).\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + " - Image #1: res/img.png\n" +
            "'l2' layer added.\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + " - Image #1: res/img.png\n" +
            "Layer #2: l2 (normal)\n" + "Invalid image format. Only PPM, JPG/JPEG," +
            " and PNG are supported.\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: res/img.png\n" + "Layer #2: l2 (normal)\n" +
            "Image added successfully to layer 'l2' at position (0, 20).\n"
            + "Current Project " +
            "Structure:\n" +
            "Layer #1: l1 (normal)\n" + " - Image #1: res/img.png\n" +
            "Layer #2: l2 (normal)\n" + " - Image #1: res/example-jpg.jpg\n" +
            "Image added successfully to layer 'l2' at position (250, 250).\n" +
            "Current Project " + "Structure:\n" +
            "Layer #1: l1 (normal)\n" + " - Image #1: res/img.png\n" + "Layer #2: " +
            "l2 (normal)\n" +
            " - Image #1: res/example-jpg.jpg\n" + " - Image #2: res/example-jpeg.jpeg\n" +
            "Error: Offset position must be within range of collage grid.\n" +
            "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + " - Image #1: res/img.png\n" +
            "Layer #2: l2 (normal)\n" + " - Image #1: res/example-jpg.jpg\n"
            + " - Image #2: res/example-jpeg.jpeg\n" +
            "Error: Offset position must be within range of collage grid.\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n"
            + " - Image #1: res/img.png\n" +
            "Layer #2: l2 (normal)\n" + " - Image #1: res/example-jpg.jpg\n"
            + " - Image #2: res/example-jpeg.jpeg\n", out.toString());
  }

  @Test
  public void testAddImageToLayerCommandPPM() throws IOException {
    Reader in = new StringReader("add-image-to-layer l1 new-project p1 300 300 255 " +
            "add-image-to-layer l1 res/example1.ppm 0 0 add-layer l1 " +
            "add-image-to-layer l1" +
            " res/example1.ppm 0 0 " +
            "add-layer l2 add-image-to-layer l2 im1 30 30 " +
            "add-image-to-layer l2 " +
            "res/example1.ppm 0 20 " +
            "add-image-to-layer l2 res/example2.ppm 250 250 " +
            "add-image-to-layer l2 " +
            "res/example1.ppm 400 50 " +
            "add-image-to-layer res/example1.ppm l2 400 -30 quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
    assertEquals(0, cc1.getModel().getLayers().size());
    cc1.newProjectCommand("p1", 300, 300, 355);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/example1.ppm", 0, 0);
    assertEquals("'p1' collage project created successfully!\n" +
            "'l1' layer added.\n" + "Image added successfully to layer 'l1' at position " +
            "(0, 0).\n", out.toString());
    assertEquals(1, cc1.getModel().getLayers().get(0).getImages().size());
    assertEquals("res/example1.ppm",
            cc1.getModel().getLayers().get(0).getImages().get(0).getFilename());
  }

  @Test
  public void testAddImageToLayerCommandJPEG() throws IOException {
    Reader in = new StringReader("add-image-to-layer l1 new-project p1 300 300 255 " +
            "add-image-to-layer l1 res/example-jpeg.jpeg 0 0 add-layer l1 " +
            "add-image-to-layer l1" +
            " res/example-jpeg.jpeg 0 0 " +
            "add-layer l2 add-image-to-layer l2 im1 30 30 " +
            "add-image-to-layer l2 " +
            "res/example-jpeg.jpeg 0 20 " +
            "add-image-to-layer l2 res/example-jpeg.jpeg 250 250 " +
            "add-image-to-layer l2 " +
            "res/example-jpeg.jpeg 400 50 " +
            "add-image-to-layer res/example-jpeg.jpeg l2 400 -30 quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
    assertEquals(0, cc1.getModel().getLayers().size());
    cc1.newProjectCommand("p1", 300, 300, 355);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/example-jpeg.jpeg", 0, 0);
    assertEquals("'p1' collage project created successfully!\n" +
            "'l1' layer added.\n" + "Image added successfully to layer 'l1' at position " +
            "(0, 0).\n", out.toString());
    assertEquals(1, cc1.getModel().getLayers().get(0).getImages().size());
    assertEquals("res/example-jpeg.jpeg",
            cc1.getModel().getLayers().get(0).getImages().get(0).getFilename());
  }

  @Test
  public void testAddImageToLayerCommandJPG() throws IOException {
    Reader in = new StringReader("add-image-to-layer l1 new-project p1 300 300 255 " +
            "add-image-to-layer l1 res/example-jpg.jpg 0 0 add-layer l1 " +
            "add-image-to-layer l1" +
            " res/example-jpg.jpg 0 0 " +
            "add-layer l2 add-image-to-layer l2 im1 30 30 " +
            "add-image-to-layer l2 " +
            "res/example-jpg.jpg 0 20 " +
            "add-image-to-layer l2 res/example-jpg.jpg 250 250 " +
            "add-image-to-layer l2 " +
            "res/example-jpg.jpg 400 50 " +
            "add-image-to-layer res/example-jpg.jpg l2 400 -30 quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
    assertEquals(0, cc1.getModel().getLayers().size());
    cc1.newProjectCommand("p1", 300, 300, 355);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/example-jpg.jpg", 0, 0);
    assertEquals("'p1' collage project created successfully!\n" +
            "'l1' layer added.\n" + "Image added successfully to layer 'l1' at position " +
            "(0, 0).\n", out.toString());
    assertEquals(1, cc1.getModel().getLayers().get(0).getImages().size());
    assertEquals("res/example-jpg.jpg",
            cc1.getModel().getLayers().get(0).getImages().get(0).getFilename());
  }

  @Test
  public void testAddImageToLayerCommandPNG() throws IOException {
    Reader in = new StringReader("add-image-to-layer l1 new-project p1 300 300 255 " +
            "add-image-to-layer l1 res/im.png 0 0 add-layer l1 " +
            "add-image-to-layer l1" +
            " res/im.png 0 0 " +
            "add-layer l2 add-image-to-layer l2 im1 30 30 " +
            "add-image-to-layer l2 " +
            "res/im.png 0 20 " +
            "add-image-to-layer l2 res/im.png 250 250 " +
            "add-image-to-layer l2 " +
            "res/im.png 400 50 " +
            "add-image-to-layer res/im.png l2 400 -30 quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
    assertEquals(0, cc1.getModel().getLayers().size());
    cc1.newProjectCommand("p1", 300, 300, 355);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/img.png", 0, 0);
    assertEquals("'p1' collage project created successfully!\n" +
            "'l1' layer added.\n" + "Image added successfully to layer 'l1' at position " +
            "(0, 0).\n", out.toString());
    assertEquals(1, cc1.getModel().getLayers().get(0).getImages().size());
    assertEquals("res/img.png",
            cc1.getModel().getLayers().get(0).getImages().get(0).getFilename());
  }

  @Test
  public void testWrongCommand() throws IOException {
    Reader in = new StringReader("addlayer saveimage newproject " +
            "new-project p1 300 400 255 fjifjfifj quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    assertEquals(0, ic1.getLayers().size());
    cc1.start();
    assertEquals(initialText +
            "Error: Invalid command. Try again.\n" +
            "Error: Invalid command. Try again.\n" +
            "Error: Invalid command. Try again.\n" +
            "'p1' collage project created successfully!\n" +
            "Error: Invalid command. Try again.\n", out.toString());
  }

  @Test
  public void testSetFilter() throws IOException {
    Reader in = new StringReader("set-filter new-project p1 200 200 255 " +
            "set-filter l1 red-component " +
            "add-layer l1 add-image-to-layer l1 res/example1.ppm " +
            "10 10 set-filter" +
            " l1 red-component add-layer l2 " +
            "set-filter l1 darken-value set-filter l2 brighten-luma quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.start();
    assertEquals(initialText +
                    "Error: Project has not been created or loaded yet.\n" + "'p1' collage " +
                    "project created successfully!\n" +
                    "Error: Layer 'l1' does not exist.\n" + "'l1' layer added.\n" +
                    "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
                    "Image added successfully to layer 'l1' at position (10, 10).\n"
                    + "Current Project Structure:\n" +
                    "Layer #1: l1 (normal)\n" + " - Image #1: res/example1.ppm\n" +
                    "Current Project Structure:\n" + "Layer #1: l1 (red-component)\n" +
                    " - Image #1: res/example1.ppm\n" + "'l2' layer added.\n" +
                    "Current Project Structure:\n" + "Layer #1: l1 (red-component)\n" +
                    " - Image #1: res/example1.ppm\n" + "Layer #2: l2 (normal)\n" +
                    "Current Project Structure:\n" + "Layer #1: l1 (darken-value)\n" +
                    " - Image #1: res/example1.ppm\n" + "Layer #2: l2 (normal)\n" +
                    "Current Project Structure:\n" + "Layer #1: l1 (darken-value)\n" +
                    " - Image #1: res/example1.ppm\n" + "Layer #2: l2 (brighten-luma)\n",
            out.toString());
  }

  @Test
  public void testSetFilterCommand1() throws IOException {
    Reader in = new StringReader(" ");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.newProjectCommand("p1", 200, 200, 255);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/example1.ppm", 10, 10);
    cc1.setFilterCommand("l1", "red-component");
    assertEquals("'p1' collage project created successfully!\n" +
                    "'l1' layer added.\n" +
                    "Image added successfully to layer 'l1' at position (10, 10).\n",
            out.toString());
    assertEquals("red-component",
            cc1.getModel().getLayers().get(0).getFilter().toString());
  }

  @Test
  public void testSetFilterCommand2() throws IOException {
    Reader in = new StringReader(" ");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.newProjectCommand("p1", 200, 200, 255);
    cc1.addLayerCommand("l1");
    cc1.addLayerCommand("l2");
    cc1.addImageToLayerCommand("l2", "res/example1.ppm", 10, 10);
    cc1.setFilterCommand("l2", "difference");
    assertEquals("difference",
            cc1.getModel().getLayers().get(1).getFilter().toString());
    cc1.setFilterCommand("l1", "screen");
    assertEquals("screen",
            cc1.getModel().getLayers().get(0).getFilter().toString());
  }

  @Test
  public void testSetFilterCommand3() throws IOException {
    Reader in = new StringReader(" ");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.newProjectCommand("p1", 200, 200, 255);
    cc1.addLayerCommand("l1");
    cc1.addLayerCommand("l2");
    cc1.addImageToLayerCommand("l2", "res/example1.ppm", 10, 10);
    cc1.setFilterCommand("l3", "multiply");
    assertEquals("'p1' collage project created successfully!\n" +
            "'l1' layer added.\n" + "'l2' layer added.\n" +
            "Image added successfully to layer 'l2' at position (10, 10).\n" +
            "Error: Layer 'l3' does not exist.\n", out.toString());
  }

  @Test
  public void testException1() {
    Reader in = new StringReader("load-project res/proj.collage");
    Appendable out = new AppendableIOExMock();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    try {
      cc1.start();
      fail("Expected IOException");
    } catch (IOException e) {
      //do nothing
    }
  }

  @Test
  public void testException2() {
    Reader in = new StringReader("new-project p1 300 300 255 save-image ex.ppm");
    Appendable out = new AppendableIOExMock();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    try {
      cc1.start();
      fail("Expected IOException");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Test
  public void testException3() {
    Reader in = new StringReader("new-project p1 300 300 255 save-project");
    Appendable out = new AppendableIOExMock();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    try {
      cc1.start();
      fail("Expected IOException");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Test
  public void testSaveImagePPM() throws IOException {
    Reader in = new StringReader("save-image new-project p1 300 300 255 " +
            "add-layer l1 add-image-to-layer l1 res/example1.ppm " +
            "0 0 save-image image1 " +
            "save-image res/imageone.ppm add-layer l2 quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.start();
    assertEquals(initialText +
            "Error: Project has not been created or loaded yet.\n" +
            "'p1' collage project created successfully!\n" +
            "'l1' layer added.\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + "Image added successfully to layer 'l1'" +
            " at position (0, 0).\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: res/example1.ppm\n" +
            "Error: Collage images must be saved as PPM, PNG, and/or JPG/JPEG.\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: res/example1.ppm\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + " - Image #1: res/example1.ppm\n" + "'l2' " +
            "layer added.\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: res/example1.ppm\n" + "Layer #2: l2 (normal)\n", out.toString());
  }

  @Test
  public void testImageSavePPM() throws FileNotFoundException {
    Scanner sc = new Scanner(new FileInputStream("res/imageone.ppm"));
    assertEquals("P3", sc.nextLine());
    assertEquals("300 300", sc.nextLine());
    assertEquals("255", sc.nextLine());
    assertEquals("255", sc.next());
    assertEquals("150", sc.next());
    assertEquals("250", sc.next());
  }

  @Test
  public void testSaveImageJPEG() throws IOException {
    Reader in = new StringReader("new-project p1 300 300 255 " +
            "add-layer l1 add-image-to-layer l1 res/example1.ppm 0 0 " +
            "save-image res/image1-jpeg.jpeg quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.start();
    assertEquals(initialText +
            "'p1' collage project created successfully!\n" +
            "'l1' layer added.\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + "Image added successfully to layer 'l1'" +
            " at position (0, 0).\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: res/example1.ppm\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: res/example1.ppm\n", out.toString());
    Reader in2 = new StringReader("new-project p1 300 300 255 " +
            "add-layer l1 add-image-to-layer l1 res/image1-jpeg.jpeg 0 0 quit");
    StringBuilder out2 = new StringBuilder();
    ICanvas ic2 = new CollageModel();
    IView iv2 = new CollageView(out2);
    CollageController cc2 = new CollageControllerImpl(ic2, iv2, in2);
    cc2.start();
  }

  @Test
  public void testSaveImageJPG() throws IOException {
    Reader in = new StringReader("new-project p1 300 300 255 " +
            "add-layer l1 add-image-to-layer l1 res/example1.ppm 0 0 " +
            "save-image res/image1-jpg.jpg quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.start();
    assertEquals(initialText +
            "'p1' collage project created successfully!\n" +
            "'l1' layer added.\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + "Image added successfully to layer 'l1'" +
            " at position (0, 0).\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: res/example1.ppm\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: res/example1.ppm\n", out.toString());
    Reader in2 = new StringReader("new-project p1 300 300 255 " +
            "add-layer l1 add-image-to-layer l1 res/image1-jpg.jpg 0 0 quit");
    StringBuilder out2 = new StringBuilder();
    ICanvas ic2 = new CollageModel();
    IView iv2 = new CollageView(out2);
    CollageController cc2 = new CollageControllerImpl(ic2, iv2, in2);
    cc2.start();
  }

  @Test
  public void testSaveImagePNG() throws IOException {
    Reader in = new StringReader("new-project p1 300 300 255 " +
            "add-layer l1 add-image-to-layer l1 res/example1.ppm 0 0 " +
            "save-image res/image1-png.png quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.start();
    assertEquals(initialText +
            "'p1' collage project created successfully!\n" +
            "'l1' layer added.\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + "Image added successfully to layer 'l1'" +
            " at position (0, 0).\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: res/example1.ppm\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: res/example1.ppm\n", out.toString());
    Reader in2 = new StringReader("new-project p1 300 300 255 " +
            "add-layer l1 add-image-to-layer l1 res/image1-png.png 0 0 quit");
    StringBuilder out2 = new StringBuilder();
    ICanvas ic2 = new CollageModel();
    IView iv2 = new CollageView(out2);
    CollageController cc2 = new CollageControllerImpl(ic2, iv2, in2);
    cc2.start();
  }

  @Test
  public void testSaveImageCommand1() throws IOException {
    Reader in = new StringReader("save-image new-project p1 300 300 255 " +
            "add-layer l1 add-image-to-layer l1 res/example1.ppm 0 0 " +
            "save-image image1 " +
            "save-image imageone.ppm add-layer l2 quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.newProjectCommand("p1", 300, 300, 255);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/example1.ppm", 0, 0);
    cc1.saveImageCommand("res/imagetwo.ppm");
    assertEquals("'p1' collage project created successfully!\n" +
            "'l1' layer added.\n" +
            "Image added successfully to layer 'l1' at position (0, 0).\n", out.toString());
  }

  @Test
  public void testImageSaveCommand2() throws FileNotFoundException {
    Scanner sc = new Scanner(new FileInputStream("res/imagetwo.ppm"));
    assertEquals("P3", sc.nextLine());
    assertEquals("300 300", sc.nextLine());
    assertEquals("255", sc.nextLine());
    assertEquals("255", sc.next());
    assertEquals("150", sc.next());
    assertEquals("250", sc.next());
  }

  @Test
  public void testSaveImageCommand3() throws IOException {
    Reader in = new StringReader(" ");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.newProjectCommand("p1", 300, 300, 255);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/example1.ppm", 0, 0);
    cc1.saveImageCommand("imagetwo");
    assertEquals("'p1' collage project created successfully!\n" +
            "'l1' layer added.\n" +
            "Image added successfully to layer 'l1' at position (0, 0).\n" +
            "Error: Collage images must be saved as PPM, PNG, and/or JPG/JPEG.\n", out.toString());
  }

  @Test
  public void testSaveProject() throws IOException {
    Reader in = new StringReader("save-project new-project project2 300 300 255 " +
            "save-project add-layer l1 add-image-to-layer l1 " +
            "res/example1.ppm " +
            "0 0 save-image image " +
            "save-image ex0.ppm add-layer l2 add-image-to-layer l2 " +
            "res/example1.ppm 30 70 " +
            "set-filter l1 brighten-value save-project set-filter l2 " +
            "red-component set-filter " +
            "l1 red-component quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.start();
    assertEquals(initialText +
            "Error: Project has not been created or loaded yet.\n" +
            "'project2' collage project created successfully!\n" +
            "Error: Project must have at least one layer before being saved.\n" +
            "'l1' layer added.\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + "Image added successfully to layer 'l1' at position " +
            "(0, 0).\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: res/example1.ppm\n" + "Error: Collage images must be saved as "
            + "PPM, PNG, and/or JPG/JPEG.\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: res/example1.ppm\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" +
            " - Image #1: res/example1.ppm\n" + "'l2' layer added.\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: res/example1.ppm\n" + "Layer #2: l2 (normal)\n" +
            "Image added successfully to layer 'l2' at position (30, 70).\n"
            + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + " - Image #1: res/example1.ppm\n" +
            "Layer #2: l2 (normal)\n" + " - Image #1: res/example1.ppm\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (brighten-value)\n" +
            " - Image #1: res/example1.ppm\n" + "Layer #2: l2 (normal)\n" +
            " - Image #1: res/example1.ppm\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (brighten-value)\n" + " - Image #1: res/example1.ppm\n" +
            "Layer #2: l2 (normal)\n" + " - Image #1: res/example1.ppm\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (brighten-value)\n" +
            " - Image #1: res/example1.ppm\n" + "Layer #2: l2 (red-component)\n" +
            " - Image #1: res/example1.ppm\n" + "Current Project Structure:\n"
            + "Layer #1: l1 (red-component)\n" +
            " - Image #1: res/example1.ppm\n" + "Layer #2: l2 (red-component)\n" +
            " - Image #1: res/example1.ppm\n", out.toString());
    Scanner sc = new Scanner(new FileInputStream("project2.collage"));
    assertEquals("project2", sc.nextLine());
    assertEquals("300 300", sc.nextLine());
    assertEquals("255", sc.nextLine());
    assertEquals("l1 brighten-value", sc.nextLine());
    assertEquals("255 150 250 255", sc.nextLine());
  }

  @Test
  public void testSaveProjectCommand1() throws IOException {
    Reader in = new StringReader(" ");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.newProjectCommand("project2", 300, 300, 255);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/example1.ppm", 0, 0);
    cc1.saveProjectCommand("project2.collage");
    assertEquals("'project2' collage project created successfully!\n" +
            "'l1' layer added.\n" + "Image added successfully to layer 'l1' at position " +
            "(0, 0).\n", out.toString());
    Scanner sc = new Scanner(new FileInputStream("project2.collage"));
    assertEquals("project2", sc.nextLine());
    assertEquals("300 300", sc.nextLine());
    assertEquals("255", sc.nextLine());
    assertEquals("l1 normal", sc.nextLine());
    assertEquals("255 150 250 255", sc.nextLine());
    assertEquals(250, cc1.getModel().getImage().getPixel(0, 0).getBlue());
  }

  @Test
  public void testLoadProject1() throws IOException {
    Reader in = new StringReader("new-project project1 300 300 255 " +
            "load-project quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.start();
    assertEquals(initialText + "'project1' collage project created successfully!\n" +
            "Error: Project has already been created/loaded.\n", out.toString());
  }

  @Test
  public void testLoadProject2() throws IOException {
    Reader in = new StringReader("load-project exproj1 load-project res/proj.collage " +
            "add-image-to-layer layer1 res/example1.ppm 150 150 " +
            "save-project set-filter" +
            " layer1 blue-component " +
            "add-layer layer2 save-project set-filter layer1 " +
            "darken-value quit");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    CollageController cc1 = new CollageControllerImpl(ic1, iv1, in);
    BufferedReader reader = new BufferedReader(new FileReader("res/proj.collage"));
    reader.readLine();
    reader.readLine();
    reader.readLine();
    assertEquals("l1 normal", reader.readLine());
    cc1.start();
    assertEquals(initialText + "Error: Collage projects must be saved with " +
            "'.collage' extension.\n" +
            "Error: Unable to read: exproj1\n" + "'proj' collage project loaded " +
            "successfully!\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: Unknown\n" + "Error: Offset position must be within range " +
            "of collage grid.\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: Unknown\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + " - Image #1: Unknown\n" +
            "Error: Layer 'layer1' does not exist.\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + " - Image #1: Unknown\n" +
            "'layer2' layer added.\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + " - Image #1: Unknown\n" +
            "Layer #2: layer2 (normal)\n" + "Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + " - Image #1: Unknown\n" +
            "Layer #2: layer2 (normal)\n" + "Error: Layer 'layer1' does not exist.\n" +
            "Current Project Structure:\n" + "Layer #1: l1 (normal)\n" +
            " - Image #1: Unknown\n" + "Layer #2: layer2 (normal)\n", out.toString());
  }

  @Test
  public void testLoadProjectCommand() throws IOException {
    Reader in = new StringReader("");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
    BufferedReader reader = new BufferedReader(new FileReader("res/proj2.collage"));
    reader.readLine();
    assertEquals("3 3", reader.readLine());
    reader.readLine();
    assertEquals("l1 normal", reader.readLine());
    cc1.newProjectCommand("", 4, 4, 255);
    cc1.loadProjectCommand("res/proj2.collage");
    assertEquals(3, cc1.getModel().getImage().getHeight());
    assertEquals("'' collage project created successfully!\n" +
            "'proj' collage project loaded successfully!\n", out.toString());
    assertEquals(3, cc1.getModel().getImage().getWidth());
  }

  @Test
  public void testHelpCommand() throws IOException {
    Reader in = new StringReader(" ");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
    cc1.helpMenuCommand();
    assertEquals("Available Commands:\n" +
            " - new-project [project-name] [project-height] [project-width] " +
            "[project-max-value] : creates a new collage project\n" +
            " - load-project [project-path] : loads a given collage project\n" +
            " - save-project : saves the current collage project\n" +
            " - add-layer [layer-name] : adds a new, unique layer to current project\n" +
            " - add-image-to-layer [layer-name] [image-path] [x-pos] [y-pos] : adds offset" +
            " (x, y) image to given layer \n" +
            " - set-filter [layer-name] [filter-option] : sets the filter of the " +
            "given layer " +
            "as one of\n" +
            "    -> normal : does nothing to the image\n" +
            "    -> red-component : only uses the red portion of the RGB\n" +
            "    -> green-component : only uses the green portion of the RGB\n" +
            "    -> blue-component : only uses the blue portion of the RGB\n" +
            "    -> brighten-value : adds the brightness value pixel by pixel " +
            "according to value\n" +
            "    -> brighten-intensity : adds the brightness intensity pixel by pixel " +
            "according to value\n" +
            "    -> brighten-luma : adds the brightness luma pixel by pixel " +
            "according to value\n" +
            "    -> darken-value : remove the brightness value pixel by pixel " +
            "according to value\n" +
            "    -> darken-intensity : remove the brightness intensity pixel " +
            "by pixel according to" +
            " value\n" +
            "    -> darken-luma : remove the brightness luma pixel by pixel " +
            "according to value\n" +
            "    -> difference : invert the colors of the layer based on the " +
            "background layers\n" +
            "    -> multiply : darken a layer based on the background images\n" +
            "    -> screen : brighten a layer based on the background images\n" +
            " - save-image [filename] : saves current project image as PPM, JPG/JPEG, or PNG image\n" +
            " - quit : quits the project and loses all unsaved work\n" +
            " - project-structure : view the current collage project structure\n" +
            " - help : view the available commands\n", out.toString());
  }

  @Test
  public void testGetNUpdateModel() {
    Reader in = new StringReader(" ");
    StringBuilder out = new StringBuilder();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerImpl(ic1, iv1, in);
    assertEquals(ic1, cc1.getModel());
  }
}