package pong;

import epicgl.*;

public class PongGame extends Game {
	
	Ball theBall;
	
	public static void main(String[] args) {
		PongGame.setScreenSize(1000, 1000);
		new PongGame();
	}

	@Override
	public void start() {
		theBall = new Ball(500f, 500f, 25f);
		objectManager.addObject(theBall);
		
	}

	@Override
	public void loop() {
		if(mouse.justClicked(MouseButton.LEFT)) {
			theBall.addForce(0,1);
		}
		if(theBall.isOutsideScreen()) {}
	}
	
	
}
