package epicgl;

public class Ball extends Object {

	float radius;

	/**
	 * Creates a new ball object.
	 * @param x The X position of the ball's center
	 * @param y The Y positions of the ball's center
	 * @param radius The radius of the ball
	 */
   public Ball(float x, float y, float radius) {
	      position.x = x;
	      position.y = y;
	      this.radius=radius;
	      meshes = new Mesh[]{new CircleMesh(this.radius)};
	      updateTransformation();
	      
	      mass=(float)Math.PI*radius*radius;
	   }
   
   public float getRadius() {
	   return radius;
   }
   
   @Override
   public boolean isOutsideScreen() {
	   //TODO: finish this method
	   return false;
   }

}
