import org.junit.Test;

import java.io.IOException;

import model.ColorImage;
import model.IImage;
import model.ImageUtil;
import model.filter.AbstractBrightness;
import model.filter.DarkenFilter;
import model.filter.LightType;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functions and behavior of the DarkenFilter class.
 */
public class DarkenFilterTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConst() {
    AbstractBrightness df = new DarkenFilter(null);
  }

  @Test
  public void testToString1() {
    AbstractBrightness df1 = new DarkenFilter(LightType.VALUE);
    assertEquals("darken-value", df1.toString());
  }

  @Test
  public void testToString2() {
    AbstractBrightness df2 = new DarkenFilter(LightType.INTENSITY);
    assertEquals("darken-intensity", df2.toString());
  }

  @Test
  public void testToString3() {
    AbstractBrightness df3 = new DarkenFilter(LightType.LUMA);
    assertEquals("darken-luma", df3.toString());
  }

  @Test
  public void testApply1() throws IOException {
    AbstractBrightness df4 = new DarkenFilter(LightType.LUMA);
    IImage im1 = new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels());
    assertEquals(50, im1.getPixel(30, 40).getRed());
    assertEquals(150, im1.getPixel(30, 40).getGreen());
    assertEquals(250, im1.getPixel(30, 40).getBlue());
    df4.apply(im1, 30, 40);
    assertEquals(0, im1.getPixel(30, 40).getRed());
    assertEquals(15, im1.getPixel(30, 40).getGreen());
    assertEquals(115, im1.getPixel(30, 40).getBlue());
    AbstractBrightness df5 = new DarkenFilter(LightType.LUMA);
    IImage im2 = new ColorImage(300, 300, 0, 255);
    assertEquals(255, im2.getPixel(230, 240).getRed());
    assertEquals(255, im2.getPixel(230, 240).getGreen());
    assertEquals(255, im2.getPixel(230, 240).getBlue());
    df5.apply(im2, 230, 240);
    assertEquals(255, im2.getPixel(230, 240).getRed());
    assertEquals(255, im2.getPixel(230, 240).getGreen());
    assertEquals(255, im2.getPixel(230, 240).getBlue());
  }

  @Test
  public void testApply2() throws IOException {
    AbstractBrightness df6 = new DarkenFilter(LightType.VALUE);
    IImage im1 = new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels());
    assertEquals(50, im1.getPixel(130, 140).getRed());
    assertEquals(253, im1.getPixel(130, 140).getGreen());
    assertEquals(250, im1.getPixel(130, 140).getBlue());
    df6.apply(im1, 130, 140);
    assertEquals(0, im1.getPixel(130, 140).getRed());
    assertEquals(0, im1.getPixel(130, 140).getGreen());
    assertEquals(0, im1.getPixel(130, 140).getBlue());
    AbstractBrightness bf7 = new DarkenFilter(LightType.VALUE);
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
    AbstractBrightness df8 = new DarkenFilter(LightType.INTENSITY);
    IImage im1 = new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels());
    assertEquals(50, im1.getPixel(60, 150).getRed());
    assertEquals(254, im1.getPixel(60, 150).getGreen());
    assertEquals(250, im1.getPixel(60, 150).getBlue());
    df8.apply(im1, 60, 150);
    assertEquals(0, im1.getPixel(60, 150).getRed());
    assertEquals(70, im1.getPixel(60, 150).getGreen());
    assertEquals(66, im1.getPixel(60, 150).getBlue());
    AbstractBrightness df9 = new DarkenFilter(LightType.INTENSITY);
    IImage im2 = new ColorImage(300, 300, 0, 255);
    assertEquals(255, im2.getPixel(50, 210).getRed());
    assertEquals(255, im2.getPixel(50, 210).getGreen());
    assertEquals(255, im2.getPixel(50, 210).getBlue());
    df9.apply(im2, 50, 210);
    assertEquals(255, im2.getPixel(50, 210).getRed());
    assertEquals(255, im2.getPixel(50, 210).getGreen());
    assertEquals(255, im2.getPixel(50, 210).getBlue());
  }
}