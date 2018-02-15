import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial") //Fick tips
public class GameMain extends JFrame implements KeyListener {

    private BufferStrategy backBuffer;
    private Canvas gameCanvas;
    private boolean gameRunning = true;
    private long lastUpdateTime;

    private HashMap<String, Boolean> keyDown = new HashMap<>();

    private CharacterEntity cMain;
    private ArrayList<ZombieEntity> zombie; //Z
    ArrayList<Entity> entityArray = new ArrayList<>();

    private int canvasWidth = 1000, canvasHeight = 800;
    private int score;

    public GameMain() {
        super("Space Invader");
        zombie = new ArrayList<ZombieEntity>(); //Z

        addKeyListener(this);

        keyDown.put("a", false);
        keyDown.put("d", false);
        keyDown.put("w", false);
        keyDown.put("s", false);
        keyDown.put("shoot", false);

        createWindow();
        loadObjects();

        gameLoop();
    }

    public void loadObjects() {

        /** 
         * Om du skall exportera till en körbar jar-fil skall raden ändras till 
         * ship = new ImageIcon(getClass().getResource("/ship.png")).getImage(); 
         */

        //ship
        Image characterImg = new ImageIcon("imagesGY/character.png").getImage();

        double x = gameCanvas.getWidth() / 2 - characterImg.getWidth(null) / 2;
        double y = gameCanvas.getHeight() /2 - characterImg.getHeight(null) / 2;

        //mainc = new ShipEntity(shipImg, x, y, 4);  

        //mainc
        Image zombieImg = new ImageIcon("imagesGY/zombie.png").getImage();
        double dy = 0; //problem. kan inte få den att ladda in på toppen av skärmen

        //zombie = new AlienEntity(alienImg, dx, dy, 4); 

        //mainc
        cMain = new CharacterEntity(characterImg, x, y, 190);
        entityArray.add(cMain);

        //aliens, also Z
        
    }

    public void createWindow() {
        gameCanvas = new Canvas();
        gameCanvas.setSize(canvasWidth, canvasHeight);
        gameCanvas.setFocusable(false);

        this.add(gameCanvas);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        gameCanvas.createBufferStrategy(2);
        backBuffer = gameCanvas.getBufferStrategy();
    }

    public void update(long deltaTime) {
        backBuffer.getDrawGraphics();

        cMain.setDirectionX(0);
        cMain.setDirectionY(0);
        if (keyDown.get("d")) {
            if (cMain.getX() > canvasWidth - 51) {
            	cMain.setDirectionX(0);
            } else {
            	cMain.setDirectionX(1);
            }
        }

        if (keyDown.get("a")) {

            if (cMain.getX() < 5) {
            	cMain.setDirectionX(0);
            } else {
            	cMain.setDirectionX(-1);
            }
        }
            
            
            if (keyDown.get("w")) {

                if (cMain.getY() < 7) {
                	cMain.setDirectionY(0);
                } else {
                	cMain.setDirectionY(-1);
                }
            }
                
                
            if (keyDown.get("s")) {

                    if (cMain.getY() >  canvasHeight - 83) {
                    	cMain.setDirectionY(0);
                    } else {
                    	cMain.setDirectionY(1);
                    }
            }
                
        
     
        
    
        if (keyDown.get("shoot")) {
        	cMain.tryToFire();
        }

        /*
        ship.move();  
        alien.move();
         */

        for (int i = 0; i < entityArray.size(); i++) {
            entityArray.get(i).move(deltaTime);
        }

        checkCollisionAndRemove();
        

    }

    public void render() {

        Graphics2D g = (Graphics2D) backBuffer.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        for (int i = 0; i < entityArray.size(); i++) {
            entityArray.get(i).draw(g);

            if (entityArray.size() == 1) {
                g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
                g.setColor(Color.green);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
                g.drawString("Final score: " + score, 430, 400);
                
                gameRunning = false;
                
            } else if (entityArray.size() != 1 && oneAlienStopped()) { //Z
                g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
                g.setColor(Color.red);
                g.drawString("GAME OVER!", 380, 200);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
                g.drawString("Final score: " + score, 430, 400);
                
                gameRunning = false;
                
                

            }
        }

        g.setFont(new Font("TimesRoman", Font.PLAIN, 13));
        g.setColor(Color.green);
        g.drawString("SCORE: " + score, 5, 15);

        cMain.draw(g);

        

        g.dispose();
        backBuffer.show();

    }

    //Z
    private boolean oneAlienStopped() {
        for (ZombieEntity zombie : zombie) {
            if (zombie.hasStopped()) {
                return true;
            }
        }
        return false;
    }

    public void gameLoop() {
        lastUpdateTime = System.nanoTime();

        while (gameRunning) {
            long deltaTime = System.nanoTime() - lastUpdateTime;

            if (deltaTime > 33333333) {
                lastUpdateTime += deltaTime;
                update(deltaTime);

                render();
            }
        }
    }

    public void checkCollisionAndRemove() {
        ArrayList<Entity> scoreList = new ArrayList<>();
        ArrayList<Entity> removeList = new ArrayList<>();

        // alien <-> missile

        for (int i = 1; i < entityArray.size(); i++) {

            if (cMain.missile != null && cMain.missile.getActive() && cMain.missile.collision(entityArray.get(i))) {

                scoreList.add(entityArray.get(i));
                removeList.add(entityArray.get(i));
                cMain.missile.setActive(false);

            }
        }
        entityArray.removeAll(removeList); // Alt namnet på arraylist
        if (scoreList.size() > 0) {
            score++;
        }

    }

    /** Spelets tangentbordslyssnare */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A)
            keyDown.put("a", true);
        else if (key == KeyEvent.VK_D)
            keyDown.put("d", true);
        else if (key == KeyEvent.VK_W)
            keyDown.put("w", true);
        else if (key == KeyEvent.VK_S)
            keyDown.put("s", true);

        if (key == KeyEvent.VK_SPACE)
            keyDown.put("shoot", true);
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A)
            keyDown.put("a", false);
        else if (key == KeyEvent.VK_D)
            keyDown.put("d", false);
        else if (key == KeyEvent.VK_W)
            keyDown.put("w", false);
        else if (key == KeyEvent.VK_S)
            keyDown.put("s", false);

        if (key == KeyEvent.VK_SPACE)
            keyDown.put("shoot", false);
    }

    public void keyTyped(KeyEvent e) {

    }

    public static void main(String[] args) {
        new GameMain();

    }

}
