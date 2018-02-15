import java.awt.Image;

public class WeaponEntity extends Entity {

    public WeaponEntity(Image image, double xPos, double yPos, int speed) {
        super(image, xPos, yPos, speed);
        dy = -1;
        this.setActive(false);
    }

    
    public void move(long deltaTime) {
        yPos += dy*(deltaTime/1000000000.0)*speed;
    }
}
