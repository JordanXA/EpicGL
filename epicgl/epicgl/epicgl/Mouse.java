package epicgl;

import org.joml.Vector2f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.io.InputStream;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;


public class Mouse {
	public boolean isDrawing;
	boolean clickedLeft;
	boolean clickedRight;

	//public float mouseX, mouseY;
	
	Vector2f position = new Vector2f();
	
	private DoubleBuffer mouseXbuffer = BufferUtils.createDoubleBuffer(1);
	private DoubleBuffer mouseYbuffer = BufferUtils.createDoubleBuffer(1);
	IntBuffer windowWidth = BufferUtils.createIntBuffer(1);
	IntBuffer windowHeight = BufferUtils.createIntBuffer(1);

	private static long window;
	List<Vector3> newMeshV = new ArrayList<Vector3>();
	Mesh drawnMesh = new Mesh(
			new Vector3[] { new Vector3(-0.5f, 0.3f), new Vector3(0.0f, 0.3f), new Vector3(-0.3f, 0.0f) });

	Mouse(long window) {
		Mouse.window = window;
		// mouseXpos.put(0).flip();
		// mouseYpos.put(0).flip();
	}
	
	public Vector2f getPosition() {
		return position;
	}

	public void startDraw() {

		System.out.println("Started Drawing!");
		isDrawing = true;
	}

	public void stopDraw() {
		isDrawing = false;
		newMeshV = new ArrayList<Vector3>();
	}

	public void update() {
		if (clickedLeft && !isClicked(MouseButton.LEFT)) {
			clickedLeft = false;
		}
		if (clickedRight && !isClicked(MouseButton.RIGHT)) {
			clickedRight = false;
		}

		glfwGetWindowSize(window, windowWidth, windowHeight);
		glfwGetCursorPos(window, mouseXbuffer, mouseYbuffer);
		position.x=(float)mouseXbuffer.get(0);
		position.y=(float)mouseYbuffer.get(0);
		position.y=Renderer.screenHeight/2-(position.y-Renderer.screenHeight/2);
		
		if (isDrawing) {
			drawUpdate();
		}

	}

	public Mesh getMesh() {
		return drawnMesh;
	}

	private void drawUpdate() {

		if (justClicked(MouseButton.LEFT)) {
			float vx = (float) mouseXbuffer.get(0);
			float vy = (float) mouseYbuffer.get(0);
			vx = (vx / (windowWidth.get(0) / 2)) - 1;
			vy = -((vy / (windowHeight.get(0) / 2)) - 1);

			Vector3 drawNewVertex = new Vector3(vx, vy);
			// Vector3 drawNewVertex = new Vector3(1f, 1f);

			newMeshV.add(drawNewVertex);
			System.out.println("Added Vector to drawing" + mouseXbuffer.get(0) + mouseYbuffer.get(0));
		}
		if (justClicked(MouseButton.RIGHT)) {

			if (newMeshV.size() > 2) {
				Vector3[] newVertices = new Vector3[newMeshV.size()];

				for (int i = 0; i < newMeshV.size(); i++) {
					newVertices[i] = newMeshV.get(i);
				}

				drawnMesh = new Mesh(newVertices, new Vector3(0, 0, 0));
				System.out.println("Made mesh");
				stopDraw();
			} else {
				System.out.println("Drawing is incomplete!");
			}
		}

	}

	public boolean justClicked(MouseButton button) {
		if (button == MouseButton.LEFT) {
			if ((isClicked(MouseButton.LEFT)) && !clickedLeft) {
				clickedLeft = true;
				return true;
			} else {
				return false;
			}
		}

		else if (button == MouseButton.RIGHT) {
			if ((isClicked(MouseButton.RIGHT)) && !clickedRight) {
				clickedRight = true;
				return true;
			} else {
				return false;
			}
		}

		else {
			return false;
		}
	}

	public boolean isClicked(MouseButton button) {
		if (button == MouseButton.LEFT) {
			if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
				return true;
			} else {
				return false;
			}
		}

		else if (button == MouseButton.RIGHT) {
			if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS) {
				return true;
			} else {
				return false;
			}
		}

		else {
			return false;
		}

	}
}
/*
 * while(glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_RIGHT) != GLFW_PRESS &&
 * verticesList.size() > 2) { if(glfwGetMouseButton(window,
 * GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) { glfwGetCursorPos(window, xpos,
 * ypos); Vector3 newVertex = new Vector3((float)xpos.get(), (float)ypos.get());
 * verticesList.add(newVertex); } while(glfwGetMouseButton(window,
 * GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {} }
 */