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
