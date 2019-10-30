package epicgl;

public class Ball extends GameObject {

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
	      
	      mass=Game.MASS_MULT*(float)Math.PI*radius*radius;
	      System.out.println(mass);
	   }
   
   public float getRadius() {
	   return radius;
   }

	@Override
	float leftFromCenter() {
		return radius;
	}

	@Override
	float rightFromCenter() {
		return radius;
	}

	@Override
	float upFromCenter() {
		return radius;
	}

	@Override
	float downFromCenter() {
		return radius;
	}

}
