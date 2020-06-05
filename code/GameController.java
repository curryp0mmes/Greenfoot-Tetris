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
    private ArrayList<Square> parents = new ArrayList<Square>();
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
            if(!currentObject.fallDown()) { //Game Over
                System.out.println("You lost! Try again!");
                World menu = new MenuScene();
                Greenfoot.setWorld(menu);
            }
            nextObject = null;
        }
        if(count > 4 || Greenfoot.isKeyDown("Down")) {
            if(!currentObject.fallDown()) {
                currentObject = nextObject;
                currentObject.moveTo(5, 1);
                if(!currentObject.fallDown()) { //Game Over
                    System.out.println("You lost! Try again!");
                    World menu = new MenuScene();
                    Greenfoot.setWorld(menu);
                }
                nextObject = null;
            }
            count = 0;
        }
        if(Greenfoot.isKeyDown("Right")) currentObject.moveOne(1);
        else if(Greenfoot.isKeyDown("Left")) currentObject.moveOne(-1);
        else if(Greenfoot.isKeyDown("Up")) currentObject.rotateBlock();
        count++;
    }
    
    private Square makeNewSquare()
    {
        Square newSquare;
        int randomInteger = (int)(Math.random()*4);
        //randomInteger = 0; //temp as this is overwriting the rng
        if (randomInteger == 0) newSquare = new RedSquare();
        else if (randomInteger == 1) newSquare = new GreenSquare();         
        else if (randomInteger == 2) newSquare = new YellowSquare();       
        else newSquare = new BlueSquare();
        
        newSquare.addParent(3, world);
        parents.add(newSquare);
        
        return newSquare;
    }
}
