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
	
	public GameModel(int type) {
		switch (type) {
			case 1:
				gameState = new ArrayGameState();
				break;
			case 2:
				gameState = new HasMapGameState();
				break;
		}
	}

	public synchronized ArrayList<Point> getPoints() {
		return gameState.getPoints();
	}

	public synchronized ArrayList<Point> getPointsNeededToCheck() {
		return gameState.getPointsNeededToCheck();
	}

	public synchronized ArrayList<Point> getNextPointsAlive() {
		return gameState.getNextPointsAlive();
	}

	public synchronized void addNewPoint(Point alivePoint) {
		this.gameState.addNewPoint(alivePoint);
	}
	
	public synchronized void setNextStatus() {
		this.steps++;
		this.gameState.calculateNextStep();
	}
	
	public synchronized void clearPoints() {
		this.steps = 0;
		this.gameState.clearPoints();
	}
	
	public synchronized void deletePoint(Point pointTodelete) {
		this.gameState.deletePoint(pointTodelete);
	}

	public synchronized int getSteps() {
		return steps;
	}

}
