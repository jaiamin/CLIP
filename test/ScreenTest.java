import org.junit.Test;

import java.io.IOException;

import model.CollageModel;
import model.ColorImage;
import model.ICanvas;
import model.ILayer;
import model.ImageLayer;
import model.ImageUtil;
import model.filter.AbstractBlendFilters;
import model.filter.DarkenFilter;
import model.filter.LightType;
import model.filter.ScreenFilter;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functions and behavior of the ScreenFilter class.
 */
public class ScreenTest {
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor() {
    AbstractBlendFilters sf = new ScreenFilter(null);
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
    m.addFilter(new ScreenFilter(m.getPrevIm("l2")), "l2");
    assertEquals(255, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(255, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(255, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
    m.addImageToLayer("l1",
            new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels()), 0, 0);
    assertEquals(29, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(29, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
    m.addFilter(new DarkenFilter(LightType.VALUE), "l1");
    m.addFilter(new ScreenFilter(m.getPrevIm("l2")), "l2");
    assertEquals(15, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(15, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
    m.addImageToLayer("l3",
            new ColorImage(ImageUtil.readPPM("res/example2.ppm").getPixels()), 0, 0);
    assertEquals(15, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(15, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
    m.addFilter(new DarkenFilter(LightType.LUMA), "l3");
    m.addFilter(new ScreenFilter(m.getPrevIm("l2")), "l2");
    assertEquals(15, m.getLayers().get(1).getImage().getPixel(3, 0).getRed());
    assertEquals(0, m.getLayers().get(1).getImage().getPixel(3, 0).getGreen());
    assertEquals(15, m.getLayers().get(1).getImage().getPixel(3, 0).getBlue());
  }

  @Test
  public void testToString() throws IOException {
    AbstractBlendFilters sf = new ScreenFilter(new ColorImage(
            ImageUtil.readPPM("res/example2.ppm").getPixels()));
    assertEquals("screen", sf.toString());
  }
}