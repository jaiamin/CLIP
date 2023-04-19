import org.junit.Test;

import java.io.IOException;
import java.util.List;

import model.ColorImage;
import model.ColorPixel;
import model.IImage;
import model.ILayer;
import model.IPixel;
import model.ImageLayer;
import model.ImageUtil;
import model.filter.BrightenFilter;
import model.filter.Component;
import model.filter.ComponentFilter;
import model.filter.DarkenFilter;
import model.filter.LightType;
import model.filter.NormalFilter;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functions and behavior of the ImageLayer class.
 */

public class ImageLayerTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor1() {
    ILayer l1 = new ImageLayer(0, 200, "image1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2() {
    ILayer l1 = new ImageLayer(3000, -4000, "image1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor3() {
    ILayer l1 = new ImageLayer(3000, 4000, null);
  }

  @Test
  public void testGetImage1() {
    ILayer layer1 = new ImageLayer(200, 300, "test layer");
    IImage cm1 = layer1.getImage();
    assertEquals(200, cm1.getHeight());
    assertEquals(300, cm1.getWidth());
  }

  @Test
  public void testGetImage2() throws IOException {
    ILayer layer1 = new ImageLayer(200, 300, "test layer");
    IImage cm1 = layer1.getImage();
    IPixel p1 = cm1.getPixel(20, 30);
    assertEquals(255, p1.getRed());
    layer1.addImageToLayer(new ColorImage(ImageUtil.readPPM(
            "res/example1.ppm").getPixels()), 10, 20);
    IImage cm2 = layer1.getImage();
    IPixel p2 = cm2.getPixel(20, 30);
    assertEquals(50, p2.getRed());
  }

  @Test
  public void testGetImages1() {
    ILayer layer1 = new ImageLayer(200, 300, "test layer");
    List<IImage> ims1 = layer1.getImages();
    assertEquals(0, ims1.size());
  }

  @Test
  public void testGetImages2() throws IOException {
    ILayer layer1 = new ImageLayer(200, 300, "test layer");
    List<IImage> ims1 = layer1.getImages();
    assertEquals(0, ims1.size());
    IImage ci1 = new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels());
    layer1.addImageToLayer(ci1, 10, 20);
    List<IImage> ims2 = layer1.getImages();
    assertEquals(1, ims2.size());
    assertEquals(ci1, ims2.get(0));
  }

  @Test
  public void testGetName() {
    ILayer layer1 = new ImageLayer(200, 300, "test layer");
    assertEquals("test layer", layer1.getName());
  }

  @Test
  public void testGetFilter() {
    ILayer layer2 = new ImageLayer(100, 150, "layer two");
    assertEquals("normal", layer2.getFilter().toString());
    layer2.applyFilter(new ComponentFilter(Component.BLUE));
    assertEquals("blue-component", layer2.getFilter().toString());
  }

  @Test
  public void testSetFilter() {
    ILayer layer1 = new ImageLayer(200, 300, "test layer");
    layer1.setFilter(new NormalFilter());
    assertEquals("normal", layer1.getFilter().toString());
    layer1.setFilter(new BrightenFilter(LightType.LUMA));
    assertEquals("brighten-luma", layer1.getFilter().toString());
    layer1.setFilter(new ComponentFilter(Component.GREEN));
    assertEquals("green-component", layer1.getFilter().toString());
    layer1.setFilter(new DarkenFilter(LightType.INTENSITY));
    assertEquals("darken-intensity", layer1.getFilter().toString());
    layer1.setFilter(new NormalFilter());
    assertEquals("normal", layer1.getFilter().toString());
  }

  @Test
  public void testApplyFilter1() {
    IPixel pix1 = new ColorPixel(200, 40, 30, 255);
    IPixel pix2 = new ColorPixel(40, 79, 2, 255);
    IPixel pix3 = new ColorPixel(100, 10, 110, 255);
    IPixel[][] pixs1 = new ColorPixel[2][3];
    pixs1[0][0] = pix1;
    pixs1[0][1] = pix2;
    pixs1[0][2] = pix3;
    pixs1[1][0] = pix2;
    pixs1[1][1] = pix3;
    pixs1[1][2] = pix1;
    IImage im2 = new ColorImage(pixs1);
    ILayer layer2 = new ImageLayer(2, 3, "layer 2");
    assertEquals("normal", layer2.getFilter().toString());
    layer2.addImageToLayer(im2, 0, 0);
    layer2.applyFilter(new BrightenFilter(LightType.LUMA));
    assertEquals("brighten-luma", layer2.getFilter().toString());
    assertEquals(136, layer2.getImage().getPixel(1,1).getRed());
    assertEquals(46, layer2.getImage().getPixel(1,1).getGreen());
    assertEquals(146, layer2.getImage().getPixel(1,1).getBlue());
  }

