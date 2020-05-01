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
		this.gameState.addNewPoint(alivePoint);
	}
	
	public void setNextStatus() {
		this.steps++;
		this.gameState.calculateNextStep();
	}
	
	public void clearPoints() {
		this.steps = 0;
		this.gameState.clearPoints();
	}

	public int getSteps() {
		return steps;
	}

}
