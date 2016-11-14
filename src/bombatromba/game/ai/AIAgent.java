package bombatromba.game.ai;

import java.util.TimerTask;

import bombatromba.game.GameCharacter;
import bombatromba.game.GameMap;
import bombatromba.game.GameMapNode;
import bombatromba.game.MoveDirection;

/*
 * clasa care reprezinta un agent inteligent
 */
public class AIAgent extends TimerTask {

	private SearchAlgorithm _algorithm; 	// algoritmul atasat
	private GameCharacter _character;		// caracterul atasat
	private GameMap _map;					// harta jocului

	public AIAgent(SearchAlgorithm alg, GameCharacter c, GameMap m) {
		this._algorithm = alg;
		this._character = c;
		this._map = m;
	}
	
	// functie care calculeaza urmatoarea miscare a agentului
	@Override
	public void run() {
		// rulam algoritmul si luam urmatoarea miscare catre tinta
		MoveDirection d = this._algorithm.nextStepTowards(this._character.getTarget().getNode());
		
		// luam nodul aferent miscarii
		GameMapNode next = this._map.getNextNode(this._character.getPosition(), d);
		
		// daca nodul e accesibil atunci facem miscarea
		if (next.isAccesible()) {
			this._character.move(next);
			System.out.println("new position: " + this._character.getNode().getPosition());
		} else {
			System.out.println("not accesible");
		}
	}

}
