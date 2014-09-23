package ca.cmpt213.as2.GameLogic;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import ca.cmpt213.as2.GameUI.*;

public class GameLogic {
	private static final String UP_COMMAND = "W";
	private static final String LEFT_COMMAND = "A";
	private static final String DOWN_COMMAND = "S";
	private static final String RIGHT_COMMAND = "D";
	private static final String HELP_COMMAND = "?";
	private static final String REVEAL_MAZE_COMMAND = "M";
	
	private final static int MAZE_HEIGHT = 15;
	private final static int MAZE_WIDTH = 20;
	
	private static Maze maze = null;
	private static Mouse mouse = null;
	private static ArrayList<Cat> cats = null;
	@SuppressWarnings("unused")
	private static Cheese cheese = null;
	
	private static boolean isGetAllCheese = false;
	private static int numberOfCheeseGathered = 0;
	private static int numberOfCheeseRequired = 5;

	public static void main(String[] args) {
		initializeMaze();
		initializeMouse();
		initializeCats();
		spawnCheese();
		revealAroundMouse();
		GameUI.printIntro();
		GameUI.printHelpScreen();
		while (isMouseAlive(mouse) && !isGetAllCheese) {
			GameUI.drawMaze(maze.getMaze());
			GameUI.printProgress(numberOfCheeseGathered, numberOfCheeseRequired);
			nextMove(mouse, cats);
			if (numberOfCheeseGathered == numberOfCheeseRequired) {
				isGetAllCheese = true;
			}
		}
		if (isMouseAlive(mouse)) {
			GameUI.printWin();
		} else {
			GameUI.printLose();
		}
		revealMazeToPlayer();
		GameUI.drawMaze(maze.getMaze());
		GameUI.printProgress(numberOfCheeseGathered, numberOfCheeseRequired);
		if (!isMouseAlive(mouse)) {
			GameUI.printGameOver();
		}
	}

	private static void initializeMaze() {
		maze = new Maze();
		for (int y = 1; y < MAZE_HEIGHT - 1; y++) {
			for (int x = 1; x < MAZE_WIDTH - 1; x++) {
				hideMazeCell(x, y);
			}
		}		
	}
	private static void hideMazeCell(int x, int y) {
		Value temp = maze.getMazeCell(x, y);
		if (temp == Value.EMPTY) {
			maze.setMazeCell(x, y, Value.EMPTY_INVISIBLE);
		} else if (temp == Value.WALL) {
			maze.setMazeCell(x, y, Value.WALL_INVISIBLE);
		}
	}
	
	private static void initializeMouse() {
		mouse = new Mouse(1, 1);
		maze.setMazeCell(1, 1, Value.MOUSE);
	}

	private static void initializeCats() {
		cats = new ArrayList<Cat>();
		cats.add(new Cat(MAZE_WIDTH - 2, 1));
		cats.add(new Cat(MAZE_WIDTH - 2, MAZE_HEIGHT - 2));
		cats.add(new Cat(1, MAZE_HEIGHT-2));
		for (Cat cat : cats) {
			maze.setMazeCell(cat.getXPos(), cat.getYPos(), Value.CAT);
		}
	}
	
	private static void spawnCheese() {
		int xPosition = 0;
		int yPosition = 0;
		Value mazeCellValue = null;
		boolean isSuccessfulPlacement = false;
		while(!isSuccessfulPlacement) {
			xPosition = generateRandomXPosition();
			yPosition = generateRandomYPosition();
			mazeCellValue = maze.getMazeCell(xPosition, yPosition);
			if (mazeCellValue == Value.EMPTY || mazeCellValue == Value.EMPTY_INVISIBLE) {
				cheese = new Cheese(xPosition, yPosition);
				maze.setMazeCell(xPosition, yPosition, Value.CHEESE);
				isSuccessfulPlacement = true;
			}
		}
	}
	public static int generateRandomXPosition() {
		Random randomNumber = new Random();
		int xPosition = randomNumber.nextInt(MAZE_WIDTH - 2) + 1;
		return xPosition;
	}
	public static int generateRandomYPosition() {
		Random randomNumber = new Random();
		int yPosition = randomNumber.nextInt(MAZE_HEIGHT - 2) + 1;
		return yPosition;
	}
	
