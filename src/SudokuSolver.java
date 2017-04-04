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

import java.io.IOException;
import java.util.Scanner;

import model.Grid;
import solver.Solver;

public class SudokuSolver {

	public static void main(String[] args) {
		System.out.print("Please enter the filename of the sudoku file or 'quit' to quit.\n> ");
		Scanner scanner = new Scanner(System.in);
		String filename = scanner.next();
		scanner.close();
		if (filename.equals("quit")) {
			System.exit(0);
		}
		
		int extPos = filename.lastIndexOf('.');
		if (extPos == -1) extPos = filename.length();

		String outputFile = filename.substring(0, extPos) + ".sln.txt";
		String partialOutputFile = filename.substring(0, extPos) + ".prt.txt";

		Grid grid = new Grid();
		try {
			grid.readFromFile(filename);
		} catch (IOException e) {
			System.out.println("There was a problem reading that file:");
			System.out.println(e.getMessage());
			System.exit(0);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		Solver solver = new Solver(grid);
		boolean solved = solver.solve();
		
		if (!solved) {
			System.out.println("Unable to solve that puzzle. Saving partial solution to "+partialOutputFile);
			try {
				grid.writeToFile(partialOutputFile);
			} catch (IOException e) {
				System.out.println("There was a problem saving the partial solution file:");
				System.out.println(e.getMessage());
				System.exit(0);
			}
			System.exit(0);
		} else {
			try {
				grid.writeToFile(outputFile);
			} catch (IOException e) {
				System.out.println("The puzzle was solved but there was a problem saving the solution file:");
				System.out.println(e.getMessage());
				System.exit(0);
			}
			System.out.println("Puzzle solved and saved to "+outputFile);
		}
	}

}
