import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;

class iosRescaler {

    /**
     *     iphone   x   ipad
     *
     * 1. portrait  x portrait  (1242x2208) x (2048x2732)
     * 2. portrait  x landscape (1242x2208) x (2732x2048)
     * 3. landscape x landscape (2208x1242) x (2732x2048)
     * 4. landscape x portrait  (2208x1242) x (2048x2732)
    **/
    
    //                                     1    2      3     4
    private final int iphoneWidth[]   = {1242, 1242, 2208, 2208};
    private final int iphoneHeight[]  = {2208, 2208, 1242, 1242};
    private final int ipadWidth[]     = {2048, 2732, 2732, 2048};
    private final int ipadHeight[]    = {2732, 2048, 2048, 2732};
    
    public static void main(String...args) {
        iosRescaler rescaler = new iosRescaler();
        
        int option = -1;
        String path = "";
        
        if(args.length == 2) {
            path = args[0];
            try {
                option = Integer.parseInt(args[1]);
            } catch(Exception e) {
                System.out.println("Invalid Option Parameter");
                printUsage();
                System.exit(0);
            }
        } else {
            printUsage();
            System.exit(0);
        }
        
        rescaler.scaleAll(path, option);
    }
    
    /*
     takes a path and looks for ipad and iphone folders within,
     grabs all files and passes each path to resize function
     */
    private void scaleAll(String path, int option) {
        File iphoneFolder = new File(path+"iphone");
        File ipadFolder = new File(path+"ipad");
        
        if(iphoneFolder.exists()) {
            File[] listOfFiles = iphoneFolder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    String filePath = path + "iphone/" + listOfFiles[i].getName();
                    try {
                        resize(filePath, filePath, iphoneWidth[option-1], iphoneHeight[option-1]);
                    } catch(IOException ioe) {
                        System.out.println("File not found: " + filePath);
                    }
                }
            }
        }
        
        if(ipadFolder.exists()) {
            File[] listOfFiles = ipadFolder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    String filePath = path + "ipad/" + listOfFiles[i].getName();
                    try {
                        resize(filePath, filePath, ipadWidth[option-1], ipadHeight[option-1]);
                    } catch(IOException ioe) {
                        System.out.println("File not found: " + filePath);
                    }
                }
            }
        }
    }
    
    private static void printUsage() {
        System.out.println("USAGE: ");
        System.out.println("");
        System.out.println("java iosRescaler <pathToImages> <option>");
        System.out.println("portrait  x portrait  (1242x2208) x (2048x2732)");
        System.out.println("portrait  x landscape (1242x2208) x (2732x2048)");
        System.out.println("landscape x landscape (2208x1242) x (2732x2048)");
        System.out.println("landscape x portrait  (2208x1242) x (2048x2732)");
        System.out.println();
        System.out.println("There should be two folders in the given path, 'iphone' and 'ipad', which should contain the respective images to be rescaled. These *WILL* be overwritten.");
        System.out.println();
    }
    
    private static void resize(String inPath, String outPath, int newWidth, int newHeight)
    throws IOException {
        
        // gets input img
        File inputFile = new File(inPath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        
        // prepares output img
        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, inputImage.getType());
        
        // create graphics context
        Graphics2D g2d = outputImage.createGraphics();
        
        // sets highest graphics option
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        
        // draw output img
        g2d.drawImage(inputImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        
        // gets file type and writes image
        String fileType = outPath.substring(outPath.lastIndexOf(".") + 1);
        ImageIO.write(outputImage, fileType, new File(outPath));
    }
}