	public static boolean isMouseAlive(Mouse mouse) {
		boolean isAlive = mouse.checkAliveStatus();
		return isAlive;
	}

	public static void revealAroundMouse() {
		int xPosition = mouse.getXPos();
		int yPosition = mouse.getYPos();

		revealMazeCell(xPosition, yPosition);
		revealMazeCell(xPosition, yPosition - 1);
		revealMazeCell(xPosition, yPosition + 1);
		revealMazeCell(xPosition - 1, yPosition);
		revealMazeCell(xPosition - 1, yPosition - 1);
		revealMazeCell(xPosition - 1, yPosition + 1);
		revealMazeCell(xPosition + 1, yPosition);
		revealMazeCell(xPosition + 1, yPosition - 1);
		revealMazeCell(xPosition + 1, yPosition + 1);
	}
	
	public static String nextMove(Mouse mouse, ArrayList<Cat> cats) {
		boolean isValidCommand = false;
		
		Scanner getCommand = new Scanner(System.in);
		String command = null;
		
		while (!isValidCommand && isMouseAlive(mouse)) {
			GameUI.printPromptUserMessage();
			command = getCommand.next().toUpperCase();
			
			boolean userEntersCommand = command.startsWith(UP_COMMAND) || command.startsWith(LEFT_COMMAND)
					|| command.startsWith(DOWN_COMMAND) || command.startsWith(RIGHT_COMMAND) 
					|| command.startsWith(HELP_COMMAND) || command.startsWith(REVEAL_MAZE_COMMAND);
			if (userEntersCommand) {
				isValidCommand = true;
			} else {
				GameUI.printInvalidInput();
				isValidCommand = false;
			}
			
			boolean thereIsAWallInTheWay = !moveMouseAndAllCats(command, mouse, cats) && isValidCommand;		
			if(thereIsAWallInTheWay) {
				GameUI.printInvalidMove();
				isValidCommand = false;
			}
		}
		return command;
	}
	public static boolean moveMouseAndAllCats(String command, Mouse mouse, ArrayList<Cat> cats) {
		boolean isSuccessfulMove = false;
		
		if (command.startsWith(UP_COMMAND)) {
			isSuccessfulMove = moveMouse(mouse, cats, UP_COMMAND);
		} else if (command.startsWith(LEFT_COMMAND)) {
			isSuccessfulMove = moveMouse(mouse, cats, LEFT_COMMAND);
		} else if (command.startsWith(DOWN_COMMAND)) {
			isSuccessfulMove = moveMouse(mouse, cats, DOWN_COMMAND);
		} else if (command.startsWith(RIGHT_COMMAND)) {
			isSuccessfulMove = moveMouse(mouse, cats, RIGHT_COMMAND);
		} else if (command.startsWith(HELP_COMMAND)) {
			GameUI.printHelpScreen();
			isSuccessfulMove = true;
		} else if (command.startsWith(REVEAL_MAZE_COMMAND)){
			revealMazeToPlayer();
			isSuccessfulMove = true;
		}
		return isSuccessfulMove;
	}

	private static boolean moveMouse(Mouse mouse, ArrayList<Cat> cats, String command) {
		Value cellToBeEntered = null;
		if (command.startsWith(UP_COMMAND)) {
			cellToBeEntered = maze.getMazeCell(mouse.getXPos(), mouse.getYPos() - 1);
		} else if (command.startsWith(LEFT_COMMAND)) {
			cellToBeEntered = maze.getMazeCell(mouse.getXPos() - 1, mouse.getYPos());
		} else if (command.startsWith(DOWN_COMMAND)) {
			cellToBeEntered = maze.getMazeCell(mouse.getXPos(), mouse.getYPos() + 1);
		} else if (command.startsWith(RIGHT_COMMAND)) {
			cellToBeEntered = maze.getMazeCell(mouse.getXPos() + 1, mouse.getYPos());
		}
		if (cellToBeEntered == Value.EMPTY || cellToBeEntered == Value.CHEESE) {
			maze.setMazeCell(mouse.getXPos(), mouse.getYPos(), Value.EMPTY);
			if (command.startsWith(UP_COMMAND)) {
				mouse.moveUp();
			} else if (command.startsWith(LEFT_COMMAND)) {
				mouse.moveLeft();
			} else if (command.startsWith(DOWN_COMMAND)) {
				mouse.moveDown();
			} else if (command.startsWith(RIGHT_COMMAND)) {
				mouse.moveRight();
			}
			maze.setMazeCell(mouse.getXPos(), mouse.getYPos(), Value.MOUSE);
			if (cellToBeEntered == Value.CHEESE) {
				numberOfCheeseGathered++;
				spawnCheese();
			}
			revealAroundMouse();
			moveAllCats(cats);
			revealAroundMouse();
			return true;
		} else if (cellToBeEntered == Value.CAT || cellToBeEntered == Value.CAT_ON_CHEESE) {
			maze.setMazeCell(mouse.getXPos(), mouse.getYPos(), Value.EMPTY);
			if (command.startsWith(UP_COMMAND)) {
				mouse.moveUp();
			} else if (command.startsWith(LEFT_COMMAND)) {
				mouse.moveLeft();
			} else if (command.startsWith(DOWN_COMMAND)) {
				mouse.moveDown();
			} else if (command.startsWith(RIGHT_COMMAND)) {
				mouse.moveRight();
			}
			maze.setMazeCell(mouse.getXPos(), mouse.getYPos(), Value.DEAD_MOUSE);
			mouse.killMouse();
			return true;
		}
		return false;
	}
	
