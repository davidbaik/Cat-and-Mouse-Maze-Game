package ca.cmpt213.as2.GameLogic;

public class Mouse {
	private int xPosition = 0;
	private int yPosition = 0;
	private boolean isAlive = false;
	
	public Mouse(int xPosition, int yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		isAlive = true;
	}
	
	public void moveUp() {
		yPosition--;
	}
	
	public void moveLeft() {
		xPosition--;
	}
	
	public void moveDown() {
		yPosition++;
	}
	
	public void moveRight() {
		xPosition++;
	}
	
	public void killMouse() {
		isAlive = false;
	}
	
	public boolean checkAliveStatus() {
		return isAlive;
	}
	
	public int getXPos() {
		return xPosition;
	}
	
	public int getYPos() {
		return yPosition;
	}
}
