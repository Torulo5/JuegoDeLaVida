package models;

import java.awt.Point;
import java.util.ArrayList;

import gameStatesTypes.ArrayGameState;
import gameStatesTypes.GameState;
import gameStatesTypes.HasMapGameState;

public class GameModel {


	private GameState gameState = null;
	private final boolean DEBUG = false;
	private int steps = 0;
	
	public GameModel() {
		//gameState = new ArrayGameState();
		gameState = new HasMapGameState();
	}

	public ArrayList<Point> getPoints() {
		return gameState.getPoints();
	}

	public ArrayList<Point> getPointsNeededToCheck() {
		return gameState.getPointsNeededToCheck();
	}

	public ArrayList<Point> getNextPointsAlive() {
		return gameState.getNextPointsAlive();
	}

	public void addNewPoint(Point alivePoint) {
		gameState.addNewPoint(alivePoint);
	}
	
	public void setNextStatus() {
		steps++;
		gameState.calculateNextStep();
		System.out.println(steps);
	}
	
	public void clearPoints() {
		gameState.calculateNextStep();
	}
}