	private static void moveAllCats(ArrayList<Cat> cats) {
		for (Cat cat : cats) {
			boolean isCatMoved = false;
			Value cellToBeEntered = null;
			
			RandomDirectionGenerator randomDirection = new RandomDirectionGenerator();
			ArrayList<String> directions = randomDirection.getArray();
			
			while (!isCatMoved) {
				for (String direction : directions) {
					if (!isCatMoved) {
						if (direction.equals("up")) {
							cellToBeEntered = maze.getMazeCell(cat.getXPos(), cat.getYPos() - 1);
						} else if (direction.equals("left")) {
							cellToBeEntered = maze.getMazeCell(cat.getXPos() - 1, cat.getYPos());
						} else if (direction.equals("down")) {
							cellToBeEntered = maze.getMazeCell(cat.getXPos(), cat.getYPos() + 1);
						} else {
							cellToBeEntered = maze.getMazeCell(cat.getXPos() + 1, cat.getYPos());
						}
						if (cellToBeEntered == Value.EMPTY || cellToBeEntered == Value.EMPTY_INVISIBLE
								|| cellToBeEntered == Value.CHEESE) {
							moveCat(cat, cellToBeEntered, direction);
							maze.setMazeCell(cat.getXPos(), cat.getYPos(), Value.CAT);
							isCatMoved = true;
						} else if (cellToBeEntered == Value.MOUSE) {
							moveCat(cat, cellToBeEntered, direction);
							maze.setMazeCell(cat.getXPos(), cat.getYPos(), Value.DEAD_MOUSE);
							mouse.killMouse();
							isCatMoved = true;
						}
					}
				}
			}
		}
	}

	private static void moveCat(Cat cat, Value cellToBeEntered,
			String direction) {
		maze.setMazeCell(cat.getXPos(), cat.getYPos(), cat.getValueOfMazeCellUnderCat());
		cat.setValueOfMazeCellUnderCat(cellToBeEntered);
		if (direction.equals("up")) {
			cat.moveUp();
		} else if (direction.equals("left")) {
			cat.moveLeft();
		} else if (direction.equals("down")) {
			cat.moveDown();
		} else {
			cat.moveRight();
		}
	}
	public static void revealMazeToPlayer() {
		for (int y = 1; y < MAZE_HEIGHT - 1; y++) {
			for (int x = 1; x < MAZE_WIDTH - 1; x++) {
				revealMazeCell(x, y);
			}
		}
		for (Cat cat : cats) {
			if (cat.getValueOfMazeCellUnderCat() != Value.CHEESE) {
				cat.setValueOfMazeCellUnderCat(Value.EMPTY);
			}
		}
	}
	
	private static void revealMazeCell(int x, int y) {
		Value cellToBeRevealed = maze.getMazeCell(x, y);
		if (cellToBeRevealed == Value.EMPTY_INVISIBLE) {
			maze.setMazeCell(x, y, Value.EMPTY);
		} else if (cellToBeRevealed == Value.WALL_INVISIBLE) {
			maze.setMazeCell(x, y, Value.WALL);
		}
	}
}
