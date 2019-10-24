package epicgl;

import org.joml.Vector2f;

/**
 * Contains static methods for making physics calculations.
 * Currently, this includes checking whether two objects are colliding.
 * @author Jayx20
 *
 */
public class Physics {
	
	/**
	 * Checks whether or not two rectangle objects are colliding.
	 * @param rect1 The first rectangle
	 * @param rect2 The second rectangle
	 * @return Returns true if the two rectangles intersect, false if they do not.
	 */
	public static boolean CollisionTest(Rectangle rect1, Rectangle rect2) {
		
		/*
		 * This way wasn't working but i may try to use it later, not sure which method is quicker
		 * 
		//uses the top left of each rectangle
		Vector2f pos1 = rect1.topLeft();
		Vector2f pos2 = rect2.topLeft();
		
		//checks for intersection on the X axis
		boolean collisionX = (rect1.getPosition().x + rect1.getWidth() >= rect2.getPosition().x &&
				rect2.getPosition().x+rect2.getWidth() >= rect1.getPosition().x);
		//checks for intersection on the y axis
		boolean collisionY = (rect1.getPosition().y + rect1.getHeight() >= rect2.getPosition().y &&
				rect2.getPosition().y+rect2.getHeight() >= rect1.getPosition().y);
		//returns true if intersects in 2d space
		return collisionX && collisionY;
		*/
		
		boolean intersectLeft, intersectTop, intersectBottom, intersectRight;
		intersectLeft = intersectTop = intersectBottom = intersectRight = false;
		
		//if();
		
		return false;

	}
	
	/**
	 * Checks whether or not two ball objects are colliding.
	 * @param ball1 The first ball
	 * @param ball2 The second ball
	 * @return Returns true if the two balls intersect, false if they do not.
	 */
	public static boolean CollisionTest(Ball ball1, Ball ball2) {
		return false;
	}
	
	/**
	 * Checks whether or not a rectangle and a ball are colliding.
	 * @param rect1 A rectangle
	 * @param ball2 A ball
	 * @return Returns true if the rectangle and ball intersect, false if they do not.
	 */
	public static boolean CollisionTest(Rectangle rect1, Ball ball2) {
		return false;
	}
	
	/**
	 * Checks whether or not a pair of rectangle objects or ball objects are colliding.
	 * @param obj1 The first object, a rectangle or a ball.
	 * @param obj2 The second object, a rectangle or a ball.
	 * @return Returns true if the two objects intersect, returns false if they do not. (also returns false if one object is not a rectangle or ball)
	 */
	public static boolean CollisionTest(Object obj1, Object obj2) {
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
