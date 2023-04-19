import org.junit.Test;

import java.io.IOException;

import model.CollageModel;
import model.ColorImage;
import model.ICanvas;
import model.ILayer;
import model.ImageLayer;
import model.ImageUtil;
import model.filter.BrightenFilter;
import model.filter.Component;
import model.filter.ComponentFilter;
import model.filter.DifferenceFilter;
import model.filter.LightType;

import static org.junit.Assert.assertEquals;

/**
 * * Tests the functions and behavior of the DifferenceFilter class.
 */
public class DifferenceFilterTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor() {
    DifferenceFilter df = new DifferenceFilter(null);
  }

  @Test
  public void testApply1() throws IOException {
    ICanvas m = new CollageModel(4, 4, "test");
    ILayer l1 = new ImageLayer(4, 4, "l1");
    ILayer l2 = new ImageLayer(4, 4, "l2");
    ILayer l3 = new ImageLayer(4, 4, "l3");
    m.addLayerToCanvas(4, 4, "l1");
    m.addLayerToCanvas(4, 4, "l2");
    m.addLayerToCanvas(4, 4, "l3");
    m.addImageToLayer("l2",
            new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels()), 0, 0);
    assertEquals(15, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(15, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
    l2.applyFilter(new DifferenceFilter(m.getPrevIm("l2")));
    assertEquals(240, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(255, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(240, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
    m.addImageToLayer("l1",
            new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels()), 0, 0);
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
    l1.applyFilter(new ComponentFilter(Component.BLUE));
    l2.applyFilter(new DifferenceFilter(m.getPrevIm("l2")));
    assertEquals(15, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
    m.addImageToLayer("l3",
            new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels()), 0, 0);
    assertEquals(15, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
    l3.applyFilter(new BrightenFilter(LightType.INTENSITY));
    l2.applyFilter(new DifferenceFilter(m.getPrevIm("l2")));
    assertEquals(15, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
  }

  @Test
  public void testToString() throws IOException {
    DifferenceFilter df = new DifferenceFilter(new ColorImage(
            ImageUtil.readPPM("res/example2.ppm").getPixels()));
    assertEquals("difference", df.toString());
  }
}