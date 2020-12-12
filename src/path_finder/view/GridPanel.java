package path_finder.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import path_finder.controller.Controller;
import path_finder.misc.tCoord;
import path_finder.model.canvasObserver;


@SuppressWarnings("serial")
public class GridPanel extends JPanel implements canvasObserver, ActionListener{

	private static final int lineThickness = 20;
	private static final int height = 40;
	private static final int width = 40;
	
	private static final int BLANK = 1;
	private static final int WALL = 2;
	private static final int IMPORTANT_POINT = 3;

	private static final String START = "StartPoint";
	private static final String END = "EndPoint";
	
	private Controller ctrl;
	
	private List<Rectangle> rectanglesList;
	private List<Rectangle> filledList;
	private List<Rectangle> visitedPattern;
	private List<Rectangle> solutionPath;
	
	private List<tCoord> visitedNodes;
	private List<tCoord> solutionNodes;
	
	private HashMap<Rectangle, tCoord> mapCoord;
	private HashMap<Integer, HashMap<Integer,Rectangle>> mapRect;
	
	private Rectangle startPoint;
	private Rectangle endPoint;
	
	private int count;
	
	private boolean paintSolution;
	
	private Timer timer;
	
	//used for peek
	private Rectangle last = null;
	
	//mode control
	private boolean paintMode, removeMode;
	
	//solution painting
	
	private int delay;
	private int solutionGraphicCount = 0;
	
	private final Color lineColor = Color.black;
	private final Color wall = Color.black;
	private final float[] start = Color.RGBtoHSB(103, 45, 105, null);
	private final float[] end = Color.RGBtoHSB(45, 79, 105, null);
	private final float[] visitedColor = Color.RGBtoHSB(175, 54, 54, null);
	private final float[] peekColor = Color.RGBtoHSB(83, 29, 29, null);
	
	
	public GridPanel(Controller c) {
		this.ctrl = c;
		c.addObserver(this);
		initGUI();
	}
	
