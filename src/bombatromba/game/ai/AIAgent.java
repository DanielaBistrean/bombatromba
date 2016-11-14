package bombatromba.game.ai;

import java.util.TimerTask;

import bombatromba.game.GameCharacter;
import bombatromba.game.GameMap;
import bombatromba.game.GameMapNode;
import bombatromba.game.MoveDirection;

public class AIAgent extends TimerTask {

	private SearchAlgorithm _algorithm;
	private GameCharacter _character;
	private GameMap _map;

	public AIAgent(SearchAlgorithm alg, GameCharacter c, GameMap m) {
		this._algorithm = alg;
		this._character = c;
		this._map = m;
	}
	
	@Override
	public void run() {
		System.out.println("computing.......");
		MoveDirection d = this._algorithm.nextStepTowards(this._character.getTarget().getNode());
		System.out.println("direction: " + d);
		GameMapNode next = this._map.getNextNode(this._character.getPosition(), d);
		if (next.isAccesible()) {
			this._character.move(next);
			System.out.println("new position: " + this._character.getNode().getPosition());
		} else {
			System.out.println("not accesible");
		}
	}

}