  @Test
  public void testApplyFilter2() {
    IPixel pix1 = new ColorPixel(200, 40, 30, 255);
    IPixel pix2 = new ColorPixel(40, 79, 2, 255);
    IPixel pix3 = new ColorPixel(100, 10, 110, 255);
    IPixel[][] pixs1 = new ColorPixel[2][3];
    pixs1[0][0] = pix1;
    pixs1[0][1] = pix2;
    pixs1[0][2] = pix3;
    pixs1[1][0] = pix2;
    pixs1[1][1] = pix3;
    pixs1[1][2] = pix1;
    IImage im2 = new ColorImage(pixs1);
    ILayer layer2 = new ImageLayer(2, 3, "layer 2");
    assertEquals("normal", layer2.getFilter().toString());
    layer2.addImageToLayer(im2, 0, 0);
    layer2.applyFilter(new ComponentFilter(Component.GREEN));
    assertEquals("green-component", layer2.getFilter().toString());
    assertEquals(0, layer2.getImage().getPixel(0,0).getRed());
    assertEquals(40, layer2.getImage().getPixel(0,0).getGreen());
    assertEquals(0, layer2.getImage().getPixel(0,0).getBlue());
    layer2.applyFilter(new NormalFilter());
    assertEquals("normal", layer2.getFilter().toString());
    assertEquals(200, layer2.getImage().getPixel(0,0).getRed());
    assertEquals(40, layer2.getImage().getPixel(0,0).getGreen());
    assertEquals(30, layer2.getImage().getPixel(0,0).getBlue());
  }

