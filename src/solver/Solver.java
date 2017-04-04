package solver;

import java.util.ArrayList;

import extensions.AbstractController;
import extensions.AbstractGui;
import extensions.ExtensionHandler;
import model.Grid;
import solver.steps.FindHiddenSingles;
import solver.steps.FindNakedPairs;
import solver.steps.FindNakedTriples;
import solver.steps.FindSolvedCells;

public class Solver {
	private Grid grid;
	private boolean solved = false;
	private int counter = 0;
	
	private PlaceCandidates placeCandidates;
	
	private ArrayList<AbstractStep> steps;
	private FindSolvedCells findSolvedCells;
	private FindHiddenSingles findHiddenSingles;
	private FindNakedPairs findNakedPairs;
	private FindNakedTriples findNakedTriples;
	
	private ArrayList<AbstractController> controllers;
	private ArrayList<AbstractGui> guis;
	private ArrayList<AbstractStep> extSteps;
	
	public Solver(Grid grid) {
		this.grid = grid;
		this.steps = new ArrayList<AbstractStep>();
		
		this.placeCandidates = new PlaceCandidates(grid);

		this.findSolvedCells = new FindSolvedCells(grid);
		this.findHiddenSingles = new FindHiddenSingles(grid);
		this.findNakedPairs = new FindNakedPairs(grid);
		this.findNakedTriples = new FindNakedTriples(grid);
		steps.add(findSolvedCells);
		steps.add(findHiddenSingles);
		steps.add(findNakedPairs);
		steps.add(findNakedTriples);
		
		controllers = ExtensionHandler.loadControllers(grid);
		guis = ExtensionHandler.loadGuis(grid);
		extSteps = ExtensionHandler.loadSteps(grid);
	}
	
	public ArrayList<AbstractStep> getSteps() {
		return steps;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public int solve() {
		// Place candidates
		placeCandidates.place();
		
		int totalFound = 0;
		String lastStep = "Init";
		int lastFound = 0;
		
		main:
		while (true) {
			grid.validate(lastStep,lastFound);
			
			ExtensionHandler.refreshAll(guis);
			ExtensionHandler.pauseAll(controllers);
			
			if (grid.isSolved()) {
				solved = true;
				break;
			}
			
			for (AbstractStep step : steps) {
				if (!step.isEnabled()) continue;
				if ((lastFound = step.perform()) > 0) {
					lastStep = step.getName();
					this.counter++;
					totalFound += lastFound;
					ExtensionHandler.refreshAll(guis);
					ExtensionHandler.pauseAll(controllers);
					continue main;
				}
			}
			
			for (AbstractStep step : extSteps) {
				if (!step.isEnabled()) continue;
				if ((lastFound = step.perform()) > 0) {
					lastStep = step.getName();
					this.counter++;
					ExtensionHandler.refreshAll(guis);
					ExtensionHandler.pauseAll(controllers);
					continue main;
				}
			}

			break;
		}

		return totalFound;
	}
}
