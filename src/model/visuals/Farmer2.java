package model.visuals;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Farmer2 implements ISprite {

    private BufferedImage[] images;

    public Farmer2() throws IOException {
        images = new BufferedImage[2];
        BufferedImage left = ImageIO.read(new File("C:\\Users\\georg\\Desktop\\River Crossing Puzzle\\src\\resources\\64px\\006-work2.png"));
        BufferedImage right = ImageIO.read(new File("C:\\Users\\georg\\Desktop\\River Crossing Puzzle\\src\\resources\\64px\\006-work2-mirror.png"));


        images[0] = left;
        images[1] = right;

    }

    @Override
    public BufferedImage[] getImage() {
        return images;
    }
}
