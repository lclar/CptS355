import java.awt.*;
import java.util.*;
import java.applet.*;
import java.awt.event.MouseEvent;
import javax.swing.event.*;
import java.io.FileReader;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser; 
import org.json.simple.parser.ParseException; 

/*<applet code="Main" height=400 width=400></applet>*/

public class Main extends Applet implements Runnable
{

/* Configuration arguments. These should be initialized with the values read from the config.JSON file*/					
    private int numBalls;
/*end of config arguments*/

    private int refreshrate = 15;	           //Refresh rate for the applet screen. Do not change this value. 
	private boolean isStoped = true;		     
    Font f = new Font ("Arial", Font.BOLD, 18);
	
	private Player player;			           //Player instance.
	private ArrayList<Ball> _balls = new ArrayList<Ball>();
	
	Thread th;						           //The applet thread. 
	  
    Cursor c;				
    private GameWindow gwindow;                 // Defines the borders of the applet screen. A ball is considered "out" when it moves out of these borders.
	private Image dbImage;
	private Graphics dbg;
	private int mouseClicks = 0;
	private int hits = 0;
	
	class HandleMouse extends MouseInputAdapter 
	{

    	public HandleMouse() 
    	{
            addMouseListener(this);
        }
		
    	public void mouseClicked(MouseEvent e) 
    	{
    		if(!isStoped){
    			for(Ball b: _balls)
    			{		
    				if (b.userHit (e.getX(), e.getY())) 
    				{
    					b.ballWasHit ();
    					player.addScore(player.scoreConstant);
    					hits++;
    				}
    			}
    		}
    		else if (isStoped && e.getClickCount() == 2) {
    			isStoped = false;
    			mouseClicks = 0;
    			init();
    		}			
    		
    		mouseClicks++;
    	}

    	public void mouseReleased(MouseEvent e) 
    	{
    	}
        
    	public void RegisterHandler() 
    	{

    	}
    }
	
	HandleMouse hm = new HandleMouse();

					
	/*initialize the game*/
	public void init ()
	{	
		_balls = new ArrayList<Ball>();	
		setBackground (Color.black);
		setFont (f);

		if (getParameter ("refreshrate") != null) {
			refreshrate = Integer.parseInt(getParameter("refreshrate"));
		}
		else refreshrate = 15;
		c = new Cursor (Cursor.CROSSHAIR_CURSOR);
		this.setCursor (c);	
		//reads info from JSON doc
		JSONParser parser = new JSONParser();
		FileReader s = null;
		JSONObject jsonObject = null;
		int x_leftout = 0;
		int x_rightout = 0;
		int y_upout = 0;
		int y_downout = 0;
		
		int numLives = 0;
		
		int _score2EarnLife = 0;
		/* The parameters for the GameWindow constructor (x_leftout, x_rightout, y_upout, y_downout) 
		should be initialized with the values read from the config.JSON file*/	
		try {
			s = new FileReader("./config.JSON");
			jsonObject = (JSONObject)parser.parse(s);
			JSONObject root = jsonObject;
			//GameWindow
			JSONObject window = (JSONObject) jsonObject.get("GameWindow");
			x_leftout = Integer.parseInt((String)window.get("x_leftout"));
			x_rightout = Integer.parseInt((String)window.get("x_rightout"));
			y_upout = Integer.parseInt((String)window.get("y_upout"));
			y_downout = Integer.parseInt((String)window.get("y_downout"));
			//Player
			JSONObject _player = (JSONObject)jsonObject.get("Player");
			numLives = Integer.parseInt((String)_player.get("numLives"));
			_score2EarnLife = Integer.parseInt((String)_player.get("score2EarnLife"));
			//numBalls
			numBalls = Integer.parseInt((String)root.get("numBalls"));
			//balls
			/*The skeleton code creates a single basic ball. Your game should support arbitrary number of balls. 
			* The number of balls and the types of those balls are specified in the config.JSON file.
			* The ball instances will be stores in an Array or Arraylist.  */
			/* The parameters for the Ball constructor (radius, initXpos, initYpos, speedX, speedY, maxBallSpeed, color) 
			should be initialized with the values read from the config.JSON file. Note that the "color" need to be initialized using the RGB values provided in the config.JSON file*/		
			JSONArray balls = (JSONArray) root.get("Ball");
			Iterator<JSONObject> iter = balls.iterator();
			int i = 0;
			while(i < numBalls)
			{
				JSONObject ball = iter.next();
				//set color of ball
				Color color;
				JSONArray colorCode = (JSONArray) ball.get("color");
				int r = Integer.parseInt((String)colorCode.get(0));
				int g = Integer.parseInt((String)colorCode.get(1));
				int b = Integer.parseInt((String)colorCode.get(2));
				color = new Color(r, g, b);
				//create ball
				String ballType = (String)ball.get("type");
				int radius = Integer.parseInt((String)ball.get("radius"));
				int xpos = Integer.parseInt((String)ball.get("initXpos"));
				int ypos = Integer.parseInt((String)ball.get("initYpos"));
				int speedx = Integer.parseInt((String)ball.get("speedX"));
				int speedy = Integer.parseInt((String)ball.get("speedY"));
				int maxspeed = Integer.parseInt((String)ball.get("maxBallSpeed"));
				
				if(ballType.equals("basicball"))
				{
					Ball basic = new Ball(radius, xpos, ypos, speedx, speedy, maxspeed, color, player, gwindow);
					_balls.add(basic);
				}
				if(ballType.equals("bounceball"))
				{
					int bouncecount = Integer.parseInt((String)ball.get("bounceCount"));
					BounceBall bounce = new BounceBall(radius, xpos, ypos, speedx, speedy, maxspeed, color, player, gwindow);
					bounce.setBounceCount(bouncecount);
					_balls.add(bounce);
				}
				else if(ballType.equals("shrinkball"))
				{
					//shrink ball
					int shrinkrate = Integer.parseInt((String)ball.get("shrinkRate"));
					ShrinkBall shrink = new ShrinkBall(radius, xpos, ypos, speedx, speedy, maxspeed, color, player, gwindow);
					shrink.setShrinkRate(shrinkrate);
					_balls.add(shrink);
				}
				i++;
			}		
			
		} catch (IOException | ParseException e) {
		}
		gwindow = new GameWindow(x_leftout, x_rightout,y_upout, y_downout);
		player = new Player(numLives, _score2EarnLife);

		this.setSize(gwindow.x_rightout, gwindow.y_downout);
		
	}
	
