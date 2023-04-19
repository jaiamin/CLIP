import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import controller.CollageControllerGUIImpl;
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
 * Tests the functions and behavior of the CollageControllerGUIImpl class.
 */
public class CollageControllerGUIImplTest {

  /**
   * Creates a mock Collage Project Model.
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
      return null;
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
      return new TestingModel(log);
    }

    @Override
    public ICanvas setModel(int height, int width, String name) {
      return new CollageModel(height, width, name);
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

  /**
   * Creates a mock View.
   */
  public static class TestingView implements IView {
    ICanvas ic;
    StringBuilder log;

    public TestingView(ICanvas model, StringBuilder log) {
      this.ic = model;
      this.log = log;
    }

    @Override
    public void setFrame(int height, int width, String token, int maxValue, boolean gui) {
      //mock
    }

    @Override
    public void updateView() {
      //mock
    }

    @Override
    public void displayProjectStructure() {
      //mock
    }

    @Override
    public void displayCommands() {
      //mock
    }

    @Override
    public void renderMessage(String message) throws IOException {
      //mock
    }

    @Override
    public void exit() {
      //mock
    }

    @Override
    public void renderGUIMessage(String message, String title, int type) {
      //mock
    }

    @Override
    public void setTextFieldText(JTextField button, String text) {
      //mock
    }

    @Override
    public void addComboBoxItem(JComboBox<String> comboBox, String item) {
      //mock
    }

    @Override
    public JTextField getAddLayerNameTF() {
      return null;
    }

    @Override
    public JComboBox<String> getAddImageToLayerNameCB() {
      return null;
    }

    @Override
    public JComboBox<String> getSetFilterLayerNameCB() {
      return null;
    }

    @Override
    public JTextField getAddImageToLayerRowTF() {
      return null;
    }

    @Override
    public JTextField getAddImageToLayerColTF() {
      return null;
    }

    @Override
    public JTextField getNewProjectNameTF() {
      return null;
    }

    @Override
    public JTextField getNewProjectHeightTF() {
      return null;
    }

    @Override
    public JTextField getNewProjectWidthTF() {
      return null;
    }

    @Override
    public JTextField getNewProjectMaxValueTF() {
      return null;
    }

    @Override
    public String helpMenuCommands() {
      return null;
    }

    @Override
    public void setController(Features controller) {

    }

    @Override
    public BufferedImage getBuffImage() {
      return null;
    }

  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor1() {
    Features cc1 = new CollageControllerGUIImpl(null);
  }

  @Test
  public void testInputs() throws IOException {
    StringBuilder log = new StringBuilder();
    ICanvas ic1 = new CollageControllerTest.TestingModel(log);
    Features cc1 = new CollageControllerGUIImpl(ic1);
    IView iv1 = new CollageView(new StringBuilder());
    cc1.setFeaturesController(iv1);
    cc1.quitCommand();
    assertEquals("", log.toString());
  }

  @Test
  public void testNewProjectCommand() throws IOException {
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerGUIImpl(ic1);
    IView iv1 = new CollageView(cc1);
    cc1.setFeaturesController(iv1);
    cc1.newProjectCommand("p1", 20, 20, 255);
    cc1.quitCommand();
    assertEquals(20, cc1.getModel().getImage().getHeight());
    assertEquals(20, cc1.getModel().getImage().getWidth());
    assertEquals("p1", cc1.getModel().getProjectName());
    assertEquals(255, cc1.getModel().getImage().getMaxValue());
  }

  @Test
  public void testNewProjectCommandGUI() throws IOException {
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerGUIImpl(ic1);
    StringBuilder log = new StringBuilder();
    IView iv1 = new TestingView(ic1, log);
    cc1.setFeaturesController(iv1);
    cc1.newProjectCommand("p1", 20, 20, 255);
    cc1.quitCommand();
    assertEquals("", log.toString());
  }

  @Test
  public void testAddLayerCommand1() throws IOException {
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerGUIImpl(ic1);
    IView iv1 = new CollageView(cc1);
    assertEquals(0, ic1.getLayers().size());
    cc1.setFeaturesController(iv1);
    cc1.newProjectCommand("p1", 30, 30, 255);
    cc1.addLayerCommand("l1");
    cc1.quitCommand();
    assertEquals(1, cc1.getModel().getLayers().size());
  }

  @Test
  public void testAddImageToLayerCommand() throws IOException {
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerGUIImpl(ic1);
    IView iv1 = new CollageView(cc1);
    assertEquals(0, cc1.getModel().getLayers().size());
    cc1.setFeaturesController(iv1);
    cc1.newProjectCommand("p1", 300, 300, 355);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/example1.ppm", 0, 0);
    cc1.quitCommand();
    assertEquals(1, cc1.getModel().getLayers().get(0).getImages().size());
    assertEquals("res/example1.ppm",
            cc1.getModel().getLayers().get(0).getImages().get(0).getFilename());
  }

