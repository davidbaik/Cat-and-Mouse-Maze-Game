package ca.cmpt213.as2.GameLogic;

public class Cat {
	private int xPosition = 0;
	private int yPosition = 0;
	private Value valueOfMazeCellUnderCat = null;
	
	public Cat(int xPosition, int yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		valueOfMazeCellUnderCat = Value.EMPTY_INVISIBLE;
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
	
	public int getXPos() {
		return xPosition;
	}
	
	public int getYPos() {
		return yPosition;
	}

	public Value getValueOfMazeCellUnderCat() {
		return valueOfMazeCellUnderCat;
	}

	public void setValueOfMazeCellUnderCat(Value valueOfMazeCellUnderCat) {
		this.valueOfMazeCellUnderCat = valueOfMazeCellUnderCat;
	}

	
}
