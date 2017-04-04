package solver.steps;

import model.Grid;
import solver.AbstractStep;

public class FindSolvedCells extends AbstractStep {
	public FindSolvedCells(Grid grid) {
		this.grid = grid;
	}
	
	public String getName() {
		return "Solved Cells";
	}
	
	public int perform() {
		int found = 0;
		boolean foundLastPass = true;
		
		while (foundLastPass) {
			foundLastPass = false;
			
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++) {
					char[] candidates = grid.getCandidates(x, y);
					if (candidates != null && candidates.length == 1) {
						foundLastPass = true;
						found++;
						grid.setValue(x, y, candidates[0]);
						grid.removeCandidates(x, y, candidates[0]);
					}
				}
			}
		}
		
		return found;
	}
}
