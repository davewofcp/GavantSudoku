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

package solver;

import model.Grid;
import model.Nonet;

public class PlaceCandidates {
	private Grid grid;
	
	public PlaceCandidates(Grid grid) {
		this.grid = grid;
	}
	
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	
	public void place() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Nonet thisNonet = grid.getNonet(i, j);
				String missing = thisNonet.getMissingValues();
				if (missing.length() == 0) continue;
				
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						char thisChar = thisNonet.getValue(k, l);
						if (thisChar != 0) continue;
						
						int gridX = (i * 3) + k;
						int gridY = (j * 3) + l;
						
						String candidates = missing;
						for (int m = 0; m < missing.length(); m++) {
							char thisCandidate = missing.charAt(m);
							if (grid.hasValueInColumn(gridX, gridY, thisCandidate)
								|| grid.hasValueInRow(gridX, gridY, thisCandidate)) {
								candidates = removeChar(candidates, thisCandidate);
							}
						}
						
						thisNonet.removeCandidates(k, l);
						thisNonet.addCandidates(k, l, candidates.toCharArray());
					}
				}
				
			}
		}
	}
	
	public String removeChar(String str, char c) {
		int chrPos = str.indexOf(c);
		if (chrPos < 0) return str;
		return str.substring(0, chrPos) + str.substring(chrPos + 1,str.length());
	}
}