  @Test
  public void testAddImageToLayerCommand2() throws IOException {
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerGUIImpl(ic1);
    IView iv1 = new CollageView(cc1);
    assertEquals(0, cc1.getModel().getLayers().size());
    cc1.setFeaturesController(iv1);
    cc1.newProjectCommand("p1", 300, 300, 355);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/example-jpeg.jpeg", 0, 0);
    cc1.quitCommand();
    assertEquals(1, cc1.getModel().getLayers().get(0).getImages().size());
    assertEquals("res/example-jpeg.jpeg",
            cc1.getModel().getLayers().get(0).getImages().get(0).getFilename());
  }

  @Test
  public void testAddImageToLayerCommand3() throws IOException {
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerGUIImpl(ic1);
    IView iv1 = new CollageView(cc1);
    assertEquals(0, cc1.getModel().getLayers().size());
    cc1.setFeaturesController(iv1);
    cc1.newProjectCommand("p1", 300, 300, 355);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/example-jpg.jpg", 0, 0);
    cc1.quitCommand();
    assertEquals(1, cc1.getModel().getLayers().get(0).getImages().size());
    assertEquals("res/example-jpg.jpg",
            cc1.getModel().getLayers().get(0).getImages().get(0).getFilename());
  }

  @Test
  public void testAddImageToLayerCommand4() throws IOException {
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerGUIImpl(ic1);
    IView iv1 = new CollageView(cc1);
    assertEquals(0, cc1.getModel().getLayers().size());
    cc1.setFeaturesController(iv1);
    cc1.newProjectCommand("p1", 300, 300, 355);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/img.png", 0, 0);
    cc1.quitCommand();
    assertEquals(1, cc1.getModel().getLayers().get(0).getImages().size());
    assertEquals("res/img.png",
            cc1.getModel().getLayers().get(0).getImages().get(0).getFilename());
  }

  @Test
  public void testSetFilterCommand1() throws IOException {
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerGUIImpl(ic1);
    IView iv1 = new CollageView(cc1);
    cc1.setFeaturesController(iv1);
    cc1.newProjectCommand("p1", 200, 200, 255);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/example1.ppm", 10, 10);
    cc1.setFilterCommand("l1", "red-component");
    cc1.quitCommand();
    assertEquals("red-component",
            cc1.getModel().getLayers().get(0).getFilter().toString());
  }

  @Test
  public void testSetFilterCommand2() throws IOException {
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerGUIImpl(ic1);
    IView iv1 = new CollageView(cc1);
    cc1.setFeaturesController(iv1);
    cc1.newProjectCommand("p1", 200, 200, 255);
    cc1.addLayerCommand("l1");
    cc1.addLayerCommand("l2");
    cc1.addImageToLayerCommand("l2", "res/example1.ppm", 10, 10);
    cc1.setFilterCommand("l2", "difference");
    assertEquals("difference",
            cc1.getModel().getLayers().get(1).getFilter().toString());
    cc1.setFilterCommand("l1", "screen");
    cc1.quitCommand();
    assertEquals("screen",
            cc1.getModel().getLayers().get(0).getFilter().toString());
  }

  @Test(expected = NullPointerException.class)
  public void testException1() throws IOException {
    Appendable out = new CollageControllerGUIImplTest.AppendableIOExMock();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerGUIImpl(ic1);
    cc1.setFeaturesController(iv1);
    cc1.newProjectCommand("p1test", 30, 30, 200);
  }

