import org.junit.Test;

import java.io.IOException;

import model.IImage;

import static model.ImageUtil.readImage;
import static model.ImageUtil.readJPG;
import static model.ImageUtil.readPNG;
import static model.ImageUtil.readPPM;
import static org.junit.Assert.*;

/**
 * * Tests the functions and behavior of the ImageUtil class.
 */

public class ImageUtilTest {

  @Test
  public void testReadImPPM() throws IOException {
    IImage im = readImage("res/example1.ppm");
    assertEquals(300, im.getHeight());
    assertEquals(300, im.getWidth());
    assertEquals(255, im.getMaxValue());
    assertEquals(255, im.getPixel(0,0).getRed());
    assertEquals("res/example1.ppm", im.getFilename());
  }

  @Test
  public void testReadImPPM2() throws IOException {
    IImage im = readPPM("res/example1.ppm");
    assertEquals(300, im.getHeight());
    assertEquals(300, im.getWidth());
    assertEquals(255, im.getMaxValue());
    assertEquals(255, im.getPixel(0,0).getRed());
    assertEquals("res/example1.ppm", im.getFilename());
  }

  @Test
  public void testReadImJPEG() throws IOException {
    IImage im = readImage("res/example-jpeg.jpeg");
    assertEquals(160, im.getHeight());
    assertEquals(320, im.getWidth());
    assertEquals(255, im.getMaxValue());
    assertEquals(143, im.getPixel(0,0).getRed());
    assertEquals("res/example-jpeg.jpeg", im.getFilename());
  }

  @Test
  public void testReadImJPEG2() throws IOException {
    IImage im = readJPG("res/example-jpeg.jpeg");
    assertEquals(160, im.getHeight());
    assertEquals(320, im.getWidth());
    assertEquals(255, im.getMaxValue());
    assertEquals(143, im.getPixel(0,0).getRed());
    assertEquals("res/example-jpeg.jpeg", im.getFilename());
  }

  @Test
  public void testReadImJPG() throws IOException {
    IImage im = readImage("res/example-jpg.jpg");
    assertEquals(400, im.getHeight());
    assertEquals(800, im.getWidth());
    assertEquals(255, im.getMaxValue());
    assertEquals(145, im.getPixel(0,0).getRed());
    assertEquals("res/example-jpg.jpg", im.getFilename());
  }

  @Test
  public void testReadImJPG2() throws IOException {
    IImage im = readJPG("res/example-jpg.jpg");
    assertEquals(400, im.getHeight());
    assertEquals(800, im.getWidth());
    assertEquals(255, im.getMaxValue());
    assertEquals(145, im.getPixel(0,0).getRed());
    assertEquals("res/example-jpg.jpg", im.getFilename());
  }

  @Test
  public void testReadImPNG() throws IOException {
    IImage im = readImage("res/img.png");
    assertEquals(160, im.getHeight());
    assertEquals(320, im.getWidth());
    assertEquals(255, im.getMaxValue());
    assertEquals(142, im.getPixel(0,0).getRed());
    assertEquals("res/img.png", im.getFilename());
  }

  @Test
  public void testReadImPNG2() throws IOException {
    IImage im = readPNG("res/img.png");
    assertEquals(160, im.getHeight());
    assertEquals(320, im.getWidth());
    assertEquals(255, im.getMaxValue());
    assertEquals(142, im.getPixel(0,0).getRed());
    assertEquals("res/img.png", im.getFilename());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadImEx() throws IOException {
    IImage im = readImage("image.image");
  }
}