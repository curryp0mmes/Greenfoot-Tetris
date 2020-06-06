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
    private OptionsButton optionsButton = new OptionsButton();
    
    private int playingX = 11;
    private int playingY = 15;
    private int blocklength = 4;

    public MenuScene()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1);
        Greenfoot.setSpeed(30);
        setBackground("images/titleScreen.png");
        addObject(playButton,608,260);
        addObject(optionsButton,713,358);
        Greenfoot.start();
    }

    private void startPlaying() {
        World newWorld = new MyWorld(playingX,playingY,blocklength);
        Greenfoot.setWorld(newWorld);
    }

    public void act() {
        if(Greenfoot.mouseClicked(playButton)) 
            startPlaying();
        else if(Greenfoot.mouseClicked(optionsButton)) 
            askForOptions();
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void askForOptions()
    {
        int newx = 0;
        while (newx == 0) {
            try {
                String input = Greenfoot.ask("How wide should the area for playing be (X)? (11): ");
                if(input.isBlank() || input.length() > 3) newx = 11;
                else newx = Integer.parseInt(input);
            }
            catch (Exception e) {
                System.out.println("Please type a Number");
            }
        }
        int newy = 0;
        while (newy == 0) {
            try {
                String input = Greenfoot.ask("How high should the area for playing be (Y)? (15): ");
                if(input.isBlank() || input.length() > 3) newy = 15;
                else newy = Integer.parseInt(input);
            }
            catch (Exception e) {
                System.out.println("Please type a Number");
            }
        }
        int newBlocklength = 0;
        while (newBlocklength == 0) {
            try {
                String input = Greenfoot.ask("How long should the Blocks be? (4): ");
                if(input.isBlank() || input.length() > 3) newBlocklength = 4;
                else newBlocklength = Integer.parseInt(input);
            }
            catch (Exception e) {
                System.out.println("Please type a Number");
            }
        }
        
        playingX = newx;
        playingY = newy;
        blocklength = newBlocklength;
    }
}
