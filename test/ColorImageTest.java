import org.junit.Test;

import java.io.IOException;

import model.ColorImage;
import model.ColorPixel;
import model.IImage;
import model.IPixel;
import model.ImageUtil;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functions and behavior of the ColorImage class.
 */

public class ColorImageTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor1() {
    IImage im = new ColorImage(0, 400, 200, new IPixel[100][400], "new image");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2() {
    IImage im = new ColorImage(1000, -400, 255, new IPixel[100][400], "new image");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor3() {
    IImage im = new ColorImage(100, 400, -1, new IPixel[100][400], "new image");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor4() {
    IImage im = new ColorImage(1000, 400, 256, new IPixel[100][400], "new image");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor5() {
    IImage im = new ColorImage(new IPixel[100][400], -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor6() {
    IImage im = new ColorImage( new IPixel[100][400], 270);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor7() {
    IImage im = new ColorImage(0, 400, 200, 100 );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor8() {
    IImage im = new ColorImage(100, -400, 200, 100 );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor9() {
    IImage im = new ColorImage(100, 400, -1, 100 );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor10() {
    IImage im = new ColorImage(100, 400, 2000, 100 );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor11() {
    IImage im = new ColorImage(100, 400, 200, -100 );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor12() {
    IImage im = new ColorImage(0, 400, 200, 256);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor13() {
    IImage im = new ColorImage(400, 400, 200, null, "new image");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor14() {
    IImage im = new ColorImage(400, 400, 200, new IPixel[100][400], null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor15() {
    IImage im = new ColorImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor16() {
    IImage im = new ColorImage(null, 255);
  }

  @Test
  public void testGetHeight1() throws IOException {
    IImage im1 = new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels());
    assertEquals(300, im1.getHeight());
  }

  @Test
  public void testGetHeight2() {
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
    assertEquals(2, im2.getHeight());
  }

  @Test
  public void testGetHeight3() {
    IImage im3 = new ColorImage(100, 200, 200, 255);
    assertEquals(100, im3.getHeight());
  }

  @Test
  public void testGetWidth1() throws IOException {
    IImage im1 = new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels());
    assertEquals(300, im1.getWidth());
  }

  @Test
  public void testGetWidth2() {
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
    assertEquals(3, im2.getWidth());
  }

  @Test
  public void testGetWidth3() {
    IImage im3 = new ColorImage(100, 200, 200, 255);
    assertEquals(200, im3.getWidth());
  }

  @Test
  public void testGetPixels1() throws IOException {
    IImage im4 = new ColorImage(ImageUtil.readPPM("res/example1.ppm").getPixels());
    IPixel[][] pixs1 = im4.getPixels();
    assertEquals(255, pixs1[0][0].getRed());
    assertEquals(150, pixs1[0][1].getGreen());
    assertEquals(250, pixs1[0][2].getBlue());
    assertEquals(50, pixs1[0][3].getRed());
    assertEquals(150, pixs1[1][0].getGreen());
    assertEquals(250, pixs1[1][1].getBlue());
    assertEquals(50, pixs1[1][2].getRed());
    assertEquals(150, pixs1[1][3].getGreen());
    assertEquals(250, pixs1[2][0].getBlue());
    assertEquals(50, pixs1[2][1].getRed());
    assertEquals(150, pixs1[2][2].getGreen());
    assertEquals(250, pixs1[2][3].getBlue());
    assertEquals(255, pixs1[3][0].getRed());
    assertEquals(150, pixs1[3][1].getGreen());
    assertEquals(250, pixs1[3][2].getBlue());
    assertEquals(50, pixs1[3][3].getRed());
  }

  @Test
  public void testGetPixels2() {
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
    assertEquals(pix3, im2.getPixel(1,1));
    assertEquals(pixs1, im2.getPixels());
    im2.setPixel(1,1, pix2);
    IPixel[][] pixs2 = im2.getPixels();
    assertEquals(pix2, pixs2[1][1]);
  }

  @Test
  public void testGetPixels3() {
    IImage im6 = new ColorImage(3, 1, 200, 255);
    IPixel[][] pixs1 = im6.getPixels();
    assertEquals(255, pixs1[0][0].getRed());
    assertEquals(255, pixs1[0][0].getGreen());
    assertEquals(255, pixs1[0][0].getBlue());
    assertEquals(200, pixs1[0][0].getAlpha());
    assertEquals(255, pixs1[1][0].getRed());
    assertEquals(255, pixs1[1][0].getGreen());
    assertEquals(255, pixs1[1][0].getBlue());
    assertEquals(200, pixs1[1][0].getAlpha());
    assertEquals(255, pixs1[2][0].getRed());
    assertEquals(255, pixs1[2][0].getGreen());
    assertEquals(255, pixs1[2][0].getBlue());
    assertEquals(200, pixs1[2][0].getAlpha());
  }

  @Test
  public void testGetPixel1() {
    IImage im4 = new ColorImage(100, 100, 100, 255);
    assertEquals(255, im4.getPixel(50, 30).getRed());
    assertEquals(255, im4.getPixel(50, 30).getGreen());
    assertEquals(255, im4.getPixel(50, 30).getBlue());
    assertEquals(100, im4.getPixel(50, 30).getAlpha());
    im4.setPixel(50, 30, new ColorPixel(200, 100, 50, 25));
    assertEquals(200, im4.getPixel(50, 30).getRed());
    assertEquals(100, im4.getPixel(50, 30).getGreen());
    assertEquals(50, im4.getPixel(50, 30).getBlue());
    assertEquals(25, im4.getPixel(50, 30).getAlpha());
  }

  @Test
  public void testGetPixel2() {
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
    assertEquals(pix1, im2.getPixel(0,0));
    assertEquals(pix2, im2.getPixel(0,1));
    assertEquals(pix3, im2.getPixel(0,2));
    assertEquals(pix2, im2.getPixel(1,0));
    assertEquals(pix3, im2.getPixel(1,1));
    assertEquals(pix1, im2.getPixel(1,2));
  }

  @Test
  public void testGetPixel3() throws IOException {
    IImage im1 = new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels());
    assertEquals(0, im1.getPixel(1, 1).getRed());
    assertEquals(15, im1.getPixel(1, 1).getGreen());
    assertEquals(7, im1.getPixel(1, 1).getBlue());
    assertEquals(15, im1.getPixel(1, 1).getAlpha());
    im1.setPixel(1, 1, new ColorPixel(150, 120, 196, 255));
    assertEquals(150, im1.getPixel(1, 1).getRed());
    assertEquals(120, im1.getPixel(1, 1).getGreen());
    assertEquals(196, im1.getPixel(1, 1).getBlue());
    assertEquals(255, im1.getPixel(1, 1).getAlpha());
  }

  @Test
  public void testSetPixel1() {
    IImage im4 = new ColorImage(200, 300, 50, 255);
    assertEquals(255, im4.getPixel(85, 170).getRed());
    assertEquals(255, im4.getPixel(85, 170).getGreen());
    assertEquals(255, im4.getPixel(85, 170).getBlue());
    assertEquals(50, im4.getPixel(85, 170).getAlpha());
    im4.setPixel(85, 170, new ColorPixel(40, 233, 210, 190));
    assertEquals(40, im4.getPixel(85, 170).getRed());
    assertEquals(233, im4.getPixel(85, 170).getGreen());
    assertEquals(210, im4.getPixel(85, 170).getBlue());
    assertEquals(190, im4.getPixel(85, 170).getAlpha());
  }

  @Test
  public void testSetPixel2() {
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
    assertEquals(pix1, im2.getPixel(0,0));
    im2.setPixel(0, 0, pix2);
    assertEquals(pix2, im2.getPixel(0,0));
    im2.setPixel(0, 0, pix1);
    assertEquals(pix1, im2.getPixel(0,0));
  }

  @Test
  public void testSetPixel3() throws IOException {
    IImage im1 = new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels());
    assertEquals(0, im1.getPixel(3, 3).getRed());
    assertEquals(0, im1.getPixel(3, 3).getGreen());
    assertEquals(0, im1.getPixel(3, 3).getBlue());
    assertEquals(15, im1.getPixel(1, 1).getAlpha());
    im1.setPixel(0, 0, new ColorPixel(30, 90, 255, 150));
    assertEquals(30, im1.getPixel(0, 0).getRed());
    assertEquals(90, im1.getPixel(0, 0).getGreen());
    assertEquals(255, im1.getPixel(0, 0).getBlue());
    assertEquals(150, im1.getPixel(0, 0).getAlpha());
  }

  @Test
  public void testGetMaxVal1() {
    IImage im = new ColorImage(1000, 400, 255, new IPixel[100][400], "new image");
    assertEquals(255, im.getMaxValue());
  }

  @Test
  public void testGetMaxVal2() {
    IImage im = new ColorImage(new IPixel[100][400], 100);
    assertEquals(100, im.getMaxValue());
  }

  @Test
  public void testGetMaxVal3() {
    IImage im = new ColorImage(100, 400, 255, 150 );
    assertEquals(150, im.getMaxValue());
  }

  @Test(expected = RuntimeException.class)
  public void testGetFilename1() {
    IImage im = new ColorImage(new IPixel[100][400], 100);
    im.getFilename();
  }

  @Test(expected = RuntimeException.class)
  public void testGetFilename2() {
    IImage im = new ColorImage(100, 400, 255, 150 );
    im.getFilename();
  }

  @Test
  public void testGetFilename3() {
    IImage im = new ColorImage(1000, 400, 255, new IPixel[100][400], "new image");
    assertEquals("new image", im.getFilename());
  }

  @Test
  public void testUpdateWithMaxValue() throws IOException {
    IImage ex1 = new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels(), 100);
    assertEquals(15, ex1.getPixel(0, 3).getRed());
    assertEquals(0, ex1.getPixel(0, 3).getGreen());
    assertEquals(15, ex1.getPixel(0, 3).getBlue());
    ex1.updateWithMaxValue();
    assertEquals(38, ex1.getPixel(0, 3).getRed());
    assertEquals(0, ex1.getPixel(0, 3).getGreen());
    assertEquals(38, ex1.getPixel(0, 3).getBlue());
    IImage ex2 = new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels(), 255);
    assertEquals(15, ex2.getPixel(0, 3).getRed());
    assertEquals(0, ex2.getPixel(0, 3).getGreen());
    assertEquals(15, ex2.getPixel(0, 3).getBlue());
    ex2.updateWithMaxValue();
    assertEquals(15, ex2.getPixel(0, 3).getRed());
    assertEquals(0, ex2.getPixel(0, 3).getGreen());
    assertEquals(15, ex2.getPixel(0, 3).getBlue());
  }
}