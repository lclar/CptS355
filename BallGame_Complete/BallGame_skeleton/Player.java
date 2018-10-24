public class Player
{
	
	private int score;			   //player score
	private boolean gameover=false;	
	public int scoreConstant = 10; //This constant value is used in score calculation. You don't need to change this. 		
	public int numLives;
	public int score2EarnLife;
	public int mouseClicks;
	
	public Player(int lives, int earnlife)
	{
		score = 0; //initialize the score to 0
		numLives = lives;
		score2EarnLife = earnlife;
	}

	/* get player score*/
	public int getScore ()
	{
		return score;
	}

	/*check if the game is over*/
	public boolean isGameOver ()
	{
		return gameover;
	}
	public void subtractLife(int lives)
	{
		this.numLives = numLives - lives;
	}
	public int getLives()
	{
		return numLives;
	}
	/*update player score*/
	public void addScore (int plus)
	{
		score += plus;
		
		// checking if we have enough points to get a life
		if(score >= score2EarnLife)
		{
			numLives++;
			score = score - 100; // take away those 100 points in exchange for the life
		}
	}

	/*update "game over" status*/
	public void gameIsOver ()
	{
		gameover = true;
	}
}