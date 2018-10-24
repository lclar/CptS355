import java.awt.Color;
import java.awt.Graphics;

public class ShrinkBall extends Ball {

	protected int initRadius;
	protected int shrinkRate = 0;
	public ShrinkBall(int radius, int initXpos, int initYpos, int speedX, int speedY, int maxBallSpeed, Color color,
			Player player, GameWindow gwindow) {
		super(radius, initXpos, initYpos, speedX, speedY, maxBallSpeed, color, player, gwindow);
		type = "shrink";
		shrinkRate = 10;
		initRadius = radius;
	}
	
	public void setShrinkRate(int rate)
	{
		this.shrinkRate = rate;
	}
	
	public int getShrinkRate(int rate)
	{
		return shrinkRate;
	}
	
	public boolean userHit (int maus_x, int maus_y)
	{
		
		double x = maus_x - pos_x;
		double y = maus_y - pos_y;

		double distance = Math.sqrt ((x*x) + (y*y));
		/*radius = radius - (shrinkRate / 3);
		if (distance-this.radius < (int)(player.scoreConstant)) {
			player.addScore(player.scoreConstant * Math.abs(x_speed) + player.scoreConstant);
			return true;
		}*/
		if (Double.compare(distance-this.radius , player.scoreConstant + Math.abs(x_speed)) <= 0)  {
			player.addScore ((int)(player.scoreConstant * Math.abs(x_speed) + player.scoreConstant));
			return true;
		}
		else return false;
	}
	
	/*check if the ball is out of the game borders. if so, game is over!*/ 
	protected boolean isOut (Player p)
	{
		if ((pos_x < gameW.x_leftout) || (pos_x > gameW.x_rightout) || (pos_y < gameW.y_upout) || (pos_y > gameW.y_downout)) {	
			p.subtractLife(1);
			resetBallPosition();	
			return true;
		}	
		else return false;
	}

	public void ballWasHit ()
	{	
		//change speed
		x_speed = rand.nextInt((maxspeed - (-5)) + 1) + (-5);
		y_speed = rand.nextInt((maxspeed - (-5)) + 1) + (-5);
		//shrink the ball
		if(radius <= (initRadius * ((double)shrinkRate/100)))
		{
			radius = initRadius;
		}
		else
		{
			radius = (int)(radius * (1 - ((double)shrinkRate/100)));
		}
		//resetBallPosition();
		
	}
	/*draw ball*/
	public void DrawBall (Graphics g)
	{
		g.setColor (color);
		g.fillOval (pos_x - radius, pos_y - radius, 2 * radius, 2 * radius);
	}

}
