package bombatromba.game.ai;

import bombatromba.game.GameMapNode;
import bombatromba.game.MoveDirection;

public interface SearchAlgorithm {
	public MoveDirection nextStepTowards(GameMapNode destination);
}