  @Test
  public void testIOEException2() {
    Appendable out = new CollageControllerGUIImplTest.AppendableIOExMock();
    ICanvas ic1 = new CollageModel();
    IView iv1 = new CollageView(out);
    Features cc1 = new CollageControllerGUIImpl(ic1);
    cc1.setFeaturesController(iv1);
    try {
      cc1.loadProjectCommand("ex01.collage");
      fail("Expected IOException");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Test
  public void testSaveImageCommand1() throws IOException {
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerGUIImpl(ic1);
    IView iv1 = new CollageView(cc1);
    cc1.setFeaturesController(iv1);
    cc1.newProjectCommand("p1", 300, 300, 255);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/example1.ppm", 0, 0);
    cc1.saveImageCommand("res/imagetwo.ppm");
    cc1.quitCommand();
    Scanner sc = new Scanner(new FileInputStream("res/imagetwo.ppm"));
    assertEquals("P3", sc.nextLine());
    assertEquals("300 300", sc.nextLine());
    assertEquals("255", sc.nextLine());
    assertEquals("255", sc.next());
    assertEquals("150", sc.next());
    assertEquals("250", sc.next());
  }

  @Test
  public void testSaveImageCommand2() throws IOException {
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerGUIImpl(ic1);
    IView iv1 = new CollageView(cc1);
    cc1.setFeaturesController(iv1);
    cc1.newProjectCommand("p1", 300, 300, 255);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/example1.ppm", 0, 0);
    iv1.updateView();
    cc1.saveImageCommand("res/imagetwo.jpg");
    cc1.quitCommand();
    ICanvas ic2 = new CollageModel();
    Features cc2 = new CollageControllerGUIImpl(ic2);
    IView iv2 = new CollageView(cc2);
    cc2.setFeaturesController(iv2);
    cc2.newProjectCommand("p1", 300, 300, 255);
    cc2.addLayerCommand("l1");
    cc2.addImageToLayerCommand("l1", "res/imagetwo.jpg", 0, 0);
    assertEquals(249, cc2.getModel().getImage().getPixel(50, 100).getBlue());
    cc2.quitCommand();
  }

  @Test
  public void testSaveImageCommand3() throws IOException {
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerGUIImpl(ic1);
    IView iv1 = new CollageView(cc1);
    cc1.setFeaturesController(iv1);
    cc1.newProjectCommand("p1", 300, 300, 255);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/example1.ppm", 0, 0);
    iv1.updateView();
    cc1.saveImageCommand("res/imagetwo.jpeg");
    cc1.quitCommand();
    ICanvas ic2 = new CollageModel();
    Features cc2 = new CollageControllerGUIImpl(ic2);
    IView iv2 = new CollageView(cc2);
    cc2.setFeaturesController(iv2);
    cc2.newProjectCommand("p1", 300, 300, 255);
    cc2.addLayerCommand("l1");
    cc2.addImageToLayerCommand("l1", "res/imagetwo.jpeg", 0, 0);
    assertEquals(249, cc2.getModel().getImage().getPixel(50, 100).getBlue());
    cc2.quitCommand();
  }

  @Test
  public void testSaveImageCommand4() throws IOException {
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerGUIImpl(ic1);
    IView iv1 = new CollageView(cc1);
    cc1.setFeaturesController(iv1);
    cc1.newProjectCommand("p1", 300, 300, 255);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/example1.ppm", 0, 0);
    iv1.updateView();
    cc1.saveImageCommand("res/imagetwo.png");
    cc1.quitCommand();
    ICanvas ic2 = new CollageModel();
    Features cc2 = new CollageControllerGUIImpl(ic2);
    IView iv2 = new CollageView(cc2);
    cc2.setFeaturesController(iv2);
    cc2.newProjectCommand("p1", 300, 300, 255);
    cc2.addLayerCommand("l1");
    cc2.addImageToLayerCommand("l1", "res/imagetwo.png", 0, 0);
    assertEquals(250, cc2.getModel().getImage().getPixel(50, 100).getBlue());
    cc2.quitCommand();
  }

  @Test
  public void testSaveProjectCommand1() throws IOException {
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerGUIImpl(ic1);
    IView iv1 = new CollageView(cc1);
    cc1.setFeaturesController(iv1);
    cc1.newProjectCommand("project2", 300, 300, 255);
    cc1.addLayerCommand("l1");
    cc1.addImageToLayerCommand("l1", "res/example1.ppm", 0, 0);
    cc1.saveProjectCommand("project2.collage");
    cc1.quitCommand();
    Scanner sc = new Scanner(new FileInputStream("project2.collage"));
    assertEquals("project2", sc.nextLine());
    assertEquals("300 300", sc.nextLine());
    assertEquals("255", sc.nextLine());
    assertEquals("l1 normal", sc.nextLine());
    assertEquals("255 150 250 255", sc.nextLine());
    assertEquals(250, cc1.getModel().getImage().getPixel(0, 0).getBlue());
  }

  @Test
  public void testLoadProjectCommand() throws IOException {
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerGUIImpl(ic1);
    IView iv1 = new CollageView(cc1);
    cc1.setFeaturesController(iv1);
    BufferedReader reader = new BufferedReader(new FileReader("res/proj2.collage"));
    reader.readLine();
    assertEquals("3 3", reader.readLine());
    reader.readLine();
    assertEquals("l1 normal", reader.readLine());
    cc1.loadProjectCommand("res/proj2.collage");
    cc1.quitCommand();
    assertEquals(3, cc1.getModel().getImage().getHeight());
    assertEquals(3, cc1.getModel().getImage().getWidth());
    assertEquals(1, cc1.getModel().getLayers().size());
    assertEquals("l1", cc1.getModel().getLayers().get(0).getName());
    assertEquals("normal", cc1.getModel().getLayers().get(0).getFilter().toString());
  }
  @Test
  public void testGetNUpdateModel() {
    ICanvas ic1 = new CollageModel();
    Features cc1 = new CollageControllerGUIImpl(ic1);
    IView iv1 = new CollageView(cc1);
    cc1.setFeaturesController(iv1);
    assertEquals(ic1, cc1.getModel());
  }
}