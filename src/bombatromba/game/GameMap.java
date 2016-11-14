package bombatromba.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import bombatromba.graphics.assets.GameTileType;
import bombatromba.graphics.layout.LayoutManager;

/*
 * Clasa care reprezinta harta logica a jocului
 * exitnde Thread pentru a actualiza blocurile vizuale pe un fix de executie paralel
 */
public class GameMap extends Thread{
	
	/*
	 * Private properties
	 */
	private GameMapNode[][] _map;					// the internal map
	private HashMap<Position, GameMapNode> _nodes;	// the same internal map as hashmap (the key is the position)
	private int _rows, _cols;						// the size of the map
	private boolean _initialized;
	private LayoutManager _view;					// the layout manager (i.e. componenta grafica)
	
	// coada de scihmbari care trebuie facute grafic (adica coada de noduri care trebuie actualizate)
	// cand o casuta se schimba, se va auto adauga in aceasta coada
	private volatile Queue<GameMapNode> _updateQueue;
	
	/*
	 * Private methods
	 */
	// constructor privat pentru ca folosit un factory
	private GameMap() {
		this._map = null;
		this._rows = this._cols = 0;
		this._view = null;
		this._nodes = null;
		this._initialized = false;
	}
	
	// metoda de initializare privat
	private boolean _initialize(int rows, int cols, int spaceDistribution) {
		
		this._rows = rows;
		this._cols = cols;
		this._map = new GameMapNode[rows + 2][cols + 2];
		this._updateQueue = new LinkedList<GameMapNode>();
		this._nodes = new HashMap<Position, GameMapNode>(rows * cols);
		
		// pentru fiecare nod de pe harta, creaza nodul
		for (int i = 0; i < this._rows + 2; i++) {
			for (int j = 0; j < this._cols + 2; j++) {
				this._map[i][j] = new GameMapNode(i, j);
			}
		}
		
		// din nou pentru fiecare nod
		for (int i = 0; i < this._rows + 2; i++) {
			for (int j = 0; j < this._cols + 2; j++) {
				
				// pune nodul in hashmap
				this._nodes.put(new Position(i, j), this._map[i][j]);
				
				// initializeeaza nodul
				if (!this._map[i][j].initialize(this._updateQueue)) {
					return false;
				}
				
				// creeaza un random generator
				Random rand = new Random();
				
				// daca nodurile snut din doua in doua atunci pune automat obstacole (ca la bomberman)
				if (i != 0 && i != this._rows + 1 && j != 0 && j != this._cols + 1)
					// daca nu sunt atunci
					if (i % 2 != 0 || j % 2 != 0)
						// pune caramida sau spatiu liber conform distributiei din setari
						if (rand.nextInt() % spaceDistribution == 0)
							this._map[i][j].setType(GameTileType.BRICK);
						else
							this._map[i][j].setType(GameTileType.EMPTY);
			}
		}
		
		// porneste thread-ul pentru actualizarea interfetei grafica
		this.start();
		this._initialized = true;
		return true;
	}
	
	/*
	 * Public methods
	 */
	
	// metoda statica de tip factory care genereaza harti
	public static GameMap generateMap(int rows, int cols, int spaceDistribution) {
		
		// creeaza o noua harta si o initializeaza
		GameMap gameMap = new GameMap();
		if (!gameMap._initialize(rows, cols, spaceDistribution)) {
			System.out.println("error initializing map");
			return null;	
		}
		
		return gameMap;
	}
	
	public boolean addLayoutManager(LayoutManager layout) {
		if (layout == null || !this._initialized)
			return false;
		
		this._view = layout;
		return true;
	}
	
	// actualizeaza harta prin actualiarea grafica a fiecarui nod
	public boolean updateMap() {
		if (!this._initialized || this._view == null)
			return false;
		
		for (int i = 0; i < this._rows + 2; i++)
			for (int j = 0; j < this._cols + 2; j++) {
				if (this._view.updateTile(i, j, this._map[i][j].getType()) == null) {
					System.out.println("error updating (" + i + "," + j + ") of type " + this._map[i][j].getType());
					return false;
				}
			}
		
		return true;
	}
	
	// metoda care intoarce un nod sau null daca nu exista
	public GameMapNode getNode(int x, int y) {
		if (!this._initialized)
			return null;
		
		if (x < 0 || x >= this._rows + 2)
			return null;
		
		if (y < 0 || y >= this._cols + 2)
			return null;
		
		return this._map[x][y];
	}
	
	
	// metoda care este executata pe thread-ul paralel pentru a actualiza interfata grafica
	public void run() {
		// ruleaza pentru totdeauna
		while (true) {
			// daca nu e nimic de actualizat, continua
			if (this._updateQueue.isEmpty())
				continue;
			
			// ia primul nod din coada de actualizari
			GameMapNode current = this._updateQueue.remove();
			
			// calculeaza pozitia lui
			Position currentPosition = current.getPosition();
			System.out.println("updating " + currentPosition.x + "," + currentPosition.y);
			
			// actualizeaza atat vizual cat si logic
			current.setType(this._view.updateTile(currentPosition.x, currentPosition.y, current.getType()));
		}
	}
	
	// metoda care calcueaza care este urmatorul nod in functie de un nod curent si o micare
	public GameMapNode getNextNode(Position pos, MoveDirection dir) {
		
		if (dir == null)
			return this._nodes.get(pos);
		
		switch (dir) {
		case DOWN:
			pos.x += 1;
			break;
		case LEFT:
			pos.y -= 1;
			break;
		case RIGHT:
			pos.y += 1;
			break;
		case UP:
			pos.x -= 1;
			break;
		default:
			break;
		}
		
		//System.out.println("action " + dir + " changed " + old + " to " + pos);
		
		return this._nodes.get(pos);
	}
}
