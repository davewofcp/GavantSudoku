package solver.steps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import model.Coords;
import model.Grid;
import solver.AbstractStep;
import solver.Constants;

public class FindNakedPairs extends AbstractStep {
	private HashMap<Constants.SetType,HashMap<Integer,ArrayList<Coords>>> found;
	
	public FindNakedPairs(Grid grid) {
		this.grid = grid;
		found = new HashMap<Constants.SetType,HashMap<Integer,ArrayList<Coords>>>();
	}

	public int perform() {
		if (!enabled) return 0;
		
		int found = 0;
		boolean foundLastPass = true;
		
		while (foundLastPass) {
			foundLastPass = false;
		
			ArrayList<Coords> cellSet;
			HashMap<String,Coords[]> pairs;
			
			for (Constants.SetType setType : Constants.SetType.values()) {
				for (int i = 0; i < 9; i++) {
					cellSet = Grid.makeCellSet(setType,i);
					pairs = findPairs(cellSet);
					if (pairs.size() > 0) {
						int unique = checkNew(setType, i, pairs);
						if (unique > 0) {
							found += unique;
							foundLastPass = true;
							pruneCandidates(setType,pairs);
						}
					}
				}
			}
		}
		
		return found;
	}

	private int checkNew(Constants.SetType setType, int index, HashMap<String,Coords[]> pairs) {
		int unique = 0;
		
		HashMap<Integer,ArrayList<Coords>> found = this.found.get(setType);
		if (found == null) {
			this.found.put(setType, new HashMap<Integer,ArrayList<Coords>>());
			return pairs.size();
		}
		ArrayList<Coords> foundHere = found.get(index);
		if (foundHere == null) {
			found.put(index, new ArrayList<Coords>());
			foundHere = found.get(index);
		}
		
		nextPair:
		for (Entry<String,Coords[]> pair : pairs.entrySet()) {
			Coords[] cells = pair.getValue();
			for (Coords cell : cells) {
				if (!Coords.inArray(cell.getX(), cell.getY(), foundHere)) {
					unique++;
					foundHere.add(cells[0]);
					foundHere.add(cells[1]);
					continue nextPair;
				}
			}
		}
		
		return unique;
	}
	
	// Takes a collection of cells from Grid and returns a map of candidate triples and the locations of the three cells
	private HashMap<String,Coords[]> findPairs(ArrayList<Coords> cellSet) {
		HashMap<String,Coords[]> returnVal = new HashMap<String,Coords[]>();
		// Get missing numbers from the set
		String missing = "123456789";
		for (Coords cell : cellSet) {
			char thisChar = grid.getValue(cell.getX(), cell.getY());
			if (thisChar == 0) continue;
			int chrPos = missing.indexOf(thisChar);
			missing = missing.substring(0, chrPos) + missing.substring(chrPos + 1,missing.length());
		}
		
		if (missing.length() <= 3) return returnVal;
		
		ArrayList<char[]> combos = permutate(missing.toCharArray());
		
		for (char[] combo : combos) {
			int cellsFound = 0;
			ArrayList<Coords> coordsFound = new ArrayList<Coords>();
			String str = new String(combo);

			nextCell:
			for (Coords cell : cellSet) {
				char[] cellCandidates = grid.getCandidates(cell.getX(), cell.getY());
				if (cellCandidates == null) continue;
				for (char candidate : cellCandidates) {
					if (str.indexOf(candidate) == -1) { // found number not in combo, skip cell
						continue nextCell;
					}
				}
				cellsFound++;
				coordsFound.add(cell);
			}
						
			if (cellsFound == 2) { // pair found
				returnVal.put(new String(combo), new Coords[]{coordsFound.get(0),coordsFound.get(1)});
			}
		}
		
		return returnVal;
	}
	
	private void pruneCandidates(Constants.SetType setType, HashMap<String,Coords[]> pairsMap) {
		switch (setType) {
			case NONET:
				for (Entry<String,Coords[]> pair : pairsMap.entrySet()) {
					String values = pair.getKey();
					Coords[] coords = pair.getValue();
					int nx = coords[0].getX() / 3;
					int ny = coords[0].getY() / 3;
					for (int cellX = 0; cellX < 3; cellX++) {
						for (int cellY = 0; cellY < 3; cellY++) {
							if (Coords.inArray((nx * 3) + cellX, (ny * 3) + cellY, coords)) continue; // skip pair cells
							for (int i = 0; i < values.length(); i++) {
								grid.getNonet(nx,ny).removeCandidate(cellX,cellY,values.charAt(i));
							}
						}
					}
					
					// Horizontally aligned
					if (coords[0].getY() == coords[1].getY()) {
						for (int x = 0; x < 9; x++) {
							if (x == coords[0].getX() || x == coords[1].getX()) continue;
							for (int i = 0; i < values.length(); i++) {
								grid.removeCandidate(x, coords[0].getY(), values.charAt(i));
							}
						}
					}
					
					// Vertically aligned
					if (coords[0].getX() == coords[1].getX()) {
						for (int y = 0; y < 9; y++) {
							if (y == coords[0].getY() || y == coords[1].getY()) continue;
							for (int i = 0; i < values.length(); i++) {
								grid.removeCandidate(coords[0].getX(), y, values.charAt(i));
							}
						}
					}
				}
				break;
			case COLUMN:
				for (Entry<String,Coords[]> pair : pairsMap.entrySet()) {
					String values = pair.getKey();
					Coords[] coords = pair.getValue();
					int cx = coords[0].getX();
					for (int cy = 0; cy < 9; cy++) {
						if (Coords.inArray(cx, cy, coords)) continue; // skip pair cells
						for (int i = 0; i < values.length(); i++) {
							grid.removeCandidate(cx, cy, values.charAt(i));
						}
					}
				}
				break;
			case ROW:
				for (Entry<String,Coords[]> pair : pairsMap.entrySet()) {
					String values = pair.getKey();
					Coords[] coords = pair.getValue();
					int ry = coords[0].getY();
					for (int rx = 0; rx < 9; rx++) {
						if (Coords.inArray(rx, ry, coords)) continue; // skip pair cells
						for (int i = 0; i < values.length(); i++) {
							grid.removeCandidate(rx, ry, values.charAt(i));
						}
					}
				}
				break;
		}
	}
	
	private ArrayList<char[]> permutate(char[] candidates) {
		if (candidates.length < 2) return new ArrayList<char[]>();
		ArrayList<char[]> returnVal = new ArrayList<char[]>();
		for (int i = 0; i < candidates.length - 1; i++) {
			for (int j = i + 1; j < candidates.length; j++) {
				char[] thisCA = {candidates[i],candidates[j]};
				returnVal.add(thisCA);
			}
		}
		return returnVal;
	}

	public String getName() {
		return "Naked Pairs";
	}
}
