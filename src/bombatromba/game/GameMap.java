package bombatromba.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import bombatromba.graphics.assets.GameTileType;
import bombatromba.graphics.layout.LayoutManager;

public class GameMap extends Thread{
	
	/*
	 * Private properties
	 */
	private GameMapNode[][] _map;
	private HashMap<Position, GameMapNode> _nodes;
	private int _rows, _cols;
	private boolean _initialized;
	private LayoutManager _view;
	
	private volatile Queue<GameMapNode> _updateQueue;
	
	/*
	 * Private methods
	 */
	private GameMap() {
		this._map = null;
		this._rows = this._cols = 0;
		this._view = null;
		this._nodes = null;
		this._initialized = false;
	}
	
	private boolean _initialize(int rows, int cols, int spaceDistribution) {
		
		this._rows = rows;
		this._cols = cols;
		this._map = new GameMapNode[rows + 2][cols + 2];
		this._updateQueue = new LinkedList<GameMapNode>();
		this._nodes = new HashMap<Position, GameMapNode>(rows * cols);
		
		for (int i = 0; i < this._rows + 2; i++) {
			for (int j = 0; j < this._cols + 2; j++) {
				this._map[i][j] = new GameMapNode(i, j);
			}
		}
		
		for (int i = 0; i < this._rows + 2; i++) {
			for (int j = 0; j < this._cols + 2; j++) {
				
//				if (i - 1 > 0)
//					this._map[i][j].getNeighbours().add(this._map[i - 1][j]);
//				
//				if (i + 1 < this._rows + 1)
//					this._map[i][j].getNeighbours().add(this._map[i + 1][j]);
//				
//				if (j - 1 > 0)
//					this._map[i][j].getNeighbours().add(this._map[i][j - 1]);
//				
//				if (j + 1 < this._cols + 1)
//					this._map[i][j].getNeighbours().add(this._map[i][j + 1]);
				
				this._map[i][j].initialize(this._updateQueue);
				this._nodes.put(new Position(i, j), this._map[i][j]);
				if (!this._map[i][j].initialize(this._updateQueue)) {
					return false;
				}
				
				Random rand = new Random();
				
				if (i != 0 && i != this._rows + 1 && j != 0 && j != this._cols + 1)
					if (i % 2 != 0 || j % 2 != 0)
						if (rand.nextInt() % spaceDistribution == 0)
							this._map[i][j].setType(GameTileType.BRICK);
						else
							this._map[i][j].setType(GameTileType.EMPTY);
			}
		}
		
		this.start();
		this._initialized = true;
		return true;
	}
	
	/*
	 * Public methods
	 */
	public static GameMap generateMap(int rows, int cols, int spaceDistribution) {
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
	
	public GameMapNode getNode(int x, int y) {
		if (!this._initialized)
			return null;
		
		if (x < 0 || x >= this._rows + 2)
			return null;
		
		if (y < 0 || y >= this._cols + 2)
			return null;
		
		return this._map[x][y];
	}
	
	public void run() {
		while (true) {
			if (this._updateQueue.isEmpty())
				continue;
			
			GameMapNode current = this._updateQueue.remove();
			Position currentPosition = current.getPosition();
			System.out.println("updating " + currentPosition.x + "," + currentPosition.y);
			
			current.setType(this._view.updateTile(currentPosition.x, currentPosition.y, current.getType()));
		}
	}
	
	public GameMapNode getNextNode(Position pos, MoveDirection dir) {
		
		Position old = new Position(pos.x, pos.y);
		
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
