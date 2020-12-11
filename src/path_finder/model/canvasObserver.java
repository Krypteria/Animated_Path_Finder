package path_finder.model;

import java.util.List;

import path_finder.misc.tCoord;

public interface canvasObserver {
	void updateSolution(List<tCoord> visitedNodes, List<tCoord> solutionPath);
}
