import greenfoot.*;
import java.util.*;

/**
 * Write a description of class GameController here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameController  
{
    private ArrayList<Square> deadSquares = new ArrayList<Square>(); //everything that fell down and is now not moving
    private Square nextObject = null;
    private Square currentObject = null;
    private int count = 0;
    /**
     * Constructor for objects of class GameController
     */
    private World world;
    public GameController(World worldCopy)
    {
        this.world = worldCopy;
    }

    public void run()
    {
        if(nextObject == null) {
            nextObject = makeNewSquare();
        }
        if(currentObject == null) {
            currentObject = nextObject;
            currentObject.moveTo(5, 1);
            nextObject = null;
        }
        if(count > 4 || Greenfoot.isKeyDown("Down")) {
            if(!currentObject.fallDown()) {
                for(Square child : currentObject.children) {
                    deadSquares.add(child);
                }
                currentObject.children.clear();
                deadSquares.add(currentObject);
                
                checkRows();
                
                currentObject = nextObject;
                currentObject.moveTo(5, 1);
                if(!currentObject.fallDown()) { //Game Over
                    world.showText("GAME OVER!", (world.getWidth() - 9)/2, world.getHeight()/2);
                    Greenfoot.delay(15);
                    World menu = new MenuScene();
                    Greenfoot.setWorld(menu);
                }
                nextObject = null;
            }
            count = 0;
        }
        
        if(Greenfoot.isKeyDown("Right")) currentObject.moveOne(1);
        else if(Greenfoot.isKeyDown("Left")) currentObject.moveOne(-1);
        
        if(Greenfoot.isKeyDown("Up")) currentObject.rotateBlock();
        
        count++;
    }
    
    private Square makeNewSquare()
    {
        Square newSquare;
        int randomInteger = (int)(Math.random()*7);
        //randomInteger = 0; //temp as this is overwriting the rng
        if (randomInteger == 0) newSquare = new RedSquare();
        else if (randomInteger == 1) newSquare = new GreenSquare();         
        else if (randomInteger == 2) newSquare = new YellowSquare();
        else if (randomInteger == 3) newSquare = new WhiteSquare(); 
        else if (randomInteger == 4) newSquare = new PurpleSquare(); 
        else if (randomInteger == 5) newSquare = new OrangeSquare(); 
        else newSquare = new BlueSquare();
        
        newSquare.addParent(3, world);
        
        return newSquare;
    }
    
    private void checkRows() {
        for(int y = 0; y < world.getHeight(); y++) {
            boolean fullRow = true;
            for(int x = 0; x < world.getWidth() - 9; x++) {
                if(world.getObjectsAt(x, y, Square.class).isEmpty()) fullRow = false;
            }
            if(fullRow) {
                ArrayList<Integer> deleteThis = new ArrayList<Integer>();
                for(Square sqr : deadSquares) {
                    if(sqr.getY() == y) {
                        world.removeObject(sqr);
                        deleteThis.add(deadSquares.indexOf(sqr));
                        
                    }
                    else if(sqr.getY() < y) {
                        sqr.setLocation(sqr.getX(), sqr.getY() + 1);
                    }
                }
                
                
                deadSquares.removeAll(deleteThis);
                
            }
        }
    }
}