	private void initGUI() {
		this.rectanglesList = new ArrayList<Rectangle>();
		this.filledList = new ArrayList<Rectangle>();
		this.visitedPattern = new ArrayList<Rectangle>();
		this.solutionPath = new ArrayList<Rectangle>();
		this.visitedNodes = new ArrayList<tCoord>();
		this.solutionNodes = new ArrayList<tCoord>();
		this.mapCoord = new HashMap<Rectangle, tCoord>();
		this.mapRect = new HashMap<Integer, HashMap<Integer, Rectangle>>();
		this.paintSolution = false;
		this.startPoint = null;
		this.endPoint = null;
		this.count = 0;

		addMouseListener();
		
		for(int i = 0; i < height; i++) {
			HashMap<Integer, Rectangle> aux = new HashMap<Integer, Rectangle>();
			for(int j = 0; j < width; j++) {
				Rectangle rect = new Rectangle(i * lineThickness, j * lineThickness, lineThickness, lineThickness);
				tCoord c = new tCoord(i, j);
				
				rectanglesList.add(rect);
				mapCoord.put(rect, c);
				aux.put(j, rect);
				mapRect.put(i, aux);
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    
		for(Rectangle r: this.rectanglesList) {
			g.setColor(lineColor);
			g.draw(r);
		}
		for(Rectangle r: this.filledList) {
			g.setColor(wall);
			g.fill(r);
		}
		if(this.startPoint != null) {
			g.setColor(Color.getHSBColor(start[0], start[1], start[2]));
			g.fill(startPoint);
		}
		if(this.endPoint != null) {
			g.setColor(Color.getHSBColor(end[0], end[1], end[2]));
			g.fill(endPoint);
		}
		if(!this.visitedPattern.isEmpty()) {
			for(Rectangle r : this.visitedPattern) {
				if(last != null) {
					g.setColor(Color.getHSBColor(visitedColor[0], visitedColor[1], visitedColor[2]));
					g.fill(last);
				}
				g.setColor(Color.getHSBColor(peekColor[0], peekColor[1], peekColor[2]));
				g.fill(r);
				last = r;
			}
		}
		if(!this.solutionPath.isEmpty()) {
			for(Rectangle r : this.solutionPath) {
				g.setColor(Color.getHSBColor(peekColor[0], peekColor[1], peekColor[2]));
				g.fill(r);
			}
		}
	}
	
	private void drawMode(MouseEvent e) {
		for(Rectangle r: rectanglesList) {
			if(r.contains(e.getPoint())) {
				if(!filledList.contains(r)) {
					filledList.add(r);
					updateMatrixModel(r, WALL);
				}
			}
		}	
		paintMode = true;
		repaint();
	}
	
	private void removeMode(MouseEvent e) {
		for(Rectangle r: rectanglesList) {
			if(r.contains(e.getPoint())) {
				if(filledList.contains(r)) {
					filledList.remove(r);
					updateMatrixModel(r, BLANK);
				}
			}
		}	
		removeMode = true;
		repaint();
	}
	
	private void dragComprobation(MouseEvent e) {
		for(Rectangle r: rectanglesList) {
			if(r.contains(e.getPoint())) {
				if(paintMode) {
					if(!filledList.contains(r)) {
						filledList.add(r);	
						updateMatrixModel(r, WALL);
					}
				}
				else if(removeMode) {
					if(filledList.contains(r)) {
						filledList.remove(r);
						updateMatrixModel(r, BLANK);
					}
				}
				repaint();
			}
		}	
	}
	
	private void releasedComprobation(MouseEvent e) {
		paintMode = false;
		removeMode = false;
	}
	
	private void setPointsMode(MouseEvent e) {
		for(Rectangle r: rectanglesList) {
			if(r.contains(e.getPoint())) {
				if(!filledList.contains(r)) {
					if(count % 2 == 0) {
						this.startPoint = r;
					}
					else {
						this.endPoint = r;
					}
					count = (count + 1) % 2;
					updateMatrixModel(r, IMPORTANT_POINT);
				}
				else {
					System.out.println("No puedes poner el punto en un muro");
				}
			}
		}	
		repaint();
	}
	
	private void updateMatrixModel(Rectangle r, int mode) {
		
		tCoord coord = mapCoord.get(r);
		
		if(mode == BLANK) {
			this.ctrl.removeWall(coord.getY(), coord.getX());						
		}
		else if(mode == WALL) {
			this.ctrl.addWall(coord.getY(), coord.getX());						
		}
		else if(mode == IMPORTANT_POINT)
			if(r.equals(this.startPoint)) {
				this.ctrl.addPoint(coord.getY(), coord.getX(), START);				
			}
			else {
				this.ctrl.addPoint(coord.getY(), coord.getX(), END);
			}	
	}
	
	@Override
	public void updateSolution(List<tCoord> visitedNodes, List<tCoord> solutionPath) { 
		this.visitedNodes = visitedNodes;
		this.solutionNodes = solutionPath;
		this.timer = new Timer(this.delay, this);
		this.timer.start();
	}
	
	private void addMouseListener() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					drawMode(e);
				}
				else if(e.getButton() == MouseEvent.BUTTON2) {
					setPointsMode(e);
				}
				else if(e.getButton() == MouseEvent.BUTTON3) {
					removeMode(e);
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				releasedComprobation(e);
			}
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				dragComprobation(e);
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(!paintSolution) {			
			if(solutionGraphicCount < this.visitedNodes.size()) {
				int x = this.visitedNodes.get(solutionGraphicCount).getX(); 
				int y = this.visitedNodes.get(solutionGraphicCount).getY();
				
				if(mapRect.get(x).get(y) != this.endPoint) {
					this.visitedPattern.add(mapRect.get(y).get(x));
					solutionGraphicCount++;
					repaint();
				}
			}
			else {
				this.solutionGraphicCount = 0;
				this.paintSolution = true;
			}			
		}
		else {
			if(solutionGraphicCount < this.solutionNodes.size()) {
				int x = this.solutionNodes.get(solutionGraphicCount).getX(); 
				int y = this.solutionNodes.get(solutionGraphicCount).getY();
				
				if(mapRect.get(x).get(y) != this.endPoint) {
					this.solutionPath.add(mapRect.get(y).get(x));
					solutionGraphicCount++;
					repaint();
				}
			}
			else {
				this.timer.stop();
			}
		}
	}
	
	public void reset(int w) {
		if(w != 0) 
			this.filledList.clear();	
		
		this.visitedPattern.clear();
		this.visitedNodes.clear();
		this.solutionPath.clear();
		this.solutionNodes.clear();
		this.last = null;
		this.solutionGraphicCount = 0;
		this.paintSolution = false;
		this.timer.stop();
		
		repaint();
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
}
