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

import java.util.ArrayList;
import java.util.Iterator;

import model.Constants;
import model.Coords;
import model.Grid;
import solver.AbstractStep;

public class FindHiddenSingles extends AbstractStep {
	public FindHiddenSingles(Grid grid) {
		this.grid =  grid;
	}
	
	public String getName() {
		return "Hidden Singles";
	}
	
	public int perform() {
		int found = 0;
		boolean foundLastPass = true;
		
		while (foundLastPass) {
			foundLastPass = false;

			for (Constants.SetType setType : Constants.SetType.values()) {
				for (int i = 0; i < 9; i++) {
					ArrayList<Coords> cellSet = Grid.makeCellSet(setType, i);
					int foundHere = findHiddenSingles(setType, i, cellSet);
					if (foundHere > 0) {
						found += foundHere;
						foundLastPass = true;
					}
				}
			}
		}
		
		return found;
	}
	
	private int findHiddenSingles(Constants.SetType setType, int index, ArrayList<Coords> cellSet) {
		int found = 0;

		// Remove all cells with a value
		Iterator<Coords> itr = cellSet.iterator();
		while (itr.hasNext()) {
			Coords cell = itr.next();
			if (grid.hasValue(cell.getX(), cell.getY())) itr.remove();
		}
		
		if (cellSet.size() == 0) return 0;

		// For each number, check if it is only a candidate in one cell
		for (int k = 1; k < 10; k++) {
			char thisChar = Integer.toString(k).charAt(0);
			Coords location = null;
			int count = 0;
			for (Coords cell : cellSet) {
				char[] candidates = grid.getCandidates(cell.getX(), cell.getY());
				if (candidates == null) continue;
				String str = new String(candidates);
				if (str.indexOf(thisChar) == -1) continue;
				count++;
				location = cell;
			}
			if (count == 1) { // hidden single
				found++;
				grid.setValue(location.getX(), location.getY(), thisChar);
				grid.removeCandidates(location.getX(), location.getY(), thisChar);
			}
		}
		
		return found;
	}
}
