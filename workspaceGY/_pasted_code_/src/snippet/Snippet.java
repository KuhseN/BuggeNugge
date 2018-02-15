package snippet;

public class Snippet {
	private CharacterEntity target;
	
	    public ZombieEntity(CharacterEntity target, int startX, int startY, int xSpeed, int ySpeed) {
	        super(startX, startY, xSpeed, ySpeed);
	        this.target = target;
	    }
	
	    public void move() {
	        int xDir = getDir(xPos - target.getX());
	        int yDir = getDir(yPos - target.getY());
	        super.moveTo(xSpeed * xDir, ySpeed * yDir);
	    }
	
	    private int getDir(int diff) {
	        if (diff == 0) {
	            return 0;
	        }
	        return diff > 0 ? 1 : -1;
	    }
	}
}

