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
	   boolean touchTop,touchBottom,touchLeft,touchRight;
	   touchTop = touchBottom = touchLeft = touchRight = false;
	   
	   touchTop = position.y+radius > Game.screenHeight;
	   touchBottom = position.y-radius < 0;
	   touchLeft = position.x-radius < 0;
	   touchRight = position.x+radius > Game.screenWidth;
	   
	   return touchTop || touchBottom || touchLeft || touchRight;
   }
   
   /**
    * Reverses ball motion if outside screen
    * Basically, will make the ball bounce off the screen
    */
   public void bounceOffScreen() {
	   float distTop, distBottom, distLeft, distRight;
	   
	   //finds how far the ball is above the screen, below, etc.
	   distTop = position.y+radius - Game.screenHeight;
	   distBottom = position.y-radius;
	   distLeft = position.x-radius;
	   distRight = position.x+radius - Game.screenWidth;
	   
	   if(distTop > 0) {}
   }
   //TODO: makes these methods execute in any object class depending on the ExitBehavior
   /**
    * Will prevent ball from entering the screen if called
    */
   public void resolveOutsideScreen() {
	   float distTop, distBottom, distLeft, distRight;
	   
	   boolean STOP = true;
	   
	   //finds how far the ball is above the screen, below, etc.
	   distTop = position.y+radius - Game.screenHeight;
	   distBottom = -1*position.y-radius;
	   distLeft = -1*position.x-radius;
	   distRight = position.x+radius - Game.screenWidth;
	   
	   if(distTop > 0) {position.y=Game.screenHeight-radius; if(STOP) speed.y=0;}
	   if(distBottom > 0) {position.y=0+radius; if(STOP) speed.y=0;}
	   if(distLeft > 0) {position.x=0+radius; if(STOP) speed.x=0;}
	   if(distRight > 0) {position.x=Game.screenWidth-radius; if(STOP) speed.x=0;}
   }

}
