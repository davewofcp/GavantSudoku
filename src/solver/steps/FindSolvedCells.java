package solver.steps;

import model.Grid;
import model.Nonet;
import solver.AbstractStep;

public class FindSolvedCells extends AbstractStep {
	public FindSolvedCells(Grid grid) {
		this.grid = grid;
	}
	
	public String getName() {
		return "Solved Cells";
	}
	
	public int perform() {
		if (!enabled) return 0;
		
		int found = 0;
		boolean foundLastPass = true;
		
		while (foundLastPass) {
			foundLastPass = false;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					Nonet thisNonet = grid.getNonet(i, j);
					for (int k = 0; k < 3; k++) {
						for (int l = 0; l < 3; l++) {
							char[] candidates = thisNonet.getCandidates(k, l);
							if (candidates != null && candidates.length == 1) {
								foundLastPass = true;
								thisNonet.setValue(k, l, candidates[0]);
								found++;
								grid.removeCandidates((i * 3) + k, (j * 3) + l, candidates[0]);
							}
						}
					}
				}
			}
		}
		
		return found;
	}
}
