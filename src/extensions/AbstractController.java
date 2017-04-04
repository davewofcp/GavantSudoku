package extensions;

import model.Grid;
import solver.AbstractStep;

public abstract class AbstractController {
	public abstract void setGrid(Grid grid);
	public abstract void pause();
}
