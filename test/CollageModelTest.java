import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import model.CollageModel;
import model.ColorImage;
import model.ColorPixel;
import model.ICanvas;
import model.IImage;
import model.ILayer;
import model.IPixel;
import model.ImageLayer;
import model.ImageUtil;
import model.filter.BrightenFilter;
import model.filter.Component;
import model.filter.ComponentFilter;
import model.filter.IFilter;
import model.filter.LightType;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functions and behavior of the CollageModel class.
 */
public class CollageModelTest {

  @Test(expected = IllegalArgumentException.class)
  public void testModelConstructor1() {
    ICanvas ic = new CollageModel(-20, 20, new IPixel[20][20], new ArrayList<ILayer>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelConstructor2() {
    ICanvas ic = new CollageModel(200, 0, new IPixel[200][1], new ArrayList<ILayer>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelConstructor3() {
    ICanvas ic = new CollageModel(-20, 20, "canvas1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelConstructor4() {
    ICanvas ic = new CollageModel(200, -1, "canvas2");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelConstructor5() {
    ICanvas ic = new CollageModel(200, 100, null, new ArrayList<ILayer>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelConstructor6() {
    ICanvas ic = new CollageModel(200, 100, new IPixel[30][30], null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelConstructor7() {
    ICanvas ic = new CollageModel(300, 100, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSameLayerName() {
    ICanvas ic1 = new CollageModel();
    ic1.addLayerToCanvas(30, 30, "L1");
    ic1.addLayerToCanvas(30, 30, "L2");
    ic1.addLayerToCanvas(30, 30, "L1");
  }

  @Test
  public void testAddLayerToCanvas1() throws IOException {
    ICanvas ic1 = new CollageModel();
    assertEquals(255, ic1.getImage().getPixel(0, 0).getRed());
    assertEquals(255, ic1.getImage().getPixel(0, 0).getGreen());
    assertEquals(255, ic1.getImage().getPixel(0, 0).getBlue());
    ic1.addLayerToCanvas(250, 250, "first layer");
    ic1.addImageToLayer("first layer", new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels()),
            0, 0);
    assertEquals(new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels()).getPixel(0,
                    0).getRed(),
            ic1.getImage().getPixel(0, 0).getRed());
    assertEquals(new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels()).getPixel(0,
                    0).getGreen(),
            ic1.getImage().getPixel(0, 0).getGreen());
    assertEquals(new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels()).getPixel(0,
                    0).getBlue(),
            ic1.getImage().getPixel(0, 0).getBlue());
  }

  @Test
  public void testAddLayerToCanvas2() {
    ICanvas ic2 = new CollageModel(1, 3, "proj1");
    assertEquals(255, ic2.getImage().getPixel(0, 1).getRed());
    assertEquals(255, ic2.getImage().getPixel(0, 1).getGreen());
    assertEquals(255, ic2.getImage().getPixel(0, 1).getBlue());
    IPixel pix1 = new ColorPixel(0, 40, 30, 255);
    IPixel pix2 = new ColorPixel(200, 79, 20, 255);
    IPixel pix3 = new ColorPixel(130, 10, 245, 255);
    IPixel[][] pixs1 = new ColorPixel[1][3];
    pixs1[0][0] = pix1;
    pixs1[0][1] = pix2;
    pixs1[0][2] = pix3;
    IImage im2 = new ColorImage(pixs1);
    ILayer layer2 = new ImageLayer(1, 3, "layer 2");
    layer2.addImageToLayer(im2, 0, 0);
    ic2.addLayerToCanvas(1, 3, "layer 2");
    ic2.addLayerToCanvas(1, 1, "Layer 3");
    assertEquals(2, ic2.getLayers().size());
    assertEquals("Layer 3", ic2.getLayers().get(1).getName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoLayerFound() throws IOException {
    ICanvas ic1 = new CollageModel();
    ic1.addLayerToCanvas(30, 30, "L0");
    ic1.addImageToLayer("L1", new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels()),
            20, 20);
  }

  @Test
  public void testAddImageToLayer1() throws IOException {
    ICanvas ic1 = new CollageModel();
    ILayer l0 = new ImageLayer(30, 30, "L0");
    assertEquals(0, l0.getImages().size());
    ic1.addLayerToCanvas(30, 30, "L0");
    ic1.addImageToLayer("L0", new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels()),
            20, 20);
    assertEquals(1, ic1.getLayers().get(0).getImages().size());
  }

  @Test
  public void testAddImageToLayer2() {
    ICanvas ic2 = new CollageModel(1, 3, "proj2");
    IPixel pix1 = new ColorPixel(0, 40, 30, 255);
    IPixel pix2 = new ColorPixel(200, 79, 20, 255);
    IPixel pix3 = new ColorPixel(130, 10, 245, 255);
    IPixel[][] pixs1 = new ColorPixel[1][3];
    pixs1[0][0] = pix1;
    pixs1[0][1] = pix2;
    pixs1[0][2] = pix3;
    IImage im2 = new ColorImage(pixs1);
    IPixel[][] pixs2 = new ColorPixel[1][3];
    pixs2[0][0] = pix2;
    pixs2[0][1] = pix3;
    pixs2[0][2] = pix1;
    IImage im3 = new ColorImage(pixs2);
    IPixel[][] pixs3 = new ColorPixel[1][3];
    pixs3[0][0] = pix3;
    pixs3[0][1] = pix1;
    pixs3[0][2] = pix2;
    IImage im4 = new ColorImage(pixs3);
    ILayer layer2 = new ImageLayer(1, 3, "layer 2");
    ic2.addLayerToCanvas(1, 3, "layer 2");
    ic2.addLayerToCanvas(30, 30, "L3");
    ic2.getLayers().get(1).addImageToLayer(im2, 0, 0);
    ic2.getLayers().get(1).addImageToLayer(im3, 1, 0);
    ic2.addImageToLayer("L3", im4, 0, 0);
    ic2.addLayerToCanvas(15, 3, "new layer");
    assertEquals(3, ic2.getLayers().get(1).getImages().size());
  }

  @Test
  public void testGetLayers1() {
    ICanvas ic1 = new CollageModel();
    assertEquals(0, ic1.getLayers().size());
    ic1.addLayerToCanvas(30, 50, "layer 1");
    assertEquals(1, ic1.getLayers().size());
    assertEquals("layer 1", ic1.getLayers().get(0).getName());
  }

  @Test
  public void testGetLayers2() {
    ICanvas ic2 = new CollageModel(100, 200, "proj3");
    assertEquals(0, ic2.getLayers().size());
    ic2.addLayerToCanvas(30, 50, "layer 1");
    assertEquals(1, ic2.getLayers().size());
    ic2.addLayerToCanvas(100, 150, "layer 2");
    assertEquals("layer 1", ic2.getLayers().get(0).getName());
    assertEquals("layer 2", ic2.getLayers().get(1).getName());
  }

  @Test
  public void testGetLayers3() {
    ArrayList<ILayer> ll = new ArrayList<>();
    ll.add(new ImageLayer(40, 60, "L1"));
    ll.add(new ImageLayer(100, 100, "L2"));
    ll.add(new ImageLayer(67, 32, "L3"));
    ICanvas ic3 = new CollageModel(100, 100, new IPixel[100][100], ll);
    assertEquals(3, ic3.getLayers().size());
    ic3.addLayerToCanvas(90, 80, "L4");
    assertEquals(4, ic3.getLayers().size());
    assertEquals("L2", ic3.getLayers().get(1).getName());
    assertEquals("L4", ic3.getLayers().get(3).getName());
  }

  @Test
  public void testUpdatePixels1() throws IOException {
    ILayer l1 = new ImageLayer(10, 10, "first layer");
    ILayer l2 = new ImageLayer(15, 20, "second layer");
    ILayer l3 = new ImageLayer(4, 7, "third layer");
    IPixel pix1 = new ColorPixel(0, 40, 30, 200, new ComponentFilter(Component.GREEN));
    IPixel pix2 = new ColorPixel(200, 79, 20, 180);
    IPixel pix3 = new ColorPixel(130, 10, 245, 0);
    IPixel[][] pixs1 = new ColorPixel[1][3];
    pixs1[0][0] = pix1;
    pixs1[0][1] = pix2;
    pixs1[0][2] = pix3;
    IImage im2 = new ColorImage(pixs1);
    ICanvas ic4 = new CollageModel(2, 2, "canvas1");
    assertEquals(255, ic4.getImage().getPixel(0, 0).getRed());
    assertEquals(255, ic4.getImage().getPixel(0, 0).getGreen());
    assertEquals(255, ic4.getImage().getPixel(0, 0).getBlue());
    assertEquals(255, ic4.getImage().getPixel(1, 1).getRed());
    assertEquals(255, ic4.getImage().getPixel(1, 1).getGreen());
    assertEquals(255, ic4.getImage().getPixel(1, 1).getBlue());
    ic4.addLayerToCanvas(10, 10, "first layer");
    ic4.addLayerToCanvas(15, 20, "second layer");
    ic4.addLayerToCanvas(4, 7, "third layer");
    ic4.addImageToLayer("third layer", new ColorImage(ImageUtil.readPPM(
            "res/example2.ppm").getPixels()), 0, 0);
    assertEquals(0, ic4.getImage().getPixel(0, 0).getRed());
    assertEquals(0, ic4.getImage().getPixel(0, 0).getGreen());
    assertEquals(0, ic4.getImage().getPixel(0, 0).getBlue());
    assertEquals(0, ic4.getImage().getPixel(1, 1).getRed());
    assertEquals(15, ic4.getImage().getPixel(1, 1).getGreen());
    assertEquals(7, ic4.getImage().getPixel(1, 1).getBlue());
    ic4.getLayers().get(1).applyFilter(new BrightenFilter(LightType.LUMA));
    ic4.addImageToLayer("third layer", im2, 0, 0);
    assertEquals(0, ic4.getImage().getPixel(0, 0).getRed());
    assertEquals(40, ic4.getImage().getPixel(0, 0).getGreen());
    assertEquals(30, ic4.getImage().getPixel(0, 0).getBlue());
    assertEquals(200, ic4.getImage().getPixel(0, 1).getRed());
    assertEquals(79, ic4.getImage().getPixel(0, 1).getGreen());
    assertEquals(20, ic4.getImage().getPixel(0, 1).getBlue());
  }

  @Test
  public void testGetImage1() {
    ICanvas ic1 = new CollageModel();
    assertEquals(1, ic1.getImage().getHeight());
    assertEquals(1, ic1.getImage().getWidth());
    assertEquals(1, ic1.getImage().getPixel(0, 0).getAlpha());
    assertEquals(255, ic1.getImage().getPixel(0, 0).getRed());
    assertEquals(255, ic1.getImage().getPixel(0, 0).getGreen());
    assertEquals(255, ic1.getImage().getPixel(0, 0).getBlue());
  }

  @Test
  public void testGetImage2() {
    IPixel pix1 = new ColorPixel(0, 40, 30, 255);
    IPixel pix2 = new ColorPixel(200, 79, 20, 255);
    IPixel pix3 = new ColorPixel(130, 10, 245, 255);
    IPixel[][] pixs1 = new ColorPixel[1][3];
    pixs1[0][0] = pix1;
    pixs1[0][1] = pix2;
    pixs1[0][2] = pix3;
    IImage im2 = new ColorImage(pixs1);
    ICanvas ic2 = new CollageModel(1, 3, "canvas2");
    ic2.addLayerToCanvas(1, 3, "test layer");
    ic2.addImageToLayer("test layer", im2, 0, 0);
    assertEquals(1, ic2.getImage().getHeight());
    assertEquals(3, ic2.getImage().getWidth());
    assertEquals(255, ic2.getImage().getPixel(0, 0).getAlpha());
    assertEquals(0, ic2.getImage().getPixel(0, 0).getRed());
    assertEquals(40, ic2.getImage().getPixel(0, 0).getGreen());
    assertEquals(30, ic2.getImage().getPixel(0, 0).getBlue());
    assertEquals(255, ic2.getImage().getPixel(0, 1).getAlpha());
    assertEquals(200, ic2.getImage().getPixel(0, 1).getRed());
    assertEquals(79, ic2.getImage().getPixel(0, 1).getGreen());
    assertEquals(20, ic2.getImage().getPixel(0, 1).getBlue());
    assertEquals(255, ic2.getImage().getPixel(0, 2).getAlpha());
    assertEquals(130, ic2.getImage().getPixel(0, 2).getRed());
    assertEquals(10, ic2.getImage().getPixel(0, 2).getGreen());
    assertEquals(245, ic2.getImage().getPixel(0, 2).getBlue());
  }

  @Test
  public void testGetProjName() {
    ICanvas ic = new CollageModel(200, 300, "proj1");
    assertEquals("proj1", ic.getProjectName());
  }

  @Test
  public void testGetPrevIm() throws IOException {
    ICanvas m = new CollageModel(4, 4, "test");
    ILayer l1 = new ImageLayer(4, 4, "l1");
    ILayer l2 = new ImageLayer(4, 4, "l2");
    ILayer l3 = new ImageLayer(4, 4, "l3");
    m.addLayerToCanvas(4, 4, "l1");
    m.addLayerToCanvas(4, 4, "l2");
    m.addLayerToCanvas(4, 4, "l3");
    m.addImageToLayer("l2",
            new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels()), 0, 0);
    assertEquals(255, m.getPrevIm("l1").getPixel(3, 0).getRed());
    assertEquals(255, m.getPrevIm("l1").getPixel(3, 0).getGreen());
    assertEquals(255, m.getPrevIm("l1").getPixel(3, 0).getBlue());
    assertEquals(255, m.getPrevIm("l2").getPixel(3, 0).getRed());
    assertEquals(255, m.getPrevIm("l2").getPixel(3, 0).getGreen());
    assertEquals(255, m.getPrevIm("l2").getPixel(3, 0).getBlue());
    assertEquals(15, m.getPrevIm("l3").getPixel(3, 0).getRed());
    assertEquals(0, m.getPrevIm("l3").getPixel(3, 0).getGreen());
    assertEquals(15, m.getPrevIm("l3").getPixel(3, 0).getBlue());
    m.addImageToLayer("l1",
            new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels()), 0, 0);
    assertEquals(255, m.getPrevIm("l2").getPixel(3, 0).getRed());
    assertEquals(150, m.getPrevIm("l2").getPixel(3, 0).getGreen());
    assertEquals(250, m.getPrevIm("l2").getPixel(3, 0).getBlue());
    assertEquals(255, m.getPrevIm("l1").getPixel(3, 0).getRed());
    assertEquals(255, m.getPrevIm("l1").getPixel(3, 0).getGreen());
    assertEquals(255, m.getPrevIm("l1").getPixel(3, 0).getBlue());
    m.addImageToLayer("l3",
            new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels()), 0, 0);
    assertEquals(15, m.getPrevIm("l3").getPixel(3, 0).getRed());
    assertEquals(0, m.getPrevIm("l3").getPixel(3, 0).getGreen());
    assertEquals(15, m.getPrevIm("l3").getPixel(3, 0).getBlue());
  }

  @Test
  public void testGetFilter() {
    ICanvas m = new CollageModel(4, 4, "test");
    ILayer l1 = new ImageLayer(4, 4, "l1");
    ILayer l2 = new ImageLayer(4, 4, "l2");
    ILayer l3 = new ImageLayer(4, 4, "l3");
    m.addLayerToCanvas(4, 4, "l1");
    m.addLayerToCanvas(4, 4, "l2");
    m.addLayerToCanvas(4, 4, "l3");
    assertEquals("red-component", m.getFilterFromString("red-component", "l1").toString());
    assertEquals("darken-value", m.getFilterFromString("darken-value", "l2").toString());
    assertEquals("difference", m.getFilterFromString("difference", "l3").toString());
  }

  @Test(expected = RuntimeException.class)
  public void testGetFilterEx() {
    ICanvas m = new CollageModel(4, 4, "test");
    ILayer l1 = new ImageLayer(4, 4, "l1");
    ILayer l2 = new ImageLayer(4, 4, "l2");
    ILayer l3 = new ImageLayer(4, 4, "l3");
    m.addLayerToCanvas(4, 4, "l1");
    m.addLayerToCanvas(4, 4, "l2");
    m.addLayerToCanvas(4, 4, "l3");
    m.getFilterFromString("multiplyfilter", "l1").toString();
  }

  @Test
  public void testProjStruct() throws IOException {
    ICanvas m = new CollageModel(4, 4, "test");
    ILayer l1 = new ImageLayer(4, 4, "l1");
    ILayer l2 = new ImageLayer(4, 4, "l2");
    ILayer l3 = new ImageLayer(4, 4, "l3");
    assertEquals("", m.getProjectStructure());
    m.addLayerToCanvas(4, 4, "l1");
    m.addLayerToCanvas(4, 4, "l2");
    m.addLayerToCanvas(4, 4, "l3");
    m.addImageToLayer("l2", new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels()),
            0, 0);
    m.getLayers().get(2).setFilter(new BrightenFilter(LightType.VALUE));
    assertEquals("Current Project Structure:\n" +
            "Layer #1: l1 (normal)\n" + "Layer #2: l2 (normal)\n" +
            " - Image #1: Unknown\n" + "Layer #3: l3 (brighten-value)\n",
            m.getProjectStructure());
  }

  @Test
  public void testGetARGB() throws IOException {
    ICanvas m = new CollageModel(4, 4, "test");
    ILayer l1 = new ImageLayer(4, 4, "l1");
    ILayer l2 = new ImageLayer(4, 4, "l2");
    ILayer l3 = new ImageLayer(4, 4, "l3");
    m.addLayerToCanvas(4, 4, "l1");
    m.addLayerToCanvas(4, 4, "l2");
    m.addLayerToCanvas(4, 4, "l3");
    m.addImageToLayer("l2", new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels()),
            0, 0);
    assertEquals(251662087, m.getARGB(1, 1, 255, 1.0));
    assertEquals(134742024, m.getARGB(3, 0, 255, 2.0));
    assertEquals(1275087907, m.getARGB(1, 1, 100, 0.5));
  }

  @Test
  public void testSetModel1() {
    ICanvas m = new CollageModel();
    assertEquals(1, m.setModel().getImage().getHeight());
  }

  @Test
  public void testSetModel2() {
    ICanvas m = new CollageModel();
    assertEquals(300, m.setModel(300, 200, "p1").getImage().getHeight());
  }

  @Test
  public void testSetModel3() {
    ICanvas m = new CollageModel();
    assertEquals(150, m.setModel(150, 234, new IPixel[150][234],
            new ArrayList<ILayer>()).getImage().getHeight());
  }

  @Test
  public void testCreateImage1() throws IOException {
    ICanvas m = new CollageModel();
    assertEquals(4, m.createImage("res/example2.ppm").getHeight());
  }

  @Test
  public void testCreateImage2() throws IOException {
    ICanvas m = new CollageModel();
    assertEquals(20, m.createImage(new IPixel[20][30], 200).getHeight());
  }

  @Test
  public void testCreatePixel() {
    ICanvas m = new CollageModel();
    assertEquals(200, m.createPixel(200, 50, 23, 255,
            new BrightenFilter(LightType.VALUE)).getRed());
  }

  @Test
  public void testAddFilter() {
    ICanvas m = new CollageModel();
    IFilter filter = new ComponentFilter(Component.BLUE);
    m.addLayerToCanvas(300, 300, "test layer");
    m.addFilter(filter, "test layer");
    assertEquals("blue-component", m.getLayers().get(0).getFilter().toString());
  }
}