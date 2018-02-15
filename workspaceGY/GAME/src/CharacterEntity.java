
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class CharacterEntity extends Entity{
	
	
	

	public CharacterEntity (Image image, double xPos, double yPos, int speed){
		super(image, xPos, yPos, speed);
	}
	
	public void draw(Graphics2D g){
	    if(missile != null && missile.getActive()){
	        missile.draw(g);   
	    }
	    super.draw(g);
	}
	
	public boolean tryToFire(){
	    if(missile == null || !missile.getActive() || missile.yPos < 0){
	        missile = new WeaponEntity(new 
	         ImageIcon("imagesG/missile.png").getImage(), getX() + 10, yPos, 800);
	        missile.setActive(true);
	        
	        return true;
	    }else{
	        return false;
	    }
	}
	
	/**
	 * Ändrar läget i y-led
	 */
	public void move(long deltaTime){
		if(missile != null && missile.getActive()){
		    missile.move(deltaTime);    
		}
		 
		xPos += dx*(deltaTime/1000000000.0)*speed;
		
		yPos += dy*(deltaTime/1000000000.0)*speed;
	}
	
	public WeaponEntity missile = null;

	
}



