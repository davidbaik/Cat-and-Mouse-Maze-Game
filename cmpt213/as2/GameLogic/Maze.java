package ca.cmpt213.as2.GameLogic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Maze creates a 20x15 maze. This is accomplished using depth first search to generate a random maze,
 * then removing random remaining walls to create a suitable game board.
 * @author dbaik, ifong
 *
 */
public class Maze {
	private static final int UNWANTED_WALLS = 5;
	private static final int NUMBER_OF_WALLS_TO_REMOVE = 30;
	private static final int WIDTH = 20;
	private static final int HEIGHT = 15;
	
	private static Cell[][] maze = null;
	
	public Maze() {
		maze = new Cell[HEIGHT][WIDTH];
		initializeMaze(maze);
		generateMaze(1, 1);
		removeWalls();
	}

	private void initializeMaze(Cell[][] maze) {
		fillMazeWithWalls(maze);
		setOuterWallsVisited();
	}
	private void fillMazeWithWalls(Cell[][] maze) {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				maze[y][x] = new Cell();
			}
		}
	}
	private void setOuterWallsVisited() {
		for (int x = 0; x < WIDTH; x++) {
			maze[0][x].setVisited();
			maze[HEIGHT - 1][x].setVisited();
		}
		for (int y = 0; y < HEIGHT; y++) {
			maze[y][0].setVisited();
			maze[y][WIDTH - 1].setVisited();
		}
	}
	
	private void generateMaze(int yPosition, int xPosition) {
		RandomDirectionGenerator randomDirection = new RandomDirectionGenerator();
		ArrayList<String> randomDirections = randomDirection.getArray();
		
		setCurrentCellVisited(yPosition, xPosition);
		depthFirstSearchRecursion(yPosition, xPosition, randomDirections);
	}
	private void setCurrentCellVisited(int yPosition, int xPosition) {
		maze[yPosition][xPosition].setVisited();
		maze[yPosition][xPosition].setCell(Value.EMPTY);
	}
	private void depthFirstSearchRecursion(int yPosition, int xPosition,
			ArrayList<String> randomDirections) {
		for (String direction : randomDirections) {
			if (direction.equals("up")) {
				if ((yPosition - 2) >= 0) {
					if (!maze[yPosition - 2][xPosition].getVisited()) {
						maze[yPosition - 1][xPosition].setCell(Value.EMPTY);
						maze[yPosition - 1][xPosition].setVisited();
						generateMaze(yPosition - 2, xPosition);
					}
				}
			}
			if (direction.equals("right")) {
				if ((xPosition + 2) < WIDTH) {
					if (!maze[yPosition][xPosition + 2].getVisited()) {
						maze[yPosition][xPosition + 1].setCell(Value.EMPTY);
						maze[yPosition][xPosition + 1].setVisited();
						generateMaze(yPosition, xPosition + 2);
					}
				}
			}
			if (direction.equals("down")) {
				if ((yPosition + 2) < HEIGHT) {
					if (!maze[yPosition + 2][xPosition].getVisited()) {
						maze[yPosition + 1][xPosition].setCell(Value.EMPTY);
						maze[yPosition + 1][xPosition].setVisited();
						generateMaze(yPosition + 2, xPosition);
					}
				}
			}
			if (direction.equals("left")) {
				if ((xPosition - 2) >= 0) {
					if (!maze[yPosition][xPosition - 2].getVisited()) {
						maze[yPosition][xPosition - 1].setCell(Value.EMPTY);
						maze[yPosition][xPosition - 1].setVisited();
						generateMaze(yPosition, xPosition - 2);
					}
				}
			}
		}
	}
	
	private void removeWalls() {
		clearTopLeft();
		clearTopRight();
		clearBottomLeft();
		clearBottomRight();
		removeRandomWalls();
	}
	private void clearTopLeft() {
		maze[1][1].setCell(Value.EMPTY);
	}
	private void clearTopRight() {
		maze[1][WIDTH - 2].setCell(Value.EMPTY);
	}
	private void clearBottomLeft() {
		maze[HEIGHT - 2][1].setCell(Value.EMPTY);
	}
	private void clearBottomRight() {
		maze[HEIGHT - 2][WIDTH - 2].setCell(Value.EMPTY);
	}
	private void removeRandomWalls() {
		Random randomNumberGenerator = new Random();
		int unwantedColumnWallsToRemove = UNWANTED_WALLS;
		while (unwantedColumnWallsToRemove > 0) {
			int randomYPosition = randomNumberGenerator.nextInt(HEIGHT - 2) + 1;
			if (maze[randomYPosition][WIDTH - 2].getCell() == Value.WALL) {
				maze[randomYPosition][WIDTH - 2].setCell(Value.EMPTY);
				unwantedColumnWallsToRemove--;
			}
		}
		
		int wallsLeftToRemove = NUMBER_OF_WALLS_TO_REMOVE;
		while(wallsLeftToRemove > 0) {
			int randomXPosition = randomNumberGenerator.nextInt(WIDTH - 2) + 1;
			int randomYPosition = randomNumberGenerator.nextInt(HEIGHT - 2) + 1;
			if (maze[randomYPosition][randomXPosition].getCell() == Value.WALL) {
				maze[randomYPosition][randomXPosition].setCell(Value.EMPTY);
				wallsLeftToRemove--;
			}
		}
	}

	public Value getMazeCell(int xPosition, int yPosition) {
		Value cellValue = maze[yPosition][xPosition].getCell();
		return cellValue;
	}
	
	public void setMazeCell(int xPosition, int yPosition, Value value) {
		maze[yPosition][xPosition].setCell(value);
	}
	
	public Cell[][] getMaze() {
		return maze.clone();
	}
}