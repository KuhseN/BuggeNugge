import java.awt.Image;
import java.util.ArrayList;


public class ZombieEntity extends Entity{
	private ArrayList<ZombieEntity> zombies;

	
    public ZombieEntity(Image image, double xPos, double yPos, int speed) {
		super(image, xPos, yPos, speed);
	    
	}
    
    public class Enemies {
        

        public Enemies(ZombieEntity... entities) {
            zombies = new ArrayList<ZombieEntity>();
            for (int i = 0; i < entities.length; ++i) {
                zombies.add(entities[i]);
            }
        }

        public void addEnemy(ZombieEntity enemy) {
            zombies.add(enemy);
        }

        public void removeEnemy(ZombieEntity enemy) {
            zombies.remove(enemy);
        }

        public ZombieEntity getEnemy(int position) {
            if (zombies.size() <= position) {
                return null;
            }
            return zombies.get(position);
        }
    }
    


	public void move(long deltaTime) {
		
         
	}


	//Z
    public boolean hasStopped() {
        return speed == 0;
    }
    

    
}