import java.awt.Color;

public class BounceBall extends Ball{
	protected int _bounceCount;
	public BounceBall(int radius, int initXpos, int initYpos, int speedX, int speedY, int maxBallSpeed, Color color,
			Player player, GameWindow gwindow) {
		super(radius, initXpos, initYpos, speedX, speedY, maxBallSpeed, color, player, gwindow);
		type = "bounce";
		_bounceCount = 0;
	}
	public void setBounceCount(int count)
	{
		this._bounceCount = count;
	}
	public int getBounceCount()
	{
		return _bounceCount;
	}
	
	protected boolean isOut (Player p)
	{
		if ((pos_x < gameW.x_leftout) || (pos_x > gameW.x_rightout) || (pos_y < gameW.y_upout) || (pos_y > gameW.y_downout)) {	
			x_speed = rand.nextInt((maxspeed - (-5)) + 1) + (-5);
			y_speed = rand.nextInt((maxspeed - (-5)) + 1) + (-5);
		
			return true;
		}	
		else return false;
	}
	public void move (Player p)
	{
		//change ball direction
		if(isOut(p) && _bounceCount != 0)
		{
			bounceMovement();
		}
		else if(isOut(p) && _bounceCount == 0)
		{
			//reset to the beginning and subtract life
			resetBallPosition();
			p.subtractLife(1);
		}
		else
		{
			//just keep moving
			pos_x += x_speed;
			pos_y += y_speed;
		}
		_bounceCount = _bounceCount - 1;
	}
	
	public void bounceMovement()
	{
		//if ball is out on the left hand side 
		if((pos_x < gameW.x_leftout))
		{
			x_speed = x_speed * (-1);
			pos_x = gameW.x_leftout;
		}
		//if ball is out on right hand side
		if(pos_x > gameW.x_rightout)
		{
			x_speed = x_speed * (-1);
			pos_x = gameW.x_rightout;
		}
		//if ball is out on the bottom
		if(pos_y > gameW.y_downout)
		{
			y_speed = y_speed * (-1);
			pos_y = gameW.y_downout;
		}
		//if ball is out on the top
		if(pos_y < gameW.y_upout)
		{
			y_speed = y_speed * (-1);
			pos_y = gameW.y_upout;
		}
	}
	public void ballWasHit ()
	{	
		x_speed = rand.nextInt((maxspeed - (-5)) + 1) + (-5);
		y_speed = rand.nextInt((maxspeed - (-5)) + 1) + (-5);
	}

}
