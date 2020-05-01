package gameStatesTypes;

import java.awt.Point;
import java.util.ArrayList;

public interface GameState {
	public void addNewPoint(Point alivePoint);
	public void calculateNextStep();
	public ArrayList<Point> getPoints();
	public ArrayList<Point> getPointsNeededToCheck();
	public ArrayList<Point> getNextPointsAlive();
	public void clearPoints();
}
