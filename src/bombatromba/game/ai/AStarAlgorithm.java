package bombatromba.game.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import bombatromba.config.GameConfig;
import bombatromba.game.GameCharacter;
import bombatromba.game.GameMap;
import bombatromba.game.GameMapNode;
import bombatromba.game.MoveDirection;
import bombatromba.game.Position;
import bombatromba.graphics.assets.GameTileType;

public class AStarAlgorithm extends Thread implements SearchAlgorithm {

	private class Score {
		public float f;
		public float g;
		public float h;
		
		public Score() {
			this.g = this.f = this.h = 0;
		}
		
		public Score(float f, float g, float h) {
			this.g = g;
			this.h = h;
			this.f = f;
		}
	}
	
	private PriorityQueue<GameMapNode> _open;
	private ArrayList<GameMapNode> _closed;
	private Score[][] _scores;
	private GameMapNode _next;
	private GameMap _map;
	
	private GameCharacter _current;
		
	public AStarAlgorithm(GameCharacter character, GameMap map) {
		this._current = character;
		this._scores = new Score[GameConfig.ROWS][GameConfig.COLS];
		this._map = map;
		this._open = new PriorityQueue<GameMapNode>(new Comparator<GameMapNode>() {

			@Override
			public int compare(GameMapNode o1, GameMapNode o2) {
				return (int) (o1.f - o2.f);
			}
			
		});
		this._closed = new ArrayList<GameMapNode>();
	}
	
	@Override
	public void run()
	{
		
	}
	
	private void _astar(GameMapNode destination) {
		
		int idest = destination.getPosition().x;
		int jdest = destination.getPosition().y;
		
		for (int i = 0; i < GameConfig.ROWS + 2; i++)
			for (int j = 0; j < GameConfig.COLS + 2; j++) {
				this._map.getNode(i, j).f = (float) Math.sqrt((idest - i) * (idest - i) + (jdest - j) * (jdest - j));
			}
		
		this._closed.clear();
		this._open.clear();
		
		System.out.println("current: " + this._current.getPosition());
		this._open.add(this._current.getNode());
		
		GameMapNode current = null;
		while (!this._open.isEmpty()) {
			GameMapNode next = this._open.remove();
			current = next;
			
			if (!current.isAccesible())
				continue;
		
			//System.out.println("selected " + current.getPosition() + " (" + current.getNeighbours().size() + " neighb)");
			
			Position p = current.getPosition();
			GameMapNode tmp = this._map.getNode(p.x, p.y + 1);
			if (tmp != null)
				current.getNeighbours().add(tmp);
			
			tmp = this._map.getNode(p.x - 1, p.y);
			if (tmp != null)
				current.getNeighbours().add(tmp);
			
			tmp = this._map.getNode(p.x + 1, p.y);
			if (tmp != null)
				current.getNeighbours().add(tmp);
			
			tmp = this._map.getNode(p.x, p.y - 1);
			if (tmp != null)
				current.getNeighbours().add(tmp);
			
			for (GameMapNode n : current.getNeighbours()) {
				if (n == destination) {
					System.out.println("FOUND!!!");
					n.parent = current;
					this._open.clear();
					break;
				}
				
				//System.out.println("checking neighbour " + n.getPosition());
				
				if (!n.isAccesible())
					continue;
				
				float new_f = current.g + 0.5f + n.h;
				
				if (this._open.contains(n) && new_f > current.f)
					continue;
				
				if (this._closed.contains(n) && new_f > current.f)
					continue;
				
				n.f = new_f;
				n.g = current.g + 0.5f;
				n.parent = current;
				
				this._open.add(n);
			}
			
			this._closed.add(current);
		}
		
		GameMapNode p = destination;
		while (p.parent != null && this._current.getNode() != p.parent) {
//			if (!p.updateType(GameTileType.EXPLOSION))
//				System.out.println("error setting type");
//			System.out.println("parent of " + p.getPosition() + " is " + p.parent.getPosition());
			p = p.parent;
		}
		
		this._next = p;
	}
	
	@Override
	public MoveDirection nextStepTowards(GameMapNode destination) {
		
		System.out.println("==================================================");
		this._next = null;
		this._astar(destination);
		
		System.out.println("next: " + this._next.getPosition());
		if (this._current.getNode().getNeighbours().contains(this._next)) {
			int cx = this._current.getPosition().x;
			int cy = this._current.getPosition().y;
			
			int nx = this._next.getPosition().x;
			int ny = this._next.getPosition().y;
			
			//System.out.println(this._next.getPosition());
			
			if (cy == ny) {
				if (cx > nx)
					return MoveDirection.UP;
				else
					return MoveDirection.DOWN;
			} else {
				if (cy > ny)
					return MoveDirection.LEFT;
				else
					return MoveDirection.RIGHT;
			}
		}
		
		return null;
	}

}