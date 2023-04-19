import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import controller.CollageControllerGUIImpl;
import controller.CollageControllerImpl;
import controller.Features;
import model.CollageModel;
import model.ColorImage;
import model.ICanvas;
import model.ImageLayer;
import model.ImageUtil;
import model.filter.BrightenFilter;
import model.filter.Component;
import model.filter.ComponentFilter;
import model.filter.LightType;
import view.CollageView;
import view.IView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the functions and behavior of the CollageView class.
 */

public class CollageViewTest {

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
  public void testConstructor2() {
    ICanvas m1 = new CollageModel();
    Appendable a = null;
    IView v1 = new CollageView(a);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor3() {
    IView v1 = new CollageView(new CollageControllerGUIImpl(null));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor4() {
    ICanvas m1 = new CollageModel();
    Features c1 = null;
    IView v1 = new CollageView(c1);
  }

  @Test(expected = NullPointerException.class)
  public void testUpdateViewIOEX() throws IOException {
    Appendable out = new AppendableIOExMock();
    ICanvas m1 = new CollageModel(30, 30, "viewEx");
    IView v1 = new CollageView(out);
    m1.addLayerToCanvas(300, 300, "l1");
    m1.addImageToLayer("l1", new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels()),
            0 ,0);
    m1.getLayers().get(0).setFilter(new ComponentFilter(Component.BLUE));
    v1.updateView();
  }

  @Test
  public void testDisplayProjectStructure1() throws IOException {
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("");
    ICanvas m1 = new CollageModel(100, 100, "viewEx");
    IView v1 = new CollageView(out);
    Features c1 = new CollageControllerImpl(m1, v1, in);
    m1.addLayerToCanvas(300, 300, "layer1");
    m1.addImageToLayer("layer1", new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels()),
            0 ,0);
    m1.getLayers().get(0).setFilter(new BrightenFilter(LightType.INTENSITY));
    m1.addImageToLayer("layer1", new ColorImage(30 ,40, 255,
            ImageUtil.readPPM("res/example1.ppm").getPixels(), "image2"), 2 ,15);
    m1.addLayerToCanvas(100, 200, "layer2");
    v1.displayProjectStructure();
    assertEquals("Current Project Structure:\n" + "Layer #1: layer1 (brighten-intensity)\n" +
            " - Image #1: Unknown\n" + " - Image #2: image2\n" +
            "Layer #2: layer2 (normal)\n", out.toString());
  }

  @Test()
  public void testDisplayProjectStructure2() throws IOException {
    StringBuilder out = new StringBuilder();
    StringReader in = new StringReader("");
    ICanvas m1 = new CollageModel(100, 100, "viewEx");
    IView v1 = new CollageView(out);
    Features c1 = new CollageControllerImpl(m1, v1, in);
    v1.displayProjectStructure();
    assertEquals("", out.toString());
  }

  @Test
  public void testDispProjStructIOEX() throws IOException {
    Appendable out = new AppendableIOExMock();
    Readable in = new StringReader("");
    ICanvas m1 = new CollageModel(30, 30, "viewEx");
    IView v1 = new CollageView(out);
    Features c1 = new CollageControllerImpl(m1, v1, in);
    m1.addLayerToCanvas(300, 300, "l1");
    m1.addImageToLayer("l1", new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels()),
            0, 0);
    m1.getLayers().get(0).setFilter(new ComponentFilter(Component.BLUE));
    try {
      v1.displayProjectStructure();
      fail("Expected IOException");
    } catch (IOException ignore) {
      //do nothing
    }
  }

  @Test
  public void testDisplayCommands() throws IOException {
    StringBuilder out = new StringBuilder();
    ICanvas m1 = new CollageModel(30, 30, "viewEx");
    IView v1 = new CollageView(out);
    v1.displayCommands();
    assertEquals("Available Commands:\n" +
            " - new-project [project-name] [project-height] [project-width] " +
            "[project-max-value] : creates a new collage project\n" +
            " - load-project [project-path] : loads a given collage project\n" +
            " - save-project : saves the current collage project\n" +
            " - add-layer [layer-name] : adds a new, unique layer to current project\n" +
            " - add-image-to-layer [layer-name] [image-path] [x-pos] [y-pos] : adds offset " +
            "(x, y) image to given layer \n" +
            " - set-filter [layer-name] [filter-option] : sets the filter of the given " +
            "layer as one of\n" +
            "    -> normal : does nothing to the image\n" +
            "    -> red-component : only uses the red portion of the RGB\n" +
            "    -> green-component : only uses the green portion of the RGB\n" +
            "    -> blue-component : only uses the blue portion of the RGB\n" +
            "    -> brighten-value : adds the brightness value pixel by pixel " +
            "according to value\n" +
            "    -> brighten-intensity : adds the brightness intensity pixel by pixel " +
            "according to value\n" +
            "    -> brighten-luma : adds the brightness luma pixel by pixel according to value\n" +
            "    -> darken-value : remove the brightness value pixel by pixel " +
            "according to value\n" +
            "    -> darken-intensity : remove the brightness intensity pixel by pixel " +
            "according to value\n" +
            "    -> darken-luma : remove the brightness luma pixel by pixel according to value\n" +
            "    -> difference : invert the colors of the layer based on the background layers\n" +
            "    -> multiply : darken a layer based on the background images\n" +
            "    -> screen : brighten a layer based on the background images\n" +
            " - save-image [filename] : saves current project image as PPM, JPG/JPEG, or PNG image\n" +
            " - quit : quits the project and loses all unsaved work\n" +
            " - project-structure : view the current collage project structure\n" +
            " - help : view the available commands\n", out.toString());
  }

  @Test
  public void testDispCommandsIOEX() throws IOException {
    Appendable out = new AppendableIOExMock();
    ICanvas m1 = new CollageModel(30, 30, "viewEx");
    IView v1 = new CollageView(out);
    try {
      v1.displayCommands();
      fail("Expected IOException");
    } catch (IOException ignore) {
      //do nothing
    }
  }

  @Test
  public void testRenderMessage() throws IOException {
    StringBuilder out = new StringBuilder();
    ICanvas m1 = new CollageModel(30, 30, "viewEx");
    IView v1 = new CollageView(out);
    v1.renderMessage("hello");
    v1.renderMessage("goodbye");
    assertEquals("hello\n" + "goodbye\n", out.toString());
  }

  @Test
  public void testRenderMessageIOEX() throws IOException {
    Appendable out = new AppendableIOExMock();
    ICanvas m1 = new CollageModel(30, 30, "viewEx");
    IView v1 = new CollageView(out);
    try {
      v1.renderMessage("hello");
      fail("Expected IOException");
    } catch (IOException ignore) {
      //do nothing
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetControllerWEx() throws IOException {
    Appendable out = new AppendableIOExMock();
    ICanvas m1 = new CollageModel(30, 30, "viewEx");
    IView v1 = new CollageView(out);
    v1.displayProjectStructure();
  }
}