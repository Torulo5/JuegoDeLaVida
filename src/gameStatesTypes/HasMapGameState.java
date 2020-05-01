package gameStatesTypes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HasMapGameState implements GameState {

	
	HashMap<Integer, List<Point>> pointsAlive = null;
	HashMap<Integer, List<Point>> nextPointsAlive = null;
	HashMap<Integer, List<Point>> pointsNeededToCheck = null;
	
	public HasMapGameState() {
		this.pointsAlive = new HashMap<Integer,List<Point>>();
		this.nextPointsAlive = new HashMap<Integer,List<Point>>();
		this.pointsNeededToCheck = new HashMap<Integer,List<Point>>();
	}
	
	@Override
	public void addNewPoint(Point alivePoint) {
		addPointToMap(alivePoint,this.pointsAlive);
		calculateNextPointsAlive();
	}

	@Override
	public void calculateNextStep() {
		this.pointsAlive.clear();
		this.pointsAlive = (HashMap<Integer, List<Point>>)nextPointsAlive.clone();
		this.pointsNeededToCheck.clear();
		this.nextPointsAlive.clear();
		this.calculateNextPointsAlive();
	}

	@Override
	public ArrayList<Point> getPoints() {
		ArrayList<Point> auxList = new ArrayList<Point>();
        for (Integer key : pointsAlive.keySet()) { 
        	auxList.addAll(pointsAlive.get(key));
        }
		return auxList;
	}

	@Override
	public ArrayList<Point> getPointsNeededToCheck() {
		ArrayList<Point> auxList = new ArrayList<Point>();
        for (Integer key : pointsNeededToCheck.keySet()) { 
        	auxList.addAll(pointsNeededToCheck.get(key));
        }
		return auxList;
	}

	@Override
	public ArrayList<Point> getNextPointsAlive() {
		ArrayList<Point> auxList = new ArrayList<Point>();
        for (Integer key : nextPointsAlive.keySet()) { 
        	auxList.addAll(nextPointsAlive.get(key));
        }
		return auxList;
	}

	@Override
	public void clearPoints() {
		// TODO Auto-generated method stub
		
	}
		
	public void calculateNextPointsAlive() {

		this.nextPointsAlive.clear();
		
		this.calculatePointsNeededToCheck();
        for (Integer key : pointsNeededToCheck.keySet()) { 
        	if(key == null)
        		break;
        	List<Point> points = pointsNeededToCheck.get(key);
        	for(Point point : points) {
        		if(neighborsState(point) == 3) {
        			addPointToMap(point,this.nextPointsAlive);
        		} else if(neighborsState(point) == 4) { //se mantiene estado anterior
        			if(checkHaspMapContainsPoint(point,this.pointsAlive))
        				addPointToMap(point,this.nextPointsAlive);
        		}
        	}
        }

        for (Integer key : pointsAlive.keySet()) { 
        	if(key == null)
        		break;
        	List<Point> points = pointsAlive.get(key);
        	for(Point point : points) {
        		if(neighborsState(point) == 3) {
        			addPointToMap(point,this.nextPointsAlive);
        		} else if(neighborsState(point) == 4) { //se mantiene estado anterior
        			if(checkHaspMapContainsPoint(point,this.pointsAlive))
    					addPointToMap(point,this.nextPointsAlive);
        		}
        	}
        }
		
	}
	

	public void calculatePointsNeededToCheck() {

		pointsNeededToCheck.clear();
		//iteramos los puntos vivos y guardamos sus vecinos
        for (Integer key : pointsAlive.keySet()) { 
        	List<Point> arrayToCopy = pointsAlive.get(key);
        	for(Point point : arrayToCopy) {
    			int x = point.x;
    			int y = point.y;
    			addPointToMap(new Point(x,y+1),this.pointsNeededToCheck);
    			addPointToMap(new Point(x,y-1),this.pointsNeededToCheck);
    			addPointToMap(new Point(x-1,y),this.pointsNeededToCheck);
    			addPointToMap(new Point(x+1,y),this.pointsNeededToCheck);
    			addPointToMap(new Point(x-1,y-1),this.pointsNeededToCheck);
    			addPointToMap(new Point(x+1,y+1),this.pointsNeededToCheck);
    			addPointToMap(new Point(x+1,y-1),this.pointsNeededToCheck);
    			addPointToMap(new Point(x-1,y+1),this.pointsNeededToCheck);
        	}
        }
	}

	private static void addPointToMap(Point point,Map<Integer, List<Point>> map) {
		Integer key = new Integer(point.x);
		List<Point> arrayOfPoints = map.get(key);
		if(arrayOfPoints == null) {
			List<Point> newArrayOfPoints =  new ArrayList<Point>();
			newArrayOfPoints.add(point);
			map.put(key, newArrayOfPoints);
		} else {
			if(!arrayOfPoints.contains(point))
				arrayOfPoints.add(point);	
		}
	}
	
	private static boolean checkHaspMapContainsPoint(Point point,Map<Integer, List<Point>> map) {
		int x = point.x;
		boolean flag = false;
		List<Point> auxList = map.get(x);
		if(auxList != null) {
			if(auxList.contains(point)) {
				flag = true;
			}
		}
		
		return flag;
	}
	
	private int neighborsState(Point point) {
		int x = point.x;
		int y = point.y;
		int neighborsAlive = 0;
		List<Point> auxList = pointsAlive.get(x);
		if(auxList != null) {
			if(auxList.contains(new Point(x,y+1))) {
				neighborsAlive++;
			}
			if(auxList.contains(new Point(x,y-1))) {
				neighborsAlive++;
			}
			if(auxList.contains(new Point(x,y))) {
				neighborsAlive++;
			}
		}
		auxList = pointsAlive.get(x-1);
		if(auxList != null) {
			if(auxList.contains(new Point(x-1,y))) {
				neighborsAlive++;
			}
			if(auxList.contains(new Point(x-1,y-1))) {
				neighborsAlive++;
			}
			if(auxList.contains(new Point(x-1,y+1))) {
				neighborsAlive++;
			}
		}
		auxList = pointsAlive.get(x+1);
		if(auxList != null) {
			if(auxList.contains(new Point(x+1,y+1))) {
				neighborsAlive++;
			}
			if(auxList.contains(new Point(x+1,y-1))) {
				neighborsAlive++;
			}
			if(auxList.contains(new Point(x+1,y))) {
				neighborsAlive++;
			}
		}
		return neighborsAlive;
	}
	
}
