import java.awt.*;

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

        Picture swan = new Picture("swan.jpg");
        Picture gore = new Picture("gorge.jpg");
        swan.explore();
        Picture hiddenSwan = hidePicture(swan, gore);
        hiddenSwan.explore();
        Picture revealedSwan = revealPicture(hiddenSwan);
        revealedSwan.explore();
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
        return source.getHeight() == secret.getHeight() && source.getWidth() == secret.getWidth();
    }

    public static Picture hidePicture(Picture source, Picture secret){
        if(!canHide(source, secret)) return source;

        Pixel[][] sourceA = source.getPixels2D();
        Pixel[][] secretA = secret.getPixels2D();

        for(int r = 0; r < sourceA.length; r++){
            for(int c = 0; c < sourceA[0].length; c++){
                setLow(sourceA[r][c], secretA[r][c].getColor());
            }
        }

        return source;
    }
}
