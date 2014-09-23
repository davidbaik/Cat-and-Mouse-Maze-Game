package ca.cmpt213.as2.GameUI;
import ca.cmpt213.as2.GameLogic.Cell;
import ca.cmpt213.as2.GameLogic.Value;

public class GameUI {
private final static int MAZE_HEIGHT = 15;
private final static int MAZE_WIDTH = 20;

	public static void printIntro() {
		System.out.println("-------------------------------------------");
		System.out.println("Welcome to da Cat and Mouse Maze Adventure!");
		System.out.println("by David Baik and Ian Fong");
		System.out.println("version.daBest");
		System.out.println("-------------------------------------------");
	}

	public static void drawMaze(Cell[][] maze) {
		System.out.printf("\n%s\n", "Maze:");
		for (int y = 0; y < MAZE_HEIGHT; y ++) {
			for (int x = 0; x < MAZE_WIDTH; x++) {
				if (maze[y][x].getCell() == Value.EMPTY) {
					System.out.printf("%s", " ");
				} else if (maze[y][x].getCell() == Value.WALL) {
					System.out.printf("%s", "#");
				} else if (maze[y][x].getCell() == Value.MOUSE) {
					System.out.printf("%s", "@");
				} else if (maze[y][x].getCell() == Value.CAT
						|| maze[y][x].getCell() == Value.CAT_ON_CHEESE) {
					System.out.printf("%s", "!");
				} else if (maze[y][x].getCell() == Value.CHEESE) {
					System.out.printf("%s", "$");
				} else if (maze[y][x].getCell() == Value.DEAD_MOUSE) {
					System.out.printf("%s", "X");
				} else {
					System.out.printf("%s", ".");
				}
			}
			System.out.printf("\n");
		}
	}
	
	public static void printProgress(int numberOfCheeseGathered, int numberOfCheeseRequired) {
		System.out.printf ("Cheese collected: %d of %d\n", numberOfCheeseGathered, numberOfCheeseRequired);
	}
	
	public static void printPromptUserMessage() {
		System.out.print ("Enter your move [WASD?]: ");
	}
	
	public static void printInvalidInput() {
		System.out.println ("INVALID COMMAND. Please enter W, A, S, D, or ?");
	}
	
	public static void printInvalidMove() {
		System.out.println ("INVALID MOVE: you cannot move through walls!");
	}

	public static void printHelpScreen() {
		System.out.println ("\nDIRECTIONS:");
		System.out.println ("\tFind da 5 cheese before da cats eat you!");
		System.out.println ("LEGEND");
		System.out.println ("\t#: Da wall");
		System.out.println ("\t@: You (da mouse)");
		System.out.println ("\t!: Da cat");
		System.out.println ("\t$: Da cheese");
		System.out.println ("\t.: Da unexplored space");
		System.out.println ("MOVES");
		System.out.println ("\tUse W (up), A (left), S (down) and D (right) to move.\n\t(You must press enter after each move).");	
	}

	public static void printWin() {
		System.out.println ("Congratulations! You won!");
	}
	
	public static void printLose() {
		System.out.println ("I'm sorry, you have been eaten!");
	}

	public static void printGameOver() {
		System.out.println ("GAME OVER; please try again.");
	}
}
