/*
 * ------------------------------------------------------------------------
 *  Copyright (c) 2017 Dave Wollyung, Clifton Park, NY, United States
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License, Version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses>.
 */

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
