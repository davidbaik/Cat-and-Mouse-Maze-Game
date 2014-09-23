package ca.cmpt213.as2.GameLogic;

import java.util.ArrayList;
import java.util.Collections;

public class RandomDirectionGenerator {
	ArrayList<String> fourDirections = null;
	
	public RandomDirectionGenerator() {
		fourDirections = new ArrayList<String>();  
		fourDirections.add("up");
		fourDirections.add("down");
		fourDirections.add("left");
		fourDirections.add("right");
		shuffleArray(fourDirections);
	}
	
	private void shuffleArray(ArrayList<String> fourDirections) {
		Collections.shuffle(fourDirections);
	}
	
	public ArrayList<String> getArray() {
		return fourDirections;
	}
}
