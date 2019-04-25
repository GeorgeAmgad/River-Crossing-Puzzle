package model.visuals;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Veggies implements ISprite {

    private static BufferedImage[] images;

    public Veggies() throws IOException {
        images = new BufferedImage[2];
        BufferedImage left = ImageIO.read(new File("C:\\Users\\georg\\Desktop\\River Crossing Puzzle\\src\\resources\\32px\\005-harvest.png"));

        images[0] = left;
        images[1] = left;

    }

    @Override
    public BufferedImage[] getImage() {
        return images;
    }
}
