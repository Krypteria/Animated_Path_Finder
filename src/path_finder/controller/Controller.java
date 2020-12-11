package path_finder.controller;

import path_finder.model.Path_finder;
import path_finder.model.canvasObserver;

public class Controller {

	private Path_finder pathfinder;
	
	public Controller() {
		this.pathfinder = new Path_finder();
	}
	
	public void addWall(int i, int j) {
		this.pathfinder.addWall(i, j);
	}
	
	public void removeWall(int i, int j) {
		this.pathfinder.removeWall(i, j);
	}
	
	public void addPoint(int i, int j, String point_type) {
		this.pathfinder.addPoint(i, j, point_type);
	}
	
	public void start() {
		this.pathfinder.start();
	}
	
	public void reset() {
		this.pathfinder.reset();
	}
	
	public void addObserver(canvasObserver co) {
		this.pathfinder.addObserver(co);
	}
}