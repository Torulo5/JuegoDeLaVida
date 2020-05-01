package models;
import java.awt.Point;
import java.util.ArrayList;

public class GameModel {

	// puntos vivos
	private ArrayList<Point> pointsAlive;
	private ArrayList<Point> nextPointsAlive;
	private ArrayList<Point> pointsNeededToCheck;

	private final boolean DEBUG = false;
	
	public GameModel() {
		pointsAlive = new ArrayList<Point>();
		nextPointsAlive = new ArrayList<Point>();
		pointsNeededToCheck = new ArrayList<Point>();
	}

	public ArrayList<Point> getPoints() {
		return pointsAlive;
	}

	public void setAlivePoint(Point alivePoint) {

		if (!pointsAlive.contains(alivePoint)) {
			pointsAlive.add(alivePoint);

			if (DEBUG) {
				System.out.println("POINT ADDED -> X: " + alivePoint.getX() + " Y: " + alivePoint.getY());
				System.out.println("TOTAL POINTS:" + pointsAlive.size());
			}
		}
	}

	public void clearPoints() {
		pointsAlive.clear();
		pointsNeededToCheck.clear();
		nextPointsAlive.clear();
	}

	public void setNextStatus() {
		
		getNextPointsAlive();
		
		pointsAlive.clear();
		for(Point point : nextPointsAlive) {
			pointsAlive.add(point);
		}
	}
	
	public ArrayList<Point> getPointsNeededToCheck() {

		pointsNeededToCheck.clear();
		//iteramos los puntos vivos y guardamos sus vecinos
		for(Point point : pointsAlive) {
			int x = point.x;
			int y = point.y;
			if(!pointsNeededToCheck.contains(new Point(x,y+1)))
				pointsNeededToCheck.add(new Point(x,y+1));
			if(!pointsNeededToCheck.contains(new Point(x,y-1)))
				pointsNeededToCheck.add(new Point(x,y-1));
			if(!pointsNeededToCheck.contains(new Point(x-1,y)))
				pointsNeededToCheck.add(new Point(x-1,y));
			if(!pointsNeededToCheck.contains(new Point(x+1,y)))
				pointsNeededToCheck.add(new Point(x+1,y));
			if(!pointsNeededToCheck.contains(new Point(x-1,y-1)))
				pointsNeededToCheck.add(new Point(x-1,y-1));
			if(!pointsNeededToCheck.contains(new Point(x+1,y+1)))
				pointsNeededToCheck.add(new Point(x+1,y+1));
			if(!pointsNeededToCheck.contains(new Point(x+1,y-1)))
				pointsNeededToCheck.add(new Point(x+1,y-1));
			if(!pointsNeededToCheck.contains(new Point(x-1,y+1)))
				pointsNeededToCheck.add(new Point(x-1,y+1));
		}

		return pointsNeededToCheck;
	}
	
	
	public ArrayList<Point> getNextPointsAlive() {

		nextPointsAlive.clear();
		ArrayList<Point> pointsToCheck = getPointsNeededToCheck();
		for(Point point : pointsToCheck) {
			if(neighborsState(point) == 3) {
				if(!nextPointsAlive.contains(point))
					nextPointsAlive.add(point);
			} else if(neighborsState(point) == 4) {
				if(pointsAlive.contains(point)) {
					nextPointsAlive.add(point);
				}
			}
		}
		
		for(Point point : pointsAlive) {
			if(neighborsState(point) == 3) {
				if(!nextPointsAlive.contains(point))
					nextPointsAlive.add(point);
			} else if(neighborsState(point) == 4) {
				if(pointsAlive.contains(point)) {
					nextPointsAlive.add(point);
				}
			}
		}
		
		return nextPointsAlive;
	}
	
	private int neighborsState(Point point) {
		int x = point.x;
		int y = point.y;
		int neighborsAlive = 0;
		
		if(pointsAlive.contains(new Point(x,y+1))) {
			neighborsAlive++;
		}
		if(pointsAlive.contains(new Point(x,y-1))) {
			neighborsAlive++;
		}
		if(pointsAlive.contains(new Point(x-1,y))) {
			neighborsAlive++;
		}
		if(pointsAlive.contains(new Point(x+1,y))) {
			neighborsAlive++;
		}
		if(pointsAlive.contains(new Point(x-1,y-1))) {
			neighborsAlive++;
		}
		if(pointsAlive.contains(new Point(x+1,y+1))) {
			neighborsAlive++;
		}
		if(pointsAlive.contains(new Point(x+1,y-1))) {
			neighborsAlive++;
		}
		if(pointsAlive.contains(new Point(x-1,y+1))) {
			neighborsAlive++;
		}
		if(pointsAlive.contains(new Point(x,y))) {
			neighborsAlive++;
		}
		
	
		return neighborsAlive;
	}
}
