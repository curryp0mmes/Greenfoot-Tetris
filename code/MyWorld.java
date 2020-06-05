import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    private GameController game = new GameController(this);
    
    public MyWorld(int width, int height)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(width + 9, height, 50);
        setBackground("images/Wall.jpg");
        Greenfoot.setSpeed(35);
        prepare();
        Greenfoot.start();
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        for(int i = 0; i < this.getHeight();i++){
            Menu menu = new Menu();
            addObject(menu,this.getWidth()-5,i);
        }
    }
    public void act()
    {
        game.run();
    }
}
