package gameStatesTypes;

import java.awt.Point;
import java.util.ArrayList;

public class ArrayGameState implements GameState {

	// puntos vivos
	private ArrayList<Point> pointsAlive = null;
	private ArrayList<Point> nextPointsAlive = null;
	private ArrayList<Point> pointsNeededToCheck = null;
	
	private final boolean DEBUG = false;
	
	public ArrayGameState() {
		this.pointsAlive = new ArrayList<Point>();
		this.nextPointsAlive = new ArrayList<Point>();
		this.pointsNeededToCheck = new ArrayList<Point>();
	}
	
	@Override
	public void addNewPoint(Point alivePoint) {
		if (!this.pointsAlive.contains(alivePoint)) {
			this.pointsAlive.add(alivePoint);

			if (DEBUG) {
				System.out.println("POINT ADDED -> X: " + alivePoint.getX() + " Y: " + alivePoint.getY());
				System.out.println("TOTAL POINTS:" + pointsAlive.size());
			}
			
			this.calculateNextPointsAlive();
		}
	
	}

	@Override
	public void calculateNextStep() {
		pointsAlive.clear();
		for(Point point : nextPointsAlive) {
			pointsAlive.add(point);
		}
		pointsNeededToCheck.clear();
		nextPointsAlive.clear();
		this.calculateNextPointsAlive();
	}
	

	private void calculateNextPointsAlive() {

		nextPointsAlive.clear();
		calculatePointsNeededToCheck();
		for(Point point : pointsNeededToCheck) {
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
					if(!nextPointsAlive.contains(point))
						nextPointsAlive.add(point);
				}
			}
		}
		
	}
	
	private void calculatePointsNeededToCheck() {

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
	
	@Override
	public void clearPoints() {
		pointsAlive.clear();
		pointsNeededToCheck.clear();
		nextPointsAlive.clear();
	}

	@Override
	public ArrayList<Point> getPoints() {
		return this.pointsAlive;
	}

	@Override
	public ArrayList<Point> getPointsNeededToCheck() {
		return this.pointsNeededToCheck;
	}

	@Override
	public ArrayList<Point> getNextPointsAlive() {
		return this.nextPointsAlive;
	}

	@Override
	public void deletePoint(Point alivePoint) {
		pointsAlive.remove(alivePoint);
		this.calculateNextPointsAlive();
	}
	
}
