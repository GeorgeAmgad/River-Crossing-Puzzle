package model.visuals;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Chicken implements ISprite {
    private BufferedImage[] images;

    public Chicken() throws IOException {
        images = new BufferedImage[2];
        BufferedImage left = ImageIO.read(new File("C:\\Users\\georg\\Desktop\\River Crossing Puzzle\\src\\resources\\32px\\003-hen.png"));
        BufferedImage right = ImageIO.read(new File("C:\\Users\\georg\\Desktop\\River Crossing Puzzle\\src\\resources\\32px\\003-hen-mirror.png"));

        images[0] = left;
        images[1] = right;

    }

    @Override
    public BufferedImage[] getImage() {
        return images;
    }
}
