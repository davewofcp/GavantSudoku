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
