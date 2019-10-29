package epicgl;

import org.joml.Vector2f;
import java.lang.Math;

/**
 * Contains static methods for making physics calculations.
 * Currently, this includes checking whether two objects are colliding.
 * @author Jayx20
 *
 */
public class Physics {
	
	public static Vector2f gravity = new Vector2f(0,0);
	public static float friction;
	
	/**
	 * Checks whether or not two rectangle objects are colliding.
	 * @param rect1 The first rectangle
	 * @param rect2 The second rectangle
	 * @return Returns true if the two rectangles intersect, false if they do not.
	 */
	public static boolean CollisionTest(Rectangle rect1, Rectangle rect2) {
		
		//Tests for intersection on the X axis
		boolean collisionX = !( Math.abs(rect1.getPosition().x - rect2.getPosition().x ) > ( rect1.getWidth()/2 + rect2.getWidth()/2 ) );
		//Tests for intersection on the Y axis
		boolean collisionY = !( Math.abs(rect1.getPosition().y - rect2.getPosition().y ) > ( rect1.getHeight()/2 + rect2.getHeight()/2 ) );
		
		//returns true if the objects intersect on both axes.
		return collisionX && collisionY;

	}
	
	/**
	 * Checks whether or not two ball objects are colliding.
	 * @param ball1 The first ball
	 * @param ball2 The second ball
	 * @return Returns true if the two balls intersect, false if they do not.
	 */
	public static boolean CollisionTest(Ball ball1, Ball ball2) {
		
		//the objects touch if the distance between them is less than or equal to both of their radii added together.
		float r1 = ball1.getRadius();
		float r2 = ball2.getRadius();
		
		float radiiSquared = (float)Math.pow((r1+r2), 2);
		
		float distanceSquared = ((float)Math.pow((ball2.getPosition().x - ball1.getPosition().x), 2))+ //X2-X1 squared
								((float)Math.pow((ball2.getPosition().y - ball1.getPosition().y), 2)); //Y2-Y1 squared
		
		return (distanceSquared<=radiiSquared);
	}
	
	/**
	 * Checks whether or not a rectangle and a ball are colliding.
	 * @param rect1 A rectangle
	 * @param ball2 A ball
	 * @return Returns true if the rectangle and ball intersect, false if they do not.
	 */
	public static boolean CollisionTest(Rectangle rect1, Ball ball2) {
		Vector2f rectPos = rect1.getPosition();
		Vector2f ballPos = ball2.getPosition();
		
		float halfWidth = 0.5f*rect1.getWidth();
		float halfHeight = 0.5f*rect1.getHeight();
		
		
		//find the point on the rectangle nearest the center of the circle
		float nearX = (float) (Math.max(rectPos.x-halfWidth, Math.min(ballPos.x, rectPos.x + halfWidth ) ));
		float nearY = (float) (Math.max(rectPos.y-halfHeight, Math.min(ballPos.y, rectPos.y + halfHeight ) ));
		
		//finds the distance between the circle and the nearest point
		float deltaX = ballPos.x - nearX;
		float deltaY = ballPos.y - nearY;
		
		//check if the distance between the two objects centers is less than or equal to the radius of the ball, if it is less, they are touching
		return (deltaX*deltaX + deltaY*deltaY) <= (ball2.getRadius()*ball2.getRadius());
	}
	
	/**
	 * Checks whether or not a pair of rectangle objects or ball objects are colliding.
	 * @param obj1 The first object, a rectangle or a ball.
	 * @param obj2 The second object, a rectangle or a ball.
	 * @return Returns true if the two objects intersect, returns false if they do not. (also returns false if one object is not a rectangle or ball)
	 */
	public static boolean CollisionTest(GameObject obj1, GameObject obj2) {
		boolean output = false;
		
		if(obj1 instanceof Rectangle && obj2 instanceof Rectangle) {
			output = CollisionTest((Rectangle)obj1, (Rectangle)obj2);
		}
		else if(obj1 instanceof Ball && obj2 instanceof Ball) {
			output = CollisionTest((Ball)obj1, (Ball)obj2);
		}
		else if(obj1 instanceof Rectangle && obj2 instanceof Ball) {
			output = CollisionTest((Rectangle)obj1, (Ball)obj2);
		}
		else if(obj1 instanceof Ball && obj2 instanceof Rectangle) {
			output = CollisionTest((Rectangle)obj2, (Ball)obj1);
		}
		else {
			System.out.println("Tested objects were not valid types!");
			output = false;
		}
		
		return output;
	}
}
