package bombatromba.game;

import java.util.ArrayList;
import java.util.Queue;

import bombatromba.graphics.assets.GameTileType;

public class GameMapNode {
	
	/*
	 * Private properties
	 */
	private ArrayList<GameMapNode> _neighbours;		// lista de vecini
	private Queue<GameMapNode> _updateList;			// referina la coada de actualizari
	private boolean _initialized;
	private GameTileType _type;
	private int _x, _y;
	
	// pentru a star
	public float f, g, h;
	public GameMapNode parent;
	
	/*
	 * Public methods
	 */
	public GameMapNode(int i, int j) {
		this._neighbours = null;
		this._updateList = null;
		this._initialized = false;
		// la inceput orice nod este un obstacol
		this._type = GameTileType.OBSTACLE;
		this._x = i;
		this._y = j;
		
		this.f = this.g = this.h = 0;
	}
	
	// functie care initializeaza nodul pasand referinta la lista de actualizari
	public boolean initialize(Queue<GameMapNode> updateList) {
		this._neighbours = new ArrayList<GameMapNode>();
		this._initialized = true;
		this._updateList = updateList;
		return true;
	}
	
	public ArrayList<GameMapNode> getNeighbours() {
		if (!this._initialized)
			return null;
		
		return this._neighbours;
	}
	
	public GameTileType getType() {
		return this._type;
	}
	
	public boolean setType(GameTileType newType) {
		if (!this._initialized)
			return false;
		
		this._type = newType;
		return true;
	}

	// functie care pregateste actualizarea grafica adaungand pe lista de actualizari nodul curent
	public boolean updateType(GameTileType newType) {
		if (!this._initialized)
			return false;
		
		this._type = newType;
		this._updateList.add(this);
		System.out.println("added " + this._x + "," + this._y + " to queue");
		return true;
	}
	
	public Position getPosition() {
		return new Position(this._x, this._y);
	}
	
	// functie care intoarce daca nodul este accesibil spre vizitare
	public boolean isAccesible() {
		return this._type == GameTileType.EMPTY || this._type == GameTileType.ENEMY || this._type == GameTileType.EXPLOSION;
	}

	// functie care pregateste restaurarea nodului la starea de dinainte de a fi vizitat de un caracter
	// de asemenea adauga nodul in lista de actualizari cu starea "undefined" care este o conventie pentru
	// restaurare
	public boolean restoreTile() {
		if (!this._initialized)
			return false;
		
		this._type = GameTileType.UNDEFINED;
		this._updateList.add(this);
		return true;
	}
}
