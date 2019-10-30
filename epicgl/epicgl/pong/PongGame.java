package pong;

import epicgl.*;

import static epicgl.Game.getDelta;
import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class PongGame extends Game {
	
	Ball theBall;
	Ball theBall2;
	Rectangle theRect;
	Rectangle theRect2;
	
	public static void main(String[] args) {
		PongGame.setScreenSize(1000, 1000);
		new PongGame();
	}

	@Override
	public void start() {
		Physics.gravity = new Vector2f(0,-500);
		Physics.friction = 25;
		Physics.drag = -0f;
		theBall = new Ball(500f, 500f, 25f);
		theBall2 = new Ball(600f, 500f, 25f);
		theRect = new Rectangle(200f,500f,200f,250f);
		theRect2 = new Rectangle(50f,50f, 100f, 100f);
		objectManager.addObject(theRect);
		objectManager.addObject(theBall); theBall.setName("test");
		objectManager.addObject(theBall2);
		objectManager.addObject(theRect2);
		objectManager.setExitBehaviors(epicgl.GameObject.ExitBehavior.BOUNCE);
		
	}

	@Override
	public void loop() {
		if(glfwGetKey(window, GLFW_KEY_UP) != 0) {
			theBall2.addForce(0,10);
		}
		if(glfwGetKey(window, GLFW_KEY_DOWN) != 0) {
			theBall2.addForce(0,-10);
		}
		if (glfwGetKey(window, GLFW_KEY_LEFT) != 0) {
			theBall2.addForce(-10,0);
		}
		if (glfwGetKey(window,GLFW_KEY_RIGHT) != 0) {
			theBall2.addForce(10,0);
		}
		
		if(glfwGetKey(window, GLFW_KEY_W) != 0) {
			theRect2.addForce(0,50);
		}
		if(glfwGetKey(window, GLFW_KEY_S) != 0) {
			theRect2.addForce(0,-50);
		}
		if (glfwGetKey(window, GLFW_KEY_A) != 0) {
			theRect2.addForce(-50,0);
		}
		if (glfwGetKey(window,GLFW_KEY_D) != 0) {
			theRect2.addForce(50,0);
		}
		

	}
	
	public static void slow(GameObject obj) {
		Vector2f newSpeed = new Vector2f();
		if(obj.getVelocity().x >= 2) {newSpeed.x = obj.getVelocity().x-1;}
		else if (obj.getVelocity().x <= -2) {newSpeed.x = obj.getVelocity().x+1;}
		else {newSpeed.x=0;}
		
		if(obj.getVelocity().y >= 2) {newSpeed.y = obj.getVelocity().y-1;}
		else if (obj.getVelocity().y <= -2) {newSpeed.y = obj.getVelocity().y+1;}
		else {newSpeed.y=0;}
		
		obj.setVelocity(newSpeed);
	}
	
}
