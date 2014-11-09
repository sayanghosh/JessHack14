package twoDimensionalGame;

import java.awt.Image;
import java.awt.Rectangle;

public class Tile {

	private int tileX, tileY, speedX, type;
	public Image tileImage;

	private MainCharacter MainCharacter = MainFrame.getMainCharacter();
	private Background bg = MainFrame.getbg1();

	private Rectangle r;

	public Tile(int x, int y, int typeInt) {
		tileX = x * 40;
		tileY = y * 40;

		type = typeInt;

		r = new Rectangle();

		if (type == 5) {
			tileImage = MainFrame.tiledirt;
		} else if (type == 8) {
			tileImage = MainFrame.tilegrassTop;
		} else if (type == 4) {
			tileImage = MainFrame.tilegrassLeft;

		} else if (type == 6) {
			tileImage = MainFrame.tilegrassRight;

		} else if (type == 2) {
			tileImage = MainFrame.tilegrassBot;
		} else {
			type = 0;
		}

	}

		public void update() {
			speedX = bg.getSpeedX() * 5;
			tileX += speedX;
			r.setBounds(tileX, tileY, 40, 40);
	
			/*if (r.intersects(MainCharacter.yellowRed) && type != 0) {
				checkVerticalCollision(MainCharacter.rect, MainCharacter.rect2);
				checkSideCollision(MainCharacter.rect3, MainCharacter.rect4, MainCharacter.footleft, MainCharacter.footright);
			}*/
	
		}

	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public Image getTileImage() {
		return tileImage;
	}

	public void setTileImage(Image tileImage) {
		this.tileImage = tileImage;
	}

	public void checkVerticalCollision(Rectangle rtop, Rectangle rbot) {
		if (rtop.intersects(r)) {
			
		}

		if (rbot.intersects(r) && type == 8) {
			MainCharacter.setJumped(false);
			MainCharacter.setSpeedY(0);
			MainCharacter.setCenterY(tileY - 63);
		}
	}

	public void checkSideCollision(Rectangle rleft, Rectangle rright, Rectangle leftfoot, Rectangle rightfoot) {
		if (type != 5 && type != 2 && type != 0){
			if (rleft.intersects(r)) {
				MainCharacter.setCenterX(tileX + 102);
	
				MainCharacter.setSpeedX(0);
	
			}else if (leftfoot.intersects(r)) {
				MainCharacter.setCenterX(tileX + 85);
				MainCharacter.setSpeedX(0);
			}
			
			if (rright.intersects(r)) {
				MainCharacter.setCenterX(tileX - 62);
				MainCharacter.setSpeedX(0);
			}
			
			else if (rightfoot.intersects(r)) {
				MainCharacter.setCenterX(tileX - 45);
				MainCharacter.setSpeedX(0);
			}
		}
	}

}