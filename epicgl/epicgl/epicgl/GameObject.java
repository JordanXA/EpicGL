package epicgl;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public abstract class GameObject {
   protected Mesh[] meshes;
   protected Vector2f position = new Vector2f(0,0);
   protected Vector2f speed = new Vector2f();
   protected Vector2f acceleration = new Vector2f();
   protected float mass;
   protected Vector3 fillColor;
   protected Matrix4f tMatrix;
   protected List<GameObject> collidingWith = new ArrayList<GameObject>();
   protected String name = new String("Unnamed");
   
   /**
    *Settings for the behavior of an object when it exits the screen
    */
   public static enum ExitBehavior {
	   FREE, STOP, BOUNCE, WRAP
   }
   
   protected GameObject() {
	   updateTransformation();
   }
   
   public void setName(String name) {
	   this.name = name;
   }
   
   public String getName() {
	   return name;
   }
   
   void resetCollisions() {
	   collidingWith = new ArrayList<GameObject>();
   }
   
   public List<GameObject> getCollisions() {
	   return collidingWith;
   }
   
   void addCollidingObj(GameObject obj) {
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
   
   
   public void setSpeed(Vector2f speed) {
	   this.speed=speed;
   }
   
   public void setSpeed(float speedX, float speedY) {
	   speed.x=speedX;
	   speed.y=speedY;
   }
   
   public Vector2f getSpeed() {
	   return new Vector2f(speed.x,speed.y);
   }
   
   public Vector2f getSpeedReference() {
	   return speed;
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
   
   /**
    * The distance from the center (origin) of the object to the left of the object
    */
   abstract float leftFromCenter();
   
   /**
    * The distance from the center (origin) of the object to the right of the object
    */
   abstract float rightFromCenter();
   
   /**
    * The distance from the center (origin) of the object to the top of the object
    */
   abstract float upFromCenter();
   
   /**
    * The distance from the center (origin) of the object to the bottom of the object
    */
   abstract float downFromCenter();
   
   /**
    * @return true or false depending on whether any part of the object is outside the screen
    */
   public boolean isOutsideScreen() {
	   boolean touchTop,touchBottom,touchLeft,touchRight;
	   touchTop = touchBottom = touchLeft = touchRight = false;
	   
	   touchTop = position.y+upFromCenter() > Game.screenHeight;
	   touchBottom = position.y-downFromCenter() < 0;
	   touchLeft = position.x-leftFromCenter() < 0;
	   touchRight = position.x+rightFromCenter() > Game.screenWidth;
	   
	   return touchTop || touchBottom || touchLeft || touchRight;
   }
   
   /**
    * Function to be called if the object has exited the screen
    * It will execute a different method depending on the exit behavior
    */
   public void resolveExit(GameObject.ExitBehavior behavior) {
	   if (behavior == GameObject.ExitBehavior.BOUNCE) {
		   exitBounce();
	   }
	   else if (behavior == GameObject.ExitBehavior.STOP) {
		   exitStop();
	   }
	   else if (behavior == GameObject.ExitBehavior.WRAP) {
		   //exitWrap
	   }
	   //else if behavior == Object.ExitBehavior.FREE
	   //do nothing
	   
   }
   
   
   /**
    * Reverses object motion if outside screen
    * Basically, will make the object bounce off the screen
    */
   public void exitBounce() {
	   float distTop, distBottom, distLeft, distRight;
	   
	   //finds how far the ball is above the screen, below, etc.
	   distTop = position.y+upFromCenter() - Game.screenHeight;
	   distBottom = -1*(position.y-downFromCenter());
	   distLeft = -1*(position.x-leftFromCenter());
	   distRight = position.x+rightFromCenter() - Game.screenWidth;
	   
	   if(distTop > 0) {speed.y=-Math.abs(speed.y);}
	   if(distBottom > 0) {speed.y=Math.abs(speed.y);}
	   if(distLeft > 0) {speed.x=Math.abs(speed.x);}
	   if(distRight > 0) {speed.x=-Math.abs(speed.x);} 
   }
   
   
   //TODO: makes these methods execute in any object class depending on the ExitBehavior
   /**
    * Will prevent object from entering the screen if called, and make it stop moving
    */
   public void exitStop() {
	   float distTop, distBottom, distLeft, distRight;
	   
	   boolean STOP = true;
	   
	   //finds how far the ball is above the screen, below, etc.
	   distTop = position.y+upFromCenter() - Game.screenHeight;
	   distBottom = -1*(position.y-downFromCenter());
	   distLeft = -1*(position.x-leftFromCenter());
	   distRight = position.x+rightFromCenter() - Game.screenWidth;
	   
	   //TODO: fix this it bugged :(
	   
	   System.out.println(""+distTop+distBottom+distLeft+distRight);
	   
	   if(distTop > 0) {position.y=Game.screenHeight-upFromCenter(); if(STOP) speed.y=0;}
	   if(distBottom > 0) {position.y=0+downFromCenter(); if(STOP) speed.y=0;}
	   if(distLeft > 0) {position.x=0+leftFromCenter(); if(STOP) speed.x=0;}
	   if(distRight > 0) {position.x=Game.screenWidth-rightFromCenter(); if(STOP) speed.x=0;}
   }
   
   
}