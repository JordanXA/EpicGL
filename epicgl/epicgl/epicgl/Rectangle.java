package epicgl;

import org.joml.Vector2f;

public class Rectangle extends GameObject {

	float width, height;
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
   public Rectangle(float x, float y, float width, float height) {
      position.x = x;
      position.y = y;
      this.width=width;
      this.height=height;
      Vector3 vert0 = new Vector3(-0.5f*this.width,+0.5f*this.height);
      Vector3 vert1 = new Vector3(+0.5f*this.width,+0.5f*this.height);
      Vector3 vert2 = new Vector3(-0.5f*this.width,-0.5f*this.height);
      Vector3 vert3 = new Vector3(+0.5f*this.width,-0.5f*this.height);
      Vector3[] rectVerts = new Vector3[]{vert0, vert1, vert2, vert3};
      meshes = new Mesh[]{new Mesh(rectVerts)};
      updateTransformation();
      
      mass = Game.MASS_MULT*width*height;
   }
   
   /**
    * Returns the coordinates of the top left corner of this rectangle.
    */
   public Vector2f topLeft() {
	   Vector2f topLeftPos = new Vector2f();
	   
	   //moves the position left by half the width, to the left edge
	   topLeftPos.x = position.x-0.5f*width;
	   //moves the position up by half the height, to the top edge
	   topLeftPos.y = position.y+0.5f*height;
	   
	   return topLeftPos;
   }
   
   
   /**
    * Returns the coordinates of the bottom right corner of this rectangle.
    */
   public Vector2f bottomRight() {
	   Vector2f bottomRightPos = new Vector2f();
	   
	   //moves the position right by half the width, to the top edge
	   bottomRightPos.x = position.x+0.5f*width;
	   //moves the position down by half the height, to the bottom edge
	   bottomRightPos.y = position.y-0.5f*height;
	   
	   return bottomRightPos;
   }
	
	@Override
	float leftFromCenter() {
		return 0.5f*width;
	}
	
	@Override
	float rightFromCenter() {
		return 0.5f*width;
	}
	
	@Override
	float upFromCenter() {
		return 0.5f*height;
	}
	
	@Override
	float downFromCenter() {
		return 0.5f*height;
	}
   
}