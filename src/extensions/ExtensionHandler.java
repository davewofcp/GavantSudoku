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

package extensions;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;

import model.Grid;
import solver.AbstractStep;
import solver.Solver;

public class ExtensionHandler {
	private static Solver solver = null;
	
	public static void setSolver(Solver s) {
		solver = s;
	}
	
	public static int getCounter() {
		if (solver == null) return -1;
		return solver.getCounter();
	}
	
	public static ArrayList<AbstractStep> getSteps() {
		if (solver == null) return new ArrayList<AbstractStep>();
		return solver.getSteps();
	}
	
	public static ArrayList<AbstractStep> loadSteps(Grid grid) {
		ArrayList<AbstractStep> returnVal = new ArrayList<AbstractStep>();
		
		URL resource = ClassLoader.getSystemClassLoader().getResource("extensions/steps");
		if (resource != null) {
			File directory = null;
		    try {
		        directory = new File(resource.toURI());
		    } catch (Exception e) {
		        directory = null;
		    }
		    
		    if (directory != null && directory.exists()) {
		    	String[] files = directory.list();
		    	for (int i = 0; i < files.length; i++) {
		    		if (files[i].endsWith(".class")) {
		    			String className = "extensions.steps."+ files[i].substring(0, files[i].length() - 6);
		    			Class<?> thisClass = null;
		    			try {
							thisClass = Class.forName(className);
							Class<? extends AbstractStep> clsAstep = thisClass.asSubclass(AbstractStep.class);
			    			Constructor<? extends AbstractStep> ctor = clsAstep.getConstructor();
			    			AbstractStep astep = (AbstractStep)ctor.newInstance();
			    			astep.setGrid(grid);
			    			returnVal.add(astep);
						} catch (Exception e) {
							continue;
						}
		    		}
		    	}
		    }
		}
		
		return returnVal;
	}
	
	public static ArrayList<AbstractController> loadControllers(Grid grid) {
		ArrayList<AbstractController> returnVal = new ArrayList<AbstractController>();
		
		URL resource = ClassLoader.getSystemClassLoader().getResource("extensions/control");
		if (resource != null) {
			File directory = null;
		    try {
		        directory = new File(resource.toURI());
		    } catch (Exception e) {
		        directory = null;
		    }
		    
		    if (directory != null && directory.exists()) {
		    	String[] files = directory.list();
		    	for (int i = 0; i < files.length; i++) {
		    		if (files[i].endsWith(".class")) {
		    			String className = "extensions.control."+ files[i].substring(0, files[i].length() - 6);
		    			Class<?> thisClass = null;
		    			try {
							thisClass = Class.forName(className);
							Class<? extends AbstractController> clsActl = thisClass.asSubclass(AbstractController.class);
			    			Constructor<? extends AbstractController> ctor = clsActl.getConstructor();
			    			AbstractController actl = (AbstractController)ctor.newInstance();
			    			actl.setGrid(grid);
			    			returnVal.add(actl);
						} catch (Exception e) {
							continue;
						}
		    		}
		    	}
		    }
		}
		
		return returnVal;
	}
	
	public static ArrayList<AbstractGui> loadGuis(Grid grid) {
		ArrayList<AbstractGui> returnVal = new ArrayList<AbstractGui>();
		
		URL resource = ClassLoader.getSystemClassLoader().getResource("extensions/gui");
		if (resource != null) {
			File directory = null;
		    try {
		        directory = new File(resource.toURI());
		    } catch (Exception e) {
		        directory = null;
		    }
		    
		    if (directory != null && directory.exists()) {
		    	String[] files = directory.list();
		    	for (int i = 0; i < files.length; i++) {
		    		if (files[i].endsWith(".class")) {
		    			String className = "extensions.gui."+ files[i].substring(0, files[i].length() - 6);
		    			Class<?> thisClass = null;
		    			try {
							thisClass = Class.forName(className);
							Class<? extends AbstractGui> clsAgui = thisClass.asSubclass(AbstractGui.class);
			    			Constructor<? extends AbstractGui> ctor = clsAgui.getConstructor();
			    			AbstractGui agui = (AbstractGui)ctor.newInstance();
			    			agui.setGrid(grid);
			    			returnVal.add(agui);
						} catch (Exception e) {
							continue;
						}
		    		}
		    	}
		    }
		}
		
		return returnVal;
	}
	
	public static void setGrids(ArrayList<Object> objs, Grid grid) {
		for (Object obj : objs) {
			if (obj instanceof AbstractController)
				((AbstractController)obj).setGrid(grid);
			
			if (obj instanceof AbstractGui)
				((AbstractGui)obj).setGrid(grid);
		}
	}
	
	public static void pauseAll(ArrayList<AbstractController> ctls) {
		for (AbstractController ctl : ctls) {
				ctl.pause();
		}
	}
	
	public static void refreshAll(ArrayList<AbstractGui> guis) {
		for (AbstractGui gui : guis) {
			gui.refresh();
		}
	}
}
