import org.junit.Test;

import java.util.ArrayList;

import static model.RepresentationConverter.convertHSLtoRGB;
import static model.RepresentationConverter.convertRGBtoHSL;
import static org.junit.Assert.assertEquals;

/**
 * * Tests the functions and behavior of the RepresentationConverter class.
 */
public class RepresentationConverterTest {

  @Test
  public void testRBGtoHSL() {
    ArrayList<Double> test = convertRGBtoHSL(0.5, 0.1, 0.25);
    double testr = test.get(0);
    double testg = test.get(1);
    double testb = test.get(2);
    assertEquals(337.5000, testr, 0.0001);
    assertEquals(0.6667, testg, 0.0001);
    assertEquals(0.3000, testb, 0.0001);
  }

  @Test
  public void testHSLtoRBG() {
    ArrayList<Integer> test = convertHSLtoRGB(337.5000, 0.6667, 0.3000);
    double testr = test.get(0) / 255.0;
    double testg = test.get(1) / 255.0;
    double testb = test.get(2) / 255.0;
    assertEquals(0.500, testr, 0.01);
    assertEquals(0.100, testg, 0.01);
    assertEquals(0.25, testb, 0.01);
  }

}