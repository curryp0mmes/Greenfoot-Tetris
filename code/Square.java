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
    private boolean parent = false;
    private ArrayList<Square> children = new ArrayList<Square>();
    private boolean falling = true;
    public void addParent(int childCount, World world) {
        parent = true;
        world.addObject(this, 5, 1);
        for (int i = 0; i < childCount; i++)
        {
            Square newChild;
            if(this instanceof RedSquare) {
                newChild = new RedSquare();
                newChild.addChildren(this.getX() + 1, this.getY(), world);
                children.add(newChild);
            }
        }
    }
    public Square addChildren(int x, int y, World world)
    {
        world.addObject(this, x, y);
        return this;
    }
    public void fallDown() 
    {
        if (!parent) return;
        
        for (Square child : children)
        {
            if(getWorld().getHeight() <= child.getY() + 1) return;
            if(!getWorld().getObjectsAt(child.getX(), child.getY() + 1, Square.class).isEmpty()) return;
            
            child.setLocation(child.getX(), child.getY() + 1);
        }
        this.setLocation(this.getX(), this.getY() + 1);
    }    
}
