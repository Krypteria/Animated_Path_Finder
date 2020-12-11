package path_finder.model;

import path_finder.misc.tCoord;

public class Path_finder {

	private static final int height = 45;
	private static final int width = 45;
	
	private static final String START = "StartPoint";
	private static final String END = "EndPoint";
	
	private tCoord startPoint;
	private tCoord endPoint;
	
	private int [][] matrix;
	
	private Searcher search;
	
	public Path_finder() {
		this.matrix = new int[height][width];
		this.startPoint = new tCoord(-1, -1);
		this.endPoint = new tCoord(-1, -1);;
		this.search = new Searcher();
		initializeMatrix();
	}
	
	private void initializeMatrix() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				matrix[i][j] = 0;
			}
		}
	}
	
	public void addObserver(canvasObserver co) {
		this.search.addObserver(co);
	}
	
	public void addWall(int i, int j) {
		matrix[i][j] = 1;
	}
	
	public void removeWall(int i, int j) {
		matrix[i][j] = 0;
	}
	
	
	//mandarle mensaje al canvas para que pinte el punto, me ahorro tambien el contador ese pocho
	public void addPoint(int i, int j, String point_type) {
		if(point_type.equals(START) && !overwritePoint(i, j, this.endPoint)) {
			checkPoint(this.startPoint);
			this.startPoint.setCoordinates(i, j);
			matrix[i][j] = 2;
		}
		else if(point_type.equals(END) && !overwritePoint(i, j, this.startPoint)){
			checkPoint(this.endPoint);
			this.endPoint.setCoordinates(i, j);
			matrix[i][j] = 3;
		}
	}
	
	private void checkPoint(tCoord point) {
		if(point.getX() != -1) {
			matrix[point.getX()][point.getY()] = 0;
		}
	}
	
	private boolean overwritePoint(int i, int j, tCoord point) {
		return this.endPoint.getX() == i && this.endPoint.getY() == j;
	}
	
	//start va a pasar a varios botones
	public void start() {
		if(this.startPoint.getX() != -1 && this.endPoint.getX() != -1) {
			search.addValues(this.startPoint, this.endPoint, this.matrix, Path_finder.height, Path_finder.width);
			search.bfs();			
		}
		else {
			System.out.println("Define los puntos de comienzo y final");
		}
	}
	
	public void reset() {
		this.initializeMatrix();
		this.search.reset();
	}
}
