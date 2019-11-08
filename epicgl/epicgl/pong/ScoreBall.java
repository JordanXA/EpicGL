package pong;

import org.joml.Vector2f;

import epicgl.Ball;
import epicgl.Rectangle;

public class ScoreBall extends Ball {

	public ScoreBall(float x, float y, float radius) {
		super(x, y, radius);
		
	}
	
	@Override
	public void onUpdate() {
		if (getCollisions().contains(PongGame.player1) || getCollisions().contains(PongGame.player2)) {
			velocity.x = velocity.x * 1.2f;
		}
	
	}
	
	/*
	public void collideWithPlayer(Rectangle player) {
		Vector2f rectPos = player.getPosition();
		Vector2f ballPos = this.getPosition();
		
		float halfWidth = 0.5f*player.getWidth();
		float halfHeight = 0.5f*player.getHeight();
		
		
		//find the point on the rectangle nearest the center of the circle
		float nearX = (float) (Math.max(rectPos.x-halfWidth, Math.min(ballPos.x, rectPos.x + halfWidth ) ));
		float nearY = (float) (Math.max(rectPos.y-halfHeight, Math.min(ballPos.y, rectPos.y + halfHeight ) ));
		
		//finds the distance between the circle and the nearest point
		float deltaX = ballPos.x - nearX;
		float deltaY = ballPos.y - nearY;
		
		//closer on the X axis
		if (deltaX < deltaY) {
			this.setVelocity(-velocity.x,velocity.y);
		}
		//closer on the Y axis
		else {
			this.setVelocity(velocity.x,-velocity.y);
		}
	}*/
	
}
	

