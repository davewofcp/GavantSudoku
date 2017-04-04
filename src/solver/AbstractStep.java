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

public abstract class AbstractStep {
	protected Grid grid;
	protected boolean enabled = true;
	
	public abstract int perform();
	public abstract String getName();
	
	public AbstractStep() {}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
}
