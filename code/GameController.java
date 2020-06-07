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
    private int score = 0;
    /**
     * Constructor for objects of class GameController
     */
    private World world;
    public GameController(World worldCopy)
    {
        this.world = worldCopy;
    }

    public void run( int blocklength)
    {
        if(nextObject == null) {
            nextObject = makeNewSquare(blocklength); //creates a preview if it is empty
        }
        if(currentObject == null) {             //gets triggered at the start of the game
            currentObject = nextObject;
            currentObject.moveTo(5, 1);
            nextObject = null;
        }
        if(count > 4 || Greenfoot.isKeyDown("Down") || Greenfoot.isKeyDown("S")) { //falling only every 4 ticks or faster with down key
            if(!currentObject.fallDown()) {     // This get triggered whenever a block lands
                score += blocklength;
                for(Square child : currentObject.children) {
                    deadSquares.add(child);     //all of the squares get added to the "dead" blocks
                }
                currentObject.children.clear();
                deadSquares.add(currentObject); //the parent aswell
                
                checkRows();                    //deletes full rows and moves others down
                
                currentObject = nextObject;     //gets new object from the preview
                currentObject.moveTo(5, 1);
                if(!currentObject.fallDown()) { //Game Over when new block can't fall
                    world.showText("GAME OVER!", (world.getWidth() - 9)/2, world.getHeight()/2);
                    Greenfoot.delay(15);
                    World menu = new MenuScene();
                    Greenfoot.setWorld(menu);   //return to menu
                }
                nextObject = null;              //clears preview
            }
            count = 0;                          
        }
        
        if(Greenfoot.isKeyDown("Right") || Greenfoot.isKeyDown("D")) currentObject.moveOne(1);
        else if(Greenfoot.isKeyDown("Left") || Greenfoot.isKeyDown("A")) currentObject.moveOne(-1);
        
        if(Greenfoot.isKeyDown("Up") || Greenfoot.isKeyDown("W")) currentObject.rotateBlock();
        
        if(Greenfoot.isKeyDown("escape")) Greenfoot.setWorld(new MenuScene());
        
        world.showText("Score: " + String.valueOf(score), world.getWidth() - 5, 7);
        count++;
    }
    
    private Square makeNewSquare(int blocklength) //generates a random colored parent square
    {
        Square newSquare;
        int randomInteger = (int)(Math.random()*7);         //random from 1-7
        //randomInteger = 0; //temp as this is overwriting the rng
        if (randomInteger == 0) newSquare = new RedSquare();
        else if (randomInteger == 1) newSquare = new GreenSquare();         
        else if (randomInteger == 2) newSquare = new YellowSquare();
        else if (randomInteger == 3) newSquare = new WhiteSquare(); 
        else if (randomInteger == 4) newSquare = new PurpleSquare(); 
        else if (randomInteger == 5) newSquare = new OrangeSquare(); 
        else newSquare = new BlueSquare();
        
        newSquare.addParent(blocklength - 1, world); //makes the block to parent
        
        return newSquare;
    }
    
    private void checkRows() {                  //deletes full rows and moves others down
        for(int y = 0; y < world.getHeight(); y++) {
            boolean fullRow = true;
            for(int x = 0; x < world.getWidth() - 9; x++) {
                if(world.getObjectsAt(x, y, Square.class).isEmpty()) fullRow = false; //when there is a hole/no block
            }
            if(fullRow) {
                score += (world.getWidth() - 9) * (world.getWidth() - 9);
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
                
                
                deleteThis.stream()                                 //not my code
                    .sorted(Comparator.reverseOrder())              //deletes array objects by index
                    .forEach(i->deadSquares.remove(i.intValue()));  // https://stackoverflow.com/questions/49283350/java-arraylist-removeall-but-for-indices
                
            }
        }
    }
}
