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

package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Grid {
	private Nonet[][] nonets;
	
	public Grid() {
		nonets = new Nonet[3][3];
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				nonets[i][j] = new Nonet();
			}
		}
	}
	
	public Nonet getNonet(int x, int y) {
		return nonets[x][y];
	}
	
	public void readFromFile(String filename) throws IOException {
		File file = new File(filename);
		try (FileReader fileReader = new FileReader(file);
			 BufferedReader bufferedReader = new BufferedReader(fileReader)){
			String line;
			int row = 0;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() < 9) throw new IllegalArgumentException("Invalid sudoku input file.");
				for (int col = 0; col < 9; col++) {
					char value = line.charAt(col);
					if ("123456789X".indexOf(value) == -1) throw new IllegalArgumentException("Invalid sudoku input file.");
					setValue(col,row,value == 'X' ? 0 : value);
				}
				row++;
			}
			if (row < 8) throw new IllegalArgumentException("Invalid sudoku input file.");
		}
	}
	
	public void writeToFile(String filename) throws IOException {
		File file = new File(filename);
		try (FileWriter fileWriter = new FileWriter(file)) {
			// write grid
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					char thisChar = nonets[j / 3][i / 3].getValue(j - ((j / 3) * 3), i - ((i / 3) * 3));
					fileWriter.write(thisChar == 0 ? 'X' : thisChar);
				}
				fileWriter.write('\r');
				fileWriter.write('\n');
			}

			if (!isSolved()) {
				fileWriter.write("\r\n\r\nCandidates:\r\n");
				fileWriter.write("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\r\n");
				
				for (int y = 0; y < 9; y++) {
					for (int i = 0; i < 3; i++) { // 3 rows for each cell
						fileWriter.write('$');
						for (int x = 0; x < 9; x++) {
							char[] c = nonets[x / 3][y / 3].getCandidates(x - ((x / 3) * 3), y - ((y / 3) * 3));
							if (c == null) {
								c = new char[1];
								c[0] = 0;
							}
							String str = new String(c);
							for (int j = 0; j < 3; j++) { // 3 columns for each cell
								char thisChar = Integer.toString( (i * 3) + j + 1 ).charAt(0);
								int chrPos = str.indexOf(thisChar);
								fileWriter.write(chrPos == -1 ? ' ' : thisChar);
							}
							fileWriter.write((x - 2) % 3 == 0 ? '$' : '|');
						}
						fileWriter.write("\r\n");
					}
					if ((y - 2) % 3 == 0 && y < 8)
						fileWriter.write("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\r\n");
					else if (y < 8)
						fileWriter.write("$---+---+---$---+---+---$---+---+---$\r\n");
				}
				fileWriter.write("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\r\n");
			}
		}
	}

	public String getMissingValuesInColumn(int x) {
		String returnVal = "123456789";
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				char thisChar = nonets[x / 3][i].getValue(x - ((x / 3) * 3),j);
				if (thisChar != 0) {
					int chrPos = returnVal.indexOf(thisChar);
					returnVal = returnVal.substring(0, chrPos) + returnVal.substring(chrPos + 1, returnVal.length());
				}
			}
		}
		
		return returnVal;
	}
	
	public String getMissingValuesInRow(int y) {
		String returnVal = "123456789";
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				char thisChar = nonets[i][y / 3].getValue(j,y - ((y / 3) * 3));
				if (thisChar != 0) {
					int chrPos = returnVal.indexOf(thisChar);
					returnVal = returnVal.substring(0, chrPos) + returnVal.substring(chrPos + 1, returnVal.length());
				}
			}
		}
		
		return returnVal;
	}
	
	/**
	 * Checks if that number appears in column X
	 * @param x
	 * @param y
	 * @param c
	 * @return
	 */
	public boolean hasValueInColumn(int x, int y, char c) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (nonets[x / 3][i].getValue(x - ((x / 3) * 3),j) == c) return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if that number appears in row Y
	 * @param x
	 * @param y
	 * @param c
	 * @return
	 */
	public boolean hasValueInRow(int x, int y, char c) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (nonets[i][y / 3].getValue(j, y - ((y / 3) * 3)) == c) return true;
			}
		}
		
		return false;
	}
	
	public boolean hasValueInNonet(int nx, int ny, char c) {
		return nonets[nx][ny].hasValue(c);
	}
	
	public boolean hasValue(int x, int y) {
		return nonets[x / 3][y / 3].getValue(x - ((x / 3) * 3), y - ((y / 3) * 3)) != 0;
	}
	
	public char getValue(int x, int y) {
		return nonets[x / 3][y / 3].getValue(x - ((x / 3) * 3), y - ((y / 3) * 3));
	}
	
	public char[] getCandidates(int x, int y) {
		return nonets[x / 3][y / 3].getCandidates(x - ((x / 3) * 3), y - ((y / 3) * 3));
	}
	
	public void setValue(int x, int y, char c) {
		nonets[(x / 3)][(y / 3)].setValue(x - ((x / 3) * 3), y - ((y / 3) * 3), c);
	}
	
	public void removeCandidate(int x, int y, char c) {
		nonets[(x / 3)][(y / 3)].removeCandidate(x - ((x / 3) * 3), y - ((y / 3) * 3),c);
	}

	// Remove conflicting candidates assuming this is the value's correct location
	public void removeCandidates(int x, int y, char c) {
		// remove all candidates from that location
		nonets[(x / 3)][(y / 3)].removeCandidates(x - ((x / 3) * 3), y - ((y / 3) * 3));
		
		// remove candidates from the nonet
		nonets[(x / 3)][(y / 3)].removeCandidate(c);
		
		// remove candidates from the row
		for (int i = 0; i < 3; i++) {
			nonets[i][(y / 3)].removeCandidate(y - ((y / 3) * 3), c, true);
		}
		
		// remove candidates from the column
		for (int i = 0; i < 3; i++) {
			nonets[(x / 3)][i].removeCandidate(x - ((x / 3) * 3), c, false);
		}
	}
	
	public static ArrayList<Coords> makeCellSet(Constants.SetType setType, int index) {
		ArrayList<Coords> returnVal = new ArrayList<Coords>();
		
		switch (setType) {
			case NONET:
				int x = index % 3;
				int y = index / 3;
				for (int nx = 0; nx < 3; nx++) {
					for (int ny = 0; ny < 3; ny++) {
						returnVal.add(new Coords((x * 3) + nx,(y * 3) + ny));
					}
				}
				break;
			case ROW:
				for (int rx = 0; rx < 9; rx++) {
					returnVal.add(new Coords(rx,index));
				}
				break;
			case COLUMN:
				for (int cy = 0; cy < 9; cy++) {
					returnVal.add(new Coords(index,cy));
				}
				break;
			default:
				return returnVal;
		}
		
		return returnVal;
	}
	
	public void print() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(getValue(j,i) == 0 ? 'X' : getValue(j,i));
			}
			System.out.print('\n');
		}
	}
	
	public void validate(String lastStep, int lastFound) {
		for (Constants.SetType setType : Constants.SetType.values()) {
			for (int i = 0; i < 9; i++) {
				ArrayList<Coords> cellSet = makeCellSet(setType, i);
				String allNumbers = "123456789";
				for (Coords cell : cellSet) {
					char c = getValue(cell.getX(),cell.getY());
					if (c == 0) continue;
					int chrPos = allNumbers.indexOf(c);
					if (chrPos == -1) {
						System.out.println("[VALIDATE] "+setType.toString()+" "+i+" has 2 values of "+c);
						System.out.println("[DEBUG] Last step performed was \""+lastStep+"\", which found "+lastFound+" values.");
						try {
							writeToFile("validate.err");
						} catch (IOException e) {
						}
						System.exit(0);						
					}
					allNumbers = allNumbers.substring(0,chrPos) + allNumbers.substring(chrPos + 1,allNumbers.length());
				}
			}
		}
	}
	
	public boolean isSolved() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (nonets[i][j].getEmptyCells().size() > 0) return false;
			}
		}
		return true;
	}
}
