package epicgl;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.Version;

public abstract class Game {
	
	protected static int screenWidth = 640;
	protected static int screenHeight = 480;

	protected static long window;
	protected static Renderer renderer = new Renderer();
	protected static ObjectManager objectManager = new ObjectManager();
	protected static Mouse mouse;

	protected static float now;
	protected static float lastTime;
	protected static float delta;
	
	public Game() {
		init();
	}
	
	public void init() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		renderer.init(screenWidth, screenHeight, "Epic!");
		window = renderer.getWindow();		
		mouse = new Mouse(window);
		start();
		// game loop
		while (!glfwWindowShouldClose(window)) {
			updateTime();
			glfwPollEvents();
			mouse.update();
			objectManager.update();
			loop();
			renderer.draw(objectManager);
			//TODO: make motion work regardless of framerate.
		}
		renderer.cleanup();
	}
	
	public static float getDelta() {
		return delta;
	}
	
	protected static void updateTime() {
		now = (float)glfwGetTime();
		delta = now - lastTime;
		lastTime = now;
	}
	
	public static void setScreenSize(int width, int height) {
		screenWidth=width;
		screenHeight=height;
	}
	
	/**
	 * @return the screen's width
	 */
	public static int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * @return the screen's height
	 */
	public static int getScreenHeight() {
		return screenHeight;
	}
	
	public abstract void start();
	
	/**
	 * Before objects are drawn, but after they move.
	 */
	public abstract void loop();
	

}
