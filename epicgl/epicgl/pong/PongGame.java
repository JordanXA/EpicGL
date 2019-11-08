package pong;

import epicgl.*;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2f;


public class PongGame extends Game {
	
	public static Player1 player1;
	public static Player2 player2;
	public static ScoreBall theBall;
	
	public static void main(String[] args) {
		
		Physics.gravity = new Vector2f(0,0);
		Physics.friction = 0;
		//Physics.drag = .0000f;
		setScreenSize(1280, 960);
		new PongGame();
	}

	@Override
	public void start() {
		setFillColor(new Vector3(0f,0f,0f));
		player1 = new Player1();
		player2 = new Player2();
		theBall = new ScoreBall((float)getScreenWidth()/2, (float)getScreenHeight()/2, 25);
		objectManager.addObject(player1);
		objectManager.addObject(player2);
		objectManager.addObject(theBall);
		objectManager.setExitBehaviors(GameObject.ExitBehavior.STOP);
		theBall.setExitBehavior(GameObject.ExitBehavior.BOUNCE);
		
		theBall.setVelocity(500, (float)Math.random()*10);
		
	}

	@Override
	public void loop() {

	}
	
}
