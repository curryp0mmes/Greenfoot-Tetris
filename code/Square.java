import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Square here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Square extends Actor
{
    /**
     * Act - do whatever the Square wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    //parent stuff
    private boolean isParent = false;
    private ArrayList<Square> children = new ArrayList<Square>();
    private boolean falling = true;
    
    //child stuff
    private int relativeX = 0;
    private int relativeY = 0;
    private Square parent = null;
    
    public void addParent(int childCount, World world) {
        isParent = true;
        world.addObject(this, 15, 2);
        for (int i = 0; i < childCount; i++)
        {
            Square newChild;
            if(this instanceof RedSquare) newChild = new RedSquare();
            else if(this instanceof GreenSquare) newChild = new GreenSquare();
            else if(this instanceof YellowSquare) newChild = new YellowSquare();
            else newChild = new BlueSquare();
            
            int randX = 0,randY = 0;
            while(!(randX == 0 ^ randY == 0)) { //Only one must be 0 (XOR)
                randX = (int)(Math.random() * 3) - 1;
                randY = (int)(Math.random() * 3) - 1;
            }
            newChild.addChildren(randX, randY, this, world);
            
            children.add(newChild);
        }
    }
    
    public Square addChildren(int relX, int relY, Square newParent, World world)
    {
        parent = newParent;
        relativeX = relX;
        relativeY = relY;
        world.addObject(this, parent.getX() + relX, parent.getY() + relY);
        return this;
    }
    
    public boolean fallDown() 
    {
        if (!isParent) return false;
        
        if(!canMove(this, 0)) return false;
        for (Square child : children) {
           if(!canMove(child, 0)) return false;
        }
        for (Square child : children) {
            child.setLocation(child.getX(), child.getY() + 1);
        }
        this.setLocation(this.getX(), this.getY() + 1);
        return true;
    }
    
    private boolean canMove(Square who, int dir) { //dir 0=down -1=left 1=right
        if(dir == 0) {
            if(getWorld().getHeight() <= who.getY() + 1) return false;
            List<Square> objectsBelowMe = getWorld().getObjectsAt(who.getX(), who.getY() + 1, Square.class);
            
            if(!objectsBelowMe.isEmpty()) {
                boolean notChild = true;
                for (Square child2 : children) {
                    if(child2.getX() == who.getX() && child2.getY() == who.getY() + 1) notChild = false;
                }
                if(notChild && !(this.getX() == who.getX() && this.getY() == who.getY() + 1)) return false;
            }
        }
        else {
            if(getWorld().getWidth() <= who.getX() + dir && who.getX() + dir < 0) return false;
        
        }
        return true;
    }
    public void moveTo(int x, int y) {
        if(isParent) {
            this.setLocation(x, y);
            for (Square child : children) {
                child.moveTo(x, y);
            }
        }
        else {
            this.setLocation(x + relativeX, y + relativeY);
        }
        
    }
}
