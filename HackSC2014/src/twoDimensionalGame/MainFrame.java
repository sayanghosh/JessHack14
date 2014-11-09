package twoDimensionalGame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;




public class MainFrame extends Applet implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	private MainCharacter robot;
	private Image image, background, character, fireFlower, flame, beam_sword, rayGun;
	private Graphics second;
	private URL base;
	private boolean isFire = false;
	private int weaponChoice = 0;
	private int rotationDegrees = 0;
	public Timer t;
	private static Background bg1, bg2;
	private boolean isTop = false;

	private Vector<Integer> blockGenerator;
	private Vector<Tile> tileVector;
	
	public static Image tilegrassTop, tilegrassBot, tilegrassLeft, tilegrassRight, tiledirt;
	
	private void playMusic(){
		File music = new File ("../data/StayWithMe.wav");
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(music));
			clip.start();
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	private void parseFeatures(){
		FileReader fr;
		try {
			fr = new FileReader("../data/features.txt");
			Scanner scan = new Scanner(fr);
			while(scan.hasNextLine()){
				String tuple = scan.nextLine();
				if (Character.toString(tuple.charAt(1)).equals("1")) {
					if (Character.toString(tuple.charAt(2)).equals("3")) {
						blockGenerator.add(1);
					}
					else if(Character.toString(tuple.charAt(2)).equals("2")) {
						if((Character.toString(tuple.charAt(0)).equals("2")) || Character.toString(tuple.charAt(0)).equals("3")) {
							blockGenerator.add(1);
						}
						else{
							blockGenerator.add(0);
						}
					}
					else{
						blockGenerator.add(0);
					}
				}
				else{
					blockGenerator.add(0);
				}
			}
			scan.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void loadMap(){
		for (int i = 0; i < blockGenerator.size(); i++){
			if (blockGenerator.get(i) == 1){
				tileVector.add(new Tile(i, 10, 8));
			}
			else{
				tileVector.add(new Tile (i, 10, 0));
			}
		}
	}

	private void updateTiles() {
		for (int i = 0; i < tileVector.size(); i++) {
			Tile t = (Tile) tileVector.get(i);
			t.update();
		}

	}
	
	private void paintTiles(Graphics g) {
		for (int i = 0; i < tileVector.size(); i++) {
			Tile t = (Tile) tileVector.get(i);
			g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
		}
	}
	
	@Override
	public void init() {
		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Wav Riderrr");
		blockGenerator = new Vector<Integer>();
		tileVector = new Vector<Tile>();
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		// Image Setups
		background = getImage(base, "../data/background.png");
		character = getImage(base, "../data/Cow.png");
		fireFlower = getImage(base, "../data/fireFlower.png");
		beam_sword = getImage(base, "../data/beam_sword.png");
		flame = getImage(base, "../data/flame.png");
		rayGun = getImage(base, "../data/rayGun.png");
		
		//Tile Image Setups
		tiledirt = getImage(base, "../data/tiledirt.png");
		tilegrassTop = getImage(base, "../data/tilegrasstop.png");
		tilegrassBot = getImage(base, "../data/tilegrassbot.png");
		tilegrassLeft = getImage(base, "../data/tilegrassleft.png");
		tilegrassRight = getImage(base, "../data/tilegrassright.png");
	}

	@Override
	public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		
		robot = new MainCharacter();


		parseFeatures();
		loadMap();

		playMusic();
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {
		while (true) {
			robot.moveRight();
			robot.update();
			bg1.update();
			bg2.update();
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ArrayList projectiles = robot.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);
				if (p.isVisible() == true) {
					p.update();
				} else {
					projectiles.remove(i);
				}
			}

			updateTiles();
		}
	}
	
	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void paint(Graphics g) {
		
		g.drawImage(background, bg1.getBackgroundX(), bg1.getBackgroundY(), this);
		g.drawImage(background, bg2.getBackgroundX(), bg2.getBackgroundY(), this);	
		g.drawImage(character, robot.getCenterX() - 35, robot.getCenterY() - 53, this);
		paintTiles(g);
		
		if(weaponChoice == 1) {
			g.drawImage(fireFlower, robot.getCenterX() + 15, robot.getCenterY() - 30, this);

			if(isFire) {				
				g.drawImage(flame, robot.getCenterX() + 25, robot.getCenterY() - 60, this);
			}
		}
		else if(weaponChoice == 2) {
			g.drawImage(rayGun, robot.getCenterX() + 20, robot.getCenterY() - 20, this);

			ArrayList projectiles = robot.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);
				g.setColor(Color.GREEN);
				g.fillRect(p.getX(), p.getY(), 30, 3);
			}
		}
		else if (weaponChoice == 3){
			Graphics2D g2d = (Graphics2D)g.create();
			g2d.rotate(Math.toRadians(rotationDegrees), robot.getCenterX(), robot.getCenterY());
			g2d.drawImage(beam_sword, robot.getCenterX() + 15, robot.getCenterY() - 75, this);
		}

	}


	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {

		case KeyEvent.VK_SPACE:
			// System.out.println("Jump");
			robot.jump();
			robot.update();
			System.out.println(robot.getCenterY());
			if(robot.getCenterY() <= 120) {
				isTop = true;
			}
			if (robot.getCenterY() >= 120) {
				robot.setJumped(false);
				robot.update();
				if(robot.getCenterY() <= 120) {
					isTop = true;
				}
			}
			if (robot.getCenterY() <= 120 && isTop) {
				robot.setJumped(true);
			}

			break;

		case KeyEvent.VK_ENTER:
			if (weaponChoice < 4) {
				weaponChoice++;
			}
			else{
				weaponChoice = 0;
			}
			break;

		case KeyEvent.VK_CONTROL:
			if (weaponChoice == 1) {
				isFire = true;
				break;
			}
			else if (weaponChoice == 2) {
				robot.shoot();
				break;
			}
			else if (weaponChoice == 3) {
				recurseRotation(5, -90);
				//rotationDegrees = 0;
				break;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			
		case KeyEvent.VK_SPACE:
			robot.setJumped(true);

		case KeyEvent.VK_CONTROL:
			if (weaponChoice == 1) {
				isFire = false;
			}
			else if (weaponChoice == 3) {
				rotationDegrees = 0;
				repaint();
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void recurseRotation(int rotate, int init) {
		rotationDegrees = init;
		rotationDegrees += rotate;
		
		if(rotationDegrees >= 100) {
			rotationDegrees = 0;
			repaint();
			return;
		}
		else {
			t = new Timer();
			TimerTask task = new TimerTask() {
				public void run() {
					recurseRotation(20, rotationDegrees);
				}
			};
			t.schedule(task, 15);
		}
	}
	
	public static Background getbg1() {
		return bg1;
	}
	
	public static Background getbg2() {
		return bg2;
	}

	public static twoDimensionalGame.MainCharacter getMainCharacter() {
		// TODO Auto-generated method stub
		return null;
	}
}