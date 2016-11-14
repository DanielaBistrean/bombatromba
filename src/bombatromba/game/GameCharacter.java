package bombatromba.game;

import java.util.Timer;

import bombatromba.game.ai.AIAgent;
import bombatromba.graphics.assets.GameTileType;

/*
 * Clasa ce reprezinta un caracter (jucator sau adversar) al jocului
 */
public class GameCharacter {
	
	/*
	 * Private properties
	 */
	private GameMapNode _position;		// pozitia pe suprafata joculuis
	private GameCharacterType _type;	// tipul caracterului (jucator / adversar)
	private GameCharacter _target;		// tinta caracterului in caz ca e adversar
	private AIAgent _agent;				// agentul de inteligenta artificiala a caracterului
	private Timer _timer;				// timer pentru agentul inteligent
	
	/*
	 * Public methods
	 */
	public GameCharacter(GameCharacterType type) {
		this._position = null;
		this._type = type;
	}
	
	// metoda care pozitioneaza initial caracterul in spatiul vizual
	public boolean place(GameMapNode position) {
		
		// modifica pozitia interna
		this._position = position;
		
		// in functie de tipul caracterului, deseneaza vizual un jucator sau un adversar
		switch (this._type) {
		case ENEMY:
			return this._position.updateType(GameTileType.ENEMY);
		case PLAYER:
			return this._position.updateType(GameTileType.PLAYER);
		default:
			return false;
		
		}
	}
	
	// metoda care muta un jucator intr-o noua pozitie
	// identica cu cea de mai sus doar ca in plus atunci cand caracterul paraseste vechea pozitie,
	// starea acelui bloc vizual va fi intoarsa la cea de dinainte (cu alte cuvinte daca un bloc era liber
	// iar apoi a fost ocupat de jucator, apoi jucatorul se departeaza de bloc, acel bloc trebuie din nou
	// sa devina liber
	public boolean move(GameMapNode position) {
		this._position.restoreTile();
		this._position = position;
		System.out.println("new position is " + this._position.getPosition());
		switch (this._type) {
		case ENEMY:
			return this._position.updateType(GameTileType.ENEMY);
		case PLAYER:
			return this._position.updateType(GameTileType.PLAYER);
		default:
			return false;
		
		}
	}

	public GameCharacterType getType() {
		return this._type;
	}
	
	public GameMapNode getNode() {
		return this._position;
	}
	
	public Position getPosition() {
		return this._position.getPosition();
	}
	
	public void setTarget(GameCharacter player) {
		this._target = player;
	}

	public GameCharacter getTarget() {
		return this._target;
	}
	
	// functie care ataseaza un agent unui caracter si porneste un timer pentru a aplica deciziile
	// agentului inteligent
	public void attachAgent(AIAgent agent) {
		this._agent = agent;
		this._timer = new Timer();
		this._timer.scheduleAtFixedRate(this._agent, 1000, 1000);
	}
}
