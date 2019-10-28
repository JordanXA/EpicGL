package pong;

import epicgl.*;
import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class PongGame extends Game {
	
	Ball theBall;
	
	public static void main(String[] args) {
		PongGame.setScreenSize(1000, 1000);
		new PongGame();
	}

	@Override
	public void start() {
		theBall = new Ball(500f, 500f, 25f);
		objectManager.addObject(theBall);
		
	}

	@Override
	public void loop() {
		if(glfwGetKey(window, GLFW_KEY_UP) != 0) {
			theBall.addForce(0,1);
		}
		if(glfwGetKey(window, GLFW_KEY_DOWN) != 0) {
			theBall.addForce(0,-1);
		}
		if (glfwGetKey(window, GLFW_KEY_LEFT) != 0) {
			theBall.addForce(-1,0);
		}
		if (glfwGetKey(window,GLFW_KEY_RIGHT) != 0) {
			theBall.addForce(1,0);
		}
		
		if (glfwGetKey(window,GLFW_KEY_SPACE) != 0) {
			slow(theBall);
		}
		
		if(theBall.isOutsideScreen()) {
			theBall.resolveExit(epicgl.GameObject.ExitBehavior.BOUNCE);
		}
	}
	
	public static void slow(GameObject obj) {
		Vector2f newSpeed = new Vector2f();
		if(obj.getSpeed().x >= 2) {newSpeed.x = obj.getSpeed().x-1;}
		else if (obj.getSpeed().x <= 2) {newSpeed.x = obj.getSpeed().x+1;}
		else {newSpeed.x=0;}
		
		if(obj.getSpeed().y >= 2) {newSpeed.y = obj.getSpeed().y-1;}
		else if (obj.getSpeed().y <= 2) {newSpeed.y = obj.getSpeed().y+1;}
		else {newSpeed.y=0;}
		
		obj.setSpeed(newSpeed);
	}
	
}
