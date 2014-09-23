package ca.cmpt213.as2.GameLogic;

public class Cell {	
	private Value cellValue = null;
	private boolean isVisited = false;
	
	public Cell() {
		cellValue = Value.WALL;
		isVisited = false;
	}
	
	public void setCell(Value value) {
		switch(value) {
			case EMPTY:
				cellValue = Value.EMPTY;
				break;
			case WALL:
				cellValue = Value.WALL;
				break;
			case MOUSE:
				cellValue = Value.MOUSE;
				break;
			case CAT:
				cellValue = Value.CAT;
				break;
			case CHEESE:
				cellValue = Value.CHEESE;
				break;
			case CAT_ON_CHEESE:
				cellValue = Value.CAT_ON_CHEESE;
				break;
			case DEAD_MOUSE:
				cellValue = Value.DEAD_MOUSE;
				break;
			case EMPTY_INVISIBLE:
				cellValue = Value.EMPTY_INVISIBLE;
				break;
			case WALL_INVISIBLE:
				cellValue = Value.WALL_INVISIBLE;
				break;
			default:
				assert false;
		}
	}
	
	public Value getCell() {
		return cellValue;
	}
	
	public void setVisited() {
		isVisited = true;
	}
	
	public boolean getVisited() {
		return isVisited;
	}
}
