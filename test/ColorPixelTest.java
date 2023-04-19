import org.junit.Test;

import model.ColorPixel;
import model.IPixel;
import model.filter.BrightenFilter;
import model.filter.Component;
import model.filter.ComponentFilter;
import model.filter.DarkenFilter;
import model.filter.LightType;
import model.filter.NormalFilter;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functions and behavior of the ColorPixel class.
 */

public class ColorPixelTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor1() {
    IPixel p = new ColorPixel(-1, 200, 100, 40);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2() {
    IPixel p = new ColorPixel(300, 200, 100, 40);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor3() {
    IPixel p = new ColorPixel(100, -200, 100, 40);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor4() {
    IPixel p = new ColorPixel(100, 400, 100, 40);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor5() {
    IPixel p = new ColorPixel(10, 200, -100, 40);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor6() {
    IPixel p = new ColorPixel(100, 200, 256, 40);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor7() {
    IPixel p = new ColorPixel(10, 200, 100, -40);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor8() {
    IPixel p = new ColorPixel(1, 200, 100, 4000);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor9() {
    IPixel p = new ColorPixel(-1, 200, 100, 40, new ComponentFilter(Component.BLUE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor10() {
    IPixel p = new ColorPixel(300, 200, 100, 40, new ComponentFilter(Component.RED));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor11() {
    IPixel p = new ColorPixel(100, -200, 100, 40, new ComponentFilter(Component.GREEN));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor12() {
    IPixel p = new ColorPixel(100, 400, 100, 40, new BrightenFilter(LightType.LUMA));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor13() {
    IPixel p = new ColorPixel(10, 200, -100, 40, new NormalFilter());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor14() {
    IPixel p = new ColorPixel(100, 200, 256, 40, new BrightenFilter(LightType.INTENSITY));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor15() {
    IPixel p = new ColorPixel(10, 200, 100, -40, new BrightenFilter(LightType.VALUE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor16() {
    IPixel p = new ColorPixel(1, 200, 100, 4000, new DarkenFilter(LightType.LUMA));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor17() {
    IPixel p = new ColorPixel(1, 200, 100, 40, null);
  }

  @Test
  public void testGetRed1() {
    IPixel cp4 = new ColorPixel(0, 250, 30, 255);
    assertEquals(0, cp4.getRed());
  }

  @Test
  public void testGetRed2() {
    IPixel cp5 = new ColorPixel(30, 110, 0 ,30);
    assertEquals(30, cp5.getRed());

  }

  @Test
  public void testGetRed3() {
    IPixel cp6 = new ColorPixel(90, 230, 255, 255, new NormalFilter());
    assertEquals(90, cp6.getRed());
  }

  @Test
  public void testGetGreen1() {
    IPixel cp7 = new ColorPixel(200, 200, 200, 255);
    assertEquals(200, cp7.getGreen());
  }

  @Test
  public void testGetGreen2() {
    IPixel cp8 = new ColorPixel(42, 90, 120, 255, new BrightenFilter(LightType.LUMA));
    assertEquals(90, cp8.getGreen());
  }

  @Test
  public void testGetGreen3() {
    IPixel cp9 = new ColorPixel(11, 230, 70, 250, new DarkenFilter(LightType.VALUE));
    assertEquals(230, cp9.getGreen());
  }

  @Test
  public void testGetBlue1() {
    IPixel cp10 = new ColorPixel(40, 2, 51, 255);
    assertEquals(51, cp10.getBlue());
  }

  @Test
  public void testGetBlue2() {
    IPixel cp11 = new ColorPixel(40, 20, 10, 40);
    assertEquals(10, cp11.getBlue());
  }

  @Test
  public void testGetBlue3() {
    IPixel cp12 = new ColorPixel(100, 100, 100, 255, new ComponentFilter(Component.RED));
    assertEquals(100, cp12.getBlue());
  }

  @Test
  public void testGetAlpha1() {
    IPixel cp13 = new ColorPixel(23, 100, 254, 0);
    assertEquals(0, cp13.getAlpha());
  }

  @Test
  public void testGetAlpha2() {
    IPixel cp14 = new ColorPixel(200, 100, 200, 255, new NormalFilter());
    assertEquals(255, cp14.getAlpha());
  }

  @Test
  public void testGetAlpha3() {
    IPixel cp15 = new ColorPixel(0, 50, 200, 87, new NormalFilter());
    assertEquals(87, cp15.getAlpha());
  }

  @Test
  public void testGetFilter1() {
    IPixel cp19 = new ColorPixel(250, 0, 190, 255);
    assertEquals("normal", cp19.getFilter().toString());
  }

  @Test
  public void testGetFilter2() {
    IPixel cp20 = new ColorPixel(159, 10, 255, 255, new NormalFilter());
    assertEquals("normal", cp20.getFilter().toString());
  }

  @Test
  public void testGetFilter3() {
    IPixel cp21 = new ColorPixel(240, 30, 150, 255, new ComponentFilter(Component.GREEN));
    assertEquals("green-component", cp21.getFilter().toString());
  }

  @Test
  public void testGetFilter4() {
    IPixel cp22 = new ColorPixel(170, 170, 3, 255, new DarkenFilter(LightType.LUMA));
    assertEquals("darken-luma", cp22.getFilter().toString());
  }

  @Test
  public void testSetFilter1() {
    IPixel cp22 = new ColorPixel(170, 170, 3, 255, new DarkenFilter(LightType.LUMA));
    assertEquals("darken-luma", cp22.getFilter().toString());
    cp22.setFilter(new ComponentFilter(Component.RED));
    assertEquals("red-component", cp22.getFilter().toString());
  }

  @Test
  public void testSetFilter2() {
    IPixel cp23 = new ColorPixel(170, 170, 3, 255);
    assertEquals("normal", cp23.getFilter().toString());
    cp23.setFilter(new BrightenFilter(LightType.INTENSITY));
    assertEquals("brighten-intensity", cp23.getFilter().toString());
    cp23.setFilter(new NormalFilter());
    assertEquals("normal", cp23.getFilter().toString());
  }
}