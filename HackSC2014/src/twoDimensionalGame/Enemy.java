package twoDimensionalGame;


public class Enemy {

	private int centerX, centerY, speedX;
	private Background bg = MainFrame.getbg1();

	// Behavioral Methods
	public void update() {
		centerX += speedX;
		speedX = bg.getSpeedX();
	}


	public void die() {


	}


	public void attack() {


	}
	
	public int getSpeedX() {
		return speedX;
	}


	public int getCenterX() {
		return centerX;
	}


	public int getCenterY() {
		return centerY;
	}


	public Background getBg() {
		return bg;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}


	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}


	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}


	public void setBg(Background bg) {
		this.bg = bg;
	}


	
}