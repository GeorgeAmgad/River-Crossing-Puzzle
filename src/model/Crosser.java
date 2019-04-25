package model;
import javafx.geometry.Dimension2D;
import model.visuals.ISprite;
import java.awt.image.BufferedImage;

public class Crosser implements ICrosser {

    private ISprite image;
    private double weight;  //if no weight assign -1
    private Boolean canSail;
    private int type;
    private String label;
    private Dimension2D position;

    public Crosser(ISprite image, double weight, Boolean canSail, int type, String label) {
        this.image = image;
        this.weight = weight;
        this.canSail = canSail;
        this.type = type;
        this.label = label;
    }

    @Override
    public boolean canSail() {
        return canSail;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public int getEatingRank() {
        return type;
    }

    @Override
    public BufferedImage[] getImages() {
        return image.getImage();
    }

    @Override
    public ICrosser makeCopy() {
        return new Crosser(image,weight,canSail,type,label);
    }

    @Override
    public void setLabelToBeShown(String label) {
        this.label = label;
    }

    @Override
    public String getLabelToBeShown() {
        return label;
    }

}