  @Test
  public void testApplyFilter3() {
    IPixel pix1 = new ColorPixel(200, 40, 30, 255);
    IPixel pix2 = new ColorPixel(40, 79, 2, 255);
    IPixel pix3 = new ColorPixel(100, 10, 110, 255);
    IPixel[][] pixs1 = new ColorPixel[2][3];
    pixs1[0][0] = pix1;
    pixs1[0][1] = pix2;
    pixs1[0][2] = pix3;
    pixs1[1][0] = pix2;
    pixs1[1][1] = pix3;
    pixs1[1][2] = pix1;
    IImage im2 = new ColorImage(pixs1);
    ILayer layer2 = new ImageLayer(2, 3, "layer 2");
    assertEquals("normal", layer2.getFilter().toString());
    layer2.addImageToLayer(im2, 0, 0);
    layer2.applyFilter(new DarkenFilter(LightType.VALUE));
    assertEquals("darken-value", layer2.getFilter().toString());
    assertEquals(0, layer2.getImage().getPixel(0,1).getRed());
    assertEquals(0, layer2.getImage().getPixel(0,1).getGreen());
    assertEquals(0, layer2.getImage().getPixel(0,1).getBlue());
    layer2.applyFilter(new ComponentFilter(Component.BLUE));
    assertEquals("blue-component", layer2.getFilter().toString());
    assertEquals(0, layer2.getImage().getPixel(0,1).getRed());
    assertEquals(0, layer2.getImage().getPixel(0,1).getGreen());
    assertEquals(2, layer2.getImage().getPixel(0,1).getBlue());
    layer2.applyFilter(new DarkenFilter(LightType.LUMA));
    assertEquals("darken-luma", layer2.getFilter().toString());
    assertEquals(0, layer2.getImage().getPixel(0,1).getRed());
    assertEquals(14, layer2.getImage().getPixel(0,1).getGreen());
    assertEquals(0, layer2.getImage().getPixel(0,1).getBlue());
    layer2.applyFilter(new NormalFilter());
    assertEquals("normal", layer2.getFilter().toString());
    assertEquals(40, layer2.getImage().getPixel(0,1).getRed());
    assertEquals(79, layer2.getImage().getPixel(0,1).getGreen());
    assertEquals(2, layer2.getImage().getPixel(0,1).getBlue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddImageToLayerFail1() throws IOException {
    ILayer layer3 = new ImageLayer(100, 75, "layer3");
    layer3.addImageToLayer(new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels()),
            -1, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddImageToLayerFail2() throws IOException {
    ILayer layer3 = new ImageLayer(100, 75, "layer3");
    layer3.addImageToLayer(new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels()),
            30, -50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddImageToLayerFail3() throws IOException {
    ILayer layer3 = new ImageLayer(100, 75, "layer3");
    layer3.addImageToLayer(new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels()),
            200, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddImageToLayerFail4() throws IOException {
    ILayer layer3 = new ImageLayer(100, 75, "layer3");
    layer3.addImageToLayer(new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels()),
            75, 75);
  }

  @Test
  public void testAddImageToLayer1() throws IOException {
    ILayer layer4 = new ImageLayer(250, 300, "layer4");
    IImage ci3 = new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels());
    IImage ci4 = new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels());
    assertEquals(50, ci3.getPixel(70, 60).getRed());
    assertEquals(150, ci3.getPixel(70, 60).getGreen());
    assertEquals(250, ci3.getPixel(70, 60).getBlue());
    assertEquals(124, ci4.getPixel(189, 45).getRed());
    assertEquals(116, ci4.getPixel(189, 45).getGreen());
    assertEquals(250, ci4.getPixel(189, 45).getBlue());
    layer4.addImageToLayer(ci3, 10, 10);
    assertEquals(1, layer4.getImages().size());
    assertEquals(50, layer4.getImage().getPixel(80,70).getRed());
    assertEquals(150, layer4.getImage().getPixel(80,70).getGreen());
    assertEquals(250, layer4.getImage().getPixel(80,70).getBlue());
    layer4.addImageToLayer(ci4, 20, 50);
    assertEquals(2, layer4.getImages().size());
    assertEquals(124, layer4.getImage().getPixel(209,95).getRed());
    assertEquals(116, layer4.getImage().getPixel(209,95).getGreen());
    assertEquals(250, layer4.getImage().getPixel(209,95).getBlue());
  }

  @Test
  public void testGetPreviousPixels1() throws IOException {
    ImageLayer il1 = new ImageLayer(3, 3, "IL1");
    IPixel[][] pp1 = il1.getPreviousPixels();
    assertEquals(255, pp1[1][1].getRed());
    assertEquals(255, pp1[1][1].getBlue());
    assertEquals(255, pp1[1][1].getGreen());
    assertEquals(3, pp1.length);
    assertEquals(3, pp1[0].length);
    il1.addImageToLayer(new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels()),
            0 ,0);
    il1.applyFilter(new ComponentFilter(Component.BLUE));
    assertEquals(new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels()).getPixel(1,
            1).getRed(), pp1[1][1].getRed());
    assertEquals(new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels()).getPixel(1,
            1).getGreen(), pp1[1][1].getGreen());
    assertEquals(new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels()).getPixel(1,
            1).getBlue(), pp1[1][1].getBlue());
  }

  @Test
  public void testRevertToOriginal() throws IOException {
    ImageLayer il1 = new ImageLayer(3, 3, "IL1");
    IPixel[][] pp1 = il1.getPreviousPixels();
    il1.addImageToLayer(new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels()),
            0 ,0);
    assertEquals(0, il1.getImage().getPixel(1,1).getRed());
    assertEquals(7, il1.getImage().getPixel(1,1).getBlue());
    assertEquals(15, il1.getImage().getPixel(1,1).getGreen());
    il1.applyFilter(new ComponentFilter(Component.BLUE));
    assertEquals(0, pp1[1][1].getRed());
    assertEquals(7, pp1[1][1].getBlue());
    assertEquals(15, pp1[1][1].getGreen());
    assertEquals(0, il1.getImage().getPixel(1,1).getRed());
    assertEquals(7, il1.getImage().getPixel(1,1).getBlue());
    assertEquals(0, il1.getImage().getPixel(1,1).getGreen());
    il1.revertToOriginal();
    assertEquals(0, il1.getImage().getPixel(1,1).getRed());
    assertEquals(7, il1.getImage().getPixel(1,1).getBlue());
    assertEquals(15, il1.getImage().getPixel(1,1).getGreen());
  }
}