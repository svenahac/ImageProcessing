import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of the image file: ");
        System.out.println("You can use the images in the images folder or put your own image in that folder and use it.");
        String fileName = sc.nextLine();
        System.out.println("Enter the name of the output file: ");
        System.out.println("The output file will be saved in the Output folder.");
        String outputFileName = sc.nextLine();
        System.out.println("Enter the name of the kernel you want to use: ");
        System.out.println("You can use the following kernels: ");
        System.out.println("1. blur");
        System.out.println("2. sharpen");
        System.out.println("3. edge detection");
        System.out.println("4. emboss");
        int kernelChoice = sc.nextInt();

        float[][] kernel = null;
        switch (kernelChoice) {
            case 1:
                kernel = Kernels.blur;
                break;
            case 2:
                kernel = Kernels.sharpen;
                break;
            case 3:
                kernel = Kernels.ridge;
                break;
            case 4:
                kernel = Kernels.emboss;
                break;
            default:
                kernel = Kernels.ridge;
        }

        BufferedImage image = readImage(fileName);


        long startTime = System.currentTimeMillis();
        System.out.println(image);
        BufferedImage outputImage = convolution(image, kernel);
        long endTime = System.currentTimeMillis();
        writeImage(outputImage, outputFileName);
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
    }
    public static BufferedImage readImage(String fileName) {
        try {
            File file = new File("src/Images/" + fileName);
            System.out.println("Image read successfully.");
            return ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("Failed to read image: " + e.getMessage());
            return null;
        }
    }

    public static void writeImage(BufferedImage image, String fileName) {
        try {
            File file = new File("src/Images/Output/" + fileName);
            ImageIO.write(image, "jpg", file);
            System.out.println("Image written successfully.");
        } catch (IOException e) {
            System.out.println("Failed to write image: " + e.getMessage());
        }
    }
    public static BufferedImage convolution(BufferedImage image, float[][] kernel) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, image.getType());
        int kernelWidth = kernel.length;
        int kernelHeight = kernel[0].length; 
        int kernelRadiusX = kernelWidth / 2;
        int kernelRadiusY = kernelHeight / 2;


        for (int imgW = 0; imgW < width; imgW++) {
            for (int imgH = 0; imgH < height; imgH++) {

                float red = 0f, green = 0f, blue = 0f;

                for (int kerH = 0; kerH < kernelHeight; kerH++) {
                    for (int kerW = 0; kerW < kernelWidth; kerW++) {

                        int pixelX = (imgW - kernelRadiusX + kerW + width) % width;
                        int pixelY = (imgH - kernelRadiusY + kerH + height) % height;

                        int rgb = image.getRGB(pixelX, pixelY);
                        red += ((rgb >> 16) & 0xff) * kernel[kerH][kerW];
                        green += ((rgb >> 8) & 0xff) * kernel[kerH][kerW];
                        blue += (rgb & 0xff) * kernel[kerH][kerW];
                    }
                }
                int r = Math.min(Math.max((int) (red), 0), 255);
                int g = Math.min(Math.max((int) (green), 0), 255);
                int b = Math.min(Math.max((int) (blue), 0), 255);
                Color color = new Color(r, g, b);
                outputImage.setRGB(imgW, imgH, color.getRGB());

            }
        }
        return outputImage;
    }

}