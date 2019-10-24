package epicgl;

import org.lwjgl.*;
import static org.lwjgl.glfw.GLFW.*;

public class Epic {

	private static long window;

	public static void main(String args[]) {

		final int INIT_SCREEN_WIDTH = 1000;
		final int INIT_SCREEN_HEIGHT = 1000;

		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		Renderer renderer = new Renderer();
		renderer.init(INIT_SCREEN_WIDTH, INIT_SCREEN_HEIGHT, "Epic!");
		window = renderer.getWindow();

		ObjectManager objectManager = new ObjectManager();

		Rectangle rect1 = new Rectangle(0,0,100f,100f);
		Rectangle rect2 = new Rectangle(200,200,100f,100f);
		Rectangle rect3 = new Rectangle(500,500,50f,250f);
		Ball ball1 = new Ball(200,500,50);
		
		objectManager.addObject(rect1); objectManager.addObject(rect2); objectManager.addObject(ball1);
		objectManager.addObject(rect3);

		Mouse mouse = new Mouse(window);

		// game loop
		while (!glfwWindowShouldClose(window)) {

			glfwPollEvents();

			if (mouse.justClicked(buttons.LEFT)) {
				ball1.getMeshes()[0].cleanup();
			}
			

			mouse.update();
			// TODO: make mouse actually work again

			rect1.setPosition(mouse.getPosition());
			
			
			objectManager.update();

			renderer.draw(objectManager);
			//TODO: make motion work regardless of framerate.

		}
		renderer.cleanup();
	}
}
