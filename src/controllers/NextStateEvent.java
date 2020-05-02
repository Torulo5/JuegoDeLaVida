package controllers;

import java.awt.Point;
import java.util.List;
import java.util.Map;

public interface NextStateEvent {
	public void nextStateEvent(Map<String, List<Point>> newState, int stept, int points, long timeElapsedToCalculateStep);
	public void newPointEvent(Map<String, List<Point>> newState, int points);
}
