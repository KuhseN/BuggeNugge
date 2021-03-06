import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class Entity {

    private Image image;

    protected double xPos, yPos;   // Positionen

    protected int speed;           // Hastighet i x- o y-led

    protected int dx = 0, dy = 0;  // Rörelseriktning

    //get för dy och dx
    int dyGet() {
        return dy;
    }

    int dxGet() {
        return dx;
    }

    private boolean active = true;
    private Rectangle rec = null;

    // Gör alla nya objekt aktiva.
    /**
     * Konstruktor
     */
    public Entity(Image image, double xPos, double yPos, int speed) {
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = speed;

        rec = new Rectangle((int) xPos, (int) yPos, image.getWidth(null), image.getHeight(null));
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Ritar bilden på ytan g
     */
    public void draw(Graphics2D g) {
        g.drawImage(image, (int) xPos, (int) yPos, null);
    }

    /**
     * Vilken riktning i x-led
     * @param dx 0 = stilla, 1 = höger, -1 = vänster
     */
    public void setDirectionX(int dx) {
        this.dx = dx;
    }

    /**
     * Vilken riktning i y-led
     * @param dy 0 = stilla, 1 = höger, -1 = vänster
     */
    public void setDirectionY(int dy) {
        this.dy = dy;
    }

    /**
     * Metod som gör förflyttningen, dvs ändrar xPos och yPos
     * Måste skaps i klasser som ärver entity
     * 
     * 
     */

    public abstract void move(long deltaTime);

    public double getX() {
        return xPos;
    }
    public double getY() {
		
		return yPos;
	}

    public Rectangle getRectangle() {
        rec.setLocation((int) xPos, (int) yPos);
        return rec;
    }

    public boolean collision(Entity entity) {
        getRectangle();
        return rec.intersects(entity.getRectangle());
    }


	

}

  


