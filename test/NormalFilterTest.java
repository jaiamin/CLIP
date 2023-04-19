import org.junit.Test;

import model.filter.IFilter;
import model.filter.NormalFilter;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functions and behavior of the NormalFilter class.
 */
public class NormalFilterTest {

  @Test
  public void testToString() {
    IFilter nf = new NormalFilter();
    assertEquals("normal", nf.toString());
  }

}