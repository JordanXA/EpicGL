package epicgl;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.lwjgl.Version;

public abstract class Game {
	
	final static int INIT_SCREEN_WIDTH = 1000;
	final static int INIT_SCREEN_HEIGHT = 1000;
	private static long window;
	static Renderer renderer = new Renderer();
	static ObjectManager objectManager = new ObjectManager();
	static Mouse mouse;

	public static void main(String[] args) {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		renderer.init(INIT_SCREEN_WIDTH, INIT_SCREEN_HEIGHT, "Epic!");
		window = renderer.getWindow();		
		mouse = new Mouse(window);
		//start();
		// game loop
		while (!glfwWindowShouldClose(window)) {
			glfwPollEvents();
			mouse.update();
			objectManager.update();
			//loop();
			renderer.draw(objectManager);
			//TODO: make motion work regardless of framerate.
		}
		renderer.cleanup();
	}
	
	public abstract void start();
	/**
	 * Before objects are drawn, but after they move.
	 */
	public abstract void loop();
	

}
