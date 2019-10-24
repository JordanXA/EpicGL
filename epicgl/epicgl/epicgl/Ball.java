package epicgl;

public class Ball extends Object {

	   Ball(float x, float y, float radius) {
		      position.x = x;
		      position.y = y;
		      meshes = new Mesh[]{new CircleMesh(radius)};
		      updateTransformation();
		      
		      mass=(float)Math.PI*radius*radius;
		   }
	
}
