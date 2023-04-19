import org.junit.Test;

import java.io.IOException;

import model.CollageModel;
import model.ColorImage;
import model.ICanvas;
import model.ILayer;
import model.ImageLayer;
import model.ImageUtil;
import model.filter.AbstractBlendFilters;
import model.filter.BrightenFilter;
import model.filter.Component;
import model.filter.ComponentFilter;
import model.filter.LightType;
import model.filter.MultiplyFilter;

import static org.junit.Assert.assertEquals;

/**
 * * Tests the functions and behavior of the MultiplyFilter class.
 */
public class MultiplyTest {
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor() {
    AbstractBlendFilters m = new MultiplyFilter(null);
  }

  @Test
  public void testApply1() throws IOException {
    ICanvas m = new CollageModel(4, 4, "test");
    ILayer l1 = new ImageLayer(4, 4, "l1");
    ILayer l2 = new ImageLayer(4, 4, "l2");
    ILayer l3 = new ImageLayer(4, 4, "l3");
    m.addLayerToCanvas(4, 4,"l1");
    m.addLayerToCanvas(4, 4, "l2");
    m.addLayerToCanvas(4, 4, "l3");
    m.addImageToLayer("l2",
            new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels()), 0, 0);
    assertEquals(15, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(15, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
    m.addFilter(new MultiplyFilter(m.getPrevIm("l2")), "l2");
    assertEquals(14, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(14, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
    m.addImageToLayer("l1",
            new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels()), 0, 0);
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
    m.addFilter(new ComponentFilter(Component.BLUE), "l1");
    m.addFilter(new MultiplyFilter(m.getPrevIm("l2")), "l2");
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
    m.addImageToLayer("l3",
            new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels()), 0, 0);
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
    m.addFilter(new BrightenFilter(LightType.INTENSITY), "l3");
    m.addFilter(new MultiplyFilter(m.getPrevIm("l2")), "l2");
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
  }

  @Test
  public void testToString() throws IOException {
    AbstractBlendFilters m = new MultiplyFilter(new ColorImage(
            ImageUtil.readPPM("res/example2.ppm").getPixels()));
    assertEquals("multiply", m.toString());
  }
}