import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MenuScene here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MenuScene extends World
{

    /**
     * Constructor for objects of class MenuScene.
     * 
     */
    private PlayButton playButton = new PlayButton();
    
    public MenuScene()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1);
        Greenfoot.setSpeed(30);
        setBackground("images/titleScreen.png");
        addObject(playButton,608,260);
        Greenfoot.start();
    }

    private void startPlaying() {
        World newWorld = new MyWorld(11,15);
        Greenfoot.setWorld(newWorld);
    }

    public void act() {
        if(Greenfoot.mouseClicked(playButton)) 
            startPlaying();
    }
}
