package twoDimensionalGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class MainCharacter {

	final int JUMPSPEED = -10;
	final int MOVESPEED = 1;
	final int GROUND = 400;
	
	private int centerX = 100;
	private int centerY = GROUND;
	private boolean jumped = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;

	private int speedX = 0;
	private int speedY = 1;
	
	private Background bg1 = MainFrame.getbg1();
	private Background bg2 = MainFrame.getbg2();
	

	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	public void update() {

		// Moves Character or Scrolls Background accordingly.
		if (speedX < 0) {
			centerX += speedX;
		}
		if (speedX == 0 || speedX < 0) {
			bg1.setSpeedX(0);
			bg2.setSpeedX(0);
		}
		if (centerX <= 100 && speedX > 0) {
			centerX += speedX;
		}
		if (speedX > 0 && centerX > 100){
			bg1.setSpeedX(-MOVESPEED);
			bg2.setSpeedX(-MOVESPEED);
		}
		
		// Updates Y Position
		centerY += speedY;
		if (centerY + speedY >= 400) {
			centerY = 400;
		}

		// Handles Jumping
		if (jumped == true) {
			speedY += 1;

			if (centerY + speedY >= 400) {
				centerY = 400;
				speedY = 0;
				jumped = false;
			}

		}

		// Prevents going beyond X coordinate of 0
		if (centerX + speedX <= 100) {
			centerX = 101;
		}
	}
	

	public void stopRight() {
		setMovingRight(false);
		stop();
	}

	public void stopLeft() {
		setMovingLeft(false);
		stop();
	}
	
	public void moveRight() {
		speedX = MOVESPEED;
	}
	
	public void moveLeft() {
		speedX = -MOVESPEED;
	}

	void stop() {
		if (isMovingRight() == false && isMovingLeft() == false) {
			speedX = 0;
		}

		if (isMovingRight() == false && isMovingLeft() == true) {
			moveLeft();
		}

		if (isMovingRight() == true && isMovingLeft() == false) {
			moveRight();
		}

	}

	public void jump() {
		if (jumped == false && centerY == 400) {
			speedY = JUMPSPEED;
			jumped = true;
			System.out.println(speedY);
			System.out.println(centerY);
		}


	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public boolean isJumped() {
		return jumped;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
	
	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}
	
	public void shoot() {
		Projectile p = new Projectile(centerX + 50, centerY - 25);
		projectiles.add(p);
	}

	public ArrayList getProjectiles() {
		return projectiles;
	}

}
