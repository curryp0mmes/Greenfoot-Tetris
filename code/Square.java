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
    public ArrayList<Square> children = new ArrayList<Square>();
    private boolean falling = true;
    
    //child stuff
    private int relativeX = 0;
    private int relativeY = 0;
    private Square parent = null;
    
    public int getRelX() {
        return relativeX;
    }
    public int getRelY() {
        return relativeY;
    }
    public void setRelPos(int x, int y) {
        relativeX = x;
        relativeY = y;
    }
    
    
    public void addParent(int childCount, World world) {
        isParent = true;
        world.addObject(this, world.getWidth() - 5, 3);
        for (int i = 0; i < childCount; i++) // add new children
        {
            Square newChild;
            if(this instanceof RedSquare) newChild = new RedSquare();                  //checking own color to give
            else if(this instanceof GreenSquare) newChild = new GreenSquare();         // children the same color
            else if(this instanceof YellowSquare) newChild = new YellowSquare();
            else if(this instanceof WhiteSquare) newChild = new WhiteSquare();
            else if(this instanceof OrangeSquare) newChild = new OrangeSquare();
            else if(this instanceof PurpleSquare) newChild = new PurpleSquare();
            else newChild = new BlueSquare();
            
            int randX = 0,randY = 0;
            while(!world.getObjectsAt(this.getX() + randX, this.getY() + randY, Square.class).isEmpty()) {  //gets a random, connected and not occupied position for child
                if((int)(Math.random() * 2) == 0) 
                    randX += (int)(Math.random() * 3) - 1;
                else
                    randY += (int)(Math.random() * 3) - 1;
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
        this.moveTo(this.getX(), this.getY() + 1);
        return true;
    }
    
    private boolean canMove(Square who, int dir) { //dir 0=down -1=left 1=right
        if(!(dir == 1 || dir == -1 || dir == 0)) return false;
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
            if(getWorld().getWidth() - 9 <= who.getX() + dir || who.getX() + dir < 0) return false;
            
            List<Square> objectsNextMe = getWorld().getObjectsAt(who.getX() + dir, who.getY(), Square.class);
            
            if(!objectsNextMe.isEmpty()) {
                boolean notChild = true;
                for (Square child2 : children) {
                    if(child2.getX() == who.getX() + dir && child2.getY() == who.getY()) notChild = false;
                }
                if(notChild && !(this.getX() == who.getX() + dir && this.getY() == who.getY())) return false;
            }
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
    
    public void moveOne(int dir) { //dir -1=left 1= right
        if(!(dir == 1 || dir == -1)) return;
        
        if(!canMove(this, dir)) return;
        for (Square child : children) {
           if(!canMove(child, dir)) return;
        }
        moveTo(this.getX() + dir, this.getY());
    }
    
    public void rotateBlock() {
        if(!isParent) return;
        
        for (Square child : children) {
           int oldX = child.getRelX();
           int oldY = child.getRelY();
           
           int newX = oldY * -1;
           int newY = oldX;
           
           child.setRelPos(newX, newY);
        }
        moveTo(this.getX(), this.getY());
    }
}
