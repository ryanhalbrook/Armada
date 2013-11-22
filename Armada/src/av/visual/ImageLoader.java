package av.visual; 
import view.*;

import java.util.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImageLoader {

    static ImageLoader instance = null;

    public static synchronized ImageLoader getInstance() {
        if (instance == null) instance = new ImageLoader();
        return instance;
    }
    
    private ImageLoader() {
    
    }
    
    private Hashtable<String, BufferedImage> images = null;
    
    public synchronized BufferedImage getImage(String imgKey) {
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
    
    public synchronized void preloadImageAsync(String imgKey) {
        Thread loadImageThread = new Thread(new AsyncImageLoader(imgKey));
        loadImageThread.start();
    }
    
    public synchronized boolean imageIsLoaded(String imgKey) {
        return (images.get(imgKey) != null);
    }
    
    private boolean isImgKey(String imgKey) {
        return true;
    }
    
    private synchronized void loadImage(String imgKey) {
        
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
    
    private class AsyncImageLoader implements Runnable {    
        String imgString = null;
        public AsyncImageLoader(String imgKey) {
            imgString = imgKey;
        }
        public void run() {
            BufferedImage img = getImage(imgString);
        }
    }
}