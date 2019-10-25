package epicgl;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Object {
   protected Mesh[] meshes;
   protected Vector2f position = new Vector2f(0,0);
   protected Vector2f speed = new Vector2f();
   protected Vector2f acceleration = new Vector2f();
   protected float mass;
   protected Vector3 fillColor;
   protected Matrix4f tMatrix;
   protected List<Object> collidingWith = new ArrayList<Object>();
   protected String name = new String("Unnamed");
   
   /**
    *Settings for the behavior of an object when it exits the screen
    */
   public static enum ExitBehavior {
	   FREE, STOP, BOUNCE, WRAP
   }
   
   protected Object() {
	   updateTransformation();
   }
   
   public void setName(String name) {
	   this.name = name;
   }
   
   public String getName() {
	   return name;
   }
   
   void resetCollisions() {
	   collidingWith = new ArrayList<Object>();
   }
   
   public List<Object> getCollisions() {
	   return collidingWith;
   }
   
   void addCollidingObj(Object obj) {
	   collidingWith.add(obj);
   }
   
   public Vector2f getPosition() {
	   return position;
   }
   
   public void setPosition(Vector2f position) {
	   this.position = position;
   }
   
   public void setPosition(float x, float y) {
	   position.x = x;
	   position.y = y;
   }
   
   /**
    * An update function common for all objects of the object class
    * The method update() is meant to be overridden by child objects, so they can have specific functionality
    */
   final void tick() {
	   speed.x+=acceleration.x;
	   speed.y+=acceleration.y;
	   
	   move(speed);
	   
	   //TODO: add the update(); method to this class, and make it run
	   //TODO: handle the exit behavior automatically
   }
   
   Mesh[] getMeshes() {
      return meshes;
   }
   
   public void fillObject(Vector3 color) {
	   fillColor = color;
	   for (Mesh m : meshes) {
		   m.fillColor(fillColor);
	   }
   }
   
   public void fillObject() {
	   for (Mesh m : meshes) {
		   m.fillColor(fillColor);
	   }
   }
   
   public void addForce(Vector2f force) {
	   speed=speed.add(force);
   }
   
   public void addForce(float forceX, float forceY) {
	   speed.x+=forceX;
	   speed.y+=forceY;
   }
   
   public void move(Vector2f speed) {
      //position.x += speed.x;
      //position.y += speed.y;
      position.add(speed);
	   updateTransformation();
   }
   
   public void move(float x, float y) {
      position.x = x;
      position.y = y;
      updateTransformation();
   }
   
   public void setY(float y) {
	      position.y = y;
	      updateTransformation();	   
   }
	   
	public void setX(float x) {
		position.x = x;
		updateTransformation();	   
	}
   
   void updateTransformation() {
      tMatrix = new Matrix4f();
      tMatrix.m00(1);
      tMatrix.m11(1);
      tMatrix.m22(1);
      tMatrix.m33(1);
      
      tMatrix.m30(position.x);
      tMatrix.m31(position.y);
   }
   
   Matrix4f getTransformation() {
      return tMatrix;
   }
   
   public boolean isOutsideScreen() {
	   System.out.println("Object "+this.getClass().getName()+" has incomplete isOutsideScreen() method!");
	   return false;
	   //TODO: change this
   }
   
   public void exitedScreen(Object.ExitBehavior behavior) {
	   //TODO: make this execute different methods based on the exit behavior
   }
   
   
}