package twoDimensionalGame;

public class Background {
	private int backgroundX;
	private int backgroundY;
	private int speedX;
	
	public Background(int backgroundX, int backgroundY) {
		
		// Initializing Background values
		this.backgroundX = backgroundX;
		this.backgroundY = backgroundY;
		speedX = 0;
	}
	
	public void update() { 
		backgroundX += speedX;
		
		if(backgroundX <= -2160) {
			backgroundX += 4320;
		}
	}
	
	public int getBackgroundX() {
		return backgroundX;
	}

	public void setBackgroundX(int backgroundX) {
		this.backgroundX = backgroundX;
	}

	public int getBackgroundY() {
		return backgroundY;
	}

	public void setBackgroundY(int backgroundY) {
		this.backgroundY = backgroundY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	
}
