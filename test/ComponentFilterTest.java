import org.junit.Test;

import java.io.IOException;

import model.ColorImage;
import model.IImage;
import model.ImageUtil;
import model.filter.Component;
import model.filter.ComponentFilter;
import model.filter.IFilter;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functions and behavior of the ComponentFilter class.
 */
public class ComponentFilterTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConst() {
    IFilter cf = new ComponentFilter(null);
  }

  @Test
  public void testApply1() {
    IFilter cf = new ComponentFilter(Component.GREEN);
    IImage ci1 = new ColorImage(300, 200, 0, 255);
    cf.apply(ci1, 20, 30);
    assertEquals(255, ci1.getPixel(20,30).getRed());
    assertEquals(255, ci1.getPixel(20,30).getBlue());
    assertEquals(255, ci1.getPixel(20,30).getGreen());
  }

  @Test
  public void testApply2() throws IOException {
    IFilter cf = new ComponentFilter(Component.GREEN);
    IImage ci2 = new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels());
    assertEquals(50, ci2.getPixel(50,70).getRed());
    assertEquals(250, ci2.getPixel(50,70).getBlue());
    assertEquals(150, ci2.getPixel(50,70).getGreen());
    cf.apply(ci2, 50, 70);
    assertEquals(0, ci2.getPixel(50,70).getRed());
    assertEquals(0, ci2.getPixel(50,70).getBlue());
    assertEquals(150, ci2.getPixel(50,70).getGreen());
    assertEquals(255, ci2.getPixel(50,70).getAlpha());
  }

  @Test
  public void testApply3() throws IOException {
    IFilter cf = new ComponentFilter(Component.RED);
    IImage ci2 = new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels());
    assertEquals(50, ci2.getPixel(100, 67).getRed());
    assertEquals(250, ci2.getPixel(100, 67).getBlue());
    assertEquals(150, ci2.getPixel(100, 67).getGreen());
    cf.apply(ci2, 100, 67);
    assertEquals(50, ci2.getPixel(100, 67).getRed());
    assertEquals(0, ci2.getPixel(100, 67).getBlue());
    assertEquals(0, ci2.getPixel(100, 67).getGreen());
    assertEquals(255, ci2.getPixel(100, 67).getAlpha());
  }

  @Test
  public void testApply4() throws IOException {
    IFilter cf = new ComponentFilter(Component.BLUE);
    IImage ci2 = new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels());
    assertEquals(255, ci2.getPixel(0, 299).getRed());
    assertEquals(255, ci2.getPixel(0, 299).getBlue());
    assertEquals(150, ci2.getPixel(0, 299).getGreen());
    cf.apply(ci2, 0, 299);
    assertEquals(0, ci2.getPixel(0, 299).getRed());
    assertEquals(255, ci2.getPixel(0, 299).getBlue());
    assertEquals(0, ci2.getPixel(0, 299).getGreen());
    assertEquals(255, ci2.getPixel(0, 299).getAlpha());
  }

  @Test
  public void testToString1() {
    IFilter cf1 = new ComponentFilter(Component.RED);
    assertEquals("red-component", cf1.toString());
  }

  @Test
  public void testToString2() {
    IFilter cf2 = new ComponentFilter(Component.GREEN);
    assertEquals("green-component", cf2.toString());
  }

  @Test
  public void testToString3() {
    IFilter cf3 = new ComponentFilter(Component.BLUE);
    assertEquals("blue-component", cf3.toString());
  }
}