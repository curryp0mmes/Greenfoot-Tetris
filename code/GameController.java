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
        if(parents.isEmpty()) {
            int randomInteger = (int)(Math.random()*2);
            randomInteger = 0; //tem as this is overwriting the rng
            if(randomInteger == 0)
            {
                Square newParent = new RedSquare();
                newParent.addParent(3, world);
                parents.add(newParent);
            }
            else if(randomInteger == 1) {
                return;
            }
        }
        for (Square parent : parents)
        {
            parent.fallDown();  
        }
    }
}
