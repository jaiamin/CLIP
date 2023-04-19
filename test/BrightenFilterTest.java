import org.junit.Test;

import java.io.IOException;

import model.ColorImage;
import model.IImage;
import model.ImageUtil;
import model.filter.AbstractBrightness;
import model.filter.BrightenFilter;
import model.filter.LightType;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functions and behavior of the BrightenFilter class.
 */
public class BrightenFilterTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConst() {
    AbstractBrightness bf = new BrightenFilter(null);
  }

  @Test
  public void testToString1() {
    AbstractBrightness bf1 = new BrightenFilter(LightType.VALUE);
    assertEquals("brighten-value", bf1.toString());
  }

  @Test
  public void testToString2() {
    AbstractBrightness bf2 = new BrightenFilter(LightType.INTENSITY);
    assertEquals("brighten-intensity", bf2.toString());
  }

  @Test
  public void testToString3() {
    AbstractBrightness bf3 = new BrightenFilter(LightType.LUMA);
    assertEquals("brighten-luma", bf3.toString());
  }

  @Test
  public void testApply1() throws IOException {
    AbstractBrightness bf4 = new BrightenFilter(LightType.LUMA);
    IImage im1 = new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels());
    assertEquals(50, im1.getPixel(30, 40).getRed());
    assertEquals(150, im1.getPixel(30, 40).getGreen());
    assertEquals(250, im1.getPixel(30, 40).getBlue());
    bf4.apply(im1, 30, 40);
    assertEquals(185, im1.getPixel(30, 40).getRed());
    assertEquals(255, im1.getPixel(30, 40).getGreen());
    assertEquals(255, im1.getPixel(30, 40).getBlue());
    AbstractBrightness bf5 = new BrightenFilter(LightType.LUMA);
    IImage im2 = new ColorImage(300, 300, 0, 255);
    assertEquals(255, im2.getPixel(230, 240).getRed());
    assertEquals(255, im2.getPixel(230, 240).getGreen());
    assertEquals(255, im2.getPixel(230, 240).getBlue());
    bf5.apply(im2, 230, 240);
    assertEquals(255, im2.getPixel(230, 240).getRed());
    assertEquals(255, im2.getPixel(230, 240).getGreen());
    assertEquals(255, im2.getPixel(230, 240).getBlue());
  }

  @Test
  public void testApply2() throws IOException {
    AbstractBrightness bf6 = new BrightenFilter(LightType.VALUE);
    IImage im1 = new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels());
    assertEquals(50, im1.getPixel(130, 140).getRed());
    assertEquals(253, im1.getPixel(130, 140).getGreen());
    assertEquals(250, im1.getPixel(130, 140).getBlue());
    bf6.apply(im1, 130, 140);
    assertEquals(255, im1.getPixel(130, 140).getRed());
    assertEquals(255, im1.getPixel(130, 140).getGreen());
    assertEquals(255, im1.getPixel(130, 140).getBlue());
    AbstractBrightness bf7 = new BrightenFilter(LightType.VALUE);
    IImage im2 = new ColorImage(300, 300, 0, 255);
    assertEquals(255, im2.getPixel(200, 200).getRed());
    assertEquals(255, im2.getPixel(200, 200).getGreen());
    assertEquals(255, im2.getPixel(200, 200).getBlue());
    bf7.apply(im2, 200, 200);
    assertEquals(255, im2.getPixel(200, 200).getRed());
    assertEquals(255, im2.getPixel(200, 200).getGreen());
    assertEquals(255, im2.getPixel(200, 200).getBlue());
  }

  @Test
  public void testApply3() throws IOException {
    AbstractBrightness bf8 = new BrightenFilter(LightType.INTENSITY);
    IImage im1 = new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels());
    assertEquals(50, im1.getPixel(60, 150).getRed());
    assertEquals(254, im1.getPixel(60, 150).getGreen());
    assertEquals(250, im1.getPixel(60, 150).getBlue());
    bf8.apply(im1, 60, 150);
    assertEquals(234, im1.getPixel(60, 150).getRed());
    assertEquals(255, im1.getPixel(60, 150).getGreen());
    assertEquals(255, im1.getPixel(60, 150).getBlue());
    AbstractBrightness bf9 = new BrightenFilter(LightType.INTENSITY);
    IImage im2 = new ColorImage(300, 300, 0, 255);
    assertEquals(255, im2.getPixel(50, 210).getRed());
    assertEquals(255, im2.getPixel(50, 210).getGreen());
    assertEquals(255, im2.getPixel(50, 210).getBlue());
    bf9.apply(im2, 50, 210);
    assertEquals(255, im2.getPixel(50, 210).getRed());
    assertEquals(255, im2.getPixel(50, 210).getGreen());
    assertEquals(255, im2.getPixel(50, 210).getBlue());
  }
}