package model;

import java.util.ArrayList;

public class Nonet {
	private char[][] values;
	private Candidates[][] candidates;
	
	public Nonet() {
		this.values = new char[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.values[i][j] = 0;
			}
		}
		this.candidates = new Candidates[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.candidates[i][j] = new Candidates();
			}
		}
	}

	// Add a candidate to a cell
	public void addCandidate(int x, int y, char c) {
		candidates[x][y].add(c);
	}
	
	public void addCandidates(int x, int y, char[] c) {
		candidates[x][y].add(c);
	}
	
	// Remove a candidate from a cell
	public void removeCandidate(int x, int y, char c) {
		candidates[x][y].remove(c);
	}

	public boolean hasCandidate(int x, int y, char c) {
		return candidates[x][y].hasValue(c);
	}

	// Check if a number has only one possible position and return its location, or null if not
	public Coords getSingleCandidate(char c) {
		int count = 0;
		Coords single = null;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (hasCandidate(i,j,c)) {
					single = new Coords(i,j);
					count++;
				}
			}
		}
		if (count == 1) {
			return single;
		} else {
			return null;
		}
	}
	
	// Remove a candidate from the nonet
	public void removeCandidate(char c) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				candidates[i][j].remove(c);
			}
		}
	}
	
	// Remove a candidate from a row or column
	public void removeCandidate(int n, char c, boolean isRow) {
		for (int i = 0; i < 3; i++) {
			if (isRow)
				candidates[i][n].remove(c);
			else
				candidates[n][i].remove(c);
		}
	}
	
	public void removeCandidates(int x, int y) {
		candidates[x][y].removeAll();
	}
	
	public char[] getCandidates(int x, int y) {
		return candidates[x][y].getValues();
	}
	
	public ArrayList<Coords> getCandidateCells(char c) {
		ArrayList<Coords> returnVal = new ArrayList<Coords>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (candidates[i][j].hasValue(c)) {
					Coords coord = new Coords(i,j);
					returnVal.add(coord);
				}
			}
		}
		return returnVal;
	}
	
	public char getValue(int x, int y) {
		return values[x][y];
	}
	
	public void setValue(int x, int y, char c) {
		values[x][y] = c;
	}
	
	public boolean hasValue(char c) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (values[i][j] == c) return true;
			}
		}
		return false;
	}
	
	public ArrayList<Coords> getEmptyCells() {
		ArrayList<Coords> returnVal = new ArrayList<Coords>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (values[i][j] == 0) {
					Coords thisVal = new Coords(i,j);
					returnVal.add(thisVal);
				}
			}
		}
		return returnVal;
	}
	
	public String getMissingValues() {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < 10; i++) {
			char thisChar = Integer.toString(i).charAt(0);
			if (!hasValue(thisChar)) {
				sb.append(thisChar);
			}
		}
		return sb.toString();
	}
}
