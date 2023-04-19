package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

/**
 * This class contains utility methods to read a PPM, JPG/JPEG, and PNG images from file
 * and retrieve its contents.
 */
public class ImageUtil {
  /**
   * Read an image file in the provided image format and return its {@code IImage}.
   * Currently, only PPM, JPG/JPEG, and PNG image formats are supported.
   *
   * @param filename the path of the PPM file.
   * @throws IOException if there is an I/O error when reading the file
   * @throws IllegalArgumentException if an unsupported/invalid image format is provided
   */
  public static IImage readImage(String filename) throws IOException, IllegalArgumentException {
    if (filename.endsWith(".ppm")) {
      return readPPM(filename);
    } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
      return readJPG(filename);
    } else if (filename.endsWith(".png")) {
      return readPNG(filename);
    } else {
      throw new IllegalArgumentException("Invalid image format. Only PPM, JPG/JPEG, " +
                                         "and PNG are supported.");
    }
  }

  /**
   * Read an image file in the PPM format and return its {@code IImage}.
   *
   * @param filename the path of the PPM file.
   * @throws IOException if there is an I/O error when reading the file
   */
  public static IImage readPPM(String filename) throws IOException {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File '" + filename + "' not found.");
    }

    try {
      StringBuilder builder = new StringBuilder();
      while (sc.hasNextLine()) {
        String s = sc.nextLine();
        if (s.charAt(0) != '#') {
          builder.append(s).append(System.lineSeparator());
        }
      }
      sc = new Scanner(builder.toString());

      String token;
      token = sc.next();
      int width = sc.nextInt();
      int height = sc.nextInt();
      int maxValue = sc.nextInt();

      IPixel[][] pixels = new ColorPixel[height][width];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int r = sc.nextInt();
          int g = sc.nextInt();
          int b = sc.nextInt();
          pixels[i][j] = new ColorPixel(r, g, b, maxValue);
        }
      }
      return new ColorImage(height, width, maxValue, pixels, filename);
    } catch (StringIndexOutOfBoundsException e) {
      throw new IOException("Unable to read file.");
    }
  }

  /**
   * Read an image file in the JPG/JPEG format and return its {@code IImage}.
   *
   * @param filename the path of the JPG/JPEG file.
   * @throws IOException if there is an I/O error when reading the file
   */
  public static IImage readJPG(String filename) throws IOException {
    try {
      BufferedImage img = ImageIO.read(new File(filename));
      int height = img.getHeight();
      int width = img.getWidth();
      int maxValue = 255;
      IPixel[][] pixels = new ColorPixel[height][width];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int rgb = img.getRGB(j, i);
          int r = (rgb >> 16) & 0xFF;
          int g = (rgb >> 8) & 0xFF;
          int b = rgb & 0xFF;
          pixels[i][j] = new ColorPixel(r, g, b, maxValue);
        }
      }
      return new ColorImage(height, width, maxValue, pixels, filename);
    } catch (IOException e) {
      throw new IOException("Unable to read file.");
    }
  }

  /**
   * Read an image file in the PNG format and return its {@code IImage}.
   *
   * @param filename the path of the PNG file.
   * @throws IOException if there is an I/O error when reading the file
   */
  public static IImage readPNG(String filename) throws IOException {
    try {
      BufferedImage img = ImageIO.read(new File(filename));
      int height = img.getHeight();
      int width = img.getWidth();
      int maxValue = 255;
      IPixel[][] pixels = new ColorPixel[height][width];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int rgba = img.getRGB(j, i);
          int r = (rgba >> 16) & 0xFF;
          int g = (rgba >> 8) & 0xFF;
          int b = rgba & 0xFF;
          int a = (rgba >> 24) & 0xFF;
          pixels[i][j] = new ColorPixel(r, g, b, a);
        }
      }
      return new ColorImage(height, width, maxValue, pixels, filename);
    } catch (IOException e) {
      throw new IOException("Unable to read file.");
    }
  }
}