	/*start the applet thread and start animating*/
	public void start ()
	{		
		if (th==null){
			th = new Thread (this);
		}
		th.start ();
	}
	
	/*stop the thread*/
	public void stop ()
	{
		th=null;
	}

    
	public void run ()
	{	
		/*Lower this thread's priority so it won't interfere with other processing going on*/
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        /*This is the animation loop. It continues until the user stops or closes the applet*/
		while (true) 
		{
			if(!isStoped)
			{
				for(Ball b: _balls)
				{
					b.move(player);
				}
			}
			if(player.getLives() == 0)
			{
				player.gameIsOver();
			}
			/*Display it*/
			repaint();
            
			try {
				
				Thread.sleep (refreshrate);
			}
			catch (InterruptedException ex) {
			}
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);	
		}			
	}


	
	public void paint (Graphics g)
	{
		/*if the game is still active draw the ball and display the player's score. If the game is active but stopped, ask player to double click to start the game*/ 
		if (!player.isGameOver()) {
			g.setColor (Color.yellow);
			
			g.drawString ("Score: " + player.getScore(), 10, 40);
			g.drawString("Lives: " + player.getLives(), 10, 70); // The player lives need to be displayed
			for(Ball b: _balls)
			{
				b.DrawBall(g);
			}
			if (isStoped) {
				g.setColor (Color.yellow);
				g.drawString ("Doubleclick on Applet to start Game!", 40, 200);
			}
		}
		/*if the game is over (i.e., the ball is out) display player's score*/
		else {
			g.setColor (Color.yellow);
			double percentage = ((double)hits/(double)mouseClicks) * 100;
			g.drawString ("Game over!", 130, 100);
			g.drawString ("You scored " + player.getScore() + " Points!", 90, 140);
			g.drawString("Statistics: ", 400, 160);
			g.drawString("Number of Clicks: " + mouseClicks, 400, 180); // The number of clicks need to be displayed
			g.drawString("% of Successful Clicks: " + percentage + "%",400,200); // The % of successful clicks need to be displayed
			g.drawString("Ball most hit: ", 400, 240); // The nball that was hit the most need to be displayed
				
			g.drawString ("Doubleclick on the Applet, to play again!", 20, 220);

			isStoped = true;	
		}
	}

	
	public void update (Graphics g)
	{
		
		if (dbImage == null)
		{
			dbImage = createImage (this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics ();
		}

		
		dbg.setColor (getBackground ());
		dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);

		
		dbg.setColor (getForeground());
		paint (dbg);

		
		g.drawImage (dbImage, 0, 0, this);
	}
}


