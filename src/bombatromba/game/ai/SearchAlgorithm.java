package bombatromba.game.ai;

import bombatromba.game.GameMapNode;
import bombatromba.game.MoveDirection;

/*
 * interfata pentru orice algoritm pentru acest joc
 */
public interface SearchAlgorithm {
	public MoveDirection nextStepTowards(GameMapNode destination);
}
