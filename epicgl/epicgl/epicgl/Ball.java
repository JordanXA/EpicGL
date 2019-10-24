package epicgl;

public class Ball extends Object {

	float radius;

   Ball(float x, float y, float radius) {
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

}
