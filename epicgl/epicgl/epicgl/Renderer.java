package epicgl;


import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.io.InputStream;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;


import java.lang.Math;
import org.joml.Matrix4f;
import org.joml.Vector4f;


public class Renderer {

	private final boolean DRAW_EDGES = false;
	
	private static long window;
	ShaderClass shaderProgram = null;
	public static int screenWidth, screenHeight;
	private Matrix4f projectionMatrix;
	float aspectRatio;

	public long getWindow() {
		return window;
	}

	public void draw(ObjectManager objMan) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		shaderProgram.bind();

		for(int i=0; i<objMan.getObjects().size(); i++) {
			drawObject(objMan.getObjects().get(i));
		}

		shaderProgram.unbind();

		glfwSwapBuffers(window);

	}

	public void drawObject(Object object) {
		for (int i=0; i<object.getMeshes().length; i++) {
			//Utils.printMatrix4f(object.getTransformation());
			drawVao(object.getMeshes()[i].getVao()
					, object.getTransformation()
					);
		}
	}

	public void drawVao(int vao, Matrix4f tMatrix) {

		if (vao == 0) {
			//maybe put code here to fix the objects VAO? 
			return;
		}

		IntBuffer numIndices = MemoryUtil.memAllocInt(1);
		numIndices.position(0);

		//Bind to the VAO
		glBindVertexArray(vao);

		try {
			shaderProgram.setUniform("worldMatrix",tMatrix);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		glGetBufferParameteriv(GL_ELEMENT_ARRAY_BUFFER, GL_BUFFER_SIZE, numIndices);
		glDrawElements(GL_TRIANGLES, numIndices.get(0), GL_UNSIGNED_INT, NULL);
		if(glGetError()!=0) {System.out.println(glGetError());}
		
		if(DRAW_EDGES) {
			try {
			shaderProgram.setUniform("DRAW_EDGES", true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			glGetBufferParameteriv(GL_ELEMENT_ARRAY_BUFFER, GL_BUFFER_SIZE, numIndices);
			glDrawElements(GL_LINES, numIndices.get(0), GL_UNSIGNED_INT, NULL);
			if(glGetError()!=0) {System.out.println(glGetError());}
			try {
			shaderProgram.setUniform("DRAW_EDGES", false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		glBindVertexArray(0);

		if (numIndices!=null) {
			MemoryUtil.memFree(numIndices);
		}
		shaderProgram.resetWorldMatrix();
	}

	public void drawVao(int vao) {

		if (vao == 0) {
			//maybe put code here to fix the objects VAO? 
			return;
		}

		IntBuffer numIndices = MemoryUtil.memAllocInt(1);
		numIndices.position(0);

		//Bind to the VAO
		glBindVertexArray(vao);


		try {
			shaderProgram.setUniform("worldMatrix",Utils.identityMatrix());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		glGetBufferParameteriv(GL_ELEMENT_ARRAY_BUFFER, GL_BUFFER_SIZE, numIndices);
		glDrawElements(GL_TRIANGLES, numIndices.get(0), GL_UNSIGNED_INT, NULL);
		if(glGetError()!=0) {System.out.println(glGetError());}
		glBindVertexArray(0);

		if (numIndices!=null) {
			MemoryUtil.memFree(numIndices);
		}
		shaderProgram.resetWorldMatrix();
	}

	public void cleanup() {
		if (shaderProgram!=null) {
			shaderProgram.cleanup();
		}
	}

	//Lots of code I didn't write myself! 
	public void init(int sWidth, int sHeight, String title) {

		screenWidth = sWidth;
		screenHeight = sHeight;

		//set up errors
		GLFWErrorCallback.createPrint(System.err).set();

		//Initialize GLFW.
		if (!glfwInit() ) {
			throw new IllegalStateException("Unable to initialize GLFW");  
		}

		//Configure GLFW
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		// Before glfwCreateWindow():
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

		//Create the Window
		window = glfwCreateWindow(screenWidth, screenHeight, title, NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW Window!");
		}

		//Sets up key input
		glfwSetKeyCallback(window, 
				(window, key, scancode, action, mods) -> {
					if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
						glfwSetWindowShouldClose(window, true); //closes the window if requested
					}
				});



		try ( MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);

			glfwGetWindowSize(window, pWidth, pHeight);

			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			glfwSetWindowPos(
					window,
					(vidmode.width() - pWidth.get(0))/2,
					(vidmode.height() - pHeight.get(0))/2
					);
		}

		glfwMakeContextCurrent(window);


		//vsync
		glfwSwapInterval(1);
		glfwShowWindow(window);

		GL.createCapabilities();
		glClearColor(0.8f,0.9f,1.0f,0.0f);

		// After glfwMakeContextCurrent() and createCapabilities():
		Callback debugProc = GLUtil.setupDebugMessageCallback();

		try {
			shaderProgram = new ShaderClass();
			shaderProgram.createVertexShader(Utils.loadResource("/resources/vertex.vs"));
			shaderProgram.createFragmentShader(Utils.loadResource("/resources/fragment.fs"));
			shaderProgram.link();
			//new stuff
			//update later
			updateSize();//please change this somehow
			aspectRatio = (float)screenWidth / (float)screenHeight;

			shaderProgram.bind();
			//this is 3d
			projectionMatrix = Utils.createOrthoProjection(0,screenWidth,0,screenHeight,-1,1);

			shaderProgram.setProjectionMatrix(projectionMatrix);
			shaderProgram.resetWorldMatrix();
			shaderProgram.unbind();
		}
		catch (Exception excp) {
			excp.printStackTrace();
		}
	}

	public void updateSize() {
		//dear god rename this method
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(window, w, h);
		screenWidth = w.get(0);
		screenHeight = h.get(0);
	}
}