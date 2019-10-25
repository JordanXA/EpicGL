package epicgl;

import org.lwjgl.*;
import static org.lwjgl.glfw.GLFW.*;

public class TestGame {

	private static long window;

	public static void main(String args[]) {

		final int INIT_SCREEN_WIDTH = 1000;
		final int INIT_SCREEN_HEIGHT = 1000;

		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		Renderer renderer = new Renderer();
		renderer.init(INIT_SCREEN_WIDTH, INIT_SCREEN_HEIGHT, "Epic!");
		window = renderer.getWindow();

		ObjectManager objectManager = new ObjectManager();

		Rectangle rect1 = new Rectangle(0,0,100f,100f); rect1.setName("Player Rectangle");
		Rectangle rect2 = new Rectangle(200,200,100f,100f); rect2.setName("Square");
		Rectangle rect3 = new Rectangle(500,500,50f,250f); rect3.setName("Funny Rectangle");
		Ball ball1 = new Ball(200,500,50); ball1.setName("Circle");
		Ball playerBall = new Ball(0,0,500f); playerBall.setName("Player Ball");
		
		objectManager.addObject(rect1); objectManager.addObject(rect2); objectManager.addObject(ball1);
		objectManager.addObject(rect3);	objectManager.addObject(playerBall);

		Mouse mouse = new Mouse(window);

		boolean testBool = false;
		
		// game loop
		while (!glfwWindowShouldClose(window)) {

			glfwPollEvents();

			if (mouse.justClicked(MouseButton.LEFT)) {
				if(testBool) {testBool=false;}
				else {testBool=true;}
			}
			
			if (mouse.justClicked(MouseButton.RIGHT)) {
				if(testBool==true) {
					ObjectManager.printNameOfObjectList(playerBall.getCollisions());
				}
				else {
					ObjectManager.printNameOfObjectList(rect1.getCollisions());
				}
			}
			

			mouse.update();
			// TODO: make mouse actually work again

			if(testBool==true) {
				playerBall.setPosition(mouse.getPosition().x, mouse.getPosition().y);
			}
			else {
				rect1.setPosition(mouse.getPosition().x, mouse.getPosition().y);
			}
			
			
			objectManager.update();

			renderer.draw(objectManager);
			//TODO: make motion work regardless of framerate.

		}
		renderer.cleanup();
	}
}
