import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Steganography {
    public static void main(String[] args) {
//        Picture beach = new Picture("beach.jpg");
//        beach.explore();
//        Picture copy = testClearLow(beach);
//        copy.explore();

//        Picture beach2 = new Picture("beach.jpg");
//        beach2.explore();
//        Picture copy2 = testSetLow(beach2, Color.PINK);
//        copy2.explore();

//        Picture copy3 = revealPicture(copy2);
//        copy3.explore();

//        Picture swan = new Picture("swan.jpg");
//        Picture gore = new Picture("gorge.jpg");
//
//        System.out.println(isSame(swan, gore));

//        swan.explore();
//        Picture hiddenSwan = hidePicture(swan, gore);
//        hiddenSwan.explore();
//        Picture revealedSwan = revealPicture(hiddenSwan);
//        revealedSwan.explore();

        // Activity 3
//        Picture beach = new Picture("beach.jpg");
//        Picture robot = new Picture("robot.jpg");
//        Picture flower1 = new Picture("flower1.jpg");
//        beach.explore();
//
//        Picture hidden1 = hidePicture(beach, robot, 65, 208);
//        Picture hidden2 = hidePicture(hidden1, flower1, 280, 110);
//        hidden2.explore();
//
//        Picture unhidden = revealPicture(hidden2);
//        unhidden.explore();


        Picture arch = new Picture("arch.jpg");
        Picture koala = new Picture("koala.jpg");
        Picture robot1 = new Picture("robot.jpg");
        ArrayList<Point> pointList = findDifferences(arch, arch);
        System.out.println("Pointlist after comparing two identical pictures has a size of " + pointList.size());
        pointList = findDifferences(arch, koala);
        System.out.println("Pointlist after comparing two different sized pictures has a size of " + pointList.size());
        Picture arch2 = hidePicture(arch, robot1, 65, 102);
        pointList = findDifferences(arch, arch2);
        System.out.println("Pointlist after hiding a picture has a size of " + pointList.size());
        arch.show();
        arch2.show();


    }

    public static void clearLow(Pixel p) {
        p.setRed(p.getRed() / 4 * 4);
        p.setGreen(p.getGreen() / 4 * 4);
        p.setBlue(p.getBlue() / 4 * 4);
    }

    public static Picture testClearLow(Picture p){
        Pixel[][] pixels = p.getPixels2D();

        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[0].length; col++){
                clearLow(pixels[row][col]);
            }
        }

        return p;
    }

    public static void setLow(Pixel p, Color c){
        int red = p.getRed() / 4 * 4;
        int green = p.getGreen() / 4 * 4;
        int blue = p.getBlue() / 4 * 4;

        p.setRed(red + (c.getRed()/64));
        p.setGreen(green + (c.getGreen()/64));
        p.setBlue(blue + (c.getBlue()/64));
    }

    public static Picture testSetLow(Picture p, Color c){
        Pixel[][] pixels = p.getPixels2D();

        for(int row = 0; row < pixels.length; row++){
            for(int col = 0; col < pixels[0].length; col++){
                setLow(pixels[row][col], c);
            }
        }

        return p;
    }

    public static Picture revealPicture(Picture hidden){
        Picture copy = new Picture(hidden);
        Pixel[][] pixels = copy.getPixels2D();
        Pixel[][] source = hidden.getPixels2D();
        for(int r = 0; r < pixels.length; r++){
            for(int c = 0; c < pixels[0].length; c++){
                Color col = source[r][c].getColor();
                int red = col.getRed() % 4 * 64;
                int green = col.getGreen() % 4 * 64;
                int blue = col.getBlue() % 4 * 64;
                pixels[r][c].setRed(red);
                pixels[r][c].setGreen(green);
                pixels[r][c].setBlue(blue);
            }
        }
        return copy;
    }

    public static boolean canHide(Picture source, Picture secret){
        return source.getHeight() >= secret.getHeight() && source.getWidth() >= secret.getWidth();
    }

    public static boolean isSame(Picture p1, Picture p2){
        Pixel[][] p1Array = p1.getPixels2D();
        Pixel[][] p2Array = p2.getPixels2D();

        if(p1Array.length != p2Array.length && p1Array[0].length != p2Array[0].length) return false;

        for(int r = 0; r < p1Array.length; r++){
            for(int c = 0; c < p1Array[0].length; c++){
                if(!p1Array[r][c].getColor().equals(p2Array[r][c].getColor())) return false;
            }
        }

        return true;
    }

    public static Picture hidePicture(Picture source, Picture secret){
        if(!canHide(source, secret)) return source;
        Picture copy = new Picture(source);

        Pixel[][] copyA = copy.getPixels2D();
        Pixel[][] secretA = secret.getPixels2D();

        for(int r = 0; r < copyA.length; r++){
            for(int c = 0; c < copyA[0].length; c++){
                setLow(copyA[r][c], secretA[r][c].getColor());
            }
        }

        return source;
    }

    public static Picture hidePicture(Picture source, Picture secret, int startRow, int startCol){
        if(!canHide(source, secret)) return source;
        Picture copy = new Picture(source);

        Pixel[][] copyA = copy.getPixels2D();
        Pixel[][] secretA = secret.getPixels2D();

        for(int r = startRow, r1 = 0; r1 < secretA.length; r++, r1++){
            for(int c = startCol, c1 = 0; c1 < secretA[0].length; c++, c1++){
                setLow(copyA[r][c], secretA[r1][c1].getColor());
            }
        }

        return copy;
    }

    public static ArrayList<Point> findDifferences(Picture p1, Picture p2){
        ArrayList<Point> pointList = new ArrayList<>();
        Pixel[][] p1Array = p1.getPixels2D();
        Pixel[][] p2Array = p2.getPixels2D();

        if(p1Array.length != p2Array.length && p1Array[0].length != p2Array[0].length) return pointList;

        for(int r = 0; r < p1Array.length; r++){
            for(int c = 0; c < p1Array[0].length; c++){
                if(!p1Array[r][c].getColor().equals(p2Array[r][c].getColor())){
                    pointList.add(new Point(r, c));
                }
            }
        }
        return pointList;
    }
}
