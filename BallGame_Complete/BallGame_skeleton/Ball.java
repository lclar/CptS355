import java.awt.*;
import java.util.Random;


public class Ball
{
    /*Properties of the basic ball. These are initialized in the constructor using the values read from the config.xml file*/
	protected  int pos_x;			
	protected int pos_y; 				
	protected int radius;
	protected int first_x;			
	protected int first_y;					
	protected int x_speed;			
	protected int y_speed;			
	protected int maxspeed;
	Color color;
	String type;
	
    GameWindow gameW;
	Player player;
	Random rand = new Random();
	
	/*constructor*/
	public Ball (int radius, int initXpos, int initYpos, int speedX, int speedY, int maxBallSpeed, Color color, Player player,  GameWindow gwindow)
	{	
		this.radius = radius;

		pos_x = initXpos;
		pos_y = initYpos;

		first_x = initXpos;
		first_y = initYpos;

		x_speed = speedX;
		y_speed = speedY;

		maxspeed = maxBallSpeed;

		this.color = color;

		this.player = player;
		this.gameW = gwindow;
		type = "basic";

	}

	/*update ball's location based on it's speed*/
	public void move (Player p)
	{
		pos_x += x_speed;
		pos_y += y_speed;
		isOut(p);
	}

	/*when the ball is hit, reset the ball location to its initial starting location*/
	public void ballWasHit ()
	{	
		x_speed = rand.nextInt((maxspeed - (-5)) + 1) + (-5);
		y_speed = rand.nextInt((maxspeed - (-5)) + 1) + (-5);
		resetBallPosition();
	}

	/*check whether the player hit the ball. If so, update the player score based on the current ball speed. */	
	public boolean userHit (int maus_x, int maus_y)
	{
		
		double x = maus_x - pos_x;
		double y = maus_y - pos_y;

		double distance = Math.sqrt ((x*x) + (y*y));
		
		if (distance-this.radius < (int)(player.scoreConstant)) {
			player.addScore(player.scoreConstant * Math.abs(x_speed) + player.scoreConstant);
			return true;
		}
		/*if (Double.compare(distance-this.radius , player.scoreConstant + Math.abs(x_speed)) <= 0)  {
			player.addScore ((int)(player.scoreConstant * Math.abs(x_speed) + player.scoreConstant));
			return true;
		}*/
		else return false;
	}

    /*reset the ball position to its initial starting location*/
	protected void resetBallPosition()
	{
		pos_x = first_x;
		pos_y = first_y;
		
		// adding speed
		x_speed = rand.nextInt((maxspeed - (-5)) + 1) + (-5);
		y_speed = rand.nextInt((maxspeed - (-5)) + 1) + (-5);
	}
	
	/*check if the ball is out of the game borders. if so, game is over!*/ 
	protected boolean isOut (Player p)
	{
		if ((pos_x < gameW.x_leftout) || (pos_x > gameW.x_rightout) || (pos_y < gameW.y_upout) || (pos_y > gameW.y_downout)) {	
			resetBallPosition();
		p.subtractLife(1);
			return true;
		}	
		else return false;
	}

	/*draw ball*/
	public void DrawBall (Graphics g)
	{
		g.setColor (color);
		g.fillOval (pos_x - radius, pos_y - radius, 2 * radius, 2 * radius);
	}

}
