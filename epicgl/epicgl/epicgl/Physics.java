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
	public static float friction = 0;
	public static float drag = 0;
	
	/**
	 * Checks whether or not two rectangle objects are colliding.
	 * @param rect1 The first rectangle
	 * @param rect2 The second rectangle
	 * @return Returns true if the two rectangles intersect, false if they do not.
	 */
	public static boolean collisionTestRR(Rectangle rect1, Rectangle rect2) {
		
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
	public static boolean collisionTestBB(Ball ball1, Ball ball2) {
		
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
	public static boolean collisionTestRB(Rectangle rect1, Ball ball2) {
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
	public static boolean collisionTest(GameObject obj1, GameObject obj2) {
		boolean output = false;
		
		if(obj1 instanceof Rectangle && obj2 instanceof Rectangle) {
			output = collisionTestRR((Rectangle)obj1, (Rectangle)obj2);
		}
		else if(obj1 instanceof Ball && obj2 instanceof Ball) {
			output = collisionTestBB((Ball)obj1, (Ball)obj2);
		}
		else if(obj1 instanceof Rectangle && obj2 instanceof Ball) {
			output = collisionTestRB((Rectangle)obj1, (Ball)obj2);
		}
		else if(obj1 instanceof Ball && obj2 instanceof Rectangle) {
			output = collisionTestRB((Rectangle)obj2, (Ball)obj1);
		}
		else {
			System.out.println("Tested objects were not valid types!");
			output = false;
		}
		
		return output;
	}
	
	/**
	 * Applies friction to an object, using the default friction of the physics class set in the game.
	 * @see <a href="https://natureofcode.com/book/chapter-2-forces/">Nature of Code - Forces</a>
	 * @param obj The Object to apply friction to
	 */
	public static void applyFriction(GameObject obj) {
		if (friction != 0) {
			Vector2f frictionVector = obj.getVelocity();
			frictionVector.normalize();
			frictionVector.mul(-1);
			
			float normal = 1;
			float frictionMag = normal*friction;
			
			frictionVector.mul(frictionMag);
			//TODO: change friction object code to make it more complex than just checking if its a ball
			if(obj.getClass()==Ball.class) {
				frictionVector.mul(0.2f);
			}
			
			obj.addForce(frictionVector);
		}

	}
	
	public static void applyDrag(GameObject obj) {
		
		if (drag != 0) {
			float speedSquared = obj.getVelocity().lengthSquared();
			
			float dragMagnitude = drag*speedSquared;
			
			//TODO: if you set drag too high (>0.01f), this will give out ridiculous values
			//it is a bug that needs to be fixed
			//System.out.println(dragMagnitude);
			
			Vector2f dragVector = obj.getVelocity();
			dragVector.mul(-1);
			dragVector.normalize();
			dragVector.mul(dragMagnitude);
			obj.addForce(dragVector);
		}

	}
	
	public static void collisionResolve(GameObject obj1, GameObject obj2) {
		
		if(obj1 instanceof Rectangle && obj2 instanceof Rectangle) {
			//collisionResolveRR((Rectangle)obj1, (Rectangle)obj2);
		}
		else if(obj1 instanceof Ball && obj2 instanceof Ball) {
			dynamicResolveBB((Ball)obj1,(Ball)obj2);
		}
		else if(obj1 instanceof Rectangle && obj2 instanceof Ball) {
			//collisionResolveRB((Rectangle)obj1,(Ball)obj2);
		}
		else if(obj1 instanceof Ball && obj2 instanceof Rectangle) {
			//collisionResolveRB((Rectangle)obj2,(Ball)obj1);
		}
		else {
			System.out.println("Colliding objects were not valid types!");
		}
	}
	
	//TODO: not done
	static void staticResolveBB(Ball ball1, Ball ball2) {
		float distanceSquared = getDistanceSquared(ball1.getPosition(), ball2.getPosition());
		
		//float offset = distanceStance - (ball1.getRadius()+ball2.getRadius());
		
		//the difference between the two positions, basically, an arrow pointing in the direction of the collision
		//this is the Collision Normal
		Vector2f normal = new Vector2f(ball1.getPosition().x - ball2.getPosition().x, ball1.getPosition().y - ball2.getPosition().y);
		normal.normalize(); //we don't care about magnitude
	}
	
	/**
	 * @see <a href="https://youtu.be/LPzyNOHY3A4">Programming Balls #1 Circle Vs Circle Collisions C++ by javidx9</a>
	 * @param ball1
	 * @param ball2
	 */
	static void dynamicResolveBB(Ball ball1, Ball ball2) {
		
		Vector2f velocity1, velocity2;
		velocity1 = ball1.getVelocity();
		velocity2 = ball2.getVelocity();
		
		
		//the difference in momentum between the two balls, after they collide
		//Vector2f deltaP = new Vector2f();
		
		//the difference between the two positions, basically, an arrow pointing in the direction of the collision
		//this is the Collision Normal
		Vector2f normal = new Vector2f(ball1.getPosition().x - ball2.getPosition().x, ball1.getPosition().y - ball2.getPosition().y);
		normal.normalize(); //we don't care about magnitude
		
		//perpendicular to the normal
		Vector2f tangent = new Vector2f(-1*normal.y, normal.x);
		
		//dot product of the velocity of each ball and the tangent
		float dpvt1 = velocity1.x * tangent.x + velocity1.y * tangent.y;
		float dpvt2 = velocity2.x * tangent.x + velocity2.y * tangent.y;
		
		ball1.setVelocity(tangent.x*dpvt1, tangent.y*dpvt1);
		ball2.setVelocity(tangent.x*dpvt2, tangent.y*dpvt2);
		
	}
	
	public static float getDistanceSquared(Vector2f pos1, Vector2f pos2) {
		//the distance formula is d = sqrt( (x2-x1)^2 + (y2-y1)^2 )
		float distanceSquared = (float) Math.pow((pos2.x - pos1.x),2) + (float) Math.pow((pos2.y - pos1.y),2);
		return distanceSquared;
	}
	
}
