package src; import src.view.*;
import java.util.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImageLoader {
    private static Hashtable<String, BufferedImage> images = null;
    
    public static BufferedImage getImage(String imgKey) {
        //System.out.println("Image: " + imgKey);
        if (imgKey == "" | imgKey == null) return null;
        if (images == null) {
            images = new Hashtable<String, BufferedImage>();
        }
        BufferedImage img = (BufferedImage)images.get(imgKey);
        if (img == null) {
            loadImage(imgKey);
            img = images.get(imgKey);
        }
        return img;
    }
    
    private static boolean isImgKey(String imgKey) {
        return true;
    }
    
    private static void loadImage(String imgKey) {
        
        BufferedImage bi = null;
        try {
            URL url = new ImageLoader().getClass().getClassLoader().getResource("image/" + imgKey);
			bi = ImageIO.read(url);
        } catch (IOException e) {
            //System.out.println("Failed to load image on attempt 1: " + imgKey);
        } catch (IllegalArgumentException e) {
            //System.out.println("Failed to load image on attempt 1: " + imgKey);
        } catch (NullPointerException e) {
            //System.out.println("Failed to load image on attempt 1: " + imgKey);
        }
        if (bi == null) {
            try {
                URL url = new ImageLoader().getClass().getClassLoader().getResource("src/image/" + imgKey);
			    bi = ImageIO.read(url);
            } catch (IOException e) {
                //System.out.println("Failed to load image on attempt 2: " + imgKey);
            } catch (IllegalArgumentException e) {
                //System.out.println("Failed to load image on attempt 2: " + imgKey);
            } catch (NullPointerException e) {
                //System.out.println("Failed to load image on attempt 2: " + imgKey);
            }
        }
        if (bi != null) {
            images.put(imgKey, bi);
        }
        
        
    }
}