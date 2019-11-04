package pong;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

import epicgl.Game;
import epicgl.Rectangle;
import epicgl.Vector3;

public class Player1 extends Rectangle {

	Player1() {
		super(150, 240, 25, 100);
		setName("Player 1");
		fillObject(new Vector3(1f, 1f, 1f));
		// TODO Auto-generated constructor stub
	}
	
	protected void onUpdate() {
		
		//TODO: use a keyboard class for this
		
		if(glfwGetKey(Game.getWindow(), GLFW_KEY_W) != 0) {
			move(0,15);
		}
		if(glfwGetKey(Game.getWindow(), GLFW_KEY_S) != 0) {
			move(0,-15);
		}
		/*if (glfwGetKey(Game.getWindow(), GLFW_KEY_LEFT) != 0) {
			addForce(-10,0);
		}
		if (glfwGetKey(Game.getWindow(),GLFW_KEY_RIGHT) != 0) {
			addForce(10,0);
		}*/
	}

}
