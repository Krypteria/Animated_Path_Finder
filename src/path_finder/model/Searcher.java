package path_finder.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import path_finder.misc.tCoord;

public class Searcher {

	private int height;
	private int width;
	
	private tCoord startPoint;
	private tCoord endPoint;
	
	private int [][] matrix;
	
	
	private Queue<tCoord> q;
	private int [][] dist;
	private boolean [][] visited;
	private tCoord[][] trackNodes;
	
	private List<tCoord> visitedNodes;
	private List<tCoord> solutionPath;
	
	private boolean founded;
	
	private int dF[] = {1,  0, -1,  0,  1,  1, -1, -1};
	private int dC[] = {0,  1,  0, -1,  1, -1,  1, -1};
	
	private canvasObserver observer;
	
	public void addValues(tCoord s, tCoord e, int [][] m, int h, int w) {
		this.startPoint = s;
		this.endPoint = e;
		this.matrix = m;
		this.height = h;
		this.width = w;
	}
	
	public void addObserver(canvasObserver co) {
		this.observer = co;
	}
	
	private boolean isOk(int nX, int nY) {
		return (0 <= nX && nX < this.height && 0 <= nY && nY < this.width);
	}
	
	private void initializeMatrixs() {
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.width; j++){
				this.dist[i][j] = 0;
				this.visited[i][j] = false;
			}
		}
	}
	
	void bfs(){ 
		
		reset();
		initializeMatrixs();		
		
		dist[this.startPoint.getX()][this.startPoint.getY()] = 0;
		visited[this.startPoint.getX()][this.startPoint.getY()] = true;

		q.add(this.startPoint);

		while(!q.isEmpty() && !this.founded){
			tCoord front = q.peek();	
			
			if(front != this.startPoint) {
				visitedNodes.add(front);				
			}
			
			q.poll();
			
			for(int i = 0; i < 8; i++) {
				
				int nX = front.getX() + dF[i];
				int nY = front.getY() + dC[i];
				
				if(isOk(nX, nY) && !visited[nX][nY] && matrix[nX][nY] != 1) {
					tCoord sig = new tCoord(nX, nY); 
					
					this.q.add(sig);
					this.trackNodes[sig.getX()][sig.getY()] = front;
					this.dist[nX][nY] = dist[front.getX()][front.getY()] + 1;
					this.visited[nX][nY] = true;
					
					if(sig.getX() == this.endPoint.getX() && sig.getY() == this.endPoint.getY()) {
						this.founded = true;
					}
				}
			}
		}
		
		//TODO eliminar
		if(founded) {
			System.out.println("Encontrado");
		}
		else {
			System.out.println("NO encontrado");
		}
		
		
		setupObserverUpdate();
	}
	
	private void setupObserverUpdate() {
		this.solutionPath = new ArrayList<tCoord>();
		obtainSolutionPath(solutionPath, trackNodes[this.endPoint.getX()][this.endPoint.getY()], trackNodes);
		
		this.observer.updateSolution(visitedNodes, solutionPath);
	}
	
	private void obtainSolutionPath(List<tCoord> solutionPath, tCoord s, tCoord trackNodes [][]) {
		if(!s.equals(this.startPoint)) {
			obtainSolutionPath(solutionPath, trackNodes[s.getX()][s.getY()], trackNodes);
			solutionPath.add(s);
		}
	}
		
	public void reset() {
		this.dist = new int [height][width];
		this.visited = new boolean [height][width];
		this.visitedNodes = new ArrayList<tCoord>();
		this.trackNodes = new tCoord [height][width];
		this.q = new LinkedList<>();
		this.founded = false;
	}
}
