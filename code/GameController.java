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
        if(currentObject == null || !currentObject.fallDown()) {
            currentObject = nextObject;
            currentObject.moveTo(5, 1);
            if(!currentObject.fallDown()) { //Game Over
                System.out.println("You lost! Try again!");
                Greenfoot.stop();
            }
            nextObject = null;
        }
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
