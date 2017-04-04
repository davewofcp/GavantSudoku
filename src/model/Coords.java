package model;

import java.util.ArrayList;

public class Coords {
	private int x;
	private int y;
	
	public Coords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public static boolean inArray(int x, int y, Coords[] arr) {
		if (arr == null) return false;
		for (Coords c : arr) {
			if (c.getX() == x && c.getY() == y) return true;
		}
		return false;
	}
	
	public static boolean inArray(int x, int y, ArrayList<Coords> arr) {
		if (arr == null) return false;
		for (Coords c : arr) {
			if (c.getX() == x && c.getY() == y) return true;
		}
		return false;
	}
	
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Coords)) return false;
		Coords c = (Coords)o;
		if (c.getX() == x && c.getY() == y) return true;
		return false;
	}
}